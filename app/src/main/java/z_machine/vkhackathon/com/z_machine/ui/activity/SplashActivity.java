package z_machine.vkhackathon.com.z_machine.ui.activity;

import android.content.Intent;
import android.os.Bundle;
import android.util.Log;

import com.vk.sdk.VKAccessToken;
import com.vk.sdk.VKCallback;
import com.vk.sdk.VKScope;
import com.vk.sdk.VKSdk;
import com.vk.sdk.api.VKError;
import com.vk.sdk.api.VKParameters;
import com.vk.sdk.api.VKRequest;
import com.vk.sdk.api.VKResponse;
import com.vk.sdk.api.model.VKApiPhotoAlbum;
import com.vk.sdk.api.model.VKApiUser;

import org.json.JSONArray;
import org.json.JSONException;

import z_machine.vkhackathon.com.z_machine.R;

public class SplashActivity extends BaseActivity {

    private static final String[] myScope = new String[]{
            VKScope.FRIENDS,
            VKScope.WALL,
            VKScope.PHOTOS,
            VKScope.NOHTTPS,
            VKScope.MESSAGES,
            VKScope.DOCS
    };

    @Override
    public void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_splash);
        if(appBridge.getSharedHelper().isValidVkToken()){
            MainActivity.start(this);
        }else {
            VKSdk.login(this, VKScope.PHOTOS);
        }
    }

    @Override
    protected void onActivityResult(int requestCode, int resultCode, Intent data) {
        super.onActivityResult(requestCode, resultCode, data);
        VKSdk.onActivityResult(requestCode, resultCode, data, new VkLoginListener());
    }

    private class VkLoginListener implements VKCallback<VKAccessToken> {

        @Override
        public void onResult(VKAccessToken res) {
            appBridge.getSharedHelper().saveVkToken(res);
            VKRequest vkGetAlbumsRequest = new VKRequest("photos.getAlbums");
            vkGetAlbumsRequest.executeWithListener(albumRequestListener);

        }

        @Override
        public void onError(VKError error) {

        }
    }

    VKRequest.VKRequestListener albumCreateListener = new VKRequest.VKRequestListener() {
        @Override
        public void onComplete(VKResponse response) {
            Log.d("album",response.responseString);
            VKApiPhotoAlbum photoAlbum = null;
            try {
                photoAlbum=  new VKApiPhotoAlbum(response.json.getJSONObject("response"));
                appBridge.getSharedHelper().setAlbumId(photoAlbum.id);
            } catch (JSONException e1) {
                e1.printStackTrace();
            }
            MainActivity.start(SplashActivity.this);
        }

        @Override
        public void onError(VKError error) {

            Log.d("album",error.apiError.errorMessage);
        }
    };

    VKRequest.VKRequestListener albumRequestListener = new VKRequest.VKRequestListener() {
        @Override
        public void onComplete(VKResponse response) {
            Log.d("album",response.responseString);
            boolean hasAlbum=false;
            JSONArray jsonArray = null;
            try {
                jsonArray = response.json.getJSONObject("response").getJSONArray("items");
                int length = jsonArray.length();
                for (int i = 0; i < length; i++) {
                    VKApiPhotoAlbum photoAlbum = new VKApiPhotoAlbum(jsonArray.getJSONObject(i));
                    if(photoAlbum.title.equals(getString(R.string.app_name))){
                        appBridge.getSharedHelper().setAlbumId(photoAlbum.id);
                        hasAlbum=true;
                        break;
                    }
                }
                if (!hasAlbum){
                    VKRequest vkRequest = new VKRequest("photos.createAlbum", VKParameters.from("title",getString(R.string.app_name),"description","Test"));
                    vkRequest.executeWithListener(albumCreateListener);
                }
                else {
                    MainActivity.start(SplashActivity.this);
                }
            } catch (JSONException e) {
                e.printStackTrace();
            }
        }

        @Override
        public void onError(VKError error) {
            Log.d("album",error.apiError.errorMessage);
        }
    };
}
