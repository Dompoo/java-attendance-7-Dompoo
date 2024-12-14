package attendance.domain;

import attendance.common.dto.AttendanceResult;

import java.time.LocalDateTime;
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
}
