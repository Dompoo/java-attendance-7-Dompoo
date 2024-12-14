package attendance.domain;

import attendance.common.CustomExceptions;
import attendance.common.dto.result.AttendanceFindResult;
import attendance.common.dto.result.AttendanceModifyResult;
import attendance.common.dto.result.AttendanceResult;

import java.time.DayOfWeek;
import java.time.LocalDate;
import java.time.LocalDateTime;
import java.time.LocalTime;
import java.util.List;

public class Attendance {
	
	private static final List<DayOfWeek> validAttendanceDayOfWeek = List.of(DayOfWeek.MONDAY, DayOfWeek.TUESDAY, DayOfWeek.WEDNESDAY, DayOfWeek.THURSDAY, DayOfWeek.FRIDAY);
	private static final LocalTime validAttendanceStartTime = LocalTime.of(8, 0);
	private static final LocalTime validAttendanceEndTime = LocalTime.of(23, 0);
	
	private final LocalDateTime attendanceDateTime;
	private final AttendanceStatus attendanceStatus;
	
	public Attendance(LocalDateTime attendanceDateTime, AttendanceStatus attendanceStatus) {
		validateValidAttendanceDate(attendanceDateTime.toLocalDate());
		validateValidAttendTime(attendanceDateTime.toLocalTime());
		this.attendanceDateTime = attendanceDateTime;
		this.attendanceStatus = attendanceStatus;
	}
	
	public static void isAttendanceValid(LocalDate localDate) {
		validateValidAttendanceDate(localDate);
	}
	
	private static void validateValidAttendanceDate(LocalDate localDate) {
		if (LegalHolidayCalendar.isLegalHoliday(localDate) || !validAttendanceDayOfWeek.contains(localDate.getDayOfWeek())) {
			throw CustomExceptions.INVALID_ATTENDANCE_DATE.get(localDate.getMonthValue(), localDate.getDayOfMonth(), KoreanDayOfWeek.from(localDate.getDayOfWeek()).name());
		}
	}
	
	private static void validateValidAttendTime(LocalTime localTime) {
		if (validAttendanceStartTime.isAfter(localTime) || validAttendanceEndTime.isBefore(localTime)) {
			throw CustomExceptions.INVALID_ATTENDANCE_TIME.get();
		}
	}
	
	public static Attendance from(LocalDateTime attendanceDateTime) {
		return new Attendance(attendanceDateTime, AttendanceStatus.getAttendanceStatus(attendanceDateTime));
	}
	
	public AttendanceResult toAttendanceResult() {
		return new AttendanceResult(attendanceDateTime, attendanceStatus);
	}
	
	public AttendanceModifyResult toAttendanceModifyResult(Attendance afterModifyAttendance) {
		return new AttendanceModifyResult(
				this.attendanceDateTime.toLocalDate(),
				this.attendanceDateTime.toLocalTime(),
				this.attendanceStatus,
				afterModifyAttendance.attendanceDateTime.toLocalTime(),
				afterModifyAttendance.attendanceStatus
		);
	}
	
	public AttendanceFindResult toAttendanceFindResult() {
		return new AttendanceFindResult(attendanceDateTime, attendanceStatus, true);
	}
	
	public boolean isAttendanceDateEquals(LocalDate localDate) {
		return attendanceDateTime.toLocalDate().isEqual(localDate);
	}
	
	public Attendance with(LocalTime time) {
		return Attendance.from(attendanceDateTime.with(time));
	}
}
