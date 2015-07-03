package dachuan.com.tianyan.view.activity;

import android.content.Intent;
import android.os.Bundle;
import android.view.animation.Animation;

import com.facebook.drawee.view.DraweeView;

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
        zoom_img.setListener(new Animation.AnimationListener() {
            @Override
            public void onAnimationStart(Animation animation) {

            }

            @Override
            public void onAnimationEnd(Animation animation) {
                Intent intent = new Intent(StartActivity.this,MainActivity.class);
                startActivity(intent);
                finish();
            }

            @Override
            public void onAnimationRepeat(Animation animation) {

            }
        });
        zoom_img.ActZoom(1.05f,2000);
    }
}
