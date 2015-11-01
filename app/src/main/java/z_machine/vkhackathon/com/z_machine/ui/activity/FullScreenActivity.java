package z_machine.vkhackathon.com.z_machine.ui.activity;

import android.app.Activity;
import android.content.Intent;
import android.os.Bundle;
import android.widget.ImageView;

import com.nostra13.universalimageloader.core.ImageLoader;

import z_machine.vkhackathon.com.z_machine.R;

/**
 * Created by Ann on 01.11.2015.
 */
public class FullScreenActivity extends BaseActivity {

    private static final String PHOTO_URL = "photo_url";

    public static void start(Activity activity, String url) {
        final Bundle bundle = new Bundle();
        final Intent intent = new Intent(activity, FullScreenActivity.class);
        bundle.putString(PHOTO_URL, url);
        intent.putExtras(bundle);
        activity.startActivity(intent);
    }

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_fullscreen);
        homeAsUp();
        final Bundle bundle = getIntent().getExtras();
        ImageLoader.getInstance().displayImage(bundle.getString(PHOTO_URL),(ImageView)findViewById(R.id.fullscreen_iv));
    }
}
