package z_machine.vkhackathon.com.z_machine.ui.adapter.abs;

import android.content.Context;
import android.graphics.Matrix;
import android.view.View;
import android.widget.ImageView;

public abstract class ParallaxAdapter<T, VHType extends ViewHolder.VH<T>>
        extends GenericAdapter<T, VHType> {

    private static final float DEFAULT_UP_VALUE = 0.9f;
    private static final float DEFAULT_DOWN_VALUE = -0.9f;

    private Matrix matrix;

    public ParallaxAdapter(Context context, int layoutResId) {
        super(context, layoutResId);
    }

    public abstract void onUpScroll(View childView);

    public abstract void onDownScroll(View childView);

    protected final void upScroll(ImageView imageView, float effectValue) {
        matrix = imageView.getImageMatrix();
        matrix.postTranslate(0, effectValue);
        imageView.setImageMatrix(matrix);
        imageView.invalidate();
    }

    protected final void downScroll(ImageView imageView, float effectValue) {
        matrix = imageView.getImageMatrix();
        matrix.postTranslate(0, effectValue);
        imageView.setImageMatrix(matrix);
        imageView.invalidate();
    }


    protected final void upScroll(ImageView imageView) {
        upScroll(imageView, DEFAULT_UP_VALUE);
    }

    protected final void downScroll(ImageView imageView) {
        downScroll(imageView, DEFAULT_DOWN_VALUE);
    }
}
