package attendance.domain;

import java.time.LocalDate;
import java.util.Arrays;

public enum LegalHolidayCalendar {

	크리스마스(LocalDate.of(2024, 12, 25));
	
	private final LocalDate localDate;
	
	LegalHolidayCalendar(LocalDate localDate) {
		this.localDate = localDate;
	}
	
	public static boolean isLegalHoliday(LocalDate target) {
		return Arrays.stream(LegalHolidayCalendar.values())
				.anyMatch(legalHolidayCalendar -> legalHolidayCalendar.localDate.equals(target));
	}
}
