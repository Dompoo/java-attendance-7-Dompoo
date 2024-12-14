package attendance.common.dto.request;

import attendance.common.CustomExceptions;

import java.util.Arrays;

public enum AttendanceFeatureCommand {
	
	출석_확인("1"),
	출석_수정("2"),
	크루별_출석_기록_확인("3"),
	제적_위험_확인("4"),
	종료("Q"),
	;
	
	private final String input;
	
	AttendanceFeatureCommand(String input) {
		this.input = input;
	}
	
	public static AttendanceFeatureCommand from(String input) {
		return Arrays.stream(AttendanceFeatureCommand.values())
				.filter(attendanceFeatureCommand -> attendanceFeatureCommand.input.equals(input))
				.findFirst()
				.orElseThrow(CustomExceptions.ILLEGAL_FORMAT::get);
	}
}
