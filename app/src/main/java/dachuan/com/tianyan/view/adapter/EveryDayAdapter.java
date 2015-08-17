package dachuan.com.tianyan.view.adapter;

import com.nineoldandroids.animation.AnimatorSet;

import android.app.ActivityOptions;
import android.content.Context;
import android.content.Intent;
import android.support.v7.widget.RecyclerView;
import android.view.LayoutInflater;
import android.view.MotionEvent;
import android.view.View;
import android.view.ViewGroup;
import android.widget.TextView;

import com.facebook.drawee.view.SimpleDraweeView;
import com.nineoldandroids.animation.ObjectAnimator;

import java.util.List;

import butterknife.Bind;
import butterknife.ButterKnife;
import dachuan.com.tianyan.R;
import dachuan.com.tianyan.model.ItemEntity;
import dachuan.com.tianyan.util.StringUtils;
import dachuan.com.tianyan.view.activity.ItemDetailActivity;
import dachuan.com.tianyan.view.activity.StartActivity;
import dachuan.com.tianyan.view.widget.BlurView;


/**
 * Created by linsj on 15-7-2.
 */
public class EveryDayAdapter extends RecyclerView.Adapter {

    static final int NORMAL_ITEM = 0;
    static final int LAST_ITEM = 1;
    private float firstX;
    private float firstY;
    private boolean isFlipUp;
    private List<ItemEntity> list;
    private View detailView;
    private Context mcontext;

    public EveryDayAdapter(List<ItemEntity> list, Context context) {
        this.mcontext = context;
        this.list = list;
    }

    @Override
    public RecyclerView.ViewHolder onCreateViewHolder(ViewGroup parent, int viewType) {
        View v = null;
        if (viewType == NORMAL_ITEM) {
            v = LayoutInflater.from(parent.getContext()).inflate(R.layout.holder_main, null);
            return new ViewHolder(v);
        } else {
            v = LayoutInflater.from(parent.getContext()).inflate(R.layout.holder_loading, null);
            return new LastView(v);
        }
    }

    @Override
    public void onBindViewHolder(RecyclerView.ViewHolder holder, int position) {
        if (position + 1 == list.size()) return;
        ViewHolder viewHolder = (ViewHolder) holder;
        viewHolder.rootView.setOnClickListener(v->{
            Intent intent = new Intent(mcontext,ItemDetailActivity.class);
            mcontext.startActivity(intent);
        });
        viewHolder.rootView.setOnTouchListener((v, event) -> {
                    AnimatorSet animatorSet = new AnimatorSet();
                    switch (event.getAction()) {
                        case MotionEvent.ACTION_DOWN:
                            firstX = event.getX();
                            firstY = event.getY();
                            animatorSet.playTogether(ObjectAnimator.ofFloat(viewHolder.textLayout, "alpha", 1.0f, 0.0f)
                                    .setDuration(500), ObjectAnimator.ofFloat(viewHolder.subtitle_layout, "alpha", 1.0f, 0.0f)
                                    .setDuration(500),ObjectAnimator.ofFloat(viewHolder.coverView, "alpha", 0.5f, 0.0f)
                                    .setDuration(500));
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
                                        .setDuration(500),ObjectAnimator.ofFloat(viewHolder.subtitle_layout, "alpha", 0.0f, 1.0f)
                                        .setDuration(500),ObjectAnimator.ofFloat(viewHolder.coverView, "alpha", 0.0f, 0.5f)
                                        .setDuration(500));
                                animatorSet.start();
                                return false;
                            } else break;
                        case MotionEvent.ACTION_UP:
                            viewHolder.rootView.performClick();
                        case MotionEvent.ACTION_OUTSIDE:
                        case MotionEvent.ACTION_CANCEL:
                            animatorSet.playTogether(ObjectAnimator.ofFloat(viewHolder.textLayout, "alpha", 0.0f, 1.0f)
                                    .setDuration(500), ObjectAnimator.ofFloat(viewHolder.subtitle_layout, "alpha", 0.0f, 1.0f)
                                    .setDuration(500), ObjectAnimator.ofFloat(viewHolder.coverView, "alpha", 0.0f, 0.5f)
                                    .setDuration(500));
                            animatorSet.start();
                            break;
                    }
                    return false;
                }
        );
        viewHolder.name.setText("Hello , " + list.get(position).getTitle());
        viewHolder.cached.setText(list.get(position).isCached()?"已经缓存":"未缓存");
        viewHolder.duration.setText(String.valueOf(list.get(position).getDuration()));
        viewHolder.sort.setText(list.get(position).getSort());
//        viewHolder.coverView.setRadius(1).setScalFactor(100).setTarget(viewHolder.bgImg).refresh();
    }

    @Override
    public int getItemCount() {
        return list.size();
    }

    @Override
    public int getItemViewType(int position) {
        if (position + 1 == list.size())
            return LAST_ITEM;
        else return NORMAL_ITEM;
    }

    static class ViewHolder extends RecyclerView.ViewHolder {


        @Bind(R.id.title)
        TextView name;
//        @Bind(R.id.tag_and_time)
//        TextView tag_and_time;
        @Bind(R.id.title_layout)
        View textLayout;
        @Bind(R.id.view_cover)
        BlurView coverView;

        @Bind(R.id.cover_bg)
        SimpleDraweeView bgImg;
        View rootView;

        @Bind(R.id.subtitle_cached)
        TextView cached;

        @Bind(R.id.subtitle_duration)
        TextView duration;

        @Bind(R.id.subtitle_sort)
        TextView sort;

        @Bind(R.id.subtitle_layout)
        View subtitle_layout;

        public ViewHolder(View itemView) {
            super(itemView);
            ButterKnife.bind(this, itemView);
            this.rootView = itemView;

//            name = (TextView) itemView.findViewById(R.id.name);
//            tag_and_time = (TextView) itemView.findViewById(R.id.tag_and_time);
//            rotate = (ImageView) itemView.findViewById(R.id.rotate);
        }
    }

    static class LastView extends RecyclerView.ViewHolder {
        public LastView(View itemView) {
            super(itemView);
        }
    }
}
