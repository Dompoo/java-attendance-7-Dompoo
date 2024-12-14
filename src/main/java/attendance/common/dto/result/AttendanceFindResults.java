package attendance.common.dto.result;

import attendance.domain.AttendanceInterview;
import attendance.domain.AttendanceStatus;

import java.util.List;
import java.util.Map;

public record AttendanceFindResults(
		String nickname,
		List<AttendanceFindResult> attendanceFindResults,
		Map<AttendanceStatus, Long> attendanceStatusCount,
		AttendanceInterview attendanceInterview
) {
}
