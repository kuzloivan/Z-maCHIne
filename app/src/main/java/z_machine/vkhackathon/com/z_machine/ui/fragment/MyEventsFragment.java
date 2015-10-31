package z_machine.vkhackathon.com.z_machine.ui.fragment;

import android.os.Bundle;
import android.support.annotation.Nullable;
import android.support.v4.app.Fragment;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.AdapterView;

import z_machine.vkhackathon.com.z_machine.R;
import z_machine.vkhackathon.com.z_machine.model.MyEventRealmObject;
import z_machine.vkhackathon.com.z_machine.model.Place;
import z_machine.vkhackathon.com.z_machine.ui.activity.DetailEventActivity;
import z_machine.vkhackathon.com.z_machine.ui.activity.place.DetailPlaceActivity;
import z_machine.vkhackathon.com.z_machine.ui.adapter.MyEventAdapter;
import z_machine.vkhackathon.com.z_machine.ui.adapter.place.PlaceAdapter;
import z_machine.vkhackathon.com.z_machine.ui.customview.ParallaxListView;

/**
 * Created by Kuzlo on 31.10.2015.
 */
public class MyEventsFragment  extends BaseFragment{
    private MyEventAdapter placeAdapter;

    @Override
    public void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        placeAdapter = new MyEventAdapter(getContext());
    }


    public static Fragment getInstance() {
        return new MyEventsFragment();
    }

    @Nullable
    @Override
    public View onCreateView(LayoutInflater inflater, ViewGroup container,
                             Bundle savedInstanceState) {
        return inflater.inflate(R.layout.fragment_event, container, false);
    }

    @Override
    public void onViewCreated(View view, @Nullable Bundle savedInstanceState) {
        super.onViewCreated(view, savedInstanceState);
        final ParallaxListView lvEvents = (ParallaxListView) view.findViewById(R.id.lvElements);
        lvEvents.setAdapter(placeAdapter);
        lvEvents.setOnItemClickListener(new MyEventItemListener());
        placeAdapter.add(appBridge.getRestoreManager().getMyEvents());
    }


    private final class MyEventItemListener implements AdapterView.OnItemClickListener {

        @Override
        public void onItemClick(AdapterView<?> parent, View view, int position, long id) {
            MyEventRealmObject item = placeAdapter.getItem(position);
            DetailEventActivity.start(getActivity(),item.getId(),item.getEventName());
        }
    }

}
