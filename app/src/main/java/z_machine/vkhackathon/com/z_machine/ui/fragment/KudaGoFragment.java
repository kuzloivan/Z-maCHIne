package z_machine.vkhackathon.com.z_machine.ui.fragment;

import android.os.Bundle;
import android.support.annotation.Nullable;
import android.support.v4.app.Fragment;
import android.util.Log;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.AdapterView;

import com.squareup.otto.Subscribe;

import z_machine.vkhackathon.com.z_machine.R;
import z_machine.vkhackathon.com.z_machine.core.bus.BusProvider;
import z_machine.vkhackathon.com.z_machine.core.bus.event.BaseEvent;
import z_machine.vkhackathon.com.z_machine.core.bus.event.ErrorEvent;
import z_machine.vkhackathon.com.z_machine.model.Place;
import z_machine.vkhackathon.com.z_machine.network.rest.response.GetPlaces;
import z_machine.vkhackathon.com.z_machine.ui.activity.place.DetailPlaceActivity;
import z_machine.vkhackathon.com.z_machine.ui.adapter.place.PlaceAdapter;
import z_machine.vkhackathon.com.z_machine.ui.customview.ParallaxListView;

public final class KudaGoFragment extends BaseFragment {

    private static final int GET_PLACES = 1;

    public static Fragment getInstance() {
        return new KudaGoFragment();
    }

    private PlaceAdapter placeAdapter;

    @Override
    public void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        placeAdapter = new PlaceAdapter(getContext());
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
        lvEvents.setOnItemClickListener(new PlaceItemListener());
    }

    @Override
    public void onStart() {
        super.onStart();
        BusProvider.getInstance().register(this);
        if (placeAdapter.isEmpty()) {
            appBridge.getNetBridge().getPlaces(GET_PLACES);
        }
    }

    @Override
    public void onStop() {
        super.onStop();
        BusProvider.getInstance().unregister(this);
    }

    @Subscribe
    public void networkEventListener(BaseEvent event) {
        if (event.getRequestId() == GET_PLACES) {
            final GetPlaces placesResponseBody = (GetPlaces) event.getBody();
            placeAdapter.add(placesResponseBody.getPlaces());
        }
    }

    @Subscribe
    public void networkErrorEventListener(ErrorEvent errorEvent) {
        Log.e("Events", "requestId: " + errorEvent.getRequestId() + " message: "
                + errorEvent.getThrowable().getMessage());
    }

    private final class PlaceItemListener implements AdapterView.OnItemClickListener {

        @Override
        public void onItemClick(AdapterView<?> parent, View view, int position, long id) {
            final Place event = placeAdapter.getItem(position);
            DetailPlaceActivity.start(getActivity(), event.getId());
        }
    }
}
