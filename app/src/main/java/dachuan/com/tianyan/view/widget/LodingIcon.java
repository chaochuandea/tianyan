package dachuan.com.tianyan.view.widget;

import android.content.Context;
import android.graphics.Canvas;
import android.util.AttributeSet;
import android.widget.TextView;

/**
 * Created by maibenben on 2015/7/6.
 */
public class LodingIcon extends TextView {

    public LodingIcon(Context context) {
        super(context);
    }

    public LodingIcon(Context context, AttributeSet attrs) {
        super(context, attrs);
    }

    public LodingIcon(Context context, AttributeSet attrs, int defStyleAttr) {
        super(context, attrs, defStyleAttr);
    }

    @Override
    protected void onDraw(Canvas canvas) {
        canvas.save();
        canvas.rotate(45);
        super.onDraw(canvas);
        canvas.restore();
    }
}
