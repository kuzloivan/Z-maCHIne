package z_machine.vkhackathon.com.z_machine.core.appinterface;

public interface NetBridge {

    void getEvents(int requestId);

    void getEvent(int requestId, int eventId);

    void getPlaces(int requestId);

    void getPlace(int requestId, int placeId);
}