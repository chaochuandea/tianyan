package dachuan.com.tianyan.view.adapter;

import android.graphics.drawable.RotateDrawable;
import android.graphics.drawable.ScaleDrawable;
import android.support.v7.widget.RecyclerView;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.view.animation.AccelerateDecelerateInterpolator;
import android.widget.ImageView;
import android.widget.TextView;

import com.facebook.drawee.view.SimpleDraweeView;
import com.nineoldandroids.animation.ObjectAnimator;
import com.nineoldandroids.animation.ValueAnimator;

import java.util.List;
import java.util.concurrent.TimeUnit;

import dachuan.com.tianyan.R;
import rx.Observable;
import rx.android.schedulers.AndroidSchedulers;
import rx.schedulers.Schedulers;
import timber.log.Timber;


/**
 * Created by linsj on 15-7-2.
 */
public class EveryDayAdapter extends RecyclerView.Adapter{

    private  List<String> list;
    public EveryDayAdapter(List<String> list) {
        this.list = list;
    }

    @Override
    public RecyclerView.ViewHolder onCreateViewHolder(ViewGroup parent, int viewType) {
        View v = LayoutInflater.from(parent.getContext()).inflate(R.layout.holder_test,null);
        return new ViewHolder(v);
    }

    @Override
    public void onBindViewHolder(RecyclerView.ViewHolder holder, int position) {
        ViewHolder viewHolder = (ViewHolder)holder;
        viewHolder.name.setText("Hello , " + list.get(position));
        RotateDrawable rottate = (RotateDrawable) viewHolder.rotate.getDrawable();
        ValueAnimator animator =  ObjectAnimator.ofInt(0, 10000, 0);
        animator.setInterpolator(new AccelerateDecelerateInterpolator());
        animator.addUpdateListener(animation -> {
            int level = (int) (10000 * animation.getAnimatedFraction());
            rottate.setLevel(level);
            Timber.d("ssss", level + "");
        });
        animator.setDuration(1000);
        animator.setRepeatCount(ValueAnimator.INFINITE);
        animator.start();

    }

    @Override
    public int getItemCount() {
        return list.size();
    }

    static class ViewHolder extends RecyclerView.ViewHolder{

        SimpleDraweeView cover,cover_bg;
        ImageView rotate;
        TextView name,tag_and_time;
        public ViewHolder(View itemView) {
            super(itemView);
            name = (TextView) itemView.findViewById(R.id.name);
            tag_and_time = (TextView) itemView.findViewById(R.id.tag_and_time);
            rotate = (ImageView) itemView.findViewById(R.id.rotate);
//            cover = (SimpleDraweeView) itemView.findViewById(R.id.cover);
//            cover_bg = (SimpleDraweeView) itemView.findViewById(R.id.cover_bg);
        }
    }
}
