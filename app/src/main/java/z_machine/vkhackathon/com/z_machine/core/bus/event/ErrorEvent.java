package z_machine.vkhackathon.com.z_machine.core.bus.event;

public final class ErrorEvent {

    private final int requestId;
    private final Throwable throwable;

    public ErrorEvent(Throwable throwable, int requestId) {
        this.throwable = throwable;
        this.requestId = requestId;
    }

    public Throwable getThrowable() {
        return throwable;
    }

    public int getRequestId() {
        return requestId;
    }
}
