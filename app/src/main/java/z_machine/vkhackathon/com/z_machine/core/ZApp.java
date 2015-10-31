package z_machine.vkhackathon.com.z_machine.core;

import android.app.Application;

import z_machine.vkhackathon.com.z_machine.core.appinterface.AppBridge;
import z_machine.vkhackathon.com.z_machine.core.appinterface.NetBridge;
import z_machine.vkhackathon.com.z_machine.network.NetManager;

public final class ZApp extends Application implements AppBridge {

    private SharedHelper sharedHelper;
    private NetBridge netBridge;

    @Override
    public void onCreate() {
        super.onCreate();
        netBridge = new NetManager();
        sharedHelper = new SharedHelper(this);
    }

    @Override
    public NetBridge getNetBridge() {
        return netBridge;
    }

    @Override
    public SharedHelper getSharedHelper() {
        return sharedHelper;
    }
}
