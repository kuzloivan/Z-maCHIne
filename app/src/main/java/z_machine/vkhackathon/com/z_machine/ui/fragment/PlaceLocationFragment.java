package z_machine.vkhackathon.com.z_machine.ui.fragment;

import android.location.Location;
import android.os.Bundle;
import android.support.v4.app.Fragment;
import android.text.Html;
import android.util.Log;
import android.view.View;
import android.widget.TextView;

import com.google.android.gms.maps.CameraUpdate;
import com.google.android.gms.maps.CameraUpdateFactory;
import com.google.android.gms.maps.GoogleMap;
import com.google.android.gms.maps.OnMapReadyCallback;
import com.google.android.gms.maps.SupportMapFragment;
import com.google.android.gms.maps.model.LatLng;
import com.google.android.gms.maps.model.Marker;
import com.google.maps.android.clustering.Cluster;
import com.google.maps.android.clustering.ClusterManager;
import com.squareup.otto.Subscribe;

import java.util.HashMap;
import java.util.Map;

import z_machine.vkhackathon.com.z_machine.R;
import z_machine.vkhackathon.com.z_machine.core.appinterface.AppBridge;
import z_machine.vkhackathon.com.z_machine.core.bus.BusProvider;
import z_machine.vkhackathon.com.z_machine.core.bus.event.BaseEvent;
import z_machine.vkhackathon.com.z_machine.core.bus.event.ErrorEvent;
import z_machine.vkhackathon.com.z_machine.model.Place;
import z_machine.vkhackathon.com.z_machine.network.rest.response.GetPlaces;
import z_machine.vkhackathon.com.z_machine.ui.activity.place.DetailPlaceActivity;

/**
 * Created by Kuzlo on 31.10.2015.
 */
public class PlaceLocationFragment extends SupportMapFragment implements OnMapReadyCallback {

    private static final int GET_PLACE_LIST = 45;
    private AppBridge appBridge;
    private ClusterManager<Place> clusterManager;
    private Map<Integer, Place> placeMap = new HashMap<>();
    private int clickedMarker;
    public static LatLng loc = new LatLng(59.927315000000014, 30.338272);
    boolean moveMap = true;

    public static Fragment getInstance() {
        return new PlaceLocationFragment();
    }

    @Override
    public void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        appBridge = (AppBridge) getActivity().getApplicationContext();
        getMapAsync(this);
        appBridge.getNetBridge().getPlaces(GET_PLACE_LIST, loc.latitude, loc.longitude);
    }



    @Override
    public void onMapReady(GoogleMap googleMap) {
        initMap(googleMap);
        moveToMyPosition(googleMap);
        initCluster(googleMap);
        addPlacesToMap();
        googleMap.setMyLocationEnabled(false);



    }

    private void initMap(GoogleMap googleMap) {
        googleMap.setMyLocationEnabled(true);
    }

    private void moveToMyPosition(final GoogleMap googleMap) {
        LatLng latLng = new LatLng(loc.latitude, loc.longitude);
        googleMap.moveCamera(CameraUpdateFactory.newLatLng(latLng));
        googleMap.moveCamera(CameraUpdateFactory.zoomTo(15));
    }


    private void initCluster(GoogleMap googleMap) {
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
           // places.getPlaces().add(0,Place.facePlace());
            appBridge.getRestoreManager().addPlaces(places.getPlaces());
            addPlacesToMap();
            return;
        }
    }

    private void addPlacesToMap() {
        for (Place place : appBridge.getRestoreManager().getPlaces()) {
            placeMap.put(place.getId(), place);
        }
        clusterManager.addItems(placeMap.values());
        clusterManager.cluster();
    }

    @Subscribe
    public void networkErrorEventListener(ErrorEvent errorEvent) {

    }


    private class Clicker implements ClusterManager.OnClusterClickListener<Place>,
            ClusterManager.OnClusterItemClickListener<Place>,
            ClusterManager.OnClusterItemInfoWindowClickListener<Place> {

        @Override
        public boolean onClusterClick(Cluster<Place> cluster) {
            LatLng position = cluster.getPosition();
            float zoomLevel = getMap().getCameraPosition().zoom + 1;
            CameraUpdate cameraUpdate = CameraUpdateFactory.newLatLngZoom(position, zoomLevel);
            getMap().animateCamera(cameraUpdate);
            return true;
        }

        @Override
        public boolean onClusterItemClick(Place place) {
            clickedMarker = place.getId();
            return false;
        }

        @Override
        public void onClusterItemInfoWindowClick(Place place) {
            DetailPlaceActivity.start(getActivity(),place.getId(),place.getTitle());
        }
    }

    private class InfoAdapter implements GoogleMap.InfoWindowAdapter {

        @Override
        public View getInfoWindow(Marker marker) {
            Place place = placeMap.get(clickedMarker);
            View view = View.inflate(getActivity(), R.layout.item_info_window, null);
            place.setTitle(place.getTitle().substring(0, 1).toUpperCase() + place.getTitle().substring(1));
            TextView title = (TextView) view.findViewById(R.id.info_window_title);
            TextView desc = (TextView) view.findViewById(R.id.info_window_desc);
            TextView comm = (TextView) view.findViewById(R.id.info_window_coments);
            TextView favour = (TextView) view.findViewById(R.id.info_window_favoutist);
            title.setText(place.getTitle());
            desc.setText(Html.fromHtml(place.getDescription()));
            comm.setText(place.getComments_count() + "");
            favour.setText(place.getFavorites_count() + "");
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


    }
}
