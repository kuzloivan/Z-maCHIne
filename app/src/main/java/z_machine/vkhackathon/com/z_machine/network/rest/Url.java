package z_machine.vkhackathon.com.z_machine.network.rest;

public class Url {

    static final String BASE = "http://kudago.com";

    static final String EVENTS = "/public-api/v1/events?fields=images,id,title,place&location=spb&lang=en&page_size=100&order_by=-favorites_count";
    static final String EVENT = "/public-api/v1/events/{event_id}?lang=en";
    static final String PLACES = "/public-api/v1/places";
    static final String PLACE = "/public-api/v1/places/{id}/?lang=en";
    static final String EVENTS_BY_PLACE = "/public-api/v1/events?fields=images,title,id";
    static final String QUERY_PLACES = "/public-api/v1/search/?type=place";

}