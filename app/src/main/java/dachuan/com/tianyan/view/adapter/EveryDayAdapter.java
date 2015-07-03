package dachuan.com.tianyan.view.adapter;

import android.support.v7.widget.RecyclerView;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.ImageView;
import android.widget.TextView;

import com.facebook.drawee.view.SimpleDraweeView;

import java.util.List;

import dachuan.com.tianyan.R;


/**
 * Created by linsj on 15-7-2.
 */
public class EveryDayAdapter extends RecyclerView.Adapter{

    private  List<String> list;
    public EveryDayAdapter(List<String> list) {
        this.list = list;
    }

    @Override
    public RecyclerView.ViewHolder onCreateViewHolder(ViewGroup parent, int viewType) {
        View v = LayoutInflater.from(parent.getContext()).inflate(R.layout.holder_test,null);
        return new ViewHolder(v);
    }

    @Override
    public void onBindViewHolder(RecyclerView.ViewHolder holder, int position) {
        ViewHolder viewHolder = (ViewHolder)holder;
        viewHolder.name.setText("Hello , " + list.get(position));
    }

    @Override
    public int getItemCount() {
        return list.size();
    }

    static class ViewHolder extends RecyclerView.ViewHolder{

        SimpleDraweeView cover,cover_bg;
        TextView name,tag_and_time;
        public ViewHolder(View itemView) {
            super(itemView);
            name = (TextView) itemView.findViewById(R.id.name);
            tag_and_time = (TextView) itemView.findViewById(R.id.tag_and_time);
//            cover = (SimpleDraweeView) itemView.findViewById(R.id.cover);
//            cover_bg = (SimpleDraweeView) itemView.findViewById(R.id.cover_bg);
        }
    }
}
