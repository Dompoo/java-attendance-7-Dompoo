package attendance.io.input;

import attendance.common.dto.request.AttendanceFindRequest;
import attendance.common.dto.request.AttendanceModifyRequest;
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
		inputValidator.validateNickName(input);
		return new AttendanceFindRequest(inputParser.parseNickName(input));
	}
	
	public AttendanceModifyRequest handleAttendanceModify() {
		writer.write("출석을 수정하려는 크루의 닉네임을 입력해 주세요.");
		String nickName = reader.readLine();
		writer.write("수정하려는 날짜(일)를 입력해 주세요.");
		String date = reader.readLine();
		writer.write("언제로 변경하겠습니까?");
		String time = reader.readLine();
		
		inputValidator.validateNickName(nickName);
		inputValidator.validateDate(date);
		inputValidator.validateTime(time);
		
		return new AttendanceModifyRequest(
				inputParser.parseNickName(nickName),
				inputParser.parseDate(date),
				inputParser.parseTime(time)
		);
	}
}
