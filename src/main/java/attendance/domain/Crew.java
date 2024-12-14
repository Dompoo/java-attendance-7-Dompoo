package attendance.domain;

import attendance.common.CustomExceptions;
import attendance.common.dto.result.*;

import java.time.DayOfWeek;
import java.time.LocalDate;
import java.time.LocalDateTime;
import java.time.LocalTime;
import java.util.*;
import java.util.stream.Collectors;

public class Crew {
	
	private final String nickname;
	private final List<Attendance> attendances;
	
	public Crew(String nickname, List<Attendance> attendances) {
		this.nickname = nickname;
		this.attendances = new ArrayList<>(attendances);
	}
	
	public String getNickname() {
		return nickname;
	}
	
	public AttendanceFindResults getAttendanceFindResult(LocalDate now) {
		List<AttendanceFindResult> attendanceFindResultsEmut = attendances.stream()
				.map(Attendance::toAttendanceFindResult)
				.toList();
		
		ArrayList<AttendanceFindResult> attendanceFindResults = new ArrayList<>(attendanceFindResultsEmut);
		
		List<LocalDate> validDates = now.withDayOfMonth(1).datesUntil(now)
				.filter(localdate -> localdate.getDayOfWeek() != DayOfWeek.SATURDAY && localdate.getDayOfWeek() != DayOfWeek.SUNDAY)
				.filter(localdate -> !LegalHolidayCalendar.isLegalHoliday(localdate))
				.toList();
		
		Map<LocalDate, AttendanceFindResult> resultMap = attendanceFindResults.stream().collect(Collectors.toMap(
				result -> result.attendanceDateTime().toLocalDate(),
				result -> result
		));
		
		int addedNoComeCount = 0;
		for (LocalDate validDate : validDates) {
			if (resultMap.containsKey(validDate)) {
				AttendanceFindResult result = resultMap.get(validDate);
				attendanceFindResults.add(result);
				continue;
			}
			addedNoComeCount++;
			attendanceFindResults.add(new AttendanceFindResult(validDate.atTime(0, 0, 0), AttendanceStatus.결석, false));
		}
		
		EnumMap<AttendanceStatus, Long> attendanceStatusCount = getAttendanceStatusCount(addedNoComeCount);
		
		return new AttendanceFindResults(nickname, attendanceFindResults, attendanceStatusCount, AttendanceInterview.getInterview(attendanceStatusCount));
	}
	
	public AttendanceResult addAttendance(LocalDateTime localDateTime) {
		boolean isAttendanceAlreadyExist = attendances.stream()
				.anyMatch(attendance -> attendance.isAttendanceDateEquals(localDateTime.toLocalDate()));
		if (isAttendanceAlreadyExist) {
			throw CustomExceptions.ALREADY_ATTEND.get();
		}
		Attendance newAttendance = Attendance.from(localDateTime);
		attendances.add(newAttendance);
		return newAttendance.toAttendanceResult();
	}
	
	private EnumMap<AttendanceStatus, Long> getAttendanceStatusCount(int addedNoComeCount) {
		EnumMap<AttendanceStatus, Long> attendanceStatusCount = new EnumMap<>(AttendanceStatus.class);
		
		Arrays.stream(AttendanceStatus.values())
				.forEach(attendance -> attendanceStatusCount.put(attendance, 0L));
		
		attendances.stream()
				.map(Attendance::toAttendanceFindResult)
				.map(AttendanceFindResult::attendanceStatus)
				.forEach(attendanceStatus -> attendanceStatusCount.put(attendanceStatus, attendanceStatusCount.get(attendanceStatus) + 1));
		
		attendanceStatusCount.put(AttendanceStatus.결석, attendanceStatusCount.get(AttendanceStatus.결석) + addedNoComeCount);
		return attendanceStatusCount;
	}
	
	public AttendanceExpellWarningResult getAttendanceExpellWaringResult() {
		EnumMap<AttendanceStatus, Long> attendanceStatusCount = getAttendanceStatusCount(0);
		
		return new AttendanceExpellWarningResult(nickname, attendanceStatusCount.get(AttendanceStatus.결석), attendanceStatusCount.get(AttendanceStatus.지각), AttendanceInterview.getInterview(attendanceStatusCount));
	}
	
	public AttendanceModifyResult modifyAttendance(LocalDate now, LocalDate attendanceDate, LocalTime attendanceTime) {
		if (now.isBefore(attendanceDate)) {
			throw CustomExceptions.INVALID_MODIFY.get();
		}
		Attendance beforeModifyAttendance = attendances.stream()
				.filter(attendance -> attendance.isAttendanceDateEquals(attendanceDate))
				.findFirst()
				.orElseThrow(CustomExceptions.ATTENDANCE_NOT_FOUND::get);
		
		Attendance modifiedAttendance = beforeModifyAttendance.with(attendanceTime);
		
		attendances.remove(beforeModifyAttendance);
		attendances.add(modifiedAttendance);
		
		return beforeModifyAttendance.toAttendanceModifyResult(modifiedAttendance);
	}
}
