package dachuan.com.tianyan.task;

import com.google.gson.Gson;

import java.util.Objects;

import dachuan.com.tianyan.AppContext;
import dachuan.com.tianyan.model.Cache;
import dachuan.com.tianyan.util.ACache;
import rx.Observable;
import rx.schedulers.Schedulers;
import rx.subjects.PublishSubject;

/**
 * Created by maibenben on 2015/7/3.
 */
public class CacheTask {
   private static PublishSubject<Cache> publishSubject = PublishSubject.create();
    private static CacheTask task;
    public static PublishSubject<Cache> getCacheSubject() {
        if (task == null) task = new CacheTask();
        return publishSubject;
    }

    private CacheTask(){
        publishSubject.subscribeOn(Schedulers.io()).subscribe(objects -> {
            ACache.get(AppContext.instance()).put(objects.getKey(), new Gson().toJson(objects.getData()));
        });
    }
}
