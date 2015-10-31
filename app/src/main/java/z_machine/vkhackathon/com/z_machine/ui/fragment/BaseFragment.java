package z_machine.vkhackathon.com.z_machine.ui.fragment;

import android.os.Bundle;
import android.support.v4.app.Fragment;

import z_machine.vkhackathon.com.z_machine.core.appinterface.AppBridge;

public abstract class BaseFragment extends Fragment {

    protected AppBridge appBridge;

    @Override
    public void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        appBridge = (AppBridge) getActivity().getApplicationContext();
    }
}
