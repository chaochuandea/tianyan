package dachuan.com.tianyan.view.adapter;

import android.content.Context;
import android.support.v7.widget.RecyclerView;
import android.util.Log;
import android.view.LayoutInflater;
import android.view.MotionEvent;
import android.view.View;
import android.view.ViewGroup;
import android.widget.TextView;
import android.widget.Toast;

import com.facebook.drawee.view.SimpleDraweeView;
import com.nineoldandroids.animation.AnimatorSet;
import com.nineoldandroids.animation.ObjectAnimator;

import java.util.List;

import butterknife.Bind;
import butterknife.ButterKnife;
import dachuan.com.tianyan.R;
import dachuan.com.tianyan.view.widget.BlurView;

/**
 * Created by linsj on 15-7-9.
 */
public class GridAdapter extends RecyclerView.Adapter {

    private float firstX;
    private float firstY;
    private boolean isFlipUp;
    private List<String> list;
    private Context mcontext;

    public GridAdapter(Context context,List<String> list) {
        this.list = list;mcontext = context;
    }

    @Override
    public RecyclerView.ViewHolder onCreateViewHolder(ViewGroup parent, int viewType) {
        View v = LayoutInflater.from(parent.getContext()).inflate(R.layout.holder_test2, null);
        return new ViewHolder(v);
    }

    @Override
    public void onBindViewHolder(RecyclerView.ViewHolder holder, int position) {
        ViewHolder viewHolder = (ViewHolder) holder;
        ViewGroup.LayoutParams params = viewHolder.rootView.getLayoutParams();
        viewHolder.rootView.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                Toast.makeText(viewHolder.rootView.getContext(), "" + position + "was click", Toast.LENGTH_SHORT).show();
            }
        });
        viewHolder.rootView.setOnTouchListener(new View.OnTouchListener() {
            @Override
            public boolean onTouch(View v, MotionEvent event) {
                AnimatorSet animatorSet = new AnimatorSet();
                switch (event.getAction()) {
                    case MotionEvent.ACTION_DOWN:
                        firstX = event.getX();
                        firstY = event.getY();
                        animatorSet.playTogether(ObjectAnimator.ofFloat(viewHolder.textLayout, "alpha", 1.0f, 0.0f)
                                .setDuration(800), ObjectAnimator.ofFloat(viewHolder.coverView, "alpha", 0.5f, 0.0f)
                                .setDuration(800));
                        animatorSet.start();
                        return true;
                    case MotionEvent.ACTION_MOVE:
                        float dx = firstX - event.getX();
                        float dy = firstY - event.getY();
                        if (dx == dy) break;
                        else if (dx > dy) isFlipUp = false;
                        else if (dx < dy) isFlipUp = true;
                        if (isFlipUp) {
                            animatorSet.playTogether(ObjectAnimator.ofFloat(viewHolder.textLayout, "alpha", 0.0f, 1.0f)
                                    .setDuration(800), ObjectAnimator.ofFloat(viewHolder.coverView, "alpha", 0.0f, 0.5f)
                                    .setDuration(800));
                            animatorSet.start();
                            return false;
                        } else break;
                    case MotionEvent.ACTION_UP:
                        viewHolder.rootView.performClick();
                    case MotionEvent.ACTION_CANCEL:
                    case MotionEvent.ACTION_OUTSIDE:
                        animatorSet.playTogether(ObjectAnimator.ofFloat(viewHolder.textLayout, "alpha", 0.0f, 1.0f)
                                .setDuration(800), ObjectAnimator.ofFloat(viewHolder.coverView, "alpha", 0.0f, 0.5f)
                                .setDuration(800));
                        animatorSet.start();
                        break;
                }
                return false;
            }
        });

        viewHolder.name.setText(list.get(position));


    }

    @Override
    public int getItemCount() {
        return list.size();
    }

    class ViewHolder extends RecyclerView.ViewHolder{

        @Bind(R.id.name)
        TextView name;
        @Bind(R.id.tag_and_time)
        TextView tag_and_time;
        @Bind(R.id.text_layout)
        View textLayout;
        @Bind(R.id.view_cover)
        BlurView coverView;

        @Bind(R.id.cover_bg)
        SimpleDraweeView bgImg;

        View rootView;

        public ViewHolder(View itemView) {
            super(itemView);
            ButterKnife.bind(this,itemView);
            this.rootView = itemView;
        }
    }

     public  int dp2px(Context context, float dpValue) {
        final float scale = context.getResources().getDisplayMetrics().density;
        return (int) (dpValue * scale + 0.5f);
    }
}
