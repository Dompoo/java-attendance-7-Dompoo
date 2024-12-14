package attendance.domain;

import java.util.List;

public class Crew {
	
	private final String name;
	private final List<Attendance> attendances;
	
	public Crew(String name, List<Attendance> attendances) {
		this.name = name;
		this.attendances = attendances;
	}
}
