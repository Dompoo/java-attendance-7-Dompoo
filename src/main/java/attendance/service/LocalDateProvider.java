package attendance.service;

import camp.nextstep.edu.missionutils.DateTimes;

import java.time.LocalDate;

public class LocalDateProvider implements DateProvider {
	
	@Override
	public LocalDate date() {
		return DateTimes.now().toLocalDate();
	}
}
