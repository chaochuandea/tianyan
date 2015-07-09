package dachuan.com.tianyan.view.activity;

import android.os.Bundle;
import android.support.v4.app.Fragment;
import android.support.v4.view.ViewPager;
import android.support.v4.widget.SwipeRefreshLayout;
import android.support.v7.widget.LinearLayoutManager;
import android.support.v7.widget.RecyclerView;
import android.support.v7.widget.Toolbar;
import android.text.Html;
import android.util.Log;
import android.view.View;
import android.view.ViewGroup;
import android.view.animation.Animation;
import android.view.animation.AnimationUtils;
import android.widget.ImageView;
import android.widget.LinearLayout;
import android.widget.TextView;

import com.ToxicBakery.viewpager.transforms.ABaseTransformer;
import com.astuetz.PagerSlidingTabStrip;
import com.nineoldandroids.view.ViewHelper;

import java.util.ArrayList;
import java.util.List;
import java.util.concurrent.TimeUnit;

import butterknife.Bind;
import butterknife.OnClick;
import dachuan.com.tianyan.R;
import dachuan.com.tianyan.api.Client;
import dachuan.com.tianyan.model.Cache;
import dachuan.com.tianyan.task.CacheTask;
import dachuan.com.tianyan.task.PageTask;
import dachuan.com.tianyan.view.adapter.DetailViewPagerAdapter;
import dachuan.com.tianyan.view.adapter.EveryDayAdapter;
import dachuan.com.tianyan.view.adapter.MainViewPagerAdapter;
import dachuan.com.tianyan.view.base.BaseActivity;
import dachuan.com.tianyan.view.fragment.MovieDetailFragment;
import dachuan.com.tianyan.view.widget.StaticViewpager;
import rx.Observable;
import rx.android.schedulers.AndroidSchedulers;
import rx.schedulers.Schedulers;
import timber.log.Timber;


public class MainActivity extends BaseActivity implements View.OnClickListener{
    public static String INTENT_TOKEN = "intent_token";
    @Bind(R.id.main_viewpager)
    public StaticViewpager mainViewPager;
    private MainViewPagerAdapter adapter;
    @Bind(R.id.bottombar)
    public  View bottombar;
    @Bind(R.id.text1)
    TextView button_1;
    @Bind(R.id.text2)
    TextView button_2;


    @Bind(R.id.tool_bar)
     Toolbar toolbar;

    @Bind(R.id.setting_con)
    View setting_con;

    @Bind(R.id.setting_neirong)
    View setting_neirong;

    @Bind(R.id.title)
    TextView title;

    @Bind(R.id.mine)
    View mine;

    @Bind(R.id.icon)
    ImageView icon;

    @Bind(R.id.icon_back)
    ImageView icon_back;

    @Bind(R.id.eye)
    ImageView eye;



    Animation showAni,hideAni,right_in,right_out,rotate_90,rotate_0;

    private boolean show_setting = false;



    @OnClick(R.id.icon)
    public void showMy(){
        animation_icon();
    }

    private void animation_icon() {
        setting_con.setVisibility(View.INVISIBLE);
        setting_neirong.setVisibility(View.INVISIBLE);
        if(mine.getVisibility() == View.VISIBLE){
            show_setting = false;
            mine.setVisibility(View.INVISIBLE);
            mine.startAnimation(hideAni);
            icon.startAnimation(rotate_90);
            eye.setImageResource(R.mipmap.eye);

        }else{
            show_setting = true;
            mine.setVisibility(View.VISIBLE);
            mine.startAnimation(showAni);
            icon.startAnimation(rotate_0);
            eye.setImageResource(R.mipmap.setting);
        }
    }

    @OnClick(R.id.eye)
   public void  eyeClick(){
        if(show_setting){
            showSetting();
        }
    }
    @OnClick(R.id.icon_back)
    public void icon_backClick(){
        hideSetting();
    }


    public void showSetting(){
        setting_con.setVisibility(View.VISIBLE);
        setting_con.startAnimation(right_in);
        setting_neirong.setVisibility(View.VISIBLE);
        setting_neirong.startAnimation(right_in);
    }


    public void hideSetting(){
        setting_con.setVisibility(View.INVISIBLE);
        setting_con.startAnimation(right_out);
        setting_neirong.setVisibility(View.INVISIBLE);
        setting_neirong.startAnimation(right_out);
    }


    @OnClick(R.id.up)
    public void show(){
        animation_icon();
    }
    @Override
    public void onClick(View v) {
        switch (v.getId())
        {
            case R.id.text1:
                mainViewPager.setCurrentItem(0,false);
                changeButtonState();
                break;
            case R.id.text2:
                mainViewPager.setCurrentItem(1,false);
                changeButtonState();
                break;
        }
    }


    private void changeButtonState(){
        Observable.just(mainViewPager.getCurrentItem()).observeOn(AndroidSchedulers.mainThread()).subscribe(item -> {
            Log.i("switch", "what the fuck is wrong?");
            switch (item)
            {
                case 0 :
                    button_1.setTextColor(getResources().getColor(R.color.text_activated));
                    button_2.setTextColor(getResources().getColor(R.color.text_normal));
                    break;
                case 1:
                    button_2.setTextColor(getResources().getColor(R.color.text_activated));
                    button_1.setTextColor(getResources().getColor(R.color.text_normal));
                    break;
            }
        });

    }

    @Override
    public void init(Bundle savedInstanceState) {
        setSupportActionBar(toolbar);
        getSupportActionBar().setDisplayShowTitleEnabled(false);
        title.setText("tianyan");
        loadAnimation();

        adapter = new MainViewPagerAdapter(getSupportFragmentManager());
        mainViewPager.setAdapter(adapter);
        bottombar.findViewById(R.id.text1).setOnClickListener(this);
        bottombar.findViewById(R.id.text2).setOnClickListener(this);
        changeButtonState();
    }

    private void loadAnimation() {
        showAni = AnimationUtils.loadAnimation(this, R.anim.slide_in_from_top);
        hideAni = AnimationUtils.loadAnimation(this,R.anim.slide_out_to_top);
        right_in = AnimationUtils.loadAnimation(this,R.anim.right_slide_in);
        right_out = AnimationUtils.loadAnimation(this,R.anim.right_slide_out);
        rotate_0 =  AnimationUtils.loadAnimation(this,R.anim.rotate_0_90);
        rotate_90 =  AnimationUtils.loadAnimation(this,R.anim.rotate_90_0);
    }


    @Override
    public int getLayoutId() {
        return R.layout.activity_main;
    }
}
