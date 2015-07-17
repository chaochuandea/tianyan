package dachuan.com.tianyan.view.activity;

import android.content.Intent;
import android.os.Bundle;
import android.support.v4.app.Fragment;
import android.support.v4.view.ViewPager;
import android.text.Html;
import android.view.View;
import android.view.ViewGroup;
import android.widget.LinearLayout;
import android.widget.TextView;

import com.ToxicBakery.viewpager.transforms.ABaseTransformer;
import com.astuetz.PagerSlidingTabStrip;
import com.nineoldandroids.view.ViewHelper;

import java.util.ArrayList;
import java.util.List;

import butterknife.Bind;
import dachuan.com.tianyan.R;
import dachuan.com.tianyan.view.adapter.DetailViewPagerAdapter;
import dachuan.com.tianyan.view.base.BaseActivity;
import dachuan.com.tianyan.view.base.ToolBarActivity;
import dachuan.com.tianyan.view.fragment.MovieDetailFragment;

/**
 * Created by xizi@linsj on 2015/7/15.
 */
public class ItemDetailActivity extends ToolBarActivity {

    @Bind(R.id.viewpager)
    ViewPager viewPager;
    @Bind(R.id.tabs)
    PagerSlidingTabStrip tabs;

    List<Fragment> fragments = new ArrayList<>();
    @Override
    public int getLayoutId() {
        return R.layout.activity_item_detial;
    }

    @Override
    public void init(Bundle savedInstanceState) {
        initToolBar();
        initFragment();
        viewPager.setAdapter(new DetailViewPagerAdapter(getSupportFragmentManager(), fragments));
        tabs.setViewPager(viewPager);
        viewPager.setPageTransformer(true, new MyTransformer());
        viewPager.setOffscreenPageLimit(5);
        viewPager.addOnPageChangeListener(new ViewPager.OnPageChangeListener() {
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



    class MyTransformer extends ABaseTransformer {

        @Override
        protected boolean isPagingEnabled() {
            return false;
        }

        @Override
        protected void onTransform(View view, float v) {


            View cover = view.findViewById(R.id.cover);
            cover.setOnClickListener(new View.OnClickListener() {
                @Override
                public void onClick(View v) {
                    Intent intent = new Intent(ItemDetailActivity.this, VideoActivity.class);
                    startActivity(intent);
                }
            });
            View blurringview = view.findViewById(R.id.blurringview);
            View text_con = view.findViewById(R.id.text);
            if (v <= 0 && v >= -1) {
                ViewHelper.setTranslationX(cover, view.getWidth() * v);
                ViewHelper.setAlpha(blurringview, 1 - Math.abs(v));

                if (v == 0) {
                    ViewHelper.setAlpha(blurringview, 1);
                }


            } else if (v > 0 && v <= 1) {
                ViewHelper.setTranslationX(cover, -view.getWidth() * (0.8f - 0.2f * v - 0.8f));

                ViewHelper.setAlpha(blurringview, 1);
            }



            if (v <= -1.0F || v >= 1.0F) {
                text_con.setAlpha(0.0F);
            } else if (v == 0.0F) {
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

    private void initFragment() {
        for (int i = 0; i < 5; i++) {
            fragments.add(new MovieDetailFragment());
        }

    }

}
