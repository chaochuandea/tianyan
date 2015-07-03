package dachuan.com.tianyan.api;

import com.google.gson.Gson;
import com.google.gson.GsonBuilder;
import com.squareup.okhttp.OkHttpClient;

import dachuan.com.tianyan.BuildConfig;
import retrofit.RestAdapter;
import retrofit.client.OkClient;
import retrofit.converter.GsonConverter;

/**
 * Created by maibenben on 2015/7/2.
 */
public class Client {
    private static GithubService apiService;
    private static Client client;
    private Client()
    {
        Gson gson = new GsonBuilder()
                .setDateFormat("yyyy'-'MM'-'dd'T'HH':'mm':'ss'.'SSS'Z'")
                .create();

        OkHttpClient okHttpClient = new OkHttpClient();
        RestAdapter restAdapter = new RestAdapter.Builder()
                .setLogLevel(BuildConfig.DEBUG ?
                        RestAdapter.LogLevel.FULL :
                        RestAdapter.LogLevel.NONE)
                .setEndpoint(GithubService.URL)
                .setClient(new OkClient(okHttpClient))
                .setConverter(new GsonConverter(gson))
                .build();

        apiService = restAdapter.create(GithubService.class);
    }

    public static GithubService getApiService()
    {
        if (client == null) client = new Client();
        return apiService;
    }
}
