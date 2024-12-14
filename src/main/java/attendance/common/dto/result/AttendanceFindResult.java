package attendance.common.dto.result;

import attendance.domain.AttendanceStatus;

import java.time.LocalDateTime;

public record AttendanceFindResult(
		LocalDateTime attendanceDateTime,
		AttendanceStatus attendanceStatus
) implements Comparable<AttendanceFindResult> {
	
	@Override
	public int compareTo(AttendanceFindResult o) {
		return attendanceDateTime.compareTo(o.attendanceDateTime);
	}
}
