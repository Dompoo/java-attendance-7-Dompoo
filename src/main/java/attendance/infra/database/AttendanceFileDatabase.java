package attendance.infra.database;

import attendance.infra.entity.AttendanceEntity;

public class AttendanceFileDatabase extends FileDatabase<AttendanceEntity> {
	
	private static final String ATTENDANCE_FILE_PATH = "src/main/resources/attendances.csv";
	
	@Override
	protected AttendanceEntity convertLineToObject(String line) {
		return AttendanceEntity.from(line);
	}
	
	@Override
	protected String getFilePath() {
		return ATTENDANCE_FILE_PATH;
	}
}

