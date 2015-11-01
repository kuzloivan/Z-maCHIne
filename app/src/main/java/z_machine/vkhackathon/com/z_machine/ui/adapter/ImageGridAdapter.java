package z_machine.vkhackathon.com.z_machine.ui.adapter;

import android.app.Activity;
import android.content.Context;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.BaseAdapter;
import android.widget.ImageView;

import com.nostra13.universalimageloader.core.ImageLoader;
import com.vk.sdk.api.model.VKApiPhoto;

import java.util.ArrayList;
import java.util.Arrays;
import java.util.Collections;
import java.util.List;

import z_machine.vkhackathon.com.z_machine.R;
import z_machine.vkhackathon.com.z_machine.ui.activity.FullScreenActivity;

/**
 * Created by Kuzlo on 31.10.2015.
 */
public class ImageGridAdapter extends BaseAdapter {

    List<VKApiPhoto> mThumbIds;
    private ImageLoader imageLoader;
    private final LayoutInflater layoutInflater;

    public ImageGridAdapter(Context context) {
        layoutInflater = LayoutInflater.from(context);
        imageLoader = ImageLoader.getInstance();
    }

    public void setmThumbIds(List<VKApiPhoto> mThumbIds) {
        this.mThumbIds = mThumbIds;
        notifyDataSetChanged();
    }

    public String[] getUrls() {
        ArrayList<String> urls = new ArrayList<>();
        for (VKApiPhoto vkApiPhoto : mThumbIds) {
            urls.add(vkApiPhoto.photo_1280);
        }
        String[] results = new String[urls.size()];
        urls.toArray(results);
        return results;
    }

    @Override
    public int getCount() {
        if (mThumbIds == null) {
            return 0;
        }
        return mThumbIds.size();
    }

    @Override
    public VKApiPhoto getItem(int position) {
        return mThumbIds.get(position);
    }

    @Override
    public long getItemId(int position) {
        return position;
    }

    @Override
    public View getView(int position, View convertView, ViewGroup parent) {
        ViewHolder viewHolder;
        if (convertView == null) {
            convertView = layoutInflater.inflate(R.layout.item_grid, parent, false);
            viewHolder = new ViewHolder();
            viewHolder.ivPage = (ImageView) convertView.findViewById(R.id.ivEventGrid);
            convertView.setTag(viewHolder);
        } else {
            viewHolder = (ViewHolder) convertView.getTag();
        }
        imageLoader.displayImage(mThumbIds.get(position).photo_604, viewHolder.ivPage);
        return convertView;
    }

    private static final class ViewHolder {
        private ImageView ivPage;
    }
}
