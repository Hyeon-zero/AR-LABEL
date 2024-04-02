package capstonedesign.arlabel.logtrace;

public interface LogTrace {

    // 클라이언트의 요청 정보(IP, User-Agent)를 포함하는 로그를 출력하는 메서드
    void requestInfo(String ipAddress, String userAgent);

    // 시작 로그를 출력하는 메서드
    TraceStatus begin(String message);

    // 종료 로그를 출력하는 메서드
    void end(TraceStatus status);

    // 예외 발생 시 로그를 출력하는 메서드
    void exception(TraceStatus status, Exception e);

}