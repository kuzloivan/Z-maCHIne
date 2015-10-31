package z_machine.vkhackathon.com.z_machine.utils;

import android.content.Context;
import android.graphics.Bitmap;

import com.nostra13.universalimageloader.core.DisplayImageOptions;
import com.nostra13.universalimageloader.core.ImageLoaderConfiguration;

public final class ImageUtils {

    public static ImageLoaderConfiguration createImageLoaderConfiguration(Context context) {
        return new ImageLoaderConfiguration.Builder(context.getApplicationContext())
                .denyCacheImageMultipleSizesInMemory()
                .defaultDisplayImageOptions(getDefaultOptions())
                .build();
    }

    public static DisplayImageOptions getDefaultOptions() {
        return new DisplayImageOptions.Builder()
                .cacheInMemory(true)
                .cacheOnDisk(true)
                .resetViewBeforeLoading(true)
                .bitmapConfig(Bitmap.Config.ARGB_8888)
                .build();
    }
}
