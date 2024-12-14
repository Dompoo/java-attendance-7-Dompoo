package attendance.controller;

import attendance.common.exception.ExceptionHandler;

public class AttendanceControllerExceptionHandleProxy implements AttendanceController {
	
	private final ExceptionHandler exceptionHandler;
	private final AttendanceController attendanceController;
	
	public AttendanceControllerExceptionHandleProxy(ExceptionHandler exceptionHandler, AttendanceController attendanceController) {
		this.exceptionHandler = exceptionHandler;
		this.attendanceController = attendanceController;
	}
	
	@Override
	public void run() {
		try {
			attendanceController.run();
		} catch (IllegalArgumentException e) {
			exceptionHandler.handleException(e);
			throw e;
		}
	}
}
