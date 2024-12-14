package attendance.domain;

import org.junit.jupiter.api.Test;

import java.time.LocalDateTime;

class AttendanceStatusTest {
	
	@Test
	void 출석_결정_테스트() {
	    //given
		AttendanceStatus result = AttendanceStatus.getAttendanceStatus(LocalDateTime.of(2024, 12, 16, 13, 30));
		
		//when
		System.out.println(result);
	    
	    //then
	    
	}
	
}