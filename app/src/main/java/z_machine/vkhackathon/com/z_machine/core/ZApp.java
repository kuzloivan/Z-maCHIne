package z_machine.vkhackathon.com.z_machine.core;

import android.app.Application;

import z_machine.vkhackathon.com.z_machine.core.appinterface.AppBridge;

public final class ZApp extends Application implements AppBridge {

    private SharedHelper sharedHelper;
    @Override
    public void onCreate() {
        super.onCreate();

        sharedHelper = new SharedHelper(this);
    }

    @Override
    public SharedHelper getSharedHelper() {
        return sharedHelper;
    }
}
