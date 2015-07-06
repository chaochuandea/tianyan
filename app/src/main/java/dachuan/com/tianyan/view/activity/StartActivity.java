package dachuan.com.tianyan.view.activity;

import android.animation.Animator;
import android.animation.AnimatorListenerAdapter;
import android.content.Intent;
import android.os.Bundle;
import android.view.animation.Animation;

import java.util.concurrent.TimeUnit;

import butterknife.Bind;
import dachuan.com.tianyan.R;
import dachuan.com.tianyan.view.base.BaseActivity;
import dachuan.com.tianyan.view.widget.ZoomDraweeView;
import rx.Observable;

/**
 * Created by linsj on 15-7-3.
 */
public class StartActivity extends BaseActivity {

    @Bind(R.id.start_img)
    ZoomDraweeView zoom_img;

    @Override
    public int getLayoutId() {
        return R.layout.activity_start;
    }

    @Override
    public void init(Bundle savedInstanceState) {
        Observable.timer(5000, TimeUnit.MILLISECONDS).subscribe(aLong -> {
            startActivity(new Intent(StartActivity.this,MainActivity.class));
            finish();
        });
    }
}
