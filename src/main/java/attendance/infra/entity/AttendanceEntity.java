package attendance.infra.entity;

import attendance.infra.DateTimeConvertor;

import java.time.LocalDateTime;

public class AttendanceEntity implements DatabaseEntity {
	
	private final String nickname;
	private final LocalDateTime attendanceDateTime;
	
	public AttendanceEntity(String nickname, LocalDateTime attendanceDateTime) {
		this.nickname = nickname;
		this.attendanceDateTime = attendanceDateTime;
	}
	
	public static AttendanceEntity from(String line) {
		String[] lines = line.split(",");
		String nickname = lines[0];
		String attendanceDateTime = lines[1];
		return new AttendanceEntity(nickname, DateTimeConvertor.convertToLocalDateTime(attendanceDateTime));
	}
}
