package z_machine.vkhackathon.com.z_machine.network.rest.query_builders;

import java.util.HashMap;
import java.util.HashSet;
import java.util.Map;
import java.util.Set;

/**
 * Created by Kuzlo on 31.10.2015.
 */
public class PlaceQBuilder {
    public static final String LOCATION = "location";
    public static final String LON = "lon";
    public static final String LAT = "lat";
    public static final String RADIUS = "radius";
    public static final String FIELDS = "fields";
    public static final String PAGE_SIZE = "page_size";

    private Map<String,String> queryMap = new HashMap<>();
    private Set<String> params = new HashSet<>();


    public PlaceQBuilder setLocation(String s){
        queryMap.put(LOCATION,s);
        return this;
    }
    public PlaceQBuilder setLocationPosition(double lat,double lon){
        queryMap.put(LAT,""+lat);
        queryMap.put(LON,""+lon);
        return this;
    }
    public PlaceQBuilder setRadius(int radius){
        queryMap.put(RADIUS,""+radius);
        return this;
    }
    public PlaceQBuilder setPageSize(int size){
        queryMap.put(PAGE_SIZE,""+size);
        return this;
    }


    public PlaceQBuilder include(OPTIONS... options){
        for(OPTIONS opt:options){
            params.add(opt.toString());
        }
        return this;
    }

    public Map<String, String> build() {
        if(params.size()>0){
            String s = "";
            for (String p:params){
                s+=p+",";
            }
            queryMap.put(FIELDS,s.substring(0,s.length()-1));
        }
        return queryMap;
    }

    public enum OPTIONS {
        FAVORITES_COUNT("favorites_count"),
        COMMENTS_COUNT("comments_count"),
        IMAGES("images"),
        ID("id"),
        DESCRIPTION("description"),
        TITLE("title"),
        SHORT_TITLE("short_title"),
        COORDS("coords");

        private String param;

        private OPTIONS(final String param) {
            this.param = param;
        }

        @Override
        public String toString() {
            return param;
        }
    }
}
