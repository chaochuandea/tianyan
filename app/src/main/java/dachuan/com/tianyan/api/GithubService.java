package dachuan.com.tianyan.api;

import java.util.List;

import dachuan.com.tianyan.model.ItemEntity;
import dachuan.com.tianyan.model.User;
import retrofit.http.Body;
import retrofit.http.GET;
import retrofit.http.POST;
import retrofit.http.Query;
import rx.Observable;

/**
 * Created by maibenben on 2015/7/2.
 */
public interface GithubService {
    public static String URL ="http://45.55.10.19:8080/untitled";

    @GET("/list")
    Observable<List<ItemEntity>> getUser(@Query("refresh") String token );
}
