package dachuan.com.tianyan.view.widget;

import android.content.Context;
import android.graphics.Path;
import android.util.AttributeSet;

import com.facebook.drawee.view.DraweeView;
import com.facebook.drawee.view.SimpleDraweeView;
import com.nineoldandroids.animation.Animator;
import com.nineoldandroids.animation.AnimatorSet;
import com.nineoldandroids.animation.ObjectAnimator;
import com.nineoldandroids.animation.ValueAnimator;

import dachuan.com.tianyan.R;

/**
 * Created by linsj on 15-7-3.
 */
public class ZoomDraweeView extends SimpleDraweeView {

    Animator.AnimatorListener listener;
    private float multiple = 1.05f;
    private int duration = 4000;
    private int width;
    private int height;

    public ZoomDraweeView(Context context) {
        super(context);
        actZoom();
    }

    public ZoomDraweeView(Context context, AttributeSet attrs) {
        super(context, attrs);
        actZoom();
    }

    public ZoomDraweeView(Context context, AttributeSet attrs, int defStyle) {
        super(context, attrs, defStyle);
        actZoom();
    }

    private void actZoom() {

        ObjectAnimator scalex = ObjectAnimator.ofFloat(this, "scaleX", 1.0f, multiple)
                .setDuration(duration);
        ObjectAnimator scaley = ObjectAnimator.ofFloat(this, "scaleY", 1.0f, multiple)
                .setDuration(duration);
        AnimatorSet animatorSet = new AnimatorSet();
        animatorSet.setTarget(this);
        animatorSet.playTogether(scalex, scaley);
        if(listener != null) {
            animatorSet.addListener(listener);
        }
        scalex.setRepeatMode(ValueAnimator.REVERSE);
        scalex.setRepeatCount(ValueAnimator.INFINITE);
        scaley.setRepeatMode(ValueAnimator.REVERSE);
        scaley.setRepeatCount(ValueAnimator.INFINITE);
        animatorSet.start();

    }

}
