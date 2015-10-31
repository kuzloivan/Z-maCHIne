package z_machine.vkhackathon.com.z_machine.network;


import z_machine.vkhackathon.com.z_machine.core.appinterface.NetBridge;
import z_machine.vkhackathon.com.z_machine.model.Event;
import z_machine.vkhackathon.com.z_machine.model.Place;
import z_machine.vkhackathon.com.z_machine.network.rest.Api;
import z_machine.vkhackathon.com.z_machine.network.rest.RestClient;
import z_machine.vkhackathon.com.z_machine.network.rest.query_builders.PlaceQBuilder;
import z_machine.vkhackathon.com.z_machine.network.rest.response.GetEvents;
import z_machine.vkhackathon.com.z_machine.network.rest.response.GetPlaces;

public class NetManager implements NetBridge {

    private final Api api;

    public NetManager() {
        RestClient restClient = new RestClient();
        api = restClient.getApi();
    }

    @Override
    public void getEvents(int requestId) {
        api.getEvents().enqueue(new MainCallback<GetEvents>(requestId));
    }

    @Override
    public void getEvent(int requestId, int eventId) {
        api.getEvent(eventId).enqueue(new MainCallback<Event>(requestId));
    }

    @Override
    public void getPlaces(int requestId, double latitude, double longitude) {
        PlaceQBuilder builder = new PlaceQBuilder();
        builder.setLocation("spb")
                .setLocationPosition(latitude,longitude)
                .setPageSize(100)
                .setRadius(1000)
                .include(PlaceQBuilder.OPTIONS.COMMENTS_COUNT,
                        PlaceQBuilder.OPTIONS.COORDS,
                        PlaceQBuilder.OPTIONS.DESCRIPTION,
                        PlaceQBuilder.OPTIONS.FAVORITES_COUNT,
                        PlaceQBuilder.OPTIONS.ID,
                        PlaceQBuilder.OPTIONS.IMAGES,
                        PlaceQBuilder.OPTIONS.TITLE,
                        PlaceQBuilder.OPTIONS.SHORT_TITLE,
                        PlaceQBuilder.OPTIONS.FAVORITES_COUNT);

        api.getPaces(builder.build()).enqueue(new MainCallback<GetPlaces>(requestId));
    }

    @Override
    public void getPlace(int requestId, int placeId) {
        api.getPlace(placeId).enqueue(new MainCallback<Place>(requestId));
    }
}
