package attendance.domain;

import attendance.common.CustomExceptions;
import attendance.common.dto.AttendanceFindResult;
import attendance.common.dto.AttendanceFindResults;
import attendance.common.dto.AttendanceModifyResult;
import attendance.common.dto.AttendanceResult;

import java.time.LocalDate;
import java.time.LocalDateTime;
import java.time.LocalTime;
import java.util.List;
import java.util.Map;
import java.util.stream.Collectors;

public class Crew {
	
	private final String name;
	private final List<Attendance> attendances;
	
	public Crew(String name, List<Attendance> attendances) {
		this.name = name;
		this.attendances = attendances;
	}
	
	public String getName() {
		return name;
	}
	
	public AttendanceFindResults getAttendanceFindResult() {
		List<AttendanceFindResult> attendanceFindResults = attendances.stream()
				.map(Attendance::toAttendanceFindResult)
				.toList();
		
		Map<AttendanceStatus, Long> attendanceStatuseCount = attendanceFindResults.stream()
				.map(AttendanceFindResult::attendanceStatus)
				.collect(Collectors.groupingBy(attendanceStatus ->
						attendanceStatus,
						Collectors.counting()));
		
		return new AttendanceFindResults(attendanceFindResults, attendanceStatuseCount, AttendanceInterview.getInterview(attendanceStatuseCount));
	}
	
	public AttendanceResult addAttendance(LocalDateTime localDateTime) {
		Attendance newAttendance = Attendance.from(localDateTime);
		attendances.add(newAttendance);
		return newAttendance.toAttendanceResult();
	}
	
	public AttendanceModifyResult modifyAttendance(LocalDate attendanceDate, LocalTime time) {
		Attendance beforeModifyAttendance = attendances.stream()
				.filter(attendance -> attendance.isAttendanceDateEquals(attendanceDate))
				.findFirst()
				.orElseThrow(CustomExceptions.ATTENDANCE_NOT_FOUND::get);
		
		Attendance modifiedAttendance = beforeModifyAttendance.with(time);
		
		attendances.remove(beforeModifyAttendance);
		attendances.add(modifiedAttendance);
		
		return beforeModifyAttendance.toAttendanceModifyResult(modifiedAttendance);
	}
}
