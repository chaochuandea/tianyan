package dachuan.com.tianyan.view.base;

import android.support.v7.widget.Toolbar;
import android.util.Log;
import android.view.View;
import android.view.animation.Animation;
import android.view.animation.AnimationUtils;
import android.widget.ImageView;
import android.widget.TextView;

import java.util.ArrayList;
import java.util.List;

import butterknife.Bind;
import dachuan.com.tianyan.R;
import dachuan.com.tianyan.view.adapter.ToolBarAdapter;
import dachuan.com.tianyan.view.widget.StaticViewpager;

/**
 * Created by xizi@linsj on 2015/7/16.
 */

/**
 *
 * */
public abstract class ToolBarActivity extends BaseActivity {

    static final int MAIN_PAGER = 0;
    static final int SETTING_PAGER = 1;
    static final int FAVORITES_PAGER = 2;
    static final int CACHE_PAGER = 3;
    static final int FEEDBACK_PAGER = 4;

    @Bind(R.id.toolbar)
    public Toolbar toolbar;
    @Bind(R.id.toolbar_dropdown)
    View dropdown;

    @Bind(R.id.toolbar_viewpager)
    StaticViewpager content;

    @Bind(R.id.icon)
    ImageView menu;

    @Bind(R.id.up)
    ImageView up;

    @Bind(R.id.eye)
    ImageView right;

    @Bind(R.id.setting_con)
    View setting;

    @Bind(R.id.favorties_con)
    View favorties;

    @Bind(R.id.cache_con)
    View cache;

    @Bind(R.id.feedback_con)
    View feedback;

    TextView myfavorites, mycache, myfeedback, daily_auto_caching, manual_caching;
    Animation showAni, hideAni, right_in, right_out, rotate_90, rotate_0, left_in;

    private boolean isShowDropDown = false;
    private List<View> views = new ArrayList<>();

    public void initToolBar() {
        loadAnimation();
        initPagerData();
        initListener();
        setSupportActionBar(toolbar);
        getSupportActionBar().setDisplayShowTitleEnabled(false);
        content.setAdapter(new ToolBarAdapter(views));
        dropdown.setVisibility(View.INVISIBLE);
        dropdown.setOnTouchListener((v, m) -> true);
    }

    public void initPagerData() {
        View mainView = getLayoutInflater().inflate(R.layout.dropdown_view_main, null);
        myfavorites = (TextView) mainView.findViewById(R.id.myfavorites);
        mycache = (TextView) mainView.findViewById(R.id.mycache);
        myfeedback = (TextView) mainView.findViewById(R.id.feedback);
        views.add(MAIN_PAGER, mainView);
        View setting = getLayoutInflater().inflate(R.layout.dropdown_view_setting, null);
        views.add(SETTING_PAGER, setting);
        View facoritesView = getLayoutInflater().inflate(R.layout.dropdown_view_myfavorites, null);
        views.add(FAVORITES_PAGER, facoritesView);
        View cache = getLayoutInflater().inflate(R.layout.dropdown_view_mycache, null);
        manual_caching = (TextView) cache.findViewById(R.id.manual_caching);
        daily_auto_caching = (TextView) cache.findViewById(R.id.daily_auto_caching);
        views.add(CACHE_PAGER, cache);
        View message = getLayoutInflater().inflate(R.layout.dropdown_view_feedback, null);
        views.add(FEEDBACK_PAGER, message);
    }


    public void showDropDown() {
        isShowDropDown = true;
        dropdown.setVisibility(View.VISIBLE);
        dropdown.startAnimation(showAni);
        menu.startAnimation(rotate_0);
        content.setCurrentItem(MAIN_PAGER, false);
        right.setImageResource(R.mipmap.setting);
    }

    public void hideDropDown() {
        isShowDropDown = false;
        backToMainFrom(content.getCurrentItem(),false);
        dropdown.setVisibility(View.INVISIBLE);
        dropdown.startAnimation(hideAni);
        menu.startAnimation(rotate_90);
        right.setImageResource(R.mipmap.eye);
    }

