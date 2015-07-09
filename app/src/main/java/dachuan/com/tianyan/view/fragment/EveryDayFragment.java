package dachuan.com.tianyan.view.fragment;

import android.os.Bundle;
import android.support.v4.app.Fragment;
import android.support.v4.view.ViewPager;
import android.support.v4.widget.SwipeRefreshLayout;
import android.support.v7.widget.LinearLayoutManager;
import android.support.v7.widget.RecyclerView;
import android.text.Html;
import android.view.View;
import android.view.ViewGroup;
import android.view.animation.AnimationUtils;
import android.widget.LinearLayout;
import android.widget.TextView;

import com.ToxicBakery.viewpager.transforms.ABaseTransformer;
import com.astuetz.PagerSlidingTabStrip;
import com.nineoldandroids.view.ViewHelper;

import java.util.ArrayList;
import java.util.List;
import java.util.concurrent.TimeUnit;

import butterknife.Bind;
import dachuan.com.tianyan.R;
import dachuan.com.tianyan.api.Client;
import dachuan.com.tianyan.model.Cache;
import dachuan.com.tianyan.task.CacheTask;
import dachuan.com.tianyan.task.PageTask;
import dachuan.com.tianyan.view.activity.MainActivity;
import dachuan.com.tianyan.view.adapter.DetailViewPagerAdapter;
import dachuan.com.tianyan.view.adapter.EveryDayAdapter;
import dachuan.com.tianyan.view.base.BaseFragment;
import rx.Observable;
import rx.schedulers.Schedulers;
import timber.log.Timber;

/**
 * Created by linsj on 15-7-9.
 */
public class EveryDayFragment extends BaseFragment {


    @Bind(R.id.main_list)
    public RecyclerView mainlist;

    @Bind(R.id.swipe)
    public SwipeRefreshLayout swipe;

    private PageTask pageTask;

    private String token;

    private EveryDayAdapter adapter;

    private List<String> datalist = new ArrayList<String>();
    private static String CACHE_KEY = "main_activity_data";

    private View detail_view;

    public void setDetail_view(View detail_view) {
        this.detail_view = detail_view;
    }

    @Override
    public void init(Bundle savedInstanceState) {
//        token = getArguments().getString(MainActivity.INTENT_TOKEN);
        pageTask = new PageTask();
        pageTask.getPageSubject().onNext(1);


        adapter = new EveryDayAdapter(datalist,detail_view);
        mainlist.setLayoutManager(new LinearLayoutManager(getActivity(), LinearLayoutManager.VERTICAL, false));
        mainlist.setAdapter(adapter);


        initListener();
        initData();
        getData();







    }





    //接收page，开始请求数据,然后cache,然后响应数据
    private void initData(){

        pageTask.getPageSubject().subscribe(integer -> {
            subscribe(Client.getApiService().getUser(token, integer).map(user1 -> {
                CacheTask.getCacheSubject().onNext(new Cache(CACHE_KEY, user1));
                return user1;
            }).map(users -> {
                return users.get(1).getUser().getBytes();
            }), user -> Timber.d(user.toString()));
        });
    }

    private void initListener(){
        swipe.setOnRefreshListener(() -> {
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




    @Override
    public int getLayoutId() {
        return R.layout.fragment_everyday;
    }
}
