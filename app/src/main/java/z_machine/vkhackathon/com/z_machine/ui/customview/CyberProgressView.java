package z_machine.vkhackathon.com.z_machine.ui.customview;

import android.content.Context;
import android.graphics.Canvas;
import android.graphics.Paint;
import android.graphics.RectF;
import android.os.Handler;
import android.util.AttributeSet;
import android.view.View;

import java.util.ArrayList;
import java.util.List;
import java.util.Random;

/**
 * Created by Kuzlo on 04.07.2015.
 */
public final class CyberProgressView extends View {

    private static int[][] colors = {
            {0xFFFFEBEE,0xFFFFCDD2,0xFFEF9A9A,0xFFE57373,0xFFEF5350,0xFFF44336,0xFFE53935,0xFFD32F2F,0xFFC62828,0xFFB71C1C},
            {0xFFE0F7FA,0xFFB2EBF2,0xFF80DEEA,0xFF4DD0E1,0xFF26C6DA,0xFF00BCD4,0xFF00ACC1,0xFF0097A7,0xFF00838F,0xFF006064},
            {0xFFF1F8E9,0xFFDCEDC8,0xFFC5E1A5,0xFFAED581,0xFF9CCC65,0xFF8BC34A,0xFF7CB342,0xFF689F38,0xFF558B2F,0xFF33691E},
            {0xFFFFF3E0,0xFFFFE0B2,0xFFFFCC80,0xFFFFB74D,0xFFFFA726,0xFFFF9800,0xFFFB8C00,0xFFF57C00,0xFFEF6C00,0xFFE65100},
            {0xFFECEFF1,0xFFCFD8DC,0xFFB0BEC5,0xFF90A4AE,0xFF78909C,0xFF607D8B,0xFF546E7A,0xFF455A64,0xFF37474F,0xFF263238}
    };

    private List<Mmodel> mMmodels = new ArrayList<>();

    private Handler mHandler = new Handler();
    private boolean start = false;
    private int defColor;
    private Runnable mRunnable = new Runnable() {
        @Override
        public void run() {
            invalidate();
            mHandler.postDelayed(this,20);
        }
    };

    public CyberProgressView(Context context, AttributeSet attrs) {
        super(context, attrs);
        defColor = new Random().nextInt(5);
    }


    @Override
    protected void onDraw(Canvas canvas) {
        super.onDraw(canvas);
        if (getWidth() <= 0) {
            return;
        }
        if (mMmodels.size() <= 0) {
            for (int i = 0; i < getWidth(); i += 25) {
                mMmodels.add(new Mmodel(i,canvas.getWidth()));
            }
            mHandler.post(mRunnable);
        }
        for(Mmodel m : mMmodels){
            m.draw(canvas);
        }
    }




    private class Mmodel {
        private int sw = 10;
        private Paint mPaint;
        private int startAngle;
        private int endAngle;
        private int s;
        private RectF mRectF;
        private int speed = 1;
        private boolean direction = true;
        boolean d = false;


        public Mmodel(int s, int canwasW) {
            Random random = new Random();
            mPaint = new Paint();
            mPaint.setDither(true);
            mPaint.setDither(true);
            mPaint.setStyle(Paint.Style.STROKE);
            mPaint.setStrokeCap(Paint.Cap.BUTT);
            mPaint.setStrokeJoin(Paint.Join.BEVEL);
            mPaint.setStrokeWidth(sw * 2);
            mPaint.setAntiAlias(true);
            mPaint.setColor(colors[defColor][random.nextInt(10)]);

            startAngle = random.nextInt(180);
            endAngle = startAngle+90;

            mRectF= new RectF(sw + s, sw + s, canwasW - sw - s, canwasW - sw - s);
            speed += random.nextInt(3);
            direction = random.nextBoolean();
        }

        public void draw(Canvas pCanvas) {
            pCanvas.drawArc(mRectF, startAngle, endAngle, false, mPaint);
            if(direction){
                startAngle += speed;
                if(startAngle>360){
                    startAngle = (360-startAngle);
                }
            }
            else {
                startAngle -= speed;
                if(startAngle<0){
                    startAngle = (360-startAngle);
                }
            }
            if(d){
                endAngle -= 1;
                if(endAngle<60){
                    d=false;
                }
            }else {
                endAngle += 1;
                if(endAngle>270){
                    d=true;
                }
            }

        }
    }
}
