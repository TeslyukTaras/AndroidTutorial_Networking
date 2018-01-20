package com.teslyuk.android.androidtutorial_volley_retrofit.data.source;

import android.util.Log;

import com.teslyuk.android.androidtutorial_volley_retrofit.data.model.StackOverflowQuestions;
import com.teslyuk.android.androidtutorial_volley_retrofit.data.model.StackOverflowTags;
import com.teslyuk.android.androidtutorial_volley_retrofit.data.source.retrofit_api.StackOverflowAPI;

import okhttp3.OkHttpClient;
import okhttp3.logging.HttpLoggingInterceptor;
import retrofit2.Call;
import retrofit2.Callback;
import retrofit2.Response;
import retrofit2.Retrofit;
import retrofit2.converter.gson.GsonConverterFactory;

/**
 * Created by teslyuk.taras on 1/16/18.
 */

public class RetrofitDataSource implements DataSource {

    StackOverflowAPI stackOverflowAPI;

    public RetrofitDataSource() {
        HttpLoggingInterceptor interceptor = new HttpLoggingInterceptor();
        interceptor.setLevel(HttpLoggingInterceptor.Level.BODY);
        OkHttpClient client = new OkHttpClient.Builder().addInterceptor(interceptor).build();

        Retrofit retrofit = new Retrofit.Builder()
                .baseUrl("https://api.stackexchange.com")
                .client(client)
                .addConverterFactory(GsonConverterFactory.create())
                .build();

        // prepare call in Retrofit 2.0
        stackOverflowAPI = retrofit.create(StackOverflowAPI.class);
    }

    @Override
    public void getQuestions(String tag, final LoadQuestionsCallback callback) {
        Call<StackOverflowQuestions> call = stackOverflowAPI.loadQuestions(tag);
        //asynchronous call
        call.enqueue(new Callback<StackOverflowQuestions>() {
            @Override
            public void onResponse(Call<StackOverflowQuestions> call, Response<StackOverflowQuestions> response) {
                if (response != null && response.body() != null) {
                    callback.onQuestionsLoaded(response.body().getItems());
                } else {
                    callback.onFailure();
                }
            }

            @Override
            public void onFailure(Call<StackOverflowQuestions> call, Throwable t) {
                callback.onFailure();
            }
        });
    }

    @Override
    public void getTags(final LoadTagsCallback callback) {
        Call<StackOverflowTags> call = stackOverflowAPI.loadPopularTags();
        //asynchronous call
        call.enqueue(new Callback<StackOverflowTags>() {
                         @Override
                         public void onResponse(Call<StackOverflowTags> call, Response<StackOverflowTags> response) {
                             if (response != null && response.body() != null) {
                                 callback.onTagsLoaded(response.body().getItems());
                             } else {
                                 callback.onFailure();
                             }
                         }

                         @Override
                         public void onFailure(Call<StackOverflowTags> call, Throwable t) {
                             callback.onFailure();
                         }
                     }
        );
    }
}
