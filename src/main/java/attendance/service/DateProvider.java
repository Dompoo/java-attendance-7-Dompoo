package attendance.service;

import java.time.LocalDate;

public interface DateProvider {
	
	int month();
	
	int dayOfMonth();
	
	LocalDate date();
}
