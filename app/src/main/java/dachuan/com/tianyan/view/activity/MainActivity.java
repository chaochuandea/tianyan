package dachuan.com.tianyan.view.activity;

import android.os.Bundle;
import android.support.v4.widget.SwipeRefreshLayout;
import android.support.v7.widget.RecyclerView;
import android.widget.AbsListView;
import android.widget.TextView;

import butterknife.Bind;
import dachuan.com.tianyan.R;
import dachuan.com.tianyan.api.Client;
import dachuan.com.tianyan.model.Cache;
import dachuan.com.tianyan.task.CacheTask;
import dachuan.com.tianyan.task.PageTask;
import dachuan.com.tianyan.view.base.BaseActivity;
import timber.log.Timber;


public class MainActivity extends BaseActivity {
    private static String INTENT_TOKEN = "intent_token";
    private static String CACHE_KEY = "main_activity_data";
    private PageTask pageTask;

    private String token;

    @Bind(R.id.swipe)
    SwipeRefreshLayout swip;

    @Bind(R.id.main_list)
    RecyclerView mainlist;



    @Override
    public void init(Bundle savedInstanceState) {
        initData();
        token = getIntent().getStringExtra(MainActivity.INTENT_TOKEN);
        pageTask = new PageTask();
        pageTask.getPageSubject().onNext(1);
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
}
