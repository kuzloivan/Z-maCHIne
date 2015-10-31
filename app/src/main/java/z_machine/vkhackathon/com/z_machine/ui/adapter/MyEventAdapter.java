package z_machine.vkhackathon.com.z_machine.ui.adapter;

import android.content.Context;
import android.view.View;
import android.widget.ImageView;
import android.widget.TextView;

import com.nostra13.universalimageloader.core.ImageLoader;

import z_machine.vkhackathon.com.z_machine.R;
import z_machine.vkhackathon.com.z_machine.model.MyEventRealmObject;
import z_machine.vkhackathon.com.z_machine.model.Place;
import z_machine.vkhackathon.com.z_machine.ui.adapter.abs.ParallaxAdapter;
import z_machine.vkhackathon.com.z_machine.ui.adapter.abs.VH;
import z_machine.vkhackathon.com.z_machine.utils.ImageUtils;
import z_machine.vkhackathon.com.z_machine.utils.SystemUtils;

/**
 * Created by Kuzlo on 31.10.2015.
 */
public class MyEventAdapter extends ParallaxAdapter<MyEventRealmObject, MyEventAdapter.ViewHolder> {


    private final ImageLoader imageLoader;

    public MyEventAdapter(Context context) {
        super(context, R.layout.item_event);
        imageLoader = ImageLoader.getInstance();
        imageLoader.init(ImageUtils.createImageLoaderConfiguration(context));
    }

    @Override
    public void onUpScroll(View childView) {
        upScroll(((ViewHolder) childView.getTag()).ivEventBackground);
    }

    @Override
    public void onDownScroll(View childView) {
        downScroll(((ViewHolder) childView.getTag()).ivEventBackground);
    }

    @Override
    protected ViewHolder createViewHolder(View inflatedView) {
        return new ViewHolder(inflatedView, imageLoader);
    }

    protected final static class ViewHolder extends VH.AbsVH<MyEventRealmObject> {

        private final TextView tvEventName;
        private final TextView tvHashTag;
        private final ImageView ivEventBackground;
        private final ImageLoader imageLoader;

        public ViewHolder(View view, ImageLoader imageLoader) {
            super(view);
            this.imageLoader = imageLoader;
            tvEventName = (TextView) view.findViewById(R.id.tvEventName);
            tvHashTag = (TextView) view.findViewById(R.id.tvEventHashTag);
            ivEventBackground = (ImageView) view.findViewById(R.id.ivEventBackground);
        }

        @Override
        public void fill(MyEventRealmObject model) {
            ivEventBackground.setImageBitmap(null);
            imageLoader.displayImage(model.getEventPhoto(), ivEventBackground);
            tvEventName.setText(model.getEventName());
            tvHashTag.setText(model.getEventHashTag());
        }

    }
}
