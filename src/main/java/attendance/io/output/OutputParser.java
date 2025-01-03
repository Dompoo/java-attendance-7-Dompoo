package attendance.io.output;

import attendance.common.dto.result.AttendanceExpellWarningResult;
import attendance.common.dto.result.AttendanceFindResult;
import attendance.common.dto.result.AttendanceFindResults;
import attendance.common.dto.result.AttendanceModifyResult;
import attendance.domain.AttendanceInterview;
import attendance.domain.AttendanceStatus;
import attendance.domain.KoreanDayOfWeek;

import java.time.LocalDate;
import java.time.LocalDateTime;
import java.util.Collections;
import java.util.EnumMap;
import java.util.List;
import java.util.Map;

public class OutputParser {
	
	public String parseAttendance(LocalDateTime localDateTime, AttendanceStatus attendanceStatus) {
		return "%02d월 %02d일 %s %02d:%02d (%s)\n".formatted(
				localDateTime.getMonthValue(),
				localDateTime.getDayOfMonth(),
				KoreanDayOfWeek.from(localDateTime.getDayOfWeek()).name(),
				localDateTime.toLocalTime().getHour(),
				localDateTime.toLocalTime().getMinute(),
				attendanceStatus.name()
		);
	}
	
	public String parseNoAttendance(LocalDate localDate) {
		return "%02d월 %02d일 %s --:-- (결석)\n".formatted(
				localDate.getMonthValue(),
				localDate.getDayOfMonth(),
				KoreanDayOfWeek.from(localDate.getDayOfWeek()).name()
		);
	}
	
	public String parseAttendanceModify(AttendanceModifyResult attendanceModifyResult) {
		return "%02d월 %02d일 %s %02d:%02d (%s) -> %02d:%02d (%s) 수정 완료!\n".formatted(
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
		stringBuilder.append("이번 달 %s의 출석 기록입니다.\n".formatted(attendanceFindResults.nickname()));
		
		stringBuilder.append(parseAttenanceFindResult(attendanceFindResults.attendanceFindResults()));
		stringBuilder.append(parseAttendanceStatusCount(attendanceFindResults.attendanceStatusCount()));
		stringBuilder.append(parseAttendanceInterview(attendanceFindResults.attendanceInterview()));
		
		return stringBuilder.toString();
	}
	
	private String parseAttenanceFindResult(List<AttendanceFindResult> attendanceFindResults) {
		StringBuilder stringBuilder = new StringBuilder();
		Collections.sort(attendanceFindResults);
		
		List<AttendanceFindResult> distinctResult = attendanceFindResults.stream().distinct().toList();
		for (AttendanceFindResult result : distinctResult) {
			if (result.isCome()) {
				stringBuilder.append(parseAttendance(result.attendanceDateTime(), result.attendanceStatus()));
				continue;
			}
			stringBuilder.append(parseNoAttendance(result.attendanceDateTime().toLocalDate()));
		}
		
		return stringBuilder.toString();
	}
	
	private String parseAttendanceInterview(AttendanceInterview attendanceInterview) {
		if (attendanceInterview.equals(AttendanceInterview.대상자_아님)) {
			return "\n";
		}
		return "%s입니다.\n".formatted(attendanceInterview.getFormattedName());
	}
	
	private String parseAttendanceStatusCount(Map<AttendanceStatus, Long> map) {
		StringBuilder stringBuilder = new StringBuilder();
		stringBuilder.append("\n");
		EnumMap<AttendanceStatus, Long> attendanceStatusCount = new EnumMap<>(map);
		for (Map.Entry<AttendanceStatus, Long> entry : attendanceStatusCount.entrySet()) {
			stringBuilder.append("%s: %d회\n".formatted(entry.getKey().name(), entry.getValue()));
		}
		
		return stringBuilder.toString();
	}
	
	public String parseAttendanceExpellWarnings(List<AttendanceExpellWarningResult> attendanceExpellWarningResults) {
		StringBuilder stringBuilder = new StringBuilder();
		stringBuilder.append("제적 위험자 조회 결과").append("\n");
		List<AttendanceExpellWarningResult> distinctResult = attendanceExpellWarningResults.stream().distinct().toList();
		for (AttendanceExpellWarningResult attendanceExpellWarningResult : distinctResult) {
			stringBuilder.append("- %s: 결석 %d회, 지각 %d회 (%s)".formatted(
					attendanceExpellWarningResult.nickname(),
					attendanceExpellWarningResult.notComeCount(),
					attendanceExpellWarningResult.lateCount(),
					attendanceExpellWarningResult.attendanceInterview().getSimpleName()
			)).append("\n");
		}
		return stringBuilder.toString();
	}
}
