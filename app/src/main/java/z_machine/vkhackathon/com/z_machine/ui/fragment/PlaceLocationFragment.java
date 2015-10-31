package z_machine.vkhackathon.com.z_machine.ui.fragment;

import android.app.Activity;
import android.os.Bundle;

import com.google.android.gms.maps.SupportMapFragment;

import z_machine.vkhackathon.com.z_machine.core.appinterface.AppBridge;

/**
 * Created by Kuzlo on 31.10.2015.
 */
public class PlaceLocationFragment extends SupportMapFragment {

    private AppBridge appBridge;

    @Override
    public void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        appBridge = (AppBridge) getActivity().getApplicationContext();
    }



}
