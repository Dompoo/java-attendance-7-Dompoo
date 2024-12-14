package attendance.io.output;

import attendance.common.dto.result.AttendanceModifyResult;
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
	
	public String parseAttendanceModify(AttendanceModifyResult attendanceModifyResult) {
		return "%2d월 %2d일 %s %2d:%2d (%s) -> %2d:%2d (%s) 수정 완료!".formatted(
				attendanceModifyResult.attendanceDate().getMonthValue(),
				attendanceModifyResult.attendanceDate().getDayOfMonth(),
				KoreanDayOfWeek.from(attendanceModifyResult.attendanceDate().getDayOfWeek()).name(),
				attendanceModifyResult.beforeModifyTime().getHour(),
				attendanceModifyResult.beforeModifyTime().getMinute(),
				attendanceModifyResult.beforeModifyStatus().name(),
				attendanceModifyResult.afterModifyTime().getHour(),
				attendanceModifyResult.afterModifyTime().getMinute(),
				attendanceModifyResult.afterModifyStatus().name()
		);
	}
}
