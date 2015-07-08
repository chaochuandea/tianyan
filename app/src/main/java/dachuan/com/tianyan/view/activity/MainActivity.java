package dachuan.com.tianyan.view.activity;

import android.os.Bundle;
import android.support.v4.widget.SwipeRefreshLayout;
import android.support.v7.widget.LinearLayoutManager;
import android.support.v7.widget.RecyclerView;
import android.support.v7.widget.Toolbar;
import android.view.View;
import android.view.animation.Animation;
import android.view.animation.AnimationUtils;
import android.widget.ImageView;
import android.widget.TextView;

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
import dachuan.com.tianyan.view.adapter.EveryDayAdapter;
import dachuan.com.tianyan.view.base.BaseActivity;
import dachuan.com.tianyan.view.widget.BlurView;
import rx.Observable;
import rx.schedulers.Schedulers;
import timber.log.Timber;


public class MainActivity extends BaseActivity {
    private static String INTENT_TOKEN = "intent_token";
    private static String CACHE_KEY = "main_activity_data";


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

    @Bind(R.id.main_list)
    public  RecyclerView mainlist;

    @Bind(R.id.swipe)
    public  SwipeRefreshLayout swipe;

    private PageTask pageTask;

    private String token;

    private EveryDayAdapter adapter;

    private List<String> datalist = new ArrayList<String>();

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
    public void init(Bundle savedInstanceState) {
        setSupportActionBar(toolbar);
        getSupportActionBar().setDisplayShowTitleEnabled(false);
        title.setText("tianyan");
        token = getIntent().getStringExtra(MainActivity.INTENT_TOKEN);
        pageTask = new PageTask();
        pageTask.getPageSubject().onNext(1);

        loadAnimation();

        adapter = new EveryDayAdapter(datalist);
        mainlist.setLayoutManager(new LinearLayoutManager(this, LinearLayoutManager.VERTICAL, false));
        mainlist.setAdapter(adapter);
        initListener();
        initData();
        getData();

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

//接收page，开始请求数据,然后cache,然后响应数据
    private void initData(){
        pageTask.getPageSubject().subscribe(integer -> {
                    subscribe(Client.getApiService().getUser(token ,integer).map(user1 -> {
                        CacheTask.getCacheSubject().onNext(new Cache(CACHE_KEY,user1));
                        return user1;
                    }).map(users -> {
                        return  users.get(1).getUser().getBytes();
                    }), user -> Timber.d(user.toString()));
        });
        }

    private void initListener(){
        swipe.setOnRefreshListener(()->{
            subscribe(Observable.timer(2, TimeUnit.SECONDS, Schedulers.io()), aLong1 -> {
                addData(10);
                adapter.notifyDataSetChanged();
                swipe.setRefreshing(false);
            });
        });
    }

    private void getData() {
        addData(10);
        adapter.notifyDataSetChanged();
        swipe.setRefreshing(false);

    }

    private void addData(int size)
    {
        for (int i = 0 ;i < size ;i ++ )
        {
            datalist.add("这是标题"  + i );
        }
    }

}
