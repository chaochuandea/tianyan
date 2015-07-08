package dachuan.com.tianyan.view.adapter;

import android.support.v4.app.Fragment;
import android.support.v4.app.FragmentManager;
import android.support.v4.app.FragmentPagerAdapter;

import dachuan.com.tianyan.view.fragment.MovieDetailFragment;

/**
 * Created by maibenben on 2015/7/8.
 */
public class DetailViewPagerAdapter extends FragmentPagerAdapter{

    public DetailViewPagerAdapter(FragmentManager fm) {
        super(fm);
    }

    @Override
    public CharSequence getPageTitle(int position) {
        return position+" - 5";
    }

    @Override
    public Fragment getItem(int position) {
        return new MovieDetailFragment();
    }

    @Override
    public int getCount() {
        return 5;
    }
}
