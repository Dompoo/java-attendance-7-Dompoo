package attendance.controller;

import attendance.common.dto.request.AttendanceFeatureCommand;
import attendance.common.dto.request.AttendanceFindRequest;
import attendance.common.dto.request.AttendanceModifyRequest;
import attendance.common.dto.request.AttendanceRequest;
import attendance.common.dto.result.AttendanceExpellWarningResult;
import attendance.common.dto.result.AttendanceFindResults;
import attendance.common.dto.result.AttendanceModifyResult;
import attendance.common.dto.result.AttendanceResult;
import attendance.domain.Crews;
import attendance.infra.repository.CrewsRepository;
import attendance.io.input.InputHandler;
import attendance.io.output.OutputHandler;
import attendance.service.DateProvider;

import java.util.List;
import java.util.Optional;

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
				AttendanceRequest request = inputHandler.handleAttendance(dateProvider.date());
				Optional<AttendanceResult> result = crews.addAttendanceByNameAndTime(request.nickname(), request.attendanceTime());
				if (result.isPresent()) {
					outputHandler.handleAttendanceResult(result.get());
					continue;
				}
				outputHandler.handleAlreadyAttend();
				continue;
			}
			if (command.equals(AttendanceFeatureCommand.출석_수정)) {
				AttendanceModifyRequest request = inputHandler.handleAttendanceModify();
				AttendanceModifyResult result = crews.modifyAttendance(request.nickname(), dateProvider.date(), request.day(), request.time());
				outputHandler.handleAttendanceModifyResult(result);
				continue;
			}
			if (command.equals(AttendanceFeatureCommand.크루별_출석_기록_확인)) {
				AttendanceFindRequest request = inputHandler.handleAttendanceFind();
				AttendanceFindResults result = crews.findAttendancesByName(request.nickname());
				outputHandler.handleAttendanceFindResults(result);
				continue;
			}
			if (command.equals(AttendanceFeatureCommand.제적_위험_확인)) {
				List<AttendanceExpellWarningResult> result = crews.findExpellWarnings();
				outputHandler.handleAttendanceExpellWarning(result);
				continue;
			}
			break;
		}
	}
}
