package com.teslyuk.android.androidtutorial_volley_retrofit.data.source.retrofit_api;

import com.teslyuk.android.androidtutorial_volley_retrofit.data.model.StackOverflowQuestions;
import com.teslyuk.android.androidtutorial_volley_retrofit.data.model.StackOverflowTags;

import retrofit2.Call;
import retrofit2.http.GET;
import retrofit2.http.Query;

/**
 * Created by taras on 14.02.16.
 */
public interface StackOverflowAPI {
    @GET("/2.2/questions?order=desc&sort=creation&site=stackoverflow")
    Call<StackOverflowQuestions> loadQuestions(@Query("tagged") String tags);

    @GET("/2.2/tags?order=desc&sort=popular&site=stackoverflow")
    Call<StackOverflowTags> loadPopularTags();
}