    private void loadAnimation() {
        showAni = AnimationUtils.loadAnimation(this, R.anim.slide_in_from_top);
        hideAni = AnimationUtils.loadAnimation(this, R.anim.slide_out_to_top);
        right_in = AnimationUtils.loadAnimation(this, R.anim.right_slide_in);
        right_out = AnimationUtils.loadAnimation(this, R.anim.right_slide_out);
        rotate_0 = AnimationUtils.loadAnimation(this, R.anim.rotate_0_90);
        rotate_90 = AnimationUtils.loadAnimation(this, R.anim.rotate_90_0);
        left_in = AnimationUtils.loadAnimation(this, R.anim.left_slide_in);
    }

    private void initListener() {
        menu.setOnClickListener(view -> {
            if (isShowDropDown) {
                hideDropDown();
            } else {
                showDropDown();
            }
        });
        up.setOnClickListener(v -> hideDropDown());
        right.setOnClickListener(v -> {
            if (content.getCurrentItem() != MAIN_PAGER || !isShowDropDown) return;
            else {
                showPager(SETTING_PAGER);
            }
        });
        myfavorites.setOnClickListener(v ->showPager(FAVORITES_PAGER));
        mycache.setOnClickListener(v -> showPager(CACHE_PAGER));
        myfeedback.setOnClickListener(v -> showPager(FEEDBACK_PAGER));
        findViewById(R.id.setting_back).setOnClickListener(v->backToMainFrom(content.getCurrentItem(),true));
        findViewById(R.id.feedback_back).setOnClickListener(v->backToMainFrom(content.getCurrentItem(),true));
        findViewById(R.id.favorties_back).setOnClickListener(v->backToMainFrom(content.getCurrentItem(),true));
        findViewById(R.id.cache_back).setOnClickListener(v->backToMainFrom(content.getCurrentItem(),true));
    }

    @Override
    public void onBackPressed() {
        if (isShowDropDown )
            backToMainFrom(content.getCurrentItem(),true);
        else if (isShowDropDown)
            hideDropDown();
        else super.onBackPressed();
    }

    public void backToMainFrom(int currentPager,boolean anim) {
        switch (currentPager) {
            case SETTING_PAGER:
                setting.setVisibility(View.INVISIBLE);
                setting.startAnimation(right_out);
                break;
            case FAVORITES_PAGER:
                favorties.setVisibility(View.INVISIBLE);
                favorties.startAnimation(right_out);
                break;
            case CACHE_PAGER:
                cache.setVisibility(View.INVISIBLE);
                cache.startAnimation(right_out);
                break;
            case FEEDBACK_PAGER:
                feedback.setVisibility(View.INVISIBLE);
                feedback.startAnimation(right_out);
                break;
        }
        if(anim){
            content.startAnimation(left_in);
            content.setCurrentItem(MAIN_PAGER, false);
        }
    }

    public void showPager(int currentPager) {
        switch (currentPager) {
            case SETTING_PAGER:
                setting.setVisibility(View.VISIBLE);
                setting.startAnimation(right_in);
                content.setCurrentItem(SETTING_PAGER, false);
                content.startAnimation(right_in);
                break;

            case FAVORITES_PAGER:
                favorties.setVisibility(View.VISIBLE);
                favorties.startAnimation(right_in);
                content.setCurrentItem(FAVORITES_PAGER, false);
                content.startAnimation(right_in);
                break;

            case CACHE_PAGER:
                cache.setVisibility(View.VISIBLE);
                cache.startAnimation(right_in);
                content.setCurrentItem(CACHE_PAGER, false);
                content.startAnimation(right_in);
                break;
            case FEEDBACK_PAGER:
                feedback.setVisibility(View.VISIBLE);
                feedback.startAnimation(right_in);
                content.setCurrentItem(FEEDBACK_PAGER, false);
                content.startAnimation(right_in);
                break;
            case MAIN_PAGER:
                backToMainFrom(content.getCurrentItem(),true);
                break;
        }
    }
}
