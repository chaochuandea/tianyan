package dachuan.com.tianyan.view.fragment;

import android.graphics.Color;
import android.os.Bundle;
import android.os.Handler;
import android.os.Looper;
import android.support.v4.widget.SwipeRefreshLayout;
import android.support.v7.widget.LinearLayoutManager;
import android.support.v7.widget.RecyclerView;
import android.util.Log;
import android.view.LayoutInflater;
import android.view.View;

import java.util.ArrayList;
import java.util.Iterator;
import java.util.List;
import java.util.concurrent.TimeUnit;

import butterknife.Bind;
import dachuan.com.tianyan.AppContext;
import dachuan.com.tianyan.R;
import dachuan.com.tianyan.api.Client;
import dachuan.com.tianyan.model.Cache;
import dachuan.com.tianyan.model.ItemEntity;
import dachuan.com.tianyan.task.CacheTask;
import dachuan.com.tianyan.task.PageTask;
import dachuan.com.tianyan.view.adapter.EveryDayAdapter;
import dachuan.com.tianyan.view.base.BaseFragment;
import dachuan.com.tianyan.model.event.OnReLoadingEntity;
import rx.Observable;
import rx.android.app.AppObservable;
import rx.android.schedulers.AndroidSchedulers;
import rx.functions.Func1;
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

    private Observable<List<ItemEntity>> netList;
    private List<ItemEntity> itemList = new ArrayList<>();

    private static String CACHE_KEY = "main_activity_data";

    private View loadingItem;

    private int lastItem;

    private boolean isGetDataFromTop = false;
    private boolean isFirst = true;
    private boolean gettingData = false;
//    private View detail_view;

//    public void setDetail_view(View detail_view) {
//        this.detail_view = detail_view;
//    }

    @Override
    public void init(Bundle savedInstanceState) {
        AppContext.instance().getBus().register(this);
//        token = getArguments().getString(MainActivity_backup.INTENT_TOKEN);
        loadingItem = LayoutInflater.from(getActivity()).inflate(R.layout.holder_loading, null);
        pageTask = new PageTask();
        pageTask.getPageSubject().onNext(1);
        swipe.setColorSchemeColors(Color.CYAN, Color.GREEN, Color.RED, Color.YELLOW);
        adapter = new EveryDayAdapter(itemList, getActivity());
        mainlist.setLayoutManager(new LinearLayoutManager(getActivity(), LinearLayoutManager.VERTICAL, false));
        mainlist.setAdapter(adapter);
        initListener();
        initData();
        getData();
    }


    private void initData() {

        pageTask.getPageSubject().subscribe(integer -> {
            subscribe(Client.getApiService().getUser(token).map(user1 -> {
                CacheTask.getCacheSubject().onNext(new Cache(CACHE_KEY, user1));
                return user1;
            }));
        });
    }


    private void initListener() {
        swipe.setOnRefreshListener(() -> {
            isGetDataFromTop = true;
            swipe.setRefreshing(true);
            getDataFromDC();
        });
        mainlist.addOnScrollListener(new RecyclerView.OnScrollListener() {
            @Override
            public void onScrollStateChanged(RecyclerView recyclerView, int newState) {
                super.onScrollStateChanged(recyclerView, newState);
                if (newState == RecyclerView.SCROLL_STATE_IDLE && lastItem + 1 == adapter.getItemCount()) {
                    isGetDataFromTop = false;
                    getData();
                }
            }

            @Override
            public void onScrolled(RecyclerView recyclerView, int dx, int dy) {
                lastItem = ((LinearLayoutManager) recyclerView.getLayoutManager()).findLastVisibleItemPosition();
            }
        });
    }

    private void getData() {
        if (gettingData) return;
        gettingData = true;
        subscribe(Observable.timer(2, TimeUnit.SECONDS, Schedulers.io()), aLong1 -> {
            addData(10);
            adapter.notifyDataSetChanged();
            swipe.setRefreshing(false);
            gettingData = false;
        });
    }

    private void addData(int size) {
        if (isFirst) {
            for (int i = 0; i < 10; i++) {
                datalist.add("I'm the original Data No." + i);
                isFirst = false;
            }
            datalist.add("last");
        } else if (!isGetDataFromTop) {
            datalist.remove(datalist.size() - 1);
            for (int i = 0; i < size; i++) {
                datalist.add("I'm Data From Bottom. No." + i + "!");
            }
            datalist.add("last");
        } else {
            for (int i = 0; i < size; i++) {
                datalist.add(i, "I'm Data From Top. No." + i + "!");
            }
        }
    }


    @Override
    public int getLayoutId() {
        return R.layout.fragment_everyday;
    }

    public void onEvent(OnReLoadingEntity e) {
        swipe.setRefreshing(true);
        subscribe(Observable.timer(2, TimeUnit.SECONDS, Schedulers.io()), action -> {
            getData();
        });
    }

    @Override
    public void onDestroyView() {
        AppContext.instance().getBus().unregister(this);
        super.onDestroyView();
    }

    public void getDataFromDC() {


//        Observable.just("hello")
//                .subscribeOn(Schedulers.newThread())
//                .flatMap(s -> { Log.i("faltmap", Thread.currentThread().getName());return Observable.just(s);})
//                .observeOn(AndroidSchedulers.mainThread())
//                .flatMap(s2 -> {
//                    Log.i("faltmap2", Thread.currentThread().getName());
//                    return Observable.just(s2);
//                })
//                .subscribe(s1 -> Log.i("subcribe", Thread.currentThread().getName()));

        netList = Client.getApiService().getUser("Hello");
//        netList
//                .subscribeOn(Schedulers.io())
//                .observeOn(AndroidSchedulers.mainThread())
//                .flatMap(itemEntities -> {Log.i("faltmap",Thread.currentThread().getName());return Observable.from(itemEntities);})
//                .subscribeOn(Schedulers.newThread())
//                .doOnNext(itemEntity -> Log.i("thread", Thread.currentThread().getName()))
//                .toList()
//                .map(itemEntities -> itemEntities.iterator())
//                .observeOn(AndroidSchedulers.mainThread())
//                .subscribe(iterator -> {
//                            Log.i("subscribe", Thread.currentThread().getName());
//                            while (iterator.hasNext())
//                                itemList.add(iterator.next());
//                            adapter.notifyDataSetChanged();
//                            swipe.setRefreshing(false);
//                        }
//                );




        netList.subscribe(itemEntities -> new Thread() {
            @Override
            public void run() {
                Looper.prepare();
                Iterator<ItemEntity> iteartor = itemEntities.iterator();
                while (iteartor.hasNext()) {
                    iteartor.next().save();
                    itemList.add(iteartor.next());
                }
                swipe.post(() -> {
                    adapter.notifyDataSetChanged();
                    swipe.setRefreshing(false);
                });
            }
        }.start());
    }
}