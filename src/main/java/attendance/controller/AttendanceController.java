package attendance.controller;

import attendance.common.dto.request.AttendanceFeatureCommand;
import attendance.common.dto.request.AttendanceFindRequest;
import attendance.common.dto.request.AttendanceModifyRequest;
import attendance.common.dto.request.AttendanceRequest;
import attendance.common.dto.result.AttendanceExpellWarningResult;
import attendance.common.dto.result.AttendanceFindResults;
import attendance.common.dto.result.AttendanceModifyResult;
import attendance.common.dto.result.AttendanceResult;
import attendance.infra.repository.CrewRepository;
import attendance.io.input.InputHandler;
import attendance.io.output.OutputHandler;
import attendance.service.DateProvider;

import java.util.List;

public class AttendanceController {
	
	private final InputHandler inputHandler;
	private final OutputHandler outputHandler;
	private final DateProvider dateProvider;
	private final CrewRepository crewRepository;
	
	public AttendanceController(InputHandler inputHandler, OutputHandler outputHandler, DateProvider dateProvider, CrewRepository crewRepository) {
		this.inputHandler = inputHandler;
		this.outputHandler = outputHandler;
		this.dateProvider = dateProvider;
		this.crewRepository = crewRepository;
	}
	
	public void run() {
		while(true) {
			AttendanceFeatureCommand command = inputHandler.handleFeatureSelect(dateProvider.date());
			if (command.equals(AttendanceFeatureCommand.출석)) {
				AttendanceRequest request = inputHandler.handleAttendance(dateProvider.date());
				AttendanceResult result = crewRepository.addAttendanceByNameAndTime(request.nickname(), request.attendanceTime());
				outputHandler.handleAttendanceResult(result);
				continue;
			}
			if (command.equals(AttendanceFeatureCommand.출석_수정)) {
				AttendanceModifyRequest request = inputHandler.handleAttendanceModify();
				AttendanceModifyResult result = crewRepository.modifyAttendance(request.nickname(), dateProvider.date().withDayOfMonth(request.day()), request.time());
				outputHandler.handleAttendanceModifyResult(result);
				continue;
			}
			if (command.equals(AttendanceFeatureCommand.크루별_출석_기록_확인)) {
				AttendanceFindRequest request = inputHandler.handleAttendanceFind();
				AttendanceFindResults result = crewRepository.findAttendancesByName(request.nickname());
				outputHandler.handleAttendanceFindResults(result);
				continue;
			}
			if (command.equals(AttendanceFeatureCommand.제적_위험_확인)) {
				List<AttendanceExpellWarningResult> result = crewRepository.findExpellWarnings();
				outputHandler.handleAttendanceExpellWarning(result);
				continue;
			}
			break;
		}
	}
}
