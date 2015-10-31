package z_machine.vkhackathon.com.z_machine.ui.adapter.abs;

import android.content.Context;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.BaseAdapter;

import java.util.ArrayList;
import java.util.List;

public abstract class GenericAdapter<T, VHType extends VH.AbsVH<T>> extends BaseAdapter {

    private final LayoutInflater layoutInflater;
    private final List<T> items;
    private final int layoutResId;

    protected abstract VHType createViewHolder(View inflatedView);

    public GenericAdapter(Context context, int layoutResId) {
        this.layoutResId = layoutResId;
        layoutInflater = LayoutInflater.from(context);
        items = new ArrayList<>();
    }

    public void add(T item) {
        items.add(item);
        notifyDataSetChanged();
    }

    public void add(List<T> items) {
        this.items.addAll(items);
        notifyDataSetChanged();
    }

    public void addAndClear(List<T> items) {
        this.items.clear();
        add(items);
    }

    @Override
    public final T getItem(int position) {
        return items.get(position);
    }

    @Override
    public int getCount() {
        return items.size();
    }

    @Override
    public long getItemId(int position) {
        return position;
    }

    @Override
    @SuppressWarnings("unchecked")
    public final View getView(int position, View convertView, ViewGroup parent) {
        VHType vhType;
        T item = getItem(position);
        if (convertView == null) {
            convertView = layoutInflater.inflate(layoutResId, parent, false);
            vhType = createViewHolder(convertView);
            convertView.setTag(vhType);
        } else {
            vhType = (VHType) convertView.getTag();
        }
        vhType.fill(item);
        return convertView;
    }
}
