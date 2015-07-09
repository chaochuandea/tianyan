package dachuan.com.tianyan.view.activity;

import android.os.Bundle;
import android.support.v4.view.ViewPager;
import android.util.Log;
import android.view.View;
import android.widget.TextView;

import butterknife.Bind;
import dachuan.com.tianyan.R;
import dachuan.com.tianyan.view.adapter.MainViewPagerAdapter;
import dachuan.com.tianyan.view.base.BaseActivity;
import dachuan.com.tianyan.view.widget.StaticViewpager;
import rx.Observable;
import rx.Observer;
import rx.android.schedulers.AndroidSchedulers;

/**
 * Created by linsj on 15-7-9.
 */
public class ActivityFoReplace extends BaseActivity implements View.OnClickListener{


    @Bind(R.id.main_viewpager)
    public  StaticViewpager mainViewPager;
    private MainViewPagerAdapter adapter;
    @Bind(R.id.bottombar)
    public  View bottombar;
    @Bind(R.id.text1)
    TextView button_1;
    @Bind(R.id.text2)
    TextView button_2;

    @Override
    public int getLayoutId() {
        return R.layout.activity_main_with_viewpager;
    }

    @Override
    public void init(Bundle savedInstanceState) {
        adapter = new MainViewPagerAdapter(getSupportFragmentManager());
        mainViewPager.setAdapter(adapter);
        bottombar.findViewById(R.id.text1).setOnClickListener(this);
        bottombar.findViewById(R.id.text2).setOnClickListener(this);
        changeButtonState();
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
            Log.i("switch","what the fuck is wrong?");
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
}
