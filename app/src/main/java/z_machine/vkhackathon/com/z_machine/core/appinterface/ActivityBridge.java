package z_machine.vkhackathon.com.z_machine.core.appinterface;

/**
 * Created by Kuzlo on 31.10.2015.
 */
public interface ActivityBridge  {
    void showProgressDialog();

    void closeDialog();

    AppBridge getAppBridge();
}
