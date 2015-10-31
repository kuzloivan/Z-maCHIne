package z_machine.vkhackathon.com.z_machine.ui.fragment;

import android.app.Activity;
import android.os.Bundle;

import com.google.android.gms.maps.SupportMapFragment;
import com.google.android.gms.maps.model.LatLng;

import z_machine.vkhackathon.com.z_machine.core.appinterface.AppBridge;

/**
 * Created by Kuzlo on 31.10.2015.
 */
public class PlaceLocationFragment extends SupportMapFragment {

    private static final int GET_PLACE_LIST = 45;
    private AppBridge appBridge;
    private LatLng loc = new LatLng(59.91979700000001, 30.334911999999996);

    @Override
    public void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        appBridge = (AppBridge) getActivity().getApplicationContext();
    }



}
