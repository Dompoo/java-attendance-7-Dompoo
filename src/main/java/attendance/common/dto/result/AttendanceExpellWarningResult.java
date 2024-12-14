package attendance.common.dto.result;

import attendance.domain.AttendanceInterview;

public record AttendanceExpellWarningResult(
		String nickname,
		long notComeCount,
		long lateCount,
		AttendanceInterview attendanceInterview
) implements Comparable<AttendanceExpellWarningResult> {
	
	public int addNotComeCountAndLateCount() {
		return (int) notComeCount + (int) lateCount;
	}
	
	@Override
	public int compareTo(AttendanceExpellWarningResult o) {
		if (attendanceInterview != o.attendanceInterview) {
			return o.attendanceInterview.ordinal() - attendanceInterview.ordinal();
		}
		if (addNotComeCountAndLateCount() != o.addNotComeCountAndLateCount()) {
			return addNotComeCountAndLateCount() - o.addNotComeCountAndLateCount();
		}
		return nickname.compareTo(o.nickname);
	}
}
