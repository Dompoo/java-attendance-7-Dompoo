package attendance.domain;

import java.util.Map;

public enum AttendanceInterview {
	
	대상자_아님,
	경고_대상자,
	면담_대상자,
	제적_대상자,
	;
	
	public static AttendanceInterview getInterview(Map<AttendanceStatus, Long> attendanceStatusCount) {
		long notComeCount = attendanceStatusCount.get(AttendanceStatus.결석) + (attendanceStatusCount.get(AttendanceStatus.지각) / 3);
		if (notComeCount < 2) {
			return 대상자_아님;
		}
		if (notComeCount < 3) {
			return 경고_대상자;
		}
		if (notComeCount < 5) {
			return 면담_대상자;
		}
		return 제적_대상자;
	}
}
