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
import rx.functions.Action0;
import rx.functions.Action1;
import rx.schedulers.Schedulers;
import rx.subscriptions.CompositeSubscription;
import uk.co.chrisjenx.calligraphy.CalligraphyContextWrapper;

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

    @Override
    protected void attachBaseContext(Context newBase) {
        super.attachBaseContext(CalligraphyContextWrapper.wrap(newBase));
    }

    public abstract int getLayoutId();

    public abstract void init(Bundle savedInstanceState);

    @Override
    protected void onDestroy() {
        subscription.clear();
        super.onDestroy();
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

    protected  <T> void subscribe(Observable<T> observable, Action1<? super T> onNext,Action1<Throwable> onError,Action0 onComplete) {
        final Context context = this;
        subscription.add(bindActivity(this, observable.subscribeOn(Schedulers.io())).observeOn(AndroidSchedulers.mainThread()).subscribe(onNext, onError,onComplete));
    }
}
