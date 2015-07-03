package dachuan.com.tianyan.view.activity;

import android.os.Bundle;
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

    private static String CACHE_KEY = "main_activity_data";
    private PageTask pageTask;

    @Bind(R.id.text)
    TextView text;

    @Override
    public void init(Bundle savedInstanceState) {
        text.setText("hello");
        initData();
        pageTask = new PageTask();
        pageTask.getPageSubject().onNext(1);
    }

    @Override
    public int getLayoutId() {
        return R.layout.activity_main;
    }

//接收page，然后cache,然后响应数据
    private void initData(){
        pageTask.getPageSubject().subscribe(integer -> {
                    subscribe(Client.getApiService().getUser(integer).map(user1 -> {
                        user1.get(0).setUser("ssssssss");
                        CacheTask.getCacheSubject().onNext(new Cache(CACHE_KEY,user1));
                        return user1;
                    }), user -> Timber.d(user.toString()));
        });
        }
}
