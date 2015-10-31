package z_machine.vkhackathon.com.z_machine.ui.adapter.place;

import android.content.Context;
import android.view.View;
import android.widget.ImageView;
import android.widget.TextView;

import com.nostra13.universalimageloader.core.ImageLoader;

import java.util.List;

import z_machine.vkhackathon.com.z_machine.R;
import z_machine.vkhackathon.com.z_machine.model.Image;
import z_machine.vkhackathon.com.z_machine.model.Place;
import z_machine.vkhackathon.com.z_machine.ui.adapter.abs.ParallaxAdapter;
import z_machine.vkhackathon.com.z_machine.ui.adapter.abs.VH;

public final class PlaceAdapter extends ParallaxAdapter<Place, PlaceAdapter.ViewHolder> {

    private final ImageLoader imageLoader;

    public PlaceAdapter(Context context) {
        super(context, R.layout.item_place);
        imageLoader = ImageLoader.getInstance();
//        imageLoader.init(ImageUtils.createImageLoaderConfiguration(context));
    }

    @Override
    public void onUpScroll(View childView) {
        upScroll(((ViewHolder) childView.getTag()).ivBackground);
    }

    @Override
    public void onDownScroll(View childView) {
        downScroll(((ViewHolder) childView.getTag()).ivBackground);
    }

    @Override
    protected ViewHolder createViewHolder(View inflatedView) {
        return new ViewHolder(inflatedView, imageLoader);
    }

    protected final static class ViewHolder extends VH.AbsVH<Place> {

        private static final String ST_PETERSBURG = "Санкт-Петербург";

        private final TextView tvCity;
        private final TextView tvName;
        private final TextView tvAddress;
        private final ImageView ivBackground;
        private final ImageLoader imageLoader;

        public ViewHolder(View view, ImageLoader imageLoader) {
            super(view);
            this.imageLoader = imageLoader;
            tvCity = (TextView) view.findViewById(R.id.tvPlaceCity);
            tvName = (TextView) view.findViewById(R.id.tvPlaceName);
            tvAddress = (TextView) view.findViewById(R.id.tvPlaceAddress);
            ivBackground = (ImageView) view.findViewById(R.id.ivPlaceBackground);
        }

        @Override
        public void fill(Place model) {
            ivBackground.setImageBitmap(null);
            if (isImageTrue(model.getImages())) {
                imageLoader.displayImage(model.getImages().get(0).getImage(), ivBackground);
            }
            tvName.setText(model.getTitle());
            tvAddress.setText(model.getAddress());
            tvCity.setText(ST_PETERSBURG);
        }

        private boolean isImageTrue(List<Image> images) {
            return images.size() > 0;
        }

    }

}
