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

    @Bind(R.id.video_back)
    View back;

    View dview;

    @Override
    public int getLayoutId() {
        return R.layout.activity_video;
    }

    @Override
    public void init(Bundle savedInstanceState) {
        getWindow().setFlags(WindowManager.LayoutParams.FLAG_FULLSCREEN, WindowManager.LayoutParams.FLAG_FULLSCREEN);
        loading.setVisibility(View.VISIBLE);
        back.setVisibility(View.VISIBLE);
        back.findViewById(R.id.img).setOnClickListener(v->finish());
        Uri uri = Uri.parse("http://www.modrails.com/videos/passenger_nginx.mov");
        video.setVideoURI(uri);
        video.setVideoQuality(MediaPlayer.VIDEOQUALITY_LOW);
        dview = getWindow().getDecorView();
        dview.setSystemUiVisibility(View.SYSTEM_UI_FLAG_IMMERSIVE_STICKY | View.SYSTEM_UI_FLAG_HIDE_NAVIGATION | View.SYSTEM_UI_FLAG_FULLSCREEN);
        mediaController = new MediaController(this);
        mediaController.setAnchorView(video);
        mediaController.setOnHiddenListener(
                ()->back.setVisibility(View.GONE));
        mediaController.setOnShownListener(
                ()->back.setVisibility(View.VISIBLE));
        video.setMediaController(mediaController);
        video.requestFocus();
        video.setOnSeekCompleteListener(mediaPlayer -> {
            mediaPlayer.start();
        });
        video.setOnPreparedListener(mediaPlayer -> {
            subscribe(Observable.just(null), action -> {
                back.setVisibility(View.GONE);
                loading.setVisibility(View.GONE);
                mediaPlayer.start();
            });
        });
    }
}
