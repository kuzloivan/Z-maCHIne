package z_machine.vkhackathon.com.z_machine.core;

import android.app.Application;
import android.graphics.drawable.Drawable;

import com.bettervectordrawable.VectorDrawableCompat;
import com.nostra13.universalimageloader.cache.disc.impl.UnlimitedDiskCache;
import com.nostra13.universalimageloader.cache.disc.naming.HashCodeFileNameGenerator;
import com.nostra13.universalimageloader.core.DisplayImageOptions;
import com.nostra13.universalimageloader.core.ImageLoader;
import com.nostra13.universalimageloader.core.ImageLoaderConfiguration;
import com.vk.sdk.VKSdk;

import z_machine.vkhackathon.com.z_machine.R;
import z_machine.vkhackathon.com.z_machine.core.appinterface.AppBridge;
import z_machine.vkhackathon.com.z_machine.core.appinterface.NetBridge;
import z_machine.vkhackathon.com.z_machine.network.NetManager;
import z_machine.vkhackathon.com.z_machine.restore.RestoreManager;

public final class ZApp extends Application implements AppBridge {

    private SharedHelper sharedHelper;
    private NetBridge netBridge;
    private RestoreManager restoreManager;

    @Override
    public void onCreate() {
        super.onCreate();
        netBridge = new NetManager();
        VKSdk.initialize(this);
        sharedHelper = new SharedHelper(this);
        restoreManager = new RestoreManager(this);
        initVector();



        DisplayImageOptions defaultOptions = new DisplayImageOptions.Builder()
                .cacheInMemory(true).cacheOnDisk(true).build();
        ImageLoaderConfiguration.Builder builder = new ImageLoaderConfiguration.Builder(this);
        builder.defaultDisplayImageOptions(defaultOptions);
        builder.diskCache(new UnlimitedDiskCache(getCacheDir())); // default
        builder.diskCacheSize(50 * 1024 * 1024);
        builder.diskCacheFileCount(150);
        builder.diskCacheFileNameGenerator(new HashCodeFileNameGenerator());
        // builder.memoryCache(new LruMemoryCache(2 * 1024 * 1024));
        builder.writeDebugLogs();
      //  builder.imageDownloader(new CustomImageDownLoader(context,token));
        ImageLoader.getInstance().init(builder.build());

    }

    private void initVector(){
        VectorDrawableCompat.enableResourceInterceptionFor(getResources(),
                R.drawable.comment_vector,
                R.drawable.favourits_vector);
    }

    @Override
    public NetBridge getNetBridge() {
        return netBridge;
    }

    @Override
    public SharedHelper getSharedHelper() {
        return sharedHelper;
    }

    @Override
    public RestoreManager getRestoreManager() {
        return restoreManager;
    }
}
