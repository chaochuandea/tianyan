package dachuan.com.tianyan.view.activity;

import android.animation.Animator;
import android.animation.AnimatorListenerAdapter;
import android.content.Intent;
import android.os.Bundle;
import android.view.animation.Animation;

import butterknife.Bind;
import dachuan.com.tianyan.R;
import dachuan.com.tianyan.view.base.BaseActivity;
import dachuan.com.tianyan.view.widget.ZoomDraweeView;

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
        zoom_img.setListener(new AnimatorListenerAdapter() {
            @Override
            public void onAnimationEnd(Animator animation) {
                Intent intent = new Intent(StartActivity.this,MainActivity.class);
                startActivity(intent);
                finish();

            }
        });
        zoom_img.actZoom(1.05f, 2000);
    }
}
