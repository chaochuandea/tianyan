package dachuan.com.tianyan.view.activity;

import android.animation.Animator;
import android.animation.AnimatorListenerAdapter;
import android.content.Intent;
import android.graphics.Paint;
import android.os.Bundle;
import android.os.Handler;
import android.view.View;
import android.view.animation.Animation;
import android.widget.ImageView;
import android.widget.TextView;

import com.nineoldandroids.animation.AnimatorSet;
import com.nineoldandroids.animation.ArgbEvaluator;
import com.nineoldandroids.animation.ObjectAnimator;
import com.nineoldandroids.animation.ValueAnimator;

import java.util.concurrent.TimeUnit;

import butterknife.Bind;
import butterknife.OnClick;
import dachuan.com.tianyan.R;
import dachuan.com.tianyan.util.TimeUtils;
import dachuan.com.tianyan.view.base.BaseActivity;
import dachuan.com.tianyan.view.widget.ZoomDraweeView;
import rx.Observable;
import rx.android.schedulers.AndroidSchedulers;

/**
 * Created by linsj on 15-7-3.
 */
public class StartActivity extends BaseActivity {

    @Bind(R.id.start_img)
    ZoomDraweeView zoom_img;
    @Bind(R.id.bg)
    View bg;
    @Bind(R.id.eye)
    ImageView eye;
    @Bind(R.id.app_name_en)
    TextView app_name_en;
    @Bind(R.id.time)
    TextView time;
    @Bind(R.id.today)
    TextView today;
    @Bind(R.id.description)
    TextView description;
    @Bind(R.id.description_en)
    TextView description_en;

    private boolean click = false;
    @Override
    public int getLayoutId() {
        return R.layout.activity_start;
    }

    @OnClick(R.id.today)
    public void toMainActivity(){
        click = true;
        startActivity(new Intent(this,MainActivity.class));
        finish();
    }

    @Override
    public void init(Bundle savedInstanceState) {
        Observable.timer(1000, TimeUnit.MILLISECONDS).observeOn(AndroidSchedulers.mainThread()).subscribe(aLong -> {
            AnimatorSet set = new AnimatorSet();
            bg.setVisibility(View.VISIBLE);
            eye.setImageResource(R.mipmap.ic_launcher);
            time.setText("-" + TimeUtils.format(System.currentTimeMillis()) + "-");
            time.setVisibility(View.VISIBLE);
            today.setVisibility(View.VISIBLE);
            today.getPaint().setFlags(Paint.UNDERLINE_TEXT_FLAG);
            ValueAnimator textAnimater =  ValueAnimator.ofObject(new ArgbEvaluator(), getResources().getColor(R.color.white), getResources().getColor(R.color.text_color_dark_secondary));
            textAnimater.addUpdateListener(animation -> {
                app_name_en.setTextColor((Integer)animation.getAnimatedValue());
            });
            set.playTogether(
                    ObjectAnimator.ofFloat(bg, "alpha", 0, 1),
                    ObjectAnimator.ofFloat(eye, "translationY", 0, -200),
                    ObjectAnimator.ofFloat(eye, "alpha", 0.5f, 1),
                    ObjectAnimator.ofFloat(today, "alpha", 0f, 1),
                    ObjectAnimator.ofFloat(time, "alpha", 0f, 1),
                    ObjectAnimator.ofFloat(description, "alpha", 1f, 0),
                    ObjectAnimator.ofFloat(description_en, "alpha", 1f, 0),
                    textAnimater,
                    ObjectAnimator.ofFloat(app_name_en, "translationY", 0, -200)
            );
            set.setDuration(1000).start();
            new Handler().postDelayed(new Runnable() {
                @Override
                public void run() {
                    if (!click)
                    toMainActivity();
                }
            }, 2000);

        });
    }

    public void initVitamio() {



    }
}
