package dachuan.com.tianyan.view.widget;

import android.content.Context;
import android.util.AttributeSet;
import android.view.animation.Animation;
import android.view.animation.ScaleAnimation;

import com.facebook.drawee.view.DraweeView;
import com.facebook.drawee.view.SimpleDraweeView;

/**
 * Created by linsj on 15-7-3.
 */
public class ZoomDraweeView extends SimpleDraweeView {

    Animation.AnimationListener listener;

    public ZoomDraweeView(Context context) {
        super(context);
    }

    public ZoomDraweeView(Context context, AttributeSet attrs) {
        super(context, attrs);
    }

    public ZoomDraweeView(Context context, AttributeSet attrs, int defStyle) {
        super(context, attrs, defStyle);
    }

    public void ActZoom(float multiple, int duration) {
        ScaleAnimation scaleAnimation = new ScaleAnimation(1, multiple, 1, multiple, ScaleAnimation.RELATIVE_TO_SELF, 0.5f, ScaleAnimation.RELATIVE_TO_SELF, 0.5f);
        scaleAnimation.setDuration(duration);
        if (listener != null) {
            scaleAnimation.setAnimationListener(listener);
        }
        this.startAnimation(scaleAnimation);
    }

    public void setListener(Animation.AnimationListener listener) {
        this.listener = listener;
    }

}
