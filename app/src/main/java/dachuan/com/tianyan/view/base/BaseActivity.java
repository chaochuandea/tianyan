package dachuan.com.tianyan.view.base;

import android.content.Context;
import android.os.Bundle;
import android.os.PersistableBundle;
import android.support.v7.app.AppCompatActivity;
import android.widget.Toast;

import com.facebook.drawee.backends.pipeline.Fresco;

import butterknife.ButterKnife;
import dachuan.com.tianyan.AppManager;
import rx.Observable;
import rx.android.schedulers.AndroidSchedulers;
import rx.functions.Action1;
import rx.schedulers.Schedulers;
import rx.subscriptions.CompositeSubscription;

import static rx.android.app.AppObservable.bindActivity;

/**
 * Created by maibenben on 2015/7/2.
 */
public  abstract  class BaseActivity extends AppCompatActivity {
    private final CompositeSubscription subscription = new CompositeSubscription();
    @Override
    public void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        Fresco.initialize(this);
        setContentView(getLayoutId());
        AppManager.getAppManager().addActivity(this);
        ButterKnife.bind(this);
        init(savedInstanceState);
    }

    public abstract int getLayoutId();

    public abstract void init(Bundle savedInstanceState);

    @Override protected void onPause() {
        subscription.clear();
        super.onPause();
    }

    protected <T> void subscribe(Observable<T> observable) {
        subscribe(observable, t -> {
        });
    }

    protected  <T> void subscribe(Observable<T> observable, Action1<? super T> onNext) {
        final Context context = this;
        subscription.add(bindActivity(this, observable.subscribeOn(Schedulers.io())).observeOn(AndroidSchedulers.mainThread()).subscribe(onNext,
                throwable -> Toast.makeText(context, throwable.getMessage(), Toast.LENGTH_SHORT).show()));
    }

    protected  <T> void subscribe(Observable<T> observable, Action1<? super T> onNext,Action1<Throwable> onError) {
        final Context context = this;
        subscription.add(bindActivity(this, observable.subscribeOn(Schedulers.io())).observeOn(AndroidSchedulers.mainThread()).subscribe(onNext, onError));
    }
}
