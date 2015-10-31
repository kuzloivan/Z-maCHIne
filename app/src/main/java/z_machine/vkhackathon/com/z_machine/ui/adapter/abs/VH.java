package z_machine.vkhackathon.com.z_machine.ui.adapter.abs;

import android.view.View;

public abstract class VH {

    public abstract static class AbsVH<T> {

        public AbsVH(View view) {
        }

        public abstract void fill(T model);
    }
}
