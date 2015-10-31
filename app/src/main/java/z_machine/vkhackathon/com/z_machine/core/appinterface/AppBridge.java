package z_machine.vkhackathon.com.z_machine.core.appinterface;

import z_machine.vkhackathon.com.z_machine.core.SharedHelper;

public interface AppBridge {

    NetBridge getNetBridge();

    SharedHelper getSharedHelper();
}
