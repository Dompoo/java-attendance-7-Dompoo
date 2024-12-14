package attendance.io.output;

import attendance.domain.AttendanceStatus;
import attendance.domain.KoreanDayOfWeek;

import java.time.LocalDateTime;

public class OutputParser {
	
	public String parseAttendance(LocalDateTime localDateTime, AttendanceStatus attendanceStatus) {
		return "%2d월 %2d일 %s %2d:%2d (%s)".formatted(
				localDateTime.getMonthValue(),
				localDateTime.getDayOfMonth(),
				KoreanDayOfWeek.from(localDateTime.getDayOfWeek()).name(),
				localDateTime.toLocalTime().getHour(),
				localDateTime.toLocalTime().getMinute(),
				attendanceStatus.name()
		);
	}
}
