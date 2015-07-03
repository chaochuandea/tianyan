package dachuan.com.tianyan.view.base;

import android.app.Fragment;
import android.content.Context;
import android.os.Bundle;
import android.support.annotation.Nullable;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.FrameLayout;
import android.widget.Toast;

import butterknife.ButterKnife;
import rx.Observable;
import rx.android.schedulers.AndroidSchedulers;
import rx.functions.Action1;
import rx.schedulers.Schedulers;
import rx.subscriptions.CompositeSubscription;

import static rx.android.app.AppObservable.bindFragment;

/**
 * Created by maibenben on 2015/7/2.
 */
public abstract class BaseFragment  extends Fragment{
    private final CompositeSubscription subscription = new CompositeSubscription();

    @Nullable
    @Override
    public View onCreateView(LayoutInflater inflater, ViewGroup container, Bundle savedInstanceState) {
        FrameLayout frameLayout = new FrameLayout(container.getContext());

        View viewRoot = inflater.inflate(getLayoutId(),
                frameLayout, false);

        frameLayout.addView(viewRoot);
        ButterKnife.bind(viewRoot);
        init(savedInstanceState);
        return frameLayout;
    }


    public abstract void init(Bundle savedInstanceState);
    public abstract int getLayoutId();


    @Override public void onPause() {
        subscription.clear();
        super.onPause();
    }

    protected <T> void subscribe(Observable<T> observable) {
        subscribe(observable, t -> {
        });
    }

    protected  <T> void subscribe(Observable<T> observable, Action1<? super T> onNext) {
        subscription.add(bindFragment(this, observable.subscribeOn(Schedulers.io())).observeOn(AndroidSchedulers.mainThread()).subscribe(onNext,
                throwable -> Toast.makeText(getActivity(), throwable.getMessage(), Toast.LENGTH_SHORT).show()));

    }

    protected  <T> void subscribe(Observable<T> observable, Action1<? super T> onNext,Action1<Throwable> onError) {
        subscription.add(bindFragment(this, observable.subscribeOn(Schedulers.io())).observeOn(AndroidSchedulers.mainThread()).subscribe(onNext,onError));
    }
}
