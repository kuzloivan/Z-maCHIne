package z_machine.vkhackathon.com.z_machine.ui.adapter.place;

import android.content.Context;
import android.text.TextUtils;
import android.view.View;
import android.widget.Filter;
import android.widget.Filterable;
import android.widget.ImageView;
import android.widget.TextView;

import com.nostra13.universalimageloader.core.ImageLoader;

import java.util.ArrayList;
import java.util.List;

import z_machine.vkhackathon.com.z_machine.R;
import z_machine.vkhackathon.com.z_machine.model.Image;
import z_machine.vkhackathon.com.z_machine.model.Place;
import z_machine.vkhackathon.com.z_machine.ui.adapter.abs.ParallaxAdapter;
import z_machine.vkhackathon.com.z_machine.ui.adapter.abs.VH;

public final class PlaceAdapter extends ParallaxAdapter<Place, PlaceAdapter.ViewHolder>
        implements Filterable {

    private final ImageLoader imageLoader;
    private List<Place> tempList;

    public PlaceAdapter(Context context) {
        super(context, R.layout.item_place);
        imageLoader = ImageLoader.getInstance();
    }

    private void postFilter(List<Place> filterItems) {
        if (tempList == null) {
            tempList = new ArrayList<>(items);
        }
        addAndClear(filterItems);
    }

    private void removeFilter() {
        if (tempList != null) {
            addAndClear(tempList);
            tempList = null;
        }
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

    @Override
    public Filter getFilter() {
        return new PlaceFilter();
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

    private final class PlaceFilter extends Filter {

        @Override
        protected FilterResults performFiltering(CharSequence constraint) {
            if (TextUtils.isEmpty(constraint)) return null;
            final FilterResults filterResults = new FilterResults();
            filterResults.values = getFilterList(constraint.toString());
            return filterResults;
        }

        @SuppressWarnings("unchecked")
        @Override
        protected void publishResults(CharSequence constraint, FilterResults results) {
            if (results != null) {
                postFilter((List<Place>) results.values);
            } else {
                removeFilter();
            }
        }

        private List<Place> getFilterList(String constraint) {
            final List<Place> places = new ArrayList<>();
            if (tempList != null) {
                findContains(tempList, places, constraint);
            } else {
                findContains(items, places, constraint);
            }
            return places;
        }

        private void findContains(List<Place> from, List<Place> to, String constraint) {
            for (Place place : from) {
                if (place.getTitle().toLowerCase().contains(constraint.toLowerCase())) {
                    to.add(place);
                }
            }
        }
    }

}
