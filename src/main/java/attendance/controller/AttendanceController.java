package attendance.controller;

import attendance.common.dto.request.AttendanceFeatureCommand;
import attendance.common.dto.request.AttendanceFindRequest;
import attendance.common.dto.request.AttendanceModifyRequest;
import attendance.common.dto.result.AttendanceExpellWarningResult;
import attendance.common.dto.result.AttendanceFindResults;
import attendance.common.dto.result.AttendanceModifyResult;
import attendance.common.dto.result.AttendanceResult;
import attendance.domain.Attendance;
import attendance.domain.Crews;
import attendance.infra.repository.CrewsRepository;
import attendance.io.input.InputHandler;
import attendance.io.output.OutputHandler;
import attendance.service.DateProvider;

import java.time.LocalDateTime;
import java.util.List;

public class AttendanceController {
	
	private final InputHandler inputHandler;
	private final OutputHandler outputHandler;
	private final DateProvider dateProvider;
	private final CrewsRepository crewsRepository;
	
	public AttendanceController(InputHandler inputHandler, OutputHandler outputHandler, DateProvider dateProvider, CrewsRepository crewsRepository) {
		this.inputHandler = inputHandler;
		this.outputHandler = outputHandler;
		this.dateProvider = dateProvider;
		this.crewsRepository = crewsRepository;
	}
	
	public void run() {
		while(true) {
			AttendanceFeatureCommand command = inputHandler.handleFeatureSelect(dateProvider.date());
			Crews crews = crewsRepository.getCrews();
			if (command.equals(AttendanceFeatureCommand.출석)) {
				Attendance.isAttendanceValid(dateProvider.date());
				String nickname = inputHandler.handleAttendanceNickname();
				crews.validateValidCrew(nickname);
				LocalDateTime dateTime = inputHandler.handleAttendanceTime(dateProvider.date());
				AttendanceResult result = crews.addAttendanceByNameAndTime(nickname, dateTime);
				outputHandler.handleAttendanceResult(result);
				continue;
			}
			if (command.equals(AttendanceFeatureCommand.출석_수정)) {
				String nickname = inputHandler.handleAttendanceModifyNickname();
				crews.validateValidCrew(nickname);
				AttendanceModifyRequest request = inputHandler.handleAttendanceModify();
				AttendanceModifyResult result = crews.modifyAttendance(nickname, dateProvider.date(), request.day(), request.time());
				outputHandler.handleAttendanceModifyResult(result);
				continue;
			}
			if (command.equals(AttendanceFeatureCommand.크루별_출석_기록_확인)) {
				AttendanceFindRequest request = inputHandler.handleAttendanceFind();
				crews.validateValidCrew(request.nickname());
				AttendanceFindResults result = crews.findAttendancesByName(request.nickname(), dateProvider.date());
				outputHandler.handleAttendanceFindResults(result);
				continue;
			}
			if (command.equals(AttendanceFeatureCommand.제적_위험_확인)) {
				List<AttendanceExpellWarningResult> result = crews.findExpellWarnings(dateProvider.date());
				outputHandler.handleAttendanceExpellWarning(result);
				continue;
			}
			break;
		}
	}
}
