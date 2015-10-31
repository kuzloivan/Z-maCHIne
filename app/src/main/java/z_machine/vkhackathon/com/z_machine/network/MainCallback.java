package z_machine.vkhackathon.com.z_machine.network;

import android.util.Log;

import retrofit.Callback;
import retrofit.Response;
import retrofit.Retrofit;
import z_machine.vkhackathon.com.z_machine.core.bus.BusProvider;
import z_machine.vkhackathon.com.z_machine.core.bus.event.BaseEvent;
import z_machine.vkhackathon.com.z_machine.core.bus.event.ErrorEvent;

public final class MainCallback<T> implements Callback<T> {

    private static final String TAG = "Network response log";

    private final int requestId;

    public MainCallback(int requestId) {
        this.requestId = requestId;
    }

    @Override
    public void onResponse(Response<T> response, Retrofit retrofit) {
        if (response.isSuccess()) {
            BusProvider.getInstance().post(new BaseEvent<T>(requestId, response.body()));
            Log.i(TAG, response.message());
        } else {
            onError(new Throwable(response.message()));
            Log.e(TAG, response.message());
        }
    }

    @Override
    public void onFailure(Throwable t) {
        Log.e(TAG, t.toString());
        onError(t);
    }

    protected void onError(Throwable t) {
        BusProvider.getInstance().post(new ErrorEvent(t, requestId));
    }
}
