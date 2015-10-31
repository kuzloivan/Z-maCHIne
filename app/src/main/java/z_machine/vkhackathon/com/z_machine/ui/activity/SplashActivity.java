package z_machine.vkhackathon.com.z_machine.ui.activity;

import android.content.Intent;
import android.os.Bundle;
import android.os.PersistableBundle;

import com.vk.sdk.VKAccessToken;
import com.vk.sdk.VKCallback;
import com.vk.sdk.VKScope;
import com.vk.sdk.VKSdk;
import com.vk.sdk.api.VKError;

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
          //  VKSdk.login(this,"photos");
            MainActivity.start(this);
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
            MainActivity.start(SplashActivity.this);
        }

        @Override
        public void onError(VKError error) {

        }
    }
}
