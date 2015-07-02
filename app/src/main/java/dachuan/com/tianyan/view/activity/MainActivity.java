package dachuan.com.tianyan.view.activity;

import android.support.v7.app.ActionBarActivity;
import android.os.Bundle;
import android.view.Menu;
import android.view.MenuItem;
import android.widget.TextView;

import butterknife.Bind;
import dachuan.com.tianyan.R;
import dachuan.com.tianyan.api.Client;
import dachuan.com.tianyan.view.base.BaseActivity;
import timber.log.Timber;


public class MainActivity extends BaseActivity {

    @Bind(R.id.text)
    TextView text;

    @Override
    public void init(Bundle savedInstanceState) {
        text.setText("hello");
    }

    @Override
    public int getLayoutId() {
        return R.layout.activity_main;
    }


    private void getData(){
       subscribe( Client.getApiService().getUser(),user -> Timber.d(user.toString()));
    }
}
