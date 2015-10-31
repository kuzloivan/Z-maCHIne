package z_machine.vkhackathon.com.z_machine.utils;

import android.content.Context;
import android.graphics.Bitmap;
import android.graphics.Canvas;
import android.graphics.Paint;
import android.graphics.PorterDuff;
import android.graphics.PorterDuffXfermode;
import android.graphics.Rect;
import android.graphics.RectF;

import com.nostra13.universalimageloader.core.DisplayImageOptions;
import com.nostra13.universalimageloader.core.ImageLoaderConfiguration;
import com.nostra13.universalimageloader.core.assist.LoadedFrom;
import com.nostra13.universalimageloader.core.display.BitmapDisplayer;
import com.nostra13.universalimageloader.core.imageaware.ImageAware;
import com.nostra13.universalimageloader.core.imageaware.ImageViewAware;

public final class ImageUtils {

    public static ImageLoaderConfiguration createImageLoaderConfiguration(Context context) {
        return new ImageLoaderConfiguration.Builder(context.getApplicationContext())
                //.denyCacheImageMultipleSizesInMemory()
                .defaultDisplayImageOptions(getDefaultOptions())
               // .memoryCacheSize(2*1024)
                .build();
    }

    public static DisplayImageOptions getDefaultOptions() {
        return new DisplayImageOptions.Builder()
                //.cacheInMemory(true)
                .cacheOnDisk(true)
                .resetViewBeforeLoading(true)
                .bitmapConfig(Bitmap.Config.ARGB_8888)
                .build();
    }

    public static DisplayImageOptions getCircleOptions() {
        return new DisplayImageOptions.Builder()
                .displayer(new CircleBitmapDisplayer())
                .cacheInMemory(true)
                .cacheOnDisk(true)
                .bitmapConfig(Bitmap.Config.RGB_565)
                .build();
    }

    private static final class CircleBitmapDisplayer implements BitmapDisplayer {

        @Override
        public void display(Bitmap bitmap, ImageAware imageAware,
                            LoadedFrom loadedFrom) {
            if (!(imageAware instanceof ImageViewAware)) {
                throw new IllegalArgumentException();
            }
            final int ivWidth = imageAware.getWidth();
            final int ivHeight = imageAware.getHeight();
            final int bmWidth = bitmap.getWidth();
            final int bmHeight = bitmap.getHeight();
            final float centerX = (float) ivWidth / 2;
            final float centerY = (float) ivHeight / 2;
            final float radius = ivWidth < ivHeight ? (float) ivWidth / 2 : (float) ivHeight / 2;
            final Rect srcRect;
            if (bmWidth < bmHeight) {
                srcRect = new Rect(0, (bmHeight - bmWidth) / 2, bmWidth, bmWidth
                        + (bmHeight - bmWidth) / 2);
            } else {
                srcRect = new Rect((bmWidth - bmHeight) / 2, 0, bmHeight
                        + (bmWidth - bmHeight) / 2, bmHeight);
            }
            final RectF destRectF = new RectF(0, 0, ivWidth, ivHeight);
            imageAware.setImageBitmap(getCircledBitmap(bitmap, centerX, centerY,
                    radius, srcRect, destRectF, ivWidth, ivHeight));
        }

        private Bitmap getCircledBitmap(Bitmap pBitmap, float pCenterX,
                                        float pCenterY, float pRadius, Rect pSrcRect,
                                        RectF pDestRectF,
                                        int pWidth, int pHeight) {
            final Bitmap output = Bitmap.createBitmap(pWidth, pHeight, Bitmap.Config.ARGB_8888);
            final Canvas canvas = new Canvas(output);
            final Paint paint = new Paint();
            paint.setAntiAlias(true);
            canvas.drawCircle(pCenterX, pCenterY, pRadius, paint);
            paint.setXfermode(new PorterDuffXfermode(PorterDuff.Mode.SRC_IN));
            canvas.drawBitmap(pBitmap, pSrcRect, pDestRectF, paint);
            return output;
        }

    }
}
