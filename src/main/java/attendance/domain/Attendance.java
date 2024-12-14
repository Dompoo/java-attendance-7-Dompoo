package attendance.domain;

import attendance.common.dto.AttendanceFindResult;
import attendance.common.dto.AttendanceModifyResult;
import attendance.common.dto.AttendanceResult;

import java.time.LocalDate;
import java.time.LocalDateTime;
import java.time.LocalTime;

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
	
	public AttendanceModifyResult toAttendanceModifyResult(Attendance afterModifyAttendance) {
		return new AttendanceModifyResult(
				this.attendanceDateTime.toLocalDate(),
				this.attendanceDateTime.toLocalTime(),
				this.attendanceStatus,
				afterModifyAttendance.attendanceDateTime.toLocalTime(),
				afterModifyAttendance.attendanceStatus
		);
	}
	
	public AttendanceFindResult toAttendanceFindResult() {
		return new AttendanceFindResult(attendanceDateTime, attendanceStatus);
	}
	
	public boolean isAttendanceDateEquals(LocalDate localDate) {
		return attendanceDateTime.toLocalDate().isEqual(localDate);
	}
	
	public Attendance with(LocalTime time) {
		return Attendance.from(attendanceDateTime.with(time));
	}
}
