package z_machine.vkhackathon.com.z_machine.model;

import io.realm.RealmObject;
import io.realm.annotations.PrimaryKey;

public class MyEventRealmObject extends RealmObject {

    @PrimaryKey
    private int id;
    private String eventName;
    private String eventPhoto;
    private String eventHashTag;
    private long time;

    public int getId() {
        return id;
    }

    public void setId(int id) {
        this.id = id;
    }

    public String getEventName() {
        return eventName;
    }

    public void setEventName(String eventName) {
        this.eventName = eventName;
    }

    public String getEventPhoto() {
        return eventPhoto;
    }

    public void setEventPhoto(String eventPhoto) {
        this.eventPhoto = eventPhoto;
    }

    public String getEventHashTag() {
        return eventHashTag;
    }

    public void setEventHashTag(String eventHashTag) {
        this.eventHashTag = eventHashTag;
    }

    public long getTime() {
        return time;
    }

    public void setTime(long time) {
        this.time = time;
    }
}
