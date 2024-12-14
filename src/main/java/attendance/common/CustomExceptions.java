package attendance.common;

public enum CustomExceptions {
    
    ATTENDANCE_NOT_FOUND(
            "등록되지 않은 출석입니다.",
            IllegalArgumentException.class
    ),
    CREW_NOT_FOUND(
            "등록되지 않은 닉네임입니다.",
            IllegalArgumentException.class
    ),
    FILE_NOT_READABLE(
            "파일 읽기 중 오류가 발생했습니다.",
            IllegalStateException.class
    )
    ;

    private final String message;
    private final Class<? extends RuntimeException> exceptionType;

    CustomExceptions(String message, Class<? extends RuntimeException> exceptionType) {
        this.message = message;
        this.exceptionType = exceptionType;
    }

    public RuntimeException get() {
        try {
            return exceptionType.getDeclaredConstructor(String.class).newInstance(message);
        } catch (Exception e) {
            return new RuntimeException(message);
        }
    }
}