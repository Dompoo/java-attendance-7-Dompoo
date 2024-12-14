package attendance.common.dto.request;

import java.time.LocalTime;

public record AttendanceModifyRequest(
		String nickname,
		int date,
		LocalTime time
) {
}
