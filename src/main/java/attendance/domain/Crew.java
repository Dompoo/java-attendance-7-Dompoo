package attendance.domain;

import attendance.common.CustomExceptions;
import attendance.common.dto.AttendanceModifyResult;
import attendance.common.dto.AttendanceResult;

import java.time.LocalDate;
import java.time.LocalDateTime;
import java.time.LocalTime;
import java.util.List;

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
	
	public List<Attendance> getAttendances() {
		return attendances;
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
