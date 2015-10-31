package z_machine.vkhackathon.com.z_machine.core.appinterface;

import z_machine.vkhackathon.com.z_machine.core.SharedHelper;
import z_machine.vkhackathon.com.z_machine.restore.RestoreManager;

public interface AppBridge {

    NetBridge getNetBridge();

    SharedHelper getSharedHelper();

    RestoreManager getRestoreManager();
}
