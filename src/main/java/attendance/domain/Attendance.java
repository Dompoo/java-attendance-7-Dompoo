package attendance.domain;

import attendance.common.dto.AttendanceResult;

import java.time.LocalDateTime;

public class Attendance {

	private final LocalDateTime attendanceDateTime;
	private final AttendanceStatus attendanceStatus;
	
	public Attendance(LocalDateTime attendanceDateTime, AttendanceStatus attendanceStatus) {
		this.attendanceDateTime = attendanceDateTime;
		this.attendanceStatus = attendanceStatus;
	}
	
	public static Attendance from(LocalDateTime attendanceDateTime) {
		return new Attendance(attendanceDateTime, AttendanceStatus.getAttendanceStatus(attendanceDateTime));
	}
	
	public AttendanceResult toAttendanceResult() {
		return new AttendanceResult(attendanceDateTime, attendanceStatus);
	}
}
