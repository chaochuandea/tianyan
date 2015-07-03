package dachuan.com.tianyan.view.activity;

import android.database.Observable;
import android.os.Bundle;
import android.support.v4.widget.SwipeRefreshLayout;
import android.support.v7.widget.LinearLayoutManager;
import android.support.v7.widget.RecyclerView;
import android.widget.TextView;

import java.util.ArrayList;
import java.util.List;

import butterknife.Bind;
import dachuan.com.tianyan.R;
import dachuan.com.tianyan.api.Client;
import dachuan.com.tianyan.model.Cache;
import dachuan.com.tianyan.task.CacheTask;
import dachuan.com.tianyan.task.PageTask;
import dachuan.com.tianyan.view.adapter.Myadapter;
import dachuan.com.tianyan.view.base.BaseActivity;
import rx.android.schedulers.AndroidSchedulers;
import timber.log.Timber;


public class MainActivity extends BaseActivity {
    private static String INTENT_TOKEN = "intent_token";
    private static String CACHE_KEY = "main_activity_data";

    @Bind(R.id.main_list)
    public  RecyclerView mainlist;

    @Bind(R.id.swipe)
    public  SwipeRefreshLayout swipe;

    private PageTask pageTask;

    private String token;

    private Myadapter adapter;

    private List<String> datalist = new ArrayList<String>();



    @Override
    public void init(Bundle savedInstanceState) {
        token = getIntent().getStringExtra(MainActivity.INTENT_TOKEN);
        pageTask = new PageTask();
        pageTask.getPageSubject().onNext(1);

        adapter = new Myadapter(datalist);
        mainlist.setLayoutManager(new LinearLayoutManager(this, LinearLayoutManager.VERTICAL, false));
        mainlist.setAdapter(adapter);
        initListener();
        initData();

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
                    }), user -> Timber.d(user.toString()));
        });
        }

    private void initListener(){
        swipe.setOnRefreshListener(new SwipeRefreshLayout.OnRefreshListener() {
            @Override
            public void onRefresh() {
                new Thread() {
                    @Override
                    public void run() {
                        try {
                            sleep(2000);
                            addData(10);
                            rx.Observable.just("")
                                    .observeOn(AndroidSchedulers.mainThread())
                                    .subscribe((s) -> {
                                        adapter.notifyDataSetChanged();
                                        swipe.setRefreshing(false);
                                    });
                        } catch (InterruptedException e) {
                            e.printStackTrace();
                        }

                    }
                }.start();
            }
        });
    }
    private void addData(int size)
    {
        for (int i = 0 ;i < size ;i ++ )
        {
            datalist.add("Item -> "  + i );
        }
    }

}
