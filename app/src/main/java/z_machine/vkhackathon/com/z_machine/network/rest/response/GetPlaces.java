package z_machine.vkhackathon.com.z_machine.network.rest.response;

import com.google.gson.annotations.SerializedName;
import com.hackathon.z_machine.model.Place;

import java.util.List;

public class GetPlaces {

    private int count;
    private String next;
    private String previous;
    @SerializedName("results")
    private List<Place> places;

    public int getCount() {
        return count;
    }

    public void setCount(int count) {
        this.count = count;
    }

    public String getNext() {
        return next;
    }

    public void setNext(String next) {
        this.next = next;
    }

    public String getPrevious() {
        return previous;
    }

    public void setPrevious(String previous) {
        this.previous = previous;
    }

    public List<Place> getPlaces() {
        return places;
    }

    public void setPlaces(List<Place> places) {
        this.places = places;
    }
}
