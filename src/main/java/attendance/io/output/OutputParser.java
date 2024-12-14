package attendance.io.output;

import attendance.common.dto.result.AttendanceFindResult;
import attendance.common.dto.result.AttendanceFindResults;
import attendance.common.dto.result.AttendanceModifyResult;
import attendance.domain.AttendanceInterview;
import attendance.domain.AttendanceStatus;
import attendance.domain.KoreanDayOfWeek;

import java.time.LocalDateTime;
import java.util.EnumMap;
import java.util.Map;

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
	
	public String parseAttendanceFind(AttendanceFindResults attendanceFindResults) {
		StringBuilder stringBuilder = new StringBuilder();
		stringBuilder.append("이번 달 %s의 출석 기록입니다.".formatted(attendanceFindResults.nickname()));
		for (AttendanceFindResult attendanceFindResult : attendanceFindResults.attendanceFindResults()) {
			stringBuilder.append(parseAttendance(attendanceFindResult.attendanceDateTime(), attendanceFindResult.attendanceStatus())).append("\n");
		}
		
		stringBuilder.append(parseAttendanceStatusCount(attendanceFindResults.attendanceStatusCount()));
		stringBuilder.append(parseAttendanceInterview(attendanceFindResults.attendanceInterview()));
		return stringBuilder.toString();
	}
	
	private String parseAttendanceInterview(AttendanceInterview attendanceInterview) {
		if (attendanceInterview.equals(AttendanceInterview.대상자_아님)) {
			return "";
		}
		return "%s입니다.".formatted(attendanceInterview.getFormattedName());
	}
	
	private String parseAttendanceStatusCount(Map<AttendanceStatus, Long> map) {
		StringBuilder stringBuilder = new StringBuilder();
		EnumMap<AttendanceStatus, Long> attendanceStatusCount = new EnumMap<>(map);
		for (Map.Entry<AttendanceStatus, Long> entry : attendanceStatusCount.entrySet()) {
			stringBuilder.append("%s: %d회".formatted(entry.getKey().name(), entry.getValue())).append("\n");
		}
		
		return stringBuilder.toString();
	}
}
