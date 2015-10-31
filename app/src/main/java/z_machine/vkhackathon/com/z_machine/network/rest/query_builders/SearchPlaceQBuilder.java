package z_machine.vkhackathon.com.z_machine.network.rest.query_builders;

public class SearchPlaceQBuilder extends PlaceQBuilder {

    public static final String Q = "q";
    public static final String TYPE = "type";

    public SearchPlaceQBuilder setQ(String query) {
        queryMap.put(Q, query);
        return this;
    }

    public SearchPlaceQBuilder setType(String type) {
        queryMap.put(TYPE, type);
        return this;
    }

}
