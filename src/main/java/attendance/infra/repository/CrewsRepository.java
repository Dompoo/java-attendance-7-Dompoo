package attendance.infra.repository;

import attendance.domain.Crew;
import attendance.domain.Crews;
import attendance.infra.database.FileDatabase;
import attendance.infra.domainConvertor.AttendanceEntityToCrewConvertor;
import attendance.infra.entity.AttendanceEntity;

import java.util.List;

public class CrewsRepository {
	
	private final List<Crew> crews;
	
	public CrewsRepository(List<Crew> crews) {
		this.crews = crews;
	}
	
	public static CrewsRepository from(FileDatabase<AttendanceEntity> attendanceEntityFileDatabase) {
		List<AttendanceEntity> attendanceEntities = attendanceEntityFileDatabase.readAll();
		List<Crew> crews = AttendanceEntityToCrewConvertor.convert(attendanceEntities);
		return new CrewsRepository(crews);
	}
	
	public Crews getCrews() {
		return new Crews(crews);
	}
}
