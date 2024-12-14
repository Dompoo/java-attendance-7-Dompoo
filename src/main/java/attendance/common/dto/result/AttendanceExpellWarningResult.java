package attendance.common.dto.result;

import attendance.domain.AttendanceInterview;

public record AttendanceExpellWarningResult(
		String nickname,
		long notComeCount,
		long lateCount,
		AttendanceInterview attendanceInterview
) {
}
