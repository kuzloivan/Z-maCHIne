package z_machine.vkhackathon.com.z_machine.network.rest;

public class Url {

    static final String BASE = "http://kudago.com";

    static final String EVENTS = "/public-api/v1/events/?fields=images,id,title,place&location=spb&lang=en&page_size=100";
    static final String EVENT = "/public-api/v1/events/{event_id}/?lang=en";
    static final String PLACES = "http://kudago.com/public-api/v1/places?location=spb&lon=30.334911999999996&lat=59.91979700000001&radius=1000&fields=favorites_count,comments_count,images,id,description,title,short_title,coords&page_size=100";
    static final String PLACE = "/public-api/v1/places/{id}/?lang=en";

}
