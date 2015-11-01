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
public final class RestoreManager {

    private final Context context;

    private Map<Integer, Place> placeMap = new HashMap<>();
    private final int fakeId;

    public void addPlaces(List<Place> places) {
        for (Place place : places) {
            placeMap.put(place.getId(), place);
        }
    }

    public Place getPlace(int placeId) {
        return placeMap.get(placeId);
    }

    public List<Place> getPlaces() {
        final List<Place> places = new ArrayList<>();
        places.addAll(placeMap.values());
        final Place fakePlace = placeMap.get(fakeId);
        final int index = places.indexOf(fakePlace);
        final Place place = places.get(0);
        places.add(0, fakePlace);
        places.add(index, place);
        return places;
    }


    public RestoreManager(Context context) {
        this.context = context;
        final Place fakePlace = Place.facePlace();
        fakeId = fakePlace.getId();
        placeMap.put(fakeId, fakePlace);
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

    public List<MyEventRealmObject> getMyEvents() {
        return Realm.getInstance(context).allObjectsSorted(MyEventRealmObject.class, "time", false);
    }


}
