package dachuan.com.tianyan.view.activity;

import android.app.Activity;
import android.net.Uri;
import android.os.Bundle;
import android.view.View;
import android.view.WindowManager;

import butterknife.Bind;
import dachuan.com.tianyan.R;
import dachuan.com.tianyan.model.Video;
import dachuan.com.tianyan.view.base.BaseActivity;
import io.vov.vitamio.LibsChecker;
import io.vov.vitamio.MediaPlayer;
import io.vov.vitamio.widget.MediaController;
import io.vov.vitamio.widget.VideoView;

/**
 * Created by linsj on 15-7-13.
 */
public class VideoActivity extends Activity{

    VideoView video;
    MediaController mediaController;
    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_video);
        getWindow().setFlags(WindowManager.LayoutParams.FLAG_FULLSCREEN,WindowManager.LayoutParams.FLAG_FULLSCREEN);
        if (!LibsChecker.checkVitamioLibs(this))
            return;
        video = (VideoView) findViewById(R.id.video);
        Uri uri = Uri.parse("http://www.modrails.com/videos/passenger_nginx.mov");
        video.setVideoURI(uri);
        mediaController = new MediaController(this);
        mediaController.setAnchorView(video);
        video.setMediaController(mediaController);
        video.requestFocus();
        video.setOnPreparedListener(new MediaPlayer.OnPreparedListener() {
            @Override
            public void onPrepared(MediaPlayer mediaPlayer) {
                mediaPlayer.start();
            }
        });
    }


}
