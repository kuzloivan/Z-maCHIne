package z_machine.vkhackathon.com.z_machine.network.rest;

import com.squareup.okhttp.OkHttpClient;

import java.util.concurrent.TimeUnit;

import retrofit.GsonConverterFactory;
import retrofit.Retrofit;

public final class RestClient {

    private final Api api;

    public RestClient() {
        final OkHttpClient client = new OkHttpClient();
        client.setConnectTimeout(2, TimeUnit.MINUTES);
        client.setReadTimeout(2, TimeUnit.MINUTES);
        final Retrofit retrofit = new Retrofit.Builder()
                .baseUrl(Url.BASE)
                .client(client)
                .addConverterFactory(GsonConverterFactory.create())
                .build();
        api = retrofit.create(Api.class);
    }

    public Api getApi() {
        return api;
    }
}
