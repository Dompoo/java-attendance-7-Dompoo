package attendance.common.dto;

import attendance.domain.AttendanceStatus;

import java.time.LocalDateTime;

public record AttendanceFindResult(
		LocalDateTime attendanceDateTime,
		AttendanceStatus attendanceStatus
) {
}
