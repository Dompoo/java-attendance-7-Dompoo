package attendance.common.dto.result;

import attendance.domain.AttendanceInterview;

public record AttendanceExpellWarningResult(
		String nickname,
		long notComeCount,
		long lateCount,
		AttendanceInterview attendanceInterview
) implements Comparable<AttendanceExpellWarningResult> {
	
	public int calculateNotComeTime() {
		return (int) notComeCount + 3 * (int) lateCount;
	}
	
	@Override
	public int compareTo(AttendanceExpellWarningResult o) {
		if (attendanceInterview != o.attendanceInterview) {
			return o.attendanceInterview.ordinal() - attendanceInterview.ordinal();
		}
		if (calculateNotComeTime() != o.calculateNotComeTime()) {
			return o.calculateNotComeTime() - calculateNotComeTime();
		}
		return nickname.compareTo(o.nickname);
	}
}
