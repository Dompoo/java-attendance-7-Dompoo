package attendance.io.input;

import attendance.common.dto.request.AttendanceFeatureCommand;
import attendance.common.dto.request.AttendanceFindRequest;
import attendance.common.dto.request.AttendanceModifyRequest;
import attendance.io.reader.Reader;
import attendance.io.writer.Writer;

import java.time.LocalDate;
import java.time.LocalDateTime;

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
	
	public AttendanceFeatureCommand handleFeatureSelect(LocalDate localDate) {
		writer.write("""
				
				오늘은 %d월 %d일 토요일입니다. 기능을 선택해 주세요.
				1. 출석 확인
				2. 출석 수정
				3. 크루별 출석 기록 확인
				4. 제적 위험자 확인
				Q. 종료
				""".formatted(localDate.getMonthValue(), localDate.getDayOfMonth()));
		return AttendanceFeatureCommand.from(reader.readLine());
	}
	
	public String handleAttendanceNickname() {
		writer.write("\n닉네임을 입력해 주세요.\n");
		String nickName = reader.readLine();
		inputValidator.validateNickName(nickName);
		return inputParser.parseNickName(nickName);
	}
	
	public LocalDateTime handleAttendanceTime(LocalDate localDate) {
		writer.write("등교 시간을 입력해 주세요.\n");
		String time = reader.readLine();
		inputValidator.validateTime(time);
		return LocalDateTime.of(localDate, inputParser.parseTime(time));
	}
	
	public String handleAttendanceModifyNickname() {
		writer.write("\n출석을 수정하려는 크루의 닉네임을 입력해 주세요.\n");
		String nickName = reader.readLine();
		inputValidator.validateNickName(nickName);
		return inputParser.parseNickName(nickName);
	}
	
	public AttendanceModifyRequest handleAttendanceModify() {
		writer.write("수정하려는 날짜(일)를 입력해 주세요.\n");
		String date = reader.readLine();
		writer.write("언제로 변경하겠습니까?\n");
		String time = reader.readLine();
		inputValidator.validateDate(date);
		inputValidator.validateTime(time);
		return new AttendanceModifyRequest(
				inputParser.parseDate(date),
				inputParser.parseTime(time)
		);
	}
	
	public AttendanceFindRequest handleAttendanceFind() {
		writer.write("\n닉네임을 입력해 주세요.\n");
		String input = reader.readLine();
		inputValidator.validateNickName(input);
		return new AttendanceFindRequest(inputParser.parseNickName(input));
	}
}
