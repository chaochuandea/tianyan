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
    @Bind(R.id.viewpager)
    ViewPager viewpager;

    @Bind(R.id.detail_view)
    View detail_view;

    @Bind(R.id.tabs)
    PagerSlidingTabStrip tabs;

    @Bind(R.id.main_list)
    public RecyclerView mainlist;

    @Bind(R.id.swipe)
    public SwipeRefreshLayout swipe;

    private PageTask pageTask;

    private String token;

    private EveryDayAdapter adapter;

    private List<String> datalist = new ArrayList<String>();
    private static String CACHE_KEY = "main_activity_data";
    List<Fragment> fragments = new ArrayList<>();
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

        initFragment();




        viewpager.setAdapter(new DetailViewPagerAdapter(getChildFragmentManager(),fragments));
        tabs.setViewPager(viewpager);
        viewpager.setPageTransformer(true, new MyTransformer());
        viewpager.setOffscreenPageLimit(5);
        viewpager.addOnPageChangeListener(new ViewPager.OnPageChangeListener() {
            @Override
            public void onPageScrolled(int position, float positionOffset, int positionOffsetPixels) {
                for (int i = 0; i < tabs.getChildCount(); i++) {
                    if (tabs.getChildAt(i) instanceof LinearLayout) {
                        ViewGroup con = (ViewGroup) tabs.getChildAt(i);
                        for (int j = 0; j < con.getChildCount(); j++) {
                            if (con.getChildAt(j) instanceof TextView) {
                                if (j == position) {
                                    ViewHelper.setAlpha(con.getChildAt(j), 1);
                                    TextView tv = (TextView) con.getChildAt(j);
                                    tv.setTextColor(getResources().getColor(R.color.white));
                                    tv.setText(Html.fromHtml("" + (position + 1) + " - " + 5));
                                    ViewHelper.setTranslationX(con.getChildAt(j), con.getChildAt(j).getWidth() * positionOffset);
                                } else {
                                    ViewHelper.setAlpha(con.getChildAt(j), 0);
                                }
                            }
                        }


                    }
                }
            }

            @Override
            public void onPageSelected(int position) {
                for (int i = 0; i < fragments.size(); i++) {
                    if (i == position) {
                        ((MovieDetailFragment) fragments.get(i)).startAnimation();
                    } else {
                        ((MovieDetailFragment) fragments.get(i)).hideTextView();
                    }
                }
            }

            @Override
            public void onPageScrollStateChanged(int state) {

            }
        });
    }

    private void initFragment() {
        for (int i = 0 ;i<5;i++){
            fragments.add(new MovieDetailFragment());
        }

    }

    class MyTransformer extends ABaseTransformer {

        @Override
        protected boolean isPagingEnabled() {
            return false;
        }

        @Override
        protected void onTransform(View view, float v) {


            View  cover = view.findViewById(R.id.cover);
            View blurringview = view.findViewById(R.id.blurringview);
            View text_con = view.findViewById(R.id.text);
            if (v<=0&&v>=-1){
                ViewHelper.setTranslationX(cover, view.getWidth() * v);
                ViewHelper.setAlpha(blurringview, 1 - Math.abs(v));

                if (v==0) {
                    ViewHelper.setAlpha(blurringview, 1);
                };


            }else if (v>0&&v<=1){
                ViewHelper.setTranslationX(cover, -view.getWidth() * (0.8f - 0.2f * v - 0.8f));

                ViewHelper.setAlpha(blurringview, 1);
            };


            if(v <= -1.0F || v >= 1.0F) {
                text_con.setAlpha(0.0F);
            } else if( v == 0.0F ) {
                text_con.setAlpha(1.0F);
            } else {
                // position is between -1.0F & 0.0F OR 0.0F & 1.0F
                text_con.setAlpha(1.0F - Math.abs(v));
            }
//            if (v <= 0f) {
//                ViewHelper.setAlpha(text_con, 1-Math.abs(v));
//                if (v == 0)ViewHelper.setAlpha(text_con, 1);
//            } else if (v <= 1f) {
//                ViewHelper.setAlpha(text_con, 0);
//                ViewHelper.setAlpha(text_con, Math.abs(v));
//            }

        }
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


    public void onBackPressed() {
        if (detail_view.getVisibility() == View.VISIBLE){
            detail_view.setVisibility(View.INVISIBLE);
            return;
        }

    }

    @Override
    public int getLayoutId() {
        return R.layout.fragment_everyday;
    }
}
