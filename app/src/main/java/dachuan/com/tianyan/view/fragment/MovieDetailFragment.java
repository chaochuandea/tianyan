package dachuan.com.tianyan.view.fragment;

import android.graphics.drawable.Animatable;
import android.net.Uri;
import android.os.Bundle;
import android.os.Handler;
import android.view.View;
import android.widget.TextView;


import com.facebook.drawee.backends.pipeline.Fresco;
import com.facebook.drawee.controller.BaseControllerListener;
import com.facebook.drawee.controller.ControllerListener;
import com.facebook.drawee.interfaces.DraweeController;
import com.facebook.imagepipeline.image.ImageInfo;
import com.facebook.imagepipeline.request.ImageRequestBuilder;

import java.util.ArrayList;
import java.util.List;
import java.util.Random;

import butterknife.Bind;
import dachuan.com.tianyan.R;
import dachuan.com.tianyan.view.base.BaseFragment;
import dachuan.com.tianyan.view.widget.AnimateTextView;
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
    AnimateTextView title;
    @Bind(R.id.tag)
    AnimateTextView tag;
    @Bind(R.id.description)
    AnimateTextView description;
@Bind(R.id.text)
    View text;
    @Bind(R.id.underline)
    AnimateTextView underline;


    List<String> pictures = new ArrayList<>();
    @Override
    public void init(Bundle savedInstanceState) {
        pictures.add("http://img2.moko.cc/users/0/42/12709/post/e9/img2_cover_10482573.jpg");
        pictures.add("http://img2.moko.cc/users/0/9/2952/post/9e/img2_cover_10487569.jpg");
        pictures.add("http://img2.moko.cc/users/0/12/3642/post/e5/img2_cover_10476002.jpg");
        ControllerListener listener = new BaseControllerListener<ImageInfo>(){
            @Override
            public void onFinalImageSet(String id, ImageInfo imageInfo, Animatable animatable) {
//                       blurringview.setTarget(cover).setScalFactor(60).setRadius(5).refresh();
            }
        };
        Uri  uri = Uri.parse(pictures.get(new Random().nextInt(3)));
        DraweeController controller = Fresco.newDraweeControllerBuilder().setControllerListener(listener).setUri(uri).build();
        cover.setController(controller);
        blurringview.setImageUri(uri);

        startAnimation();
    }

    public void startAnimation(){
        text.setVisibility(View.VISIBLE);
        String titles = "aaaaaaaa" + new Random().nextInt(100);
        title.animateText(titles);
        tag.animateText("" + new Random().nextInt(1000000));
        description.animateText("" + new Random().nextInt(1000000) + new Random().nextInt(1000000) + new Random().nextInt(1000000) + new Random().nextInt(1000000) + new Random().nextInt(1000000) + new Random().nextInt(1000000) + new Random().nextInt(1000000) + new Random().nextInt(1000000) + new Random().nextInt(1000000) + new Random().nextInt(1000000) + new Random().nextInt(1000000) + new Random().nextInt(1000000) + new Random().nextInt(1000000) + new Random().nextInt(1000000) + new Random().nextInt(1000000) + new Random().nextInt(1000000) + new Random().nextInt(1000000) + new Random().nextInt(1000000) + new Random().nextInt(1000000) + new Random().nextInt(1000000) + new Random().nextInt(1000000) + new Random().nextInt(1000000) + new Random().nextInt(1000000));
        String underline_str = "";
        int length = 5;
        if (titles.length()>5) length = titles.length()-3;
        for (int i = 0;i<length;i++){
            underline_str +="_";
        }
        underline.animateText(underline_str);
    }

    public void hideTextView(){
        text.setVisibility(View.INVISIBLE);
    }

    @Override
    public int getLayoutId() {
        return R.layout.fragment_movie_detail;
    }
}
