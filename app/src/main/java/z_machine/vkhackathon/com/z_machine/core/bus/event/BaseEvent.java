package z_machine.vkhackathon.com.z_machine.core.bus.event;

public final class BaseEvent<T> {

    private final int requestId;
    private final T body;

    public BaseEvent(int requestId, T body) {
        this.requestId = requestId;
        this.body = body;
    }

    public int getRequestId() {
        return requestId;
    }

    public T getBody() {
        return body;
    }
}
