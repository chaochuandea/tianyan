package dachuan.com.tianyan.view.widget;

import android.content.Context;
import android.graphics.Canvas;
import android.graphics.Rect;
import android.graphics.drawable.Drawable;
import android.support.v7.widget.RecyclerView;
import android.view.View;

import dachuan.com.tianyan.R;

/**
 * Created by linsj on 15-7-9.
 */
public class WhiteDecoration extends RecyclerView.ItemDecoration{

    private int space;
    private Context mcontext;

    public WhiteDecoration(Context context,int space) {
        this.space = space;
        mcontext = context;
    }

    @Override
    public void getItemOffsets(Rect outRect, View view, RecyclerView parent, RecyclerView.State state) {
        int position = parent.getChildLayoutPosition(view);
        if(position == 0 || position%2 == 0 )
        {
            if(position == 0 ) {
                outRect.top = space;
            }
            outRect.right = space/2;
            outRect.bottom = space;
        }
        else {
            if (position == 1)
            {
                outRect.top = space;
            }
            outRect.left = space/2;
            outRect.bottom = space;
        }
    }




}
