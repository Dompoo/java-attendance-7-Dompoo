package attendance.common.dto;

import attendance.domain.AttendanceStatus;

import java.time.LocalDateTime;

public record AttendanceResult(
		LocalDateTime attendanceDateTime,
		AttendanceStatus attendanceStatus
) {
}
