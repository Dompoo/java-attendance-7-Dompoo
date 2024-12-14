package attendance.io.input;

import attendance.common.CustomExceptions;

import java.util.regex.Pattern;

public class InputValidator {
	
	private static final Pattern NICKNAME_PATTERN = Pattern.compile(".+");
	
	public void validateAttendanceFind(String input) {
		if (!NICKNAME_PATTERN.matcher(input).matches()) {
			throw CustomExceptions.ILLEGAL_FORMAT.get();
		}
 	}
}
