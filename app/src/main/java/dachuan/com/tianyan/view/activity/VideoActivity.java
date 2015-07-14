package dachuan.com.tianyan.view.activity;

import android.net.Uri;
import android.os.Bundle;
import android.view.View;
import android.view.WindowManager;

import butterknife.Bind;
import dachuan.com.tianyan.R;
import dachuan.com.tianyan.view.base.BaseActivity;
import io.vov.vitamio.LibsChecker;
import io.vov.vitamio.MediaPlayer;
import io.vov.vitamio.widget.MediaController;
import io.vov.vitamio.widget.VideoView;
import rx.Observable;

/**
 * Created by linsj on 15-7-13.
 */
public class VideoActivity extends BaseActivity{

    @Bind(R.id.video)
    VideoView video;

    @Bind(R.id.loading_view)
    View loading;

    MediaController mediaController;

    View dview;

    @Override
    public int getLayoutId() {
        return R.layout.activity_video;
    }

    @Override
    public void init(Bundle savedInstanceState) {
        getWindow().setFlags(WindowManager.LayoutParams.FLAG_FULLSCREEN, WindowManager.LayoutParams.FLAG_FULLSCREEN);
        Uri uri = Uri.parse("http://www.modrails.com/videos/passenger_nginx.mov");
        video.setVideoURI(uri);
        video.setVideoQuality(MediaPlayer.VIDEOQUALITY_LOW);
        dview = getWindow().getDecorView();
        int option = View.SYSTEM_UI_FLAG_LAYOUT_FULLSCREEN | View.SYSTEM_UI_FLAG_HIDE_NAVIGATION;
        dview.setSystemUiVisibility(option);
        mediaController = new MediaController(this);
        mediaController.setAnchorView(video);
        mediaController.setOnHiddenListener(
                ()->dview.setSystemUiVisibility(View.SYSTEM_UI_FLAG_HIDE_NAVIGATION));
        mediaController.setOnShownListener(
                ()->dview.setSystemUiVisibility(View.SYSTEM_UI_FLAG_VISIBLE));
        video.setMediaController(mediaController);
        video.requestFocus();
        video.setOnSeekCompleteListener(mediaPlayer -> {
            mediaPlayer.start();
        });
        video.setOnPreparedListener(mediaPlayer -> {
            subscribe(Observable.just(null), action -> {
                loading.setVisibility(View.GONE);
                mediaPlayer.start();
            });
        });
    }
}
