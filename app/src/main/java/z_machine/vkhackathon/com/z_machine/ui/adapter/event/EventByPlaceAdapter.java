package z_machine.vkhackathon.com.z_machine.ui.adapter.event;

import android.content.Context;
import android.view.View;
import android.widget.ImageView;
import android.widget.TextView;

import com.nostra13.universalimageloader.core.ImageLoader;

import org.w3c.dom.Text;

import z_machine.vkhackathon.com.z_machine.R;
import z_machine.vkhackathon.com.z_machine.model.Event;
import z_machine.vkhackathon.com.z_machine.ui.adapter.abs.GenericAdapter;
import z_machine.vkhackathon.com.z_machine.ui.adapter.abs.VH;
import z_machine.vkhackathon.com.z_machine.utils.ImageUtils;
import z_machine.vkhackathon.com.z_machine.utils.SystemUtils;

public class EventByPlaceAdapter extends GenericAdapter<Event, EventByPlaceAdapter.ViewHolder>{

    private final ImageLoader imageLoader;

    public EventByPlaceAdapter(Context context) {
        super(context, R.layout.item_small_event);
        imageLoader = ImageLoader.getInstance();
        imageLoader.init(ImageUtils.createImageLoaderConfiguration(context));
    }

    @Override
    protected ViewHolder createViewHolder(View inflatedView) {
        return new ViewHolder(inflatedView, imageLoader);
    }

    protected final class ViewHolder extends VH.AbsVH<Event> {

        private final ImageView ivEventImage;
        private final TextView tvEventName;
        private final TextView tvEventHashTag;
        private final ImageLoader imageLoader;

        public ViewHolder(View view, ImageLoader imageLoader) {
            super(view);
            this.imageLoader = imageLoader;
            ivEventImage = (ImageView) view.findViewById(R.id.ivEventBackground);
            tvEventName = (TextView) view.findViewById(R.id.tvEventName);
            tvEventHashTag = (TextView) view.findViewById(R.id.tvEventHashTag);
        }

        @Override
        public void fill(Event model) {
            imageLoader.displayImage(model.getImages().get(0).getImage(), ivEventImage);
            tvEventHashTag.setText(SystemUtils.eventHashTagById(model.getId()));
            tvEventName.setText(model.getTitle());
        }
    }
}
