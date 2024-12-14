package attendance;

import attendance.common.exception.ExceptionHandler;
import attendance.controller.AttendanceController;
import attendance.controller.AttendanceControllerExceptionHandleProxy;
import attendance.controller.DefaultAttendanceController;
import attendance.infra.database.AttendanceFileDatabase;
import attendance.infra.repository.CrewsRepository;
import attendance.io.input.InputHandler;
import attendance.io.input.InputParser;
import attendance.io.input.InputValidator;
import attendance.io.output.OutputHandler;
import attendance.io.output.OutputParser;
import attendance.io.reader.ConsoleReader;
import attendance.io.writer.ConsoleWriter;
import attendance.service.LocalDateProvider;

public class Application {
    public static void main(String[] args) {
        ConsoleWriter writer = new ConsoleWriter();
        AttendanceController attendanceController = new DefaultAttendanceController(
                new InputHandler(new ConsoleReader(), writer, new InputValidator(), new InputParser()),
                new OutputHandler(writer, new OutputParser()),
                new LocalDateProvider(),
                CrewsRepository.from(new AttendanceFileDatabase())
        );
        
        new AttendanceControllerExceptionHandleProxy(
                new ExceptionHandler(writer),
                attendanceController
        ).run();
    }
}
