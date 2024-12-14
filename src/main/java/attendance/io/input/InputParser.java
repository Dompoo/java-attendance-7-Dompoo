package attendance.io.input;

import attendance.common.dto.request.AttendanceFindRequest;

public class InputParser {
	
	public AttendanceFindRequest parseAttendanceFind(String input) {
		return new AttendanceFindRequest(input.trim());
	}
}
