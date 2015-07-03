package dachuan.com.tianyan.view.widget;

import android.animation.Animator;
import android.animation.AnimatorInflater;
import android.animation.AnimatorListenerAdapter;
import android.animation.AnimatorSet;
import android.animation.IntEvaluator;
import android.animation.ObjectAnimator;
import android.animation.ValueAnimator;
import android.content.Context;
import android.graphics.Path;
import android.util.AttributeSet;
import android.view.animation.Animation;
import android.view.animation.ScaleAnimation;

import com.facebook.drawee.view.DraweeView;
import com.facebook.drawee.view.SimpleDraweeView;

import dachuan.com.tianyan.R;

/**
 * Created by linsj on 15-7-3.
 */
public class ZoomDraweeView extends SimpleDraweeView {

    Animator.AnimatorListener listener;

    private int width;
    private int height;

    public ZoomDraweeView(Context context) {
        super(context);
    }

    public ZoomDraweeView(Context context, AttributeSet attrs) {
        super(context, attrs);
    }

    public ZoomDraweeView(Context context, AttributeSet attrs, int defStyle) {
        super(context, attrs, defStyle);
    }

    public void actZoom(float multiple, int duration) {

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
        animatorSet.start();
    }

    public void setListener(Animator.AnimatorListener listener) {
        this.listener = listener;
    }

    public void setWidth(int width) {
        this.getLayoutParams().width = width;
        this.requestLayout();
    }

    public void setHeight(int height) {
        this.getLayoutParams().height = height;
        this.requestLayout();
    }
}
