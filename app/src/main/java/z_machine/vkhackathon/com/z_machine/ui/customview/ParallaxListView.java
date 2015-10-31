package z_machine.vkhackathon.com.z_machine.ui.customview;

import android.content.Context;
import android.util.AttributeSet;
import android.view.View;
import android.widget.AbsListView;
import android.widget.ListAdapter;
import android.widget.ListView;

import z_machine.vkhackathon.com.z_machine.ui.adapter.abs.ParallaxAdapter;

public final class ParallaxListView extends ListView {

    private OnScrollListener onScrollListener;

    public ParallaxListView(Context context) {
        super(context);
        setListeners();
    }

    public ParallaxListView(Context context, AttributeSet attrs) {
        super(context, attrs);
        setListeners();
    }

    public ParallaxListView(Context context, AttributeSet attrs, int defStyle) {
        super(context, attrs, defStyle);
        setListeners();
    }

    @Override
    public void setAdapter(ListAdapter adapter) {
        super.setAdapter(adapter);
        if (!(adapter instanceof ParallaxAdapter)) {
            throw new IllegalArgumentException("Use ParallaxAdapter!");
        }
    }

    private void setListeners() {
        super.setOnScrollListener(new OnScrollListener() {

            private int oldTop;
            private int oldFirstVisibleItem;

            @Override
            public void onScrollStateChanged(AbsListView view, int scrollState) {
                if (onScrollListener != null) {
                    onScrollListener.onScrollStateChanged(view, scrollState);
                }
            }

            @Override
            public void onScroll(AbsListView view, int firstVisibleItem, int visibleItemCount, int totalItemCount) {
                if (onScrollListener != null) {
                    onScrollListener.onScroll(view, firstVisibleItem, visibleItemCount, totalItemCount);
                }
                onDetectedListScroll(view, firstVisibleItem);
            }

            private void onDetectedListScroll(AbsListView absListView, int firstVisibleItem) {
                View view = absListView.getChildAt(0);
                int top = (view == null) ? 0 : view.getTop();
                if (firstVisibleItem == oldFirstVisibleItem) {
                    if (top > oldTop) {
                        upScroll((ParallaxAdapter) absListView.getAdapter());
                    } else if (top < oldTop) {
                        downScroll((ParallaxAdapter) absListView.getAdapter());
                    }
                } else {
                    if (firstVisibleItem < oldFirstVisibleItem) {
                        upScroll((ParallaxAdapter) absListView.getAdapter());
                    } else {
                        downScroll((ParallaxAdapter) absListView.getAdapter());
                    }
                }
                oldTop = top;
                oldFirstVisibleItem = firstVisibleItem;
            }

            private void upScroll(ParallaxAdapter adapter) {
                final int items = getLastVisiblePosition() - getFirstVisiblePosition();
                for (int i = 0; i < items; i++) {
                    adapter.onUpScroll(getChildAt(i));
                }
            }

            private void downScroll(ParallaxAdapter adapter) {
                final int items = getLastVisiblePosition() - getFirstVisiblePosition();
                for (int i = 0; i < items; i++) {
                    adapter.onDownScroll(getChildAt(i));
                }
            }
        });
    }

    @Override
    public void setOnScrollListener(OnScrollListener onScrollListener) {
        this.onScrollListener = onScrollListener;
    }

}
