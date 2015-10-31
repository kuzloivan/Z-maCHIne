package z_machine.vkhackathon.com.z_machine.ui.fragment;

import android.app.Activity;
import android.os.Bundle;
import android.support.v4.app.Fragment;
import android.view.View;
import android.widget.TextView;

import com.google.android.gms.maps.OnMapReadyCallback;
import com.google.android.gms.maps.SupportMapFragment;
import com.google.android.gms.maps.model.LatLng;
import com.google.android.gms.maps.model.Marker;

import java.util.HashMap;

import z_machine.vkhackathon.com.z_machine.R;
import z_machine.vkhackathon.com.z_machine.core.appinterface.AppBridge;

/**
 * Created by Ann on 24.10.2015.
 */
public class GeolocationFragment extends SupportMapFragment /*implements OnMapReadyCallback*/ {

    private static final int GET_PLACE_LIST = 45;
    private static final int GET_PLACE = 46;
    protected AppBridge applicationBridge;
   // private ClusterManager<Place> clusterManager;

   // private Map<Integer, Place> placeMap = new HashMap<>();
    private int clickedMarker;

    private LatLng loc = new LatLng(59.91979700000001, 30.334911999999996);

    public static Fragment getInstance() {
        return new GeolocationFragment();
    }

    @Override
    public void onAttach(Activity activity) {
        super.onAttach(activity);
        applicationBridge = (AppBridge) activity.getApplicationContext();
    }
/*
    @Override
    public void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        getMapAsync(this);
        applicationBridge.getNetBridge().getPlaces(GET_PLACE_LIST);
    }

    @Override
    public void onMapReady(final GoogleMap googleMap) {
        googleMap.moveCamera(CameraUpdateFactory.newLatLng(loc));
        googleMap.moveCamera(CameraUpdateFactory.zoomTo(12));
        googleMap.setMyLocationEnabled(true);
        clusterManager = new ClusterManager<Place>(getActivity(), googleMap);
        googleMap.setOnCameraChangeListener(clusterManager);
        googleMap.setOnMarkerClickListener(clusterManager);
        googleMap.setOnInfoWindowClickListener(clusterManager);
        googleMap.setInfoWindowAdapter(new InfoAdapter());

        Clicker clicker = new Clicker();

        clusterManager.setOnClusterItemClickListener(clicker);
        clusterManager.setOnClusterClickListener(clicker);
        clusterManager.setOnClusterItemInfoWindowClickListener(clicker);

    }

    @Override
    public void onResume() {
        super.onResume();
        BusProvider.getInstance().register(this);
    }


    @Override
    public void onPause() {
        super.onPause();
        BusProvider.getInstance().unregister(this);
    }


    @Subscribe
    public void networkEventListener(BaseEvent item) {
        if (item.getRequestId() == GET_PLACE_LIST) {
            GetPlaces places = (GetPlaces) item.getBody();
            for (Place place : places.getPlaces()) {
                clusterManager.addItem(place);
                placeMap.put(place.getId(),place);
            }
            clusterManager.cluster();
            return;
        }
    }

    @Subscribe
    public void networkErrorEventListener(ErrorEvent errorEvent) {

    }


    private class Clicker implements ClusterManager.OnClusterClickListener<Place>,
            ClusterManager.OnClusterItemClickListener<Place>,
            ClusterManager.OnClusterItemInfoWindowClickListener<Place> {

        @Override
        public boolean onClusterClick(Cluster<Place> cluster) {
            getMap().animateCamera(CameraUpdateFactory.zoomIn(), 1000, new GoogleMap.CancelableCallback() {
                @Override
                public void onFinish() {

                }

                @Override
                public void onCancel() {

                }
            });
            return true;
        }

        @Override
        public boolean onClusterItemClick(Place place) {
            clickedMarker = place.getId();
            return false;
        }

        @Override
        public void onClusterItemInfoWindowClick(Place place) {
            DetailPlaceActivity.start(getActivity(), place.getId());
        }
    }

    private class InfoAdapter implements GoogleMap.InfoWindowAdapter {

        @Override
        public View getInfoWindow(Marker marker) {
            Place place = placeMap.get(clickedMarker);
            View view = View.inflate(getActivity(), R.layout.item_info_window, null);
            TextView title = (TextView) view.findViewById(R.id.info_window_title);
            TextView desc = (TextView) view.findViewById(R.id.info_window_desc);
            title.setText(place.getTitle());
            desc.setText(place.getDescription());
            return view;
        }

        @Override
        public View getInfoContents(Marker marker) {
            Place place = placeMap.get(clickedMarker);
            View view = View.inflate(getActivity(), R.layout.item_info_window, null);
            TextView title = (TextView) view.findViewById(R.id.info_window_title);
            TextView desc = (TextView) view.findViewById(R.id.info_window_desc);
            title.setText(place.getTitle());
            desc.setText(place.getDescription());
            return view;
        }


    }*/
}
