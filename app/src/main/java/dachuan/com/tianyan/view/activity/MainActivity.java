package dachuan.com.tianyan.view.activity;

import android.os.Bundle;
import android.view.View;
import android.widget.ProgressBar;
import android.widget.TextView;

import butterknife.Bind;
import dachuan.com.tianyan.AppContext;
import dachuan.com.tianyan.R;
import dachuan.com.tianyan.view.adapter.MainViewPagerAdapter;
import dachuan.com.tianyan.view.base.ToolBarActivity;
import dachuan.com.tianyan.view.entity.OnLoadingFailedEntity;
import dachuan.com.tianyan.view.entity.OnReLoadingEntity;
import dachuan.com.tianyan.view.widget.StaticViewpager;
import io.vov.vitamio.LibsChecker;
import rx.Observable;
import rx.android.schedulers.AndroidSchedulers;

/**
 * Created by xizi@linsj on 2015/7/17.
 */
public class MainActivity extends ToolBarActivity implements View.OnClickListener{

    public static String INTENT_TOKEN = "intent_token";


    @Bind(R.id.main_viewpager)
    public StaticViewpager mainViewPager;
    private MainViewPagerAdapter adapter;
    @Bind(R.id.bottombar)
    public View bottombar;
    @Bind(R.id.text1)
    TextView button_1;
    @Bind(R.id.text2)
    TextView button_2;
    @Bind(R.id.cancel)
    View cancelRetry;
    @Bind(R.id.loading_failed)
    View loadingFailedView;
    @Bind(R.id.title)
    TextView title;

    @Override
    public int getLayoutId() {
        return R.layout.activity_main;
    }


    @Override
    public void onClick(View v) {
        switch (v.getId()) {
            case R.id.text1:
                mainViewPager.setCurrentItem(0, false);
                changeButtonState();
                break;
            case R.id.text2:
                mainViewPager.setCurrentItem(1, false);
                changeButtonState();
                break;
        }
    }
    private void changeButtonState() {
        Observable.just(mainViewPager.getCurrentItem()).observeOn(AndroidSchedulers.mainThread()).subscribe(item -> {
            switch (item) {
                case 0:
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
        if (!LibsChecker.checkVitamioLibs(this))
            return;
        initToolBar();
        AppContext.instance().getBus().register(this);
        setSupportActionBar(toolbar);
        getSupportActionBar().setDisplayShowTitleEnabled(false);
        title.setText("水不息");

        adapter = new MainViewPagerAdapter(getSupportFragmentManager());
//        adapter.getEveryDayFragment().setDetail_view(detail_view);
        mainViewPager.setAdapter(adapter);
        bottombar.findViewById(R.id.text1).setOnClickListener(this);
        bottombar.findViewById(R.id.text2).setOnClickListener(this);
        changeButtonState();
        initListener();
    }

    @Override
    protected void onDestroy() {
        AppContext.instance().getBus().unregister(this);
        super.onDestroy();
    }

    public void onEvent(OnLoadingFailedEntity e) {
        loadingFailedView.setVisibility(View.VISIBLE);
    }

    public void initListener() {
        loadingFailedView.setOnTouchListener((v, m) -> {
            loadingFailedView.setVisibility(View.GONE);
            AppContext.instance().getBus().post(new OnReLoadingEntity());
            return true;
        });
        cancelRetry.setOnClickListener(v -> loadingFailedView.setVisibility(View.GONE));
    }


}
