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
    private Context context;
    public BlurView(Context context) {
        super(context);
        this.context = context;
    }

    public BlurView(Context context, AttributeSet attrs) {
        super(context, attrs);
        this.context = context;
    }

    public BlurView(Context context, AttributeSet attrs, int defStyleAttr) {
        super(context, attrs, defStyleAttr);
        this.context = context;
    }


    public void setImageUri(Uri uri){
        int color =  getResources().getColor(R.color.text_color_dark_secondary_20);
        Postprocessor postprocessor = new BasePostprocessor() {
            @Override
            public void process(Bitmap bitmap) {
                        Blur.apply(context, bitmap, 8);

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
}
