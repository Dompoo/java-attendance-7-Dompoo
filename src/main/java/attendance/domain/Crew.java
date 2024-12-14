package attendance.domain;

import attendance.common.CustomExceptions;
import attendance.common.dto.result.*;

import java.time.LocalDate;
import java.time.LocalDateTime;
import java.time.LocalTime;
import java.util.ArrayList;
import java.util.List;
import java.util.Map;
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
	
	public AttendanceFindResults getAttendanceFindResult() {
		List<AttendanceFindResult> attendanceFindResults = attendances.stream()
				.map(Attendance::toAttendanceFindResult)
				.toList();
		
		Map<AttendanceStatus, Long> attendanceStatusCount = attendanceFindResults.stream()
				.map(AttendanceFindResult::attendanceStatus)
				.collect(Collectors.groupingBy(attendanceStatus -> attendanceStatus, Collectors.counting()));
		
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
	
	public AttendanceExpellWarningResult getAttendanceExpellWaringResult() {
		Map<AttendanceStatus, Long> attendanceStatusCount = attendances.stream()
				.map(Attendance::toAttendanceFindResult)
				.map(AttendanceFindResult::attendanceStatus)
				.collect(Collectors.groupingBy(attendanceStatus -> attendanceStatus, Collectors.counting()));
		
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
