package z_machine.vkhackathon.com.z_machine.ui.fragment;

import android.os.Bundle;
import android.support.annotation.Nullable;
import android.support.v4.app.Fragment;
import android.support.v4.view.MenuItemCompat;
import android.support.v7.widget.SearchView;
import android.util.Log;
import android.view.LayoutInflater;
import android.view.Menu;
import android.view.MenuInflater;
import android.view.MenuItem;
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
    private static final int GET_QUERY_PLACES = 5;

    public static Fragment getInstance() {
        return new KudaGoFragment();
    }

    private PlaceAdapter placeAdapter;
    private SearchView searchView;
    private MenuItem searchItem;
    private ParallaxListView lvEvents;

    @Override
    public void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        placeAdapter = new PlaceAdapter(getContext());
        setHasOptionsMenu(true);
    }

    @Override
    public void onCreateOptionsMenu(Menu menu, MenuInflater inflater) {
        inflater.inflate(R.menu.main, menu);
        searchItem = menu.findItem(R.id.actionSearch);
        searchView = (SearchView) MenuItemCompat.getActionView(searchItem);
        searchView.setOnQueryTextListener(new SearchView.OnQueryTextListener() {
            @Override
            public boolean onQueryTextSubmit(String query) {
                return false;
            }

            @Override
            public boolean onQueryTextChange(String newText) {
                placeAdapter.getFilter().filter(newText);
                return true;
            }
        });

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
        lvEvents = (ParallaxListView) view.findViewById(R.id.lvElements);
        lvEvents.setAdapter(placeAdapter);
        placeAdapter.add(appBridge.getRestoreManager().getPlaces());
        lvEvents.setOnItemClickListener(new PlaceItemListener());
    }

    @Override
    public void onStart() {
        super.onStart();
        BusProvider.getInstance().register(this);
        if (placeAdapter.isEmpty()) {
            appBridge.getNetBridge().getPlaces(GET_PLACES, PlaceLocationFragment.loc.latitude, PlaceLocationFragment.loc.longitude);
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
            placesResponseBody.getPlaces().add(0, Place.facePlace());
            appBridge.getRestoreManager().addPlaces(placesResponseBody.getPlaces());
            placeAdapter.add(appBridge.getRestoreManager().getPlaces());
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
            DetailPlaceActivity.start(getActivity(), event.getId(), event.getTitle());
        }
    }
}
