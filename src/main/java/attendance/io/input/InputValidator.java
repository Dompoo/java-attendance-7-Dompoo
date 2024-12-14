package attendance.io.input;

import attendance.common.CustomExceptions;

import java.util.regex.Pattern;

public class InputValidator {
	
	private static final Pattern NICKNAME_PATTERN = Pattern.compile(".+");
	private static final Pattern TIME_PATTERN = Pattern.compile("..:..");
	
	public void validateNickName(String input) {
		if (!NICKNAME_PATTERN.matcher(input).matches()) {
			throw CustomExceptions.ILLEGAL_FORMAT.get();
		}
 	}
	
	public void validateDate(String input) {
		try {
			Integer.parseInt(input);
		} catch (IllegalArgumentException e) {
			throw CustomExceptions.ILLEGAL_FORMAT.get();
		}
	}
	
	public void validateTime(String input) {
		if (!TIME_PATTERN.matcher(input).matches()) {
			throw CustomExceptions.ILLEGAL_FORMAT.get();
		}
	}
}
