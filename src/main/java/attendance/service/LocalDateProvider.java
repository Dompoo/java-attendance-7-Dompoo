package attendance.service;

import java.time.LocalDate;

public class LocalDateProvider implements DateProvider {
	
	@Override
	public int month() {
		return LocalDate.now().getMonthValue();
	}
	
	@Override
	public int dayOfMonth() {
		return LocalDate.now().getDayOfMonth();
	}
}
