package z_machine.vkhackathon.com.z_machine.restore;

import android.content.Context;

import java.util.ArrayList;
import java.util.HashMap;
import java.util.List;
import java.util.Map;

import io.realm.Realm;
import z_machine.vkhackathon.com.z_machine.model.Event;
import z_machine.vkhackathon.com.z_machine.model.MyEventRealmObject;
import z_machine.vkhackathon.com.z_machine.model.Place;
import z_machine.vkhackathon.com.z_machine.utils.SystemUtils;

/**
 * Created by Kuzlo on 31.10.2015.
 */
public class RestoreManager {

    private Context context;

    private Map<Integer,Place> placeMap = new HashMap<>();


    public void addPlaces(List<Place> places){
        for(Place place: places){
            placeMap.put(place.getId(),place);
        }
    }

    public List<Place> getPlaces(){
        placeMap.remove(Place.facePlace().getId());
        List<Place> places = new ArrayList<>();
        places.addAll(placeMap.values());
        places.add(0, Place.facePlace());
        return places;
    }


    public RestoreManager(Context context) {
        this.context = context;
    }

    public void saveEvent(Event event) {
        MyEventRealmObject m = new MyEventRealmObject();
        m.setId(event.getId());
        m.setEventHashTag(SystemUtils.eventHashTagByTitle(event.getTitle()));
        m.setEventPhoto(event.getImages().get(0).getImage());
        m.setEventName(event.getTitle());
        Realm realm = Realm.getInstance(context);
        realm.copyToRealmOrUpdate(m);
        realm.commitTransaction();
    }

    public void saveEvent(int id, String image, String title) {
        MyEventRealmObject m = new MyEventRealmObject();
        m.setId(id);
        m.setEventHashTag(SystemUtils.eventHashTagByTitle(title));
        m.setEventPhoto(image);
        m.setEventName(title);
        Realm realm = Realm.getInstance(context);
        realm.beginTransaction();
        realm.copyToRealmOrUpdate(m);
        realm.commitTransaction();
    }

    public List<MyEventRealmObject> getMyEvents(){
        return Realm.getInstance(context).allObjectsSorted(MyEventRealmObject.class,"time",false);
    }


}
