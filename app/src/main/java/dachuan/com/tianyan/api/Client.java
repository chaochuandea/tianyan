package dachuan.com.tianyan.api;

import com.google.gson.Gson;
import com.google.gson.GsonBuilder;

import retrofit.RestAdapter;
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

        RestAdapter restAdapter = new RestAdapter.Builder()
                .setLogLevel(RestAdapter.LogLevel.FULL)
                .setEndpoint(GithubService.URL)
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
