package attendance.io.input;

import attendance.infra.DateTimeConvertor;

import java.time.LocalTime;

public class InputParser {
	
	public String parseNickName(String input) {
		return input.trim();
	}
	
	public int parseDate(String input) {
		return Integer.parseInt(input);
	}
	
	public LocalTime parseTime(String input) {
		return DateTimeConvertor.convertToLocalTime(input);
	}
}
