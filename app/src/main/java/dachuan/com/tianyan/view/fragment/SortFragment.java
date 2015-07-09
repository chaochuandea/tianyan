package dachuan.com.tianyan.view.fragment;

import android.os.Bundle;
import android.support.v7.widget.GridLayoutManager;
import android.support.v7.widget.LinearLayoutManager;
import android.support.v7.widget.RecyclerView;

import java.util.ArrayList;
import java.util.List;

import butterknife.Bind;
import butterknife.ButterKnife;
import dachuan.com.tianyan.R;
import dachuan.com.tianyan.view.adapter.GridAdapter;
import dachuan.com.tianyan.view.base.BaseFragment;
import dachuan.com.tianyan.view.widget.WhiteDecoration;

/**
 * Created by linsj on 15-7-9.
 */
public class SortFragment extends BaseFragment {

    @Bind(R.id.grid_recycler)
    RecyclerView gridlist;

    List<String> list = new ArrayList<String>();

    @Override
    public void init(Bundle savedInstanceState) {
        initData();
        gridlist.setLayoutManager(new GridLayoutManager(getActivity(), 2));
        gridlist.setAdapter(new GridAdapter(getActivity(),list));
        gridlist.addItemDecoration(new WhiteDecoration(getActivity(),8));
    }

    @Override
    public int getLayoutId() {
        return R.layout.fragment_sort;
    }

    private void initData() {
        for (int i = 0; i < 10; i++)
        {
            list.add("#item  " + i);
        }
    }
}
