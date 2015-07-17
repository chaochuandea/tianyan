package dachuan.com.tianyan.view.adapter;

import android.support.v4.app.Fragment;
import android.support.v4.app.FragmentManager;
import android.support.v4.app.FragmentPagerAdapter;
import android.util.Log;

import java.util.List;

import dachuan.com.tianyan.view.fragment.MovieDetailFragment;

/**
 * Created by maibenben on 2015/7/8.
 */
public class DetailViewPagerAdapter extends FragmentPagerAdapter{
    List<Fragment> fragments ;
    public DetailViewPagerAdapter(FragmentManager fm,List<Fragment> fragments) {
        super(fm);
        this.fragments = fragments;
    }

    @Override
    public CharSequence getPageTitle(int position) {
        return position+" - 5";
    }

    @Override
    public Fragment getItem(int position) {
        return fragments.get(position);
    }

    @Override
    public int getCount() {
        return fragments.size();
    }
}
