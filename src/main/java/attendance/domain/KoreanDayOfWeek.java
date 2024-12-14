package attendance.domain;

import attendance.common.CustomExceptions;

import java.time.DayOfWeek;
import java.util.Arrays;

public enum KoreanDayOfWeek {

	월요일(DayOfWeek.MONDAY),
	화요일(DayOfWeek.TUESDAY),
	수요일(DayOfWeek.WEDNESDAY),
	목요일(DayOfWeek.THURSDAY),
	금요일(DayOfWeek.FRIDAY),
	토요일(DayOfWeek.SATURDAY),
	일요일(DayOfWeek.SUNDAY),
	;
	
	private final DayOfWeek dayOfWeek;
	
	KoreanDayOfWeek(DayOfWeek dayOfWeek) {
		this.dayOfWeek = dayOfWeek;
	}
	
	public static KoreanDayOfWeek from(DayOfWeek dayOfWeek) {
		return Arrays.stream(KoreanDayOfWeek.values())
				.filter(koreanDayOfWeek -> koreanDayOfWeek.dayOfWeek.equals(dayOfWeek))
				.findFirst()
				.orElseThrow(CustomExceptions.ILLEGAL_FORMAT::get);
	}
}
