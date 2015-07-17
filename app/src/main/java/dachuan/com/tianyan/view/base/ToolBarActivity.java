package dachuan.com.tianyan.view.base;

import android.support.v7.widget.Toolbar;
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
import dachuan.com.tianyan.view.base.BaseActivity;
import dachuan.com.tianyan.view.widget.StaticViewpager;

/**
 * Created by xizi@linsj on 2015/7/16.
 */

/**
 *
* */
public abstract class ToolBarActivity extends BaseActivity {

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

    TextView myfavorites,mycache,feedback,daily_auto_caching,manual_caching;
    Animation showAni, hideAni, right_in, right_out, rotate_90, rotate_0,left_in;

    private boolean isShowDropDown = false;
    private boolean isShowSubMenu = false;
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

    public void initPagerData()
    {
        View mainView = getLayoutInflater().inflate(R.layout.dropdown_view_main, null);
        myfavorites = (TextView) mainView.findViewById(R.id.myfavorites);
        mycache = (TextView) mainView.findViewById(R.id.mycache);
        feedback = (TextView)   mainView.findViewById(R.id.feedback);
        views.add(mainView);
        View setting =  getLayoutInflater().inflate(R.layout.dropdown_view_setting, null);
        views.add(setting);
        View facoritesView = getLayoutInflater().inflate(R.layout.dropdown_view_myfavorites, null);
        views.add(facoritesView);
        View cache = getLayoutInflater().inflate(R.layout.dropdown_view_mycache, null);
        manual_caching = (TextView) cache.findViewById(R.id.manual_caching);
        daily_auto_caching = (TextView) cache.findViewById(R.id.daily_auto_caching);
        views.add(cache);
        View message = getLayoutInflater().inflate(R.layout.dropdown_view_feedback, null);
        views.add(message);
    }


    public void showDropDown(){
        isShowDropDown = true;
        dropdown.setVisibility(View.VISIBLE);
        dropdown.startAnimation(showAni);
        menu.startAnimation(rotate_0);
        content.setCurrentItem(0, false);
        isShowSubMenu = false;
        right.setImageResource(R.mipmap.setting);
    }
    public void hideDropDown(){
        isShowDropDown = false;
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
        up.setOnClickListener(v ->hideDropDown());
        right.setOnClickListener(v->{
            if(isShowSubMenu) return;
            else {
                content.setCurrentItem(1,false);
                content.startAnimation(right_in);
                isShowSubMenu = true;}
            });
        myfavorites.setOnClickListener(v->{
            content.setCurrentItem(2,false);
            content.startAnimation(right_in);

            isShowSubMenu = true;
        });
        mycache.setOnClickListener(v->{
            content.setCurrentItem(3,false);
            content.startAnimation(right_in);

            isShowSubMenu = true;
        });
        feedback.setOnClickListener(v->{
            content.setCurrentItem(4,false);
            content.startAnimation(right_in);

            isShowSubMenu = true;
        });

    }

    @Override
    public void onBackPressed() {
        if(isShowDropDown && isShowSubMenu)
            backToMain();
        else if(isShowDropDown)
            hideDropDown();
        else super.onBackPressed();
    }

    public void backToMain(){
        isShowSubMenu = false;
        content.setCurrentItem(0,false);
        content.startAnimation(left_in);
    }
}
