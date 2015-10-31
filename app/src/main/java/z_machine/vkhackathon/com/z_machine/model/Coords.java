package z_machine.vkhackathon.com.z_machine.model;

import com.google.android.gms.maps.model.LatLng;

public final class Coords {

    private double lat;
    private double lon;

    public double getLat() {
        return lat;
    }

    public void setLat(double lat) {
        this.lat = lat;
    }

    public double getLon() {
        return lon;
    }

    public void setLon(double lon) {
        this.lon = lon;
    }

    public static LatLng toLatLng(Coords coords) {
        return new LatLng(coords.getLat(), coords.getLon());
    }
}
