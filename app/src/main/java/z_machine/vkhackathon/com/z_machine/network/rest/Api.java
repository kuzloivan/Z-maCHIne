package z_machine.vkhackathon.com.z_machine.network.rest;

import java.util.Map;

import retrofit.Call;
import retrofit.http.GET;
import retrofit.http.Path;
import retrofit.http.QueryMap;
import retrofit.http.Query;
import z_machine.vkhackathon.com.z_machine.model.Event;
import z_machine.vkhackathon.com.z_machine.model.Place;
import z_machine.vkhackathon.com.z_machine.network.rest.response.GetEvents;
import z_machine.vkhackathon.com.z_machine.network.rest.response.GetPlaces;

public interface Api {

    @GET(Url.EVENTS)
    Call<GetEvents> getEvents();

    @GET(Url.EVENT)
    Call<Event> getEvent(@Path("event_id") int eventId);

    @GET(Url.PLACES)
    Call<GetPlaces> getPaces(@QueryMap Map<String, String> map);

    @GET(Url.PLACE)
    Call<Place> getPlace(@Path("id") int placeId);

    @GET(Url.EVENTS_BY_PLACE)
    Call<GetEvents> getEventsByPlace(@Query("place_id") String placeId);

    @GET(Url.QUERY_PLACES)
    Call<GetPlaces> getQueryPlaces(@Query("q") String query);
}
