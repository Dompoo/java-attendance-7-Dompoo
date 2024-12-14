package attendance.common.dto.request;

import java.time.LocalDateTime;

public record AttendanceRequest(
		String nickname,
		LocalDateTime attendanceTime
) {
}
