package attendance.io.input;

import attendance.common.dto.request.AttendanceFindRequest;
import attendance.io.reader.Reader;
import attendance.io.writer.Writer;

public class InputHandler {
	
	private final Reader reader;
	private final Writer writer;
	private final InputValidator inputValidator;
	private final InputParser inputParser;
	
	public InputHandler(Reader reader, Writer writer, InputValidator inputValidator, InputParser inputParser) {
		this.reader = reader;
		this.writer = writer;
		this.inputValidator = inputValidator;
		this.inputParser = inputParser;
	}
	
	public AttendanceFindRequest handleAttendanceFind() {
		writer.write("닉네임을 입력해 주세요.");
		String input = reader.readLine();
		inputValidator.validateAttendanceFind(input);
		return inputParser.parseAttendanceFind(input);
	}
}
