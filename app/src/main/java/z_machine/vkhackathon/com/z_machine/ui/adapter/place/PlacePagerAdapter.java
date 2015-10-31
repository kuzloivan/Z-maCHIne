package z_machine.vkhackathon.com.z_machine.ui.adapter.place;

import android.content.Context;
import android.support.v4.view.PagerAdapter;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.ImageView;

import com.nostra13.universalimageloader.core.ImageLoader;

import java.util.List;

import z_machine.vkhackathon.com.z_machine.R;
import z_machine.vkhackathon.com.z_machine.model.Image;
import z_machine.vkhackathon.com.z_machine.utils.ImageUtils;

public final class PlacePagerAdapter extends PagerAdapter {

    private final List<Image> urls;
    private final LayoutInflater layoutInflater;
    private final ImageLoader imageLoader;

    public PlacePagerAdapter(List<Image> urls, Context context) {
        this.urls = urls;
        layoutInflater = LayoutInflater.from(context);
        imageLoader = ImageLoader.getInstance();
        imageLoader.init(ImageUtils.createImageLoaderConfiguration(context));
    }

    @Override
    public int getCount() {
        return urls.size();
    }

    @Override
    public Object instantiateItem(ViewGroup container, int position) {
        final View item = layoutInflater.inflate(R.layout.item_page, container, false);
        final ImageView imageView = (ImageView) item.findViewById(R.id.ivEventPage);
        imageLoader.displayImage(urls.get(position).getImage(), imageView);
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
