package z_machine.vkhackathon.com.z_machine.ui.adapter.place;

import android.content.Context;
import android.view.View;
import android.widget.ImageView;
import android.widget.TextView;

import com.nostra13.universalimageloader.core.ImageLoader;

import z_machine.vkhackathon.com.z_machine.R;
import z_machine.vkhackathon.com.z_machine.model.Place;
import z_machine.vkhackathon.com.z_machine.ui.adapter.abs.ParallaxAdapter;
import z_machine.vkhackathon.com.z_machine.ui.adapter.abs.VH;
import z_machine.vkhackathon.com.z_machine.utils.ImageUtils;
import z_machine.vkhackathon.com.z_machine.utils.SystemUtils;

public final class PlaceAdapter extends ParallaxAdapter<Place, PlaceAdapter.ViewHolder> {

    private final ImageLoader imageLoader;

    public PlaceAdapter(Context context) {
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

    protected final static class ViewHolder extends VH.AbsVH<Place> {

        private final TextView tvEventCity;
        private final TextView tvEventName;
        private final TextView tvHashTag;
        private final ImageView ivEventBackground;
        private final ImageLoader imageLoader;

        public ViewHolder(View view, ImageLoader imageLoader) {
            super(view);
            this.imageLoader = imageLoader;
            tvEventCity = (TextView) view.findViewById(R.id.tvEventCity);
            tvEventName = (TextView) view.findViewById(R.id.tvEventName);
            tvHashTag = (TextView) view.findViewById(R.id.tvEventHashTag);
            ivEventBackground = (ImageView) view.findViewById(R.id.ivEventBackground);
        }

        @Override
        public void fill(Place model) {
            ivEventBackground.setImageBitmap(null);
            imageLoader.displayImage(model.getImages().get(0).getImage(), ivEventBackground);
            tvEventName.setText(model.getTitle());
            tvHashTag.setText(SystemUtils.placeHashTagById(model.getId()));
        }

    }

}
