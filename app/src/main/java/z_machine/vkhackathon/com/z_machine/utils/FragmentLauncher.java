package z_machine.vkhackathon.com.z_machine.utils;

import android.support.annotation.NonNull;
import android.support.v4.app.Fragment;
import android.support.v4.app.FragmentManager;

import z_machine.vkhackathon.com.z_machine.R;


public class FragmentLauncher {

    public static void replaceMainContainer(FragmentManager fragmentManager,
                                            @NonNull Fragment fragment) {
        fragmentManager.beginTransaction()
                .replace(R.id.flFragmentContainer, fragment)
                .commit();
    }
}
