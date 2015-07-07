package dachuan.com.tianyan.view.activity;

import android.os.Bundle;
import android.support.v4.widget.SwipeRefreshLayout;
import android.support.v7.widget.LinearLayoutManager;
import android.support.v7.widget.RecyclerView;
import android.support.v7.widget.Toolbar;
import android.widget.TextView;

import java.util.ArrayList;
import java.util.List;
import java.util.concurrent.TimeUnit;

import butterknife.Bind;
import dachuan.com.tianyan.R;
import dachuan.com.tianyan.api.Client;
import dachuan.com.tianyan.model.Cache;
import dachuan.com.tianyan.task.CacheTask;
import dachuan.com.tianyan.task.PageTask;
import dachuan.com.tianyan.view.adapter.EveryDayAdapter;
import dachuan.com.tianyan.view.base.BaseActivity;
import rx.Observable;
import rx.Scheduler;
import rx.android.schedulers.AndroidSchedulers;
import rx.schedulers.Schedulers;
import timber.log.Timber;


public class MainActivity extends BaseActivity {
    private static String INTENT_TOKEN = "intent_token";
    private static String CACHE_KEY = "main_activity_data";
    @Bind(R.id.tool_bar)
    public Toolbar toolbar;

    @Bind(R.id.title)
    TextView title;

    @Bind(R.id.main_list)
    public  RecyclerView mainlist;

    @Bind(R.id.swipe)
    public  SwipeRefreshLayout swipe;

    private PageTask pageTask;

    private String token;

    private EveryDayAdapter adapter;

    private List<String> datalist = new ArrayList<String>();



    @Override
    public void init(Bundle savedInstanceState) {
        setSupportActionBar(toolbar);
        getSupportActionBar().setDisplayShowTitleEnabled(false);
        title.setText("tianyan");
        token = getIntent().getStringExtra(MainActivity.INTENT_TOKEN);
        pageTask = new PageTask();
        pageTask.getPageSubject().onNext(1);

        adapter = new EveryDayAdapter(datalist);
        mainlist.setLayoutManager(new LinearLayoutManager(this, LinearLayoutManager.VERTICAL, false));
        mainlist.setAdapter(adapter);
        initListener();
        initData();
        getData();
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
