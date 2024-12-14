package attendance.infra;

import java.time.LocalDate;
import java.time.LocalDateTime;
import java.time.LocalTime;

public class DateTimeConvertor {
	
	//2024-12-13 10:08
	public static LocalDateTime convertToLocalDateTime(String line) {
		String[] lines = line.split(" ");
		String date = lines[0];
		String time = lines[1];
		return LocalDateTime.of(
				convertToLocalDate(date),
				convertToLocalTime(time)
		);
	}
	
	//2024-12-13
	public static LocalDate convertToLocalDate(String line) {
		String[] dates = line.split("-");
		return LocalDate.of(
				Integer.parseInt(dates[0]),
				Integer.parseInt(dates[1]),
				Integer.parseInt(dates[2])
		);
	}
	
	//10:08
	public static LocalTime convertToLocalTime(String line) {
		String[] times = line.split(":");
		return LocalTime.of(
				Integer.parseInt(times[0]),
				Integer.parseInt(times[1]),
				0
		);
	}
}
