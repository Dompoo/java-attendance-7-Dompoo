package attendance.domain;

import java.time.DayOfWeek;
import java.time.LocalDateTime;
import java.time.LocalTime;

public enum AttendanceStatus {
	
	출석,
	지각,
	결석,
	;
	
	public static AttendanceStatus getAttendanceStatus(LocalDateTime attendanceDateTime) {
		LocalDateTime safeTime = getSafeTime(attendanceDateTime);
		int minuteSub = compareMinute(attendanceDateTime, safeTime);
		if (minuteSub < 5) {
			return AttendanceStatus.출석;
		}
		if (minuteSub < 30) {
			return AttendanceStatus.지각;
		}
		return AttendanceStatus.결석;
	}
	
	private static LocalDateTime getSafeTime(LocalDateTime attendanceDateTime) {
		if (attendanceDateTime.getDayOfWeek() == DayOfWeek.MONDAY) {
			return LocalDateTime.of(attendanceDateTime.toLocalDate(), LocalTime.of(13, 0, 0));
		}
		return LocalDateTime.of(attendanceDateTime.toLocalDate(), LocalTime.of(10, 0, 0));
	}
	
	private static int compareMinute(LocalDateTime date1, LocalDateTime date2) {
		int minute1 = date1.getHour() * 60 + date1.getMinute();
		int minute2 = date2.getHour() * 60 + date2.getMinute();
		return minute1 - minute2;
	}
}
