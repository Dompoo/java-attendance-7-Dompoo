package attendance.infra.entity;

import attendance.domain.Attendance;
import attendance.infra.DateTimeConvertor;

import java.time.LocalDateTime;

public record AttendanceEntity(
		String nickname,
		LocalDateTime attendanceDateTime
) implements DatabaseEntity {
	public static AttendanceEntity from(String line) {
		String[] lines = line.split(",");
		String nickname = lines[0];
		String attendanceDateTime = lines[1];
		return new AttendanceEntity(nickname, DateTimeConvertor.convertToLocalDateTime(attendanceDateTime));
	}
	
	public Attendance toAttendance() {
		return Attendance.from(attendanceDateTime);
	}
}
