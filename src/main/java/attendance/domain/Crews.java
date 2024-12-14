package attendance.domain;

import attendance.common.CustomExceptions;
import attendance.common.dto.result.AttendanceExpellWarningResult;
import attendance.common.dto.result.AttendanceFindResults;
import attendance.common.dto.result.AttendanceModifyResult;
import attendance.common.dto.result.AttendanceResult;

import java.time.LocalDate;
import java.time.LocalDateTime;
import java.time.LocalTime;
import java.util.List;

public class Crews {
	
	private final List<Crew> crews;
	
	public Crews(List<Crew> crews) {
		this.crews = crews;
	}
	
	public void validateValidCrew(String nickname) {
		boolean isCrewExist = crews.stream()
				.anyMatch(crew -> crew.getNickname().equals(nickname));
		
		if (!isCrewExist) {
			throw CustomExceptions.CREW_NOT_FOUND.get();
		}
	}
	
	public AttendanceResult addAttendanceByNameAndTime(String nickname, LocalDateTime attendanceDateTime) {
		Crew findCrew = crews.stream()
				.filter(crew -> crew.getNickname().equals(nickname))
				.findFirst()
				.orElseThrow(CustomExceptions.CREW_NOT_FOUND::get);
		
		return findCrew.addAttendance(attendanceDateTime);
	}
	
	public AttendanceModifyResult modifyAttendance(String nickname, LocalDate now, int requestDay, LocalTime time) {
		Crew findCrew = crews.stream()
				.filter(crew -> crew.getNickname().equals(nickname))
				.findFirst()
				.orElseThrow(CustomExceptions.CREW_NOT_FOUND::get);
		
		return findCrew.modifyAttendance(now, now.withDayOfMonth(requestDay), time);
	}
	
	public AttendanceFindResults findAttendancesByName(String nickname, LocalDate now) {
		Crew findCrew = crews.stream()
				.filter(crew -> crew.getNickname().equals(nickname))
				.findFirst()
				.orElseThrow(CustomExceptions.CREW_NOT_FOUND::get);
		
		return findCrew.getAttendanceFindResult(now);
	}
	
	public List<AttendanceExpellWarningResult> findExpellWarnings(LocalDate now) {
		return crews.stream()
				.map(crew -> crew.getAttendanceExpellWaringResult(now))
				.filter(attendanceExpellWarningResult -> {
					AttendanceInterview attendanceInterview = attendanceExpellWarningResult.attendanceInterview();
					return attendanceInterview.equals(AttendanceInterview.면담_대상자) || attendanceInterview.equals(AttendanceInterview.경고_대상자);
				})
				.sorted()
				.toList();
	}
}
