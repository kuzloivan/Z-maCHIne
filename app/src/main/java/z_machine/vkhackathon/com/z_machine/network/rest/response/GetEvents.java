package z_machine.vkhackathon.com.z_machine.network.rest.response;

import com.google.gson.annotations.SerializedName;

import java.util.List;

import z_machine.vkhackathon.com.z_machine.model.Event;

public class GetEvents {

    private int count;
    private String next;
    private String previous;
    @SerializedName("results")
    private List<Event> events;

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

    public List<Event> getEvents() {
        return events;
    }

    public void setEvents(List<Event> events) {
        this.events = events;
    }
}
