package attendance.infra.repository;

import attendance.common.CustomExceptions;
import attendance.common.dto.AttendanceModifyResult;
import attendance.common.dto.AttendanceResult;
import attendance.domain.Crew;
import attendance.infra.database.FileDatabase;
import attendance.infra.entity.AttendanceEntity;
import attendance.infra.repository.domainConvertor.AttendanceEntityToCrewConvertor;

import java.time.LocalDate;
import java.time.LocalDateTime;
import java.time.LocalTime;
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
	
	public AttendanceResult addAttendanceByNameAndTime(String name, LocalDateTime attendanceDateTime) {
		Crew findCrew = crews.stream()
				.filter(crew -> crew.getName().equals(name))
				.findFirst()
				.orElseThrow(CustomExceptions.CREW_NOT_FOUND::get);
		
		return findCrew.addAttendance(attendanceDateTime);
	}
	
	public AttendanceModifyResult modifyAttendance(String name, LocalDate now, int date, LocalTime time) {
		Crew findCrew = crews.stream()
				.filter(crew -> crew.getName().equals(name))
				.findFirst()
				.orElseThrow(CustomExceptions.CREW_NOT_FOUND::get);
		
		return findCrew.modifyAttendance(now.withDayOfMonth(date), time);
	}
}
