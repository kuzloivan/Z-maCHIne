package z_machine.vkhackathon.com.z_machine.ui.fragment;

import android.os.Bundle;
import android.support.v4.app.Fragment;
import android.widget.Toast;

import z_machine.vkhackathon.com.z_machine.core.appinterface.AppBridge;

public abstract class BaseFragment extends Fragment {

    protected AppBridge appBridge;

    @Override
    public void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        appBridge = (AppBridge) getActivity().getApplicationContext();
    }

    protected void toast(String message) {
        Toast.makeText(getContext(), message, Toast.LENGTH_SHORT).show();
    }
}
