package dachuan.com.tianyan.task;

import dachuan.com.tianyan.api.Client;
import rx.Observable;
import rx.subjects.PublishSubject;

/**
 * Created by maibenben on 2015/7/3.
 */
public class PageTask {
   private   PublishSubject<Integer> publishSubject = PublishSubject.create();
    public PublishSubject<Integer> getPageSubject() {
        return publishSubject;
    }

   public   PageTask(){
    }
}
