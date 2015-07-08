package dachuan.com.tianyan.view.fragment;

import android.net.Uri;
import android.os.Bundle;
import android.widget.TextView;


import java.util.ArrayList;
import java.util.List;
import java.util.Random;

import butterknife.Bind;
import dachuan.com.tianyan.R;
import dachuan.com.tianyan.view.base.BaseFragment;
import dachuan.com.tianyan.view.widget.BlurView;
import dachuan.com.tianyan.view.widget.ZoomDraweeView;

/**
 * Created by maibenben on 2015/7/8.
 */
public class MovieDetailFragment extends BaseFragment {
   @Bind(R.id.cover)
   ZoomDraweeView cover;
    @Bind(R.id.blurringview)
    BlurView blurringview;
    @Bind(R.id.title)
    TextView title;
    @Bind(R.id.tag)
    TextView tag;
    @Bind(R.id.description)
    TextView description;

    List<String> pictures = new ArrayList<>();
    @Override
    public void init(Bundle savedInstanceState) {
        pictures.add("http://img2.moko.cc/users/0/42/12709/post/e9/img2_cover_10482573.jpg");
        pictures.add("http://img2.moko.cc/users/0/9/2952/post/9e/img2_cover_10487569.jpg");
        pictures.add("http://img2.moko.cc/users/0/12/3642/post/e5/img2_cover_10476002.jpg");

        cover.setImageURI(Uri.parse(pictures.get(new Random().nextInt(3))));
        blurringview.setTarget(cover).setScalFactor(20).rotate(180).refresh();
    }

    @Override
    public int getLayoutId() {
        return R.layout.fragment_movie_detail;
    }
}
