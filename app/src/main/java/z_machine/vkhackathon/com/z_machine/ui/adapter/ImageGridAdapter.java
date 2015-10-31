package z_machine.vkhackathon.com.z_machine.ui.adapter;

import android.app.Activity;
import android.content.Context;
import android.view.View;
import android.view.ViewGroup;
import android.widget.BaseAdapter;
import android.widget.GridView;
import android.widget.ImageView;

import com.nostra13.universalimageloader.core.ImageLoader;
import com.vk.sdk.api.model.VKApiPhoto;

import java.util.List;

/**
 * Created by Kuzlo on 31.10.2015.
 */
public class ImageGridAdapter extends BaseAdapter {

    // Keep all Images in array
    List<VKApiPhoto> mThumbIds;
    private Context context;
    private ImageLoader imageLoader;

    public ImageGridAdapter(Context context) {
        this.context = context;
        imageLoader = ImageLoader.getInstance();
    }

    public void setmThumbIds(List<VKApiPhoto> mThumbIds) {
        this.mThumbIds = mThumbIds;
        notifyDataSetChanged();
    }

    @Override
    public int getCount() {
        if(mThumbIds==null){
            return 0;
        }
        return mThumbIds.size();
    }

    @Override
    public Object getItem(int position) {
        return mThumbIds.get(position);
    }

    @Override
    public long getItemId(int position) {
        return position;
    }

    @Override
    public View getView(int position, View convertView, ViewGroup parent) {
        ImageView imageView = new ImageView(context);
        imageView.setScaleType(ImageView.ScaleType.CENTER_CROP);
        imageView.setLayoutParams(new GridView.LayoutParams(256, 256));
        imageLoader.displayImage(mThumbIds.get(position).photo_604, imageView);
        return imageView;
    }
}
