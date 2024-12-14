package attendance.common.dto.request;

import java.time.LocalTime;

public record AttendanceModifyRequest(
		int day,
		LocalTime time
) {
}
