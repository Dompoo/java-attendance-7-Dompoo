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
import java.util.Optional;

public class Crews {
	
	private final List<Crew> crews;
	
	public Crews(List<Crew> crews) {
		this.crews = crews;
	}
	
	public Optional<AttendanceResult> addAttendanceByNameAndTime(String nickname, LocalDateTime attendanceDateTime) {
		Crew findCrew = crews.stream()
				.filter(crew -> crew.getNickname().equals(nickname))
				.findFirst()
				.orElseThrow(CustomExceptions.CREW_NOT_FOUND::get);
		
		return findCrew.addAttendance(attendanceDateTime);
	}
	
	// TODO : 미래 날짜 수정 검증
	// TODO : 아직 출석하지 않은 날짜를 수정하려고 할 경우 그냥 추가
	public AttendanceModifyResult modifyAttendance(String nickname, LocalDate date, LocalTime time) {
		Crew findCrew = crews.stream()
				.filter(crew -> crew.getNickname().equals(nickname))
				.findFirst()
				.orElseThrow(CustomExceptions.CREW_NOT_FOUND::get);
		
		return findCrew.modifyAttendance(date, time);
	}
	
	// TODO : 등교하지 않아 출석 기록이 없는 날은 결석으로 간주
	// TODO : 오늘을 제외하고 전날까지만 반환
	public AttendanceFindResults findAttendancesByName(String nickname) {
		Crew findCrew = crews.stream()
				.filter(crew -> crew.getNickname().equals(nickname))
				.findFirst()
				.orElseThrow(CustomExceptions.CREW_NOT_FOUND::get);
		
		return findCrew.getAttendanceFindResult();
	}
	
	// TODO : 정렬
	public List<AttendanceExpellWarningResult> findExpellWarnings() {
		return crews.stream()
				.map(Crew::getAttendanceExpellWaringResult)
				.filter(attendanceExpellWarningResult -> {
					AttendanceInterview attendanceInterview = attendanceExpellWarningResult.attendanceInterview();
					return attendanceInterview.equals(AttendanceInterview.면담_대상자) || attendanceInterview.equals(AttendanceInterview.경고_대상자);
				})
				.toList();
	}
}
