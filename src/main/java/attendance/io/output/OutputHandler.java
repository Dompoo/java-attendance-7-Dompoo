package attendance.io.output;

import attendance.common.dto.result.AttendanceResult;
import attendance.io.writer.Writer;

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
}
