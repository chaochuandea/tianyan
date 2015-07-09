package dachuan.com.tianyan.view.widget;

import android.content.Context;
import android.graphics.Bitmap;
import android.graphics.Canvas;
import android.graphics.Color;
import android.graphics.Matrix;
import android.graphics.Paint;
import android.graphics.drawable.BitmapDrawable;
import android.net.Uri;
import android.util.AttributeSet;
import android.view.View;
import android.view.ViewTreeObserver;

import com.facebook.drawee.backends.pipeline.Fresco;
import com.facebook.drawee.backends.pipeline.PipelineDraweeController;
import com.facebook.drawee.view.SimpleDraweeView;
import com.facebook.imagepipeline.common.ImageDecodeOptions;
import com.facebook.imagepipeline.common.ResizeOptions;
import com.facebook.imagepipeline.request.BasePostprocessor;
import com.facebook.imagepipeline.request.BaseRepeatedPostProcessor;
import com.facebook.imagepipeline.request.ImageRequest;
import com.facebook.imagepipeline.request.ImageRequestBuilder;
import com.facebook.imagepipeline.request.Postprocessor;

import dachuan.com.tianyan.R;
import dachuan.com.tianyan.util.Blur;
import rx.android.schedulers.AndroidSchedulers;
import rx.schedulers.Schedulers;
import rx.subjects.PublishSubject;

/**
 * Created by maibenben on 2015/7/8.
 */
public class BlurView extends SimpleDraweeView{
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



    private static Bitmap rotateBitmap(Bitmap source, int angle)
    {
        if (angle>0){
            Matrix matrix = new Matrix();
            matrix.postRotate(angle);
            return Bitmap.createBitmap(source, 0, 0, source.getWidth(), source.getHeight(), matrix, true);
        }
       return source;
    }


    public void setImageUri(Uri uri){
        int color =  getResources().getColor(R.color.text_color_dark_secondary_20);
        Postprocessor postprocessor = new BasePostprocessor() {
            @Override
            public void process(Bitmap bitmap) {
                Blur.apply(getContext(), bitmap, radius);
            }
        };

        ImageRequest request = ImageRequestBuilder.newBuilderWithSource(uri)
                .setPostprocessor(postprocessor)
                .setResizeOptions(new ResizeOptions(20, 20))
                .build();

        PipelineDraweeController controller = (PipelineDraweeController)
                Fresco.newDraweeControllerBuilder()
                        .setImageRequest(request)
                        .setOldController(getController())

                                // other setters as you need
                        .build();
        setController(controller);
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
            canvas.drawBitmap(integer1,0,0, paint);

            return  rotateBitmap(Blur.apply(getContext(),overlay,radius),degree) ;

        }).subscribe(integer -> {
            int sdk = android.os.Build.VERSION.SDK_INT;
            if(sdk < android.os.Build.VERSION_CODES.JELLY_BEAN) {
                setBackgroundDrawable(new BitmapDrawable(getResources(), integer));
            } else {
                setBackground(new BitmapDrawable(getResources(), integer));
            }
            invalidate();
        });
    }
    private View view;
    public BlurView setTarget(View view){
        this.view = view;
       return this;
    }
    private boolean sync = false;

    private int degree = 0;
    public BlurView rotate(int degree){
        this.degree = degree;
        return this;
    }
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
                try {
                    if (!sync){
                        view.getViewTreeObserver().removeOnPreDrawListener(this);
                    }

                    view.buildDrawingCache();
                    blur(view.getDrawingCache());
                }catch (IllegalArgumentException e){
                    e.printStackTrace();
                }

                return true;
            }
        });
    }

    private void blur(Bitmap bkg) {
        subject.onNext(bkg);
    }
}
