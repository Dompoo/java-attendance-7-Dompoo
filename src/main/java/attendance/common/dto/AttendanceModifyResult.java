package attendance.common.dto;

import attendance.domain.AttendanceStatus;

import java.time.LocalDate;
import java.time.LocalTime;

public record AttendanceModifyResult(
		LocalDate attendanceDate,
		LocalTime beforeModifyTime,
		AttendanceStatus beforeModifyStatus,
		LocalTime afterModifyTime,
		AttendanceStatus afterModifyStatus
) {
}
