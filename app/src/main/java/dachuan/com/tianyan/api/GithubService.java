package dachuan.com.tianyan.api;

import java.util.List;

import dachuan.com.tianyan.model.User;
import retrofit.http.POST;
import rx.Observable;

/**
 * Created by maibenben on 2015/7/2.
 */
public interface GithubService {
    public static String URL ="http://baidu.com";

    @POST("/user")
    Observable<List<User>> getUser(String token ,int page);
}
