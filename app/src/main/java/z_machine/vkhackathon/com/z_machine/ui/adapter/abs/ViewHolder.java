package z_machine.vkhackathon.com.z_machine.ui.adapter.abs;

import android.view.View;

public abstract class ViewHolder {

    public abstract static class VH<T> {

        public VH(View view) {
        }

        public abstract void fill(T model);
    }
}
