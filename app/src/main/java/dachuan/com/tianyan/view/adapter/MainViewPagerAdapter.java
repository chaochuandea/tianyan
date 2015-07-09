package dachuan.com.tianyan.view.adapter;

import android.support.v4.app.Fragment;
import android.support.v4.app.FragmentManager;
import android.support.v4.app.FragmentPagerAdapter;

import dachuan.com.tianyan.view.fragment.EveryDayFragment;
import dachuan.com.tianyan.view.fragment.SortFragment;

/**
 * Created by linsj on 15-7-9.
 */
public class MainViewPagerAdapter extends FragmentPagerAdapter {

    public MainViewPagerAdapter(FragmentManager fm) {
        super(fm);
    }

    @Override
    public Fragment getItem(int position) {
        switch(position)
        {
            case 0 : return new EveryDayFragment();
            case 1 : return new SortFragment();
            default: return null;
        }
    }

    @Override
    public int getCount() {
        return 2;
    }
}
