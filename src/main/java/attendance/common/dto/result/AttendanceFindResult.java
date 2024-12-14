package attendance.common.dto.result;

import attendance.domain.AttendanceStatus;

import java.time.LocalDateTime;

public record AttendanceFindResult(
		LocalDateTime attendanceDateTime,
		AttendanceStatus attendanceStatus
) {
}
