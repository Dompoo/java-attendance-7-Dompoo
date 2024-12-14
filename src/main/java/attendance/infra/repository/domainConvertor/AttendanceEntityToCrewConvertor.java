package attendance.infra.repository.domainConvertor;

import attendance.domain.Attendance;
import attendance.domain.Crew;
import attendance.infra.entity.AttendanceEntity;

import java.util.ArrayList;
import java.util.List;

public class AttendanceEntityToCrewConvertor {
	
	public static List<Crew> convert(List<AttendanceEntity> attendanceEntities) {
		List<String> nickNames = convertToNickNames(attendanceEntities);
		List<Crew> crews = new ArrayList<>();
		for (String nickName : nickNames) {
			List<Attendance> attendances = convertToAttendances(attendanceEntities, nickName);
			crews.add(new Crew(nickName, attendances));
		}
		return crews;
	}
	
	private static List<String> convertToNickNames(List<AttendanceEntity> attendanceEntities) {
		return attendanceEntities.stream()
				.map(AttendanceEntity::nickname)
				.toList();
	}
	
	private static List<Attendance> convertToAttendances(List<AttendanceEntity> attendanceEntities, String nickName) {
		return attendanceEntities.stream()
				.filter(attendanceEntity -> attendanceEntity.nickname().equals(nickName))
				.map(AttendanceEntity::toAttendance)
				.toList();
	}
}
