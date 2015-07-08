package dachuan.com.tianyan.view.widget;

import android.content.Context;
import android.graphics.Bitmap;
import android.graphics.Canvas;
import android.graphics.Paint;
import android.graphics.drawable.BitmapDrawable;
import android.util.AttributeSet;
import android.view.View;
import android.view.ViewTreeObserver;

import dachuan.com.tianyan.util.Blur;
import rx.android.schedulers.AndroidSchedulers;
import rx.schedulers.Schedulers;
import rx.subjects.PublishSubject;

/**
 * Created by maibenben on 2015/7/8.
 */
public class BlurView extends View{
    private  PublishSubject<Bitmap> subject = PublishSubject.create();
    public BlurView(Context context) {
        super(context);
        subject.subscribeOn(Schedulers.io()).observeOn(AndroidSchedulers.mainThread());
        setUp();
    }

    public BlurView(Context context, AttributeSet attrs) {
        super(context, attrs);
        subject.subscribeOn(Schedulers.io()).observeOn(AndroidSchedulers.mainThread());
        setUp();
    }

    public BlurView(Context context, AttributeSet attrs, int defStyleAttr) {
        super(context, attrs, defStyleAttr);
        subject.subscribeOn(Schedulers.io()).observeOn(AndroidSchedulers.mainThread());
        setUp();
    }

    private int radius= 8;
    private   float scaleFactor = 8;
    public BlurView setRadius(int radius) {
        this.radius = radius;
        return this;
    }

    public BlurView setScalFactor(  float scaleFactor){
        this.scaleFactor = scaleFactor;
        return this;
    }



    private void setUp(){
        subject.map(integer1 -> {


            Bitmap overlay = Bitmap.createBitmap((int) (getMeasuredWidth() / scaleFactor),
                    (int) (getMeasuredHeight() / scaleFactor), Bitmap.Config.ARGB_8888);
            Canvas canvas = new Canvas(overlay);
            canvas.translate(-getLeft() / scaleFactor, -getTop() / scaleFactor);
            canvas.scale(1 / scaleFactor, 1 / scaleFactor);
            Paint paint = new Paint();
            paint.setFlags(Paint.FILTER_BITMAP_FLAG);
            canvas.drawBitmap(integer1, 0, 0, paint);

            return   overlay = Blur.apply(getContext(),overlay,radius);

        }).subscribe(integer -> {
            int sdk = android.os.Build.VERSION.SDK_INT;
            if(sdk < android.os.Build.VERSION_CODES.JELLY_BEAN) {
                setBackgroundDrawable(new BitmapDrawable(getResources(), integer));
            } else {
                setBackground(new BitmapDrawable(getResources(), integer));
            }
        });
    }
    private View view;
    public BlurView setTarget(View view){
        this.view = view;
       return this;
    }
    private boolean sync = false;
    public BlurView setSync(boolean sync) {
        this.sync = sync;
        return this;
    }

    public void refresh(){
        applay();
    }

    private void applay(){
        view.getViewTreeObserver().addOnPreDrawListener(new ViewTreeObserver.OnPreDrawListener() {
            @Override
            public boolean onPreDraw() {
                if (!sync){
                    view.getViewTreeObserver().removeOnPreDrawListener(this);
                }

                view.buildDrawingCache();
                blur(view.getDrawingCache());
                return true;
            }
        });
    }

    private void blur(Bitmap bkg) {
        subject.onNext(bkg);
    }
}
