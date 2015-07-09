package dachuan.com.tianyan.view.widget;

import android.content.Context;
import android.util.AttributeSet;
import android.widget.TextView;


/**
 * Created by maibenben on 2015/7/9.
 */
import android.content.Context;
import android.os.Handler;
import android.util.AttributeSet;
import android.view.View;
import android.view.ViewGroup;
import android.view.animation.Animation;
public class AnimateTextView extends TextView {

    public AnimateTextView(Context context) {
        super(context);
    }

    public AnimateTextView(Context context, AttributeSet attrs) {
        super(context, attrs);
    }

    public AnimateTextView(Context context, AttributeSet attrs, int defStyleAttr) {
        super(context, attrs, defStyleAttr);
    }

    public void animateText(String text){
        char[] arr = text.toCharArray();
        Handler handler = new Handler();
        int duration = 800;
        int min = 1;
        int max = 50;
        int delta = duration/arr.length;
       delta =  Math.max(min,Math.min(delta,max));
        for (int i=0;i<arr.length;i++){
            final int positon = i;
            handler.postDelayed(new Runnable() {
                @Override
                public void run() {
                    setText(String.valueOf(arr,0,positon));
                }
            },delta*i);
        }
    }



}
