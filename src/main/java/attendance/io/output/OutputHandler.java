package attendance.io.output;

import attendance.common.dto.result.AttendanceExpellWarningResult;
import attendance.common.dto.result.AttendanceFindResults;
import attendance.common.dto.result.AttendanceModifyResult;
import attendance.common.dto.result.AttendanceResult;
import attendance.io.writer.Writer;

import java.util.List;

public class OutputHandler {
	
	private final Writer writer;
	private final OutputParser outputParser;
	
	public OutputHandler(Writer writer, OutputParser outputParser) {
		this.writer = writer;
		this.outputParser = outputParser;
	}
	
	public void handleAttendanceResult(AttendanceResult attendanceResult) {
		String output = outputParser.parseAttendance(attendanceResult.attendanceDateTime(), attendanceResult.attendanceStatus());
		writer.write(output);
	}
	
	public void handleAttendanceModifyResult(AttendanceModifyResult attendanceModifyResult) {
		String output = outputParser.parseAttendanceModify(attendanceModifyResult);
		writer.write(output);
	}
	
	public void handleAttendanceFindResults(AttendanceFindResults attendanceFindResults) {
		String output = outputParser.parseAttendanceFind(attendanceFindResults);
		writer.write(output);
	}
	
	public void handleAttendanceExpellWarning(List<AttendanceExpellWarningResult> attendanceExpellWarningResults) {
		String output = outputParser.parseAttendanceExpellWarnings(attendanceExpellWarningResults);
	}
}
