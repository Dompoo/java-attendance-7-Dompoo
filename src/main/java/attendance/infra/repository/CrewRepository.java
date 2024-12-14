package attendance.infra.repository;

import attendance.domain.Crew;
import attendance.infra.database.FileDatabase;
import attendance.infra.entity.AttendanceEntity;
import attendance.infra.repository.domainConvertor.AttendanceEntityToCrewConvertor;

import java.util.List;

public class CrewRepository {
	
	private final List<Crew> crews;
	
	public CrewRepository(List<Crew> crews) {
		this.crews = crews;
	}
	
	public static CrewRepository from(FileDatabase<AttendanceEntity> attendanceEntityFileDatabase) {
		List<AttendanceEntity> attendanceEntities = attendanceEntityFileDatabase.readAll();
		List<Crew> crews = AttendanceEntityToCrewConvertor.convert(attendanceEntities);
		return new CrewRepository(crews);
	}
}
