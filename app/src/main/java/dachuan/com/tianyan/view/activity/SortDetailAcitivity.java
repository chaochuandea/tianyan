package dachuan.com.tianyan.view.activity;

import android.os.Bundle;
import android.support.v7.widget.LinearLayoutManager;
import android.support.v7.widget.RecyclerView;
import android.view.View;
import android.widget.ImageView;
import android.widget.TextView;

import java.util.ArrayList;
import java.util.List;

import butterknife.Bind;
import dachuan.com.tianyan.R;
import dachuan.com.tianyan.view.adapter.EveryDayAdapter;
import dachuan.com.tianyan.view.base.BaseActivity;

/**
 * Created by xizi@linsj on 2015/7/15.
 */
public class SortDetailAcitivity extends BaseActivity {


    @Bind(R.id.right_eye)
    ImageView right_eye;
    @Bind(R.id.sort_title)
    TextView title;
    @Bind(R.id.img_back)
    ImageView back;
    @Bind(R.id.sort_list)
    RecyclerView sortList;

    List<String> DataLIist = new ArrayList<String>();


    EveryDayAdapter adapter;
    @Override
    public int getLayoutId() {
        return R.layout.activity_sort_detail;
    }

    @Override
    public void init(Bundle savedInstanceState) {
        getData();
        adapter = new EveryDayAdapter(DataLIist,this);
        title.setText(getIntent().getStringExtra("title"));
        sortList.setLayoutManager(new LinearLayoutManager(this,LinearLayoutManager.VERTICAL,false));
        sortList.setAdapter(adapter);
        back.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                finish();
            }
        });
    }

    public void getData()
    {
        for(int i = 0 ; i < 10 ;i ++)
        {
            DataLIist.add("#Item " + i );
        }
    }

    @Override
    public void onBackPressed() {
        finish();
    }
}
