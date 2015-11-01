package z_machine.vkhackathon.com.z_machine.ui.activity;

import android.app.Activity;
import android.content.Context;
import android.content.Intent;
import android.os.Bundle;
import android.support.v4.view.PagerAdapter;
import android.support.v4.view.ViewPager;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.ImageView;

import com.nostra13.universalimageloader.core.ImageLoader;

import z_machine.vkhackathon.com.z_machine.R;

/**
 * Created by Ann on 01.11.2015.
 */
public final class FullScreenActivity extends Activity {

    private static final String PHOTO_URL = "photo_url";
    private static final String URLS = "photo_urls";

    public static void start(Activity activity, int urlId, String[] urls) {
        final Bundle bundle = new Bundle();
        final Intent intent = new Intent(activity, FullScreenActivity.class);
        bundle.putInt(PHOTO_URL, urlId);
        bundle.putStringArray(URLS, urls);
        intent.putExtras(bundle);
        activity.startActivity(intent);
    }

    private int position;
    private String[] urls;
    private ViewPager viewPager;
    private ViewPagerAdapter viewPagerAdapter;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_fullscreen);
        viewPager = (ViewPager) findViewById(R.id.pagerPhoto);
        final Bundle bundle = getIntent().getExtras();
        position = bundle.getInt(PHOTO_URL);
                urls = bundle.getStringArray(URLS);
        viewPagerAdapter = new ViewPagerAdapter(urls, getApplicationContext());
        viewPager.setAdapter(viewPagerAdapter);
        viewPager.setCurrentItem(position);
    }

    private static class ViewPagerAdapter extends PagerAdapter {

        private final String[] urls;
        private final LayoutInflater layoutInflater;
        private final ImageLoader imageLoader;

        public ViewPagerAdapter(String[] urls, Context context) {
            this.urls = urls;
            layoutInflater = LayoutInflater.from(context);
            imageLoader = ImageLoader.getInstance();
        }

        @Override
        public int getCount() {
            return urls.length;
        }

        @Override
        public Object instantiateItem(ViewGroup container, int position) {
            final View item = layoutInflater.inflate(R.layout.item_photo, container, false);
            final ImageView imageView = (ImageView) item.findViewById(R.id.fullscreen_iv);
            imageLoader.displayImage(urls[position], imageView);
            container.addView(imageView);
            return imageView;
        }

        @Override
        public void destroyItem(ViewGroup container, int position, Object object) {
            container.removeView((View) object);
        }

        @Override
        public boolean isViewFromObject(View view, Object object) {
            return view == object;
        }
    }
}
