package attendance.infra;

import java.time.LocalDateTime;

public class DateTimeConvertor {
	
	public static LocalDateTime convertToLocalDateTime(String line) {
		String[] lines = line.split(" ");
		String date = lines[0];
		String time = lines[1];
		String[] dates = date.split("-");
		String[] times = time.split(":");
		return LocalDateTime.of(
				Integer.parseInt(dates[0]), Integer.parseInt(dates[1]), Integer.parseInt(dates[2]),
				Integer.parseInt(times[0]), Integer.parseInt(times[1]), 0
		);
	}
}
