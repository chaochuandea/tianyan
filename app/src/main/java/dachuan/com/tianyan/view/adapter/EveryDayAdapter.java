package dachuan.com.tianyan.view.adapter;

import com.nineoldandroids.animation.AnimatorSet;

import android.animation.StateListAnimator;
import android.graphics.drawable.LayerDrawable;
import android.graphics.drawable.RotateDrawable;
import android.graphics.drawable.ScaleDrawable;
import android.support.v7.widget.RecyclerView;
import android.util.Log;
import android.view.LayoutInflater;
import android.view.MotionEvent;
import android.view.View;
import android.view.ViewGroup;
import android.view.animation.AccelerateDecelerateInterpolator;
import android.view.animation.Animation;
import android.view.animation.AnimationUtils;
import android.widget.Button;
import android.widget.ImageView;
import android.widget.TextView;

import com.facebook.drawee.view.SimpleDraweeView;
import com.nineoldandroids.animation.ObjectAnimator;
import com.nineoldandroids.animation.ValueAnimator;

import java.util.List;
import java.util.concurrent.TimeUnit;

import butterknife.Bind;
import butterknife.ButterKnife;
import dachuan.com.tianyan.R;
import dachuan.com.tianyan.util.Blur;
import dachuan.com.tianyan.view.widget.BlurView;
import rx.Observable;
import rx.android.schedulers.AndroidSchedulers;
import rx.schedulers.Schedulers;
import timber.log.Timber;


/**
 * Created by linsj on 15-7-2.
 */
public class EveryDayAdapter extends RecyclerView.Adapter {


    private float firstX;
    private float firstY;
    private boolean isFlipUp;
    private List<String> list;
    private View detailView;

    public EveryDayAdapter(List<String> list,View detailView) {
        this.detailView = detailView;
        this.list = list;
    }

    @Override
    public RecyclerView.ViewHolder onCreateViewHolder(ViewGroup parent, int viewType) {
        View v = LayoutInflater.from(parent.getContext()).inflate(R.layout.holder_test, null);
        return new ViewHolder(v);
    }

    @Override
    public void onBindViewHolder(RecyclerView.ViewHolder holder, int position) {
        ViewHolder viewHolder = (ViewHolder) holder;
        viewHolder.rootView.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                detailView.setVisibility(View.VISIBLE);
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
                    case MotionEvent.ACTION_UP: viewHolder.rootView.performClick();
                    case MotionEvent.ACTION_OUTSIDE:
                    case MotionEvent.ACTION_CANCEL:
                        animatorSet.playTogether(ObjectAnimator.ofFloat(viewHolder.textLayout, "alpha", 0.0f, 1.0f)
                                .setDuration(800), ObjectAnimator.ofFloat(viewHolder.coverView, "alpha", 0.0f, 0.5f)
                                .setDuration(800));
                        animatorSet.start();
                        break;
                }
                return false;
            }
        });
        viewHolder.name.setText("Hello , " + list.get(position));
//        viewHolder.coverView.setRadius(1).setScalFactor(100).setTarget(viewHolder.bgImg).refresh();


    }

    @Override
    public int getItemCount() {
        return list.size();
    }

    static class ViewHolder extends RecyclerView.ViewHolder {


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
            ButterKnife.bind(this, itemView);
            this.rootView = itemView;

//            name = (TextView) itemView.findViewById(R.id.name);
//            tag_and_time = (TextView) itemView.findViewById(R.id.tag_and_time);
//            rotate = (ImageView) itemView.findViewById(R.id.rotate);
        }
    }
}
