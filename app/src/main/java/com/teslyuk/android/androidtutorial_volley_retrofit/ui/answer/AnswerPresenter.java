package com.teslyuk.android.androidtutorial_volley_retrofit.ui.answer;

import com.teslyuk.android.androidtutorial_volley_retrofit.data.model.Answer;
import com.teslyuk.android.androidtutorial_volley_retrofit.data.source.ApiRepository;
import com.teslyuk.android.androidtutorial_volley_retrofit.data.source.DataSource;

import android.util.Log;

import java.util.List;

public class AnswerPresenter implements AnswerContract.IAnswerPresenter {

    private static final String TAG = AnswerPresenter.class.getSimpleName();
    private AnswerContract.IAnswerView view;
    private ApiRepository apiRepository;

    public AnswerPresenter(ApiRepository apiRepository, AnswerContract.IAnswerView view) {
        this.apiRepository = apiRepository;
        this.view = view;
    }

    @Override
    public void start() {
        Log.d(TAG, "onStart");
        view.showProgress();
        apiRepository.getAnswers(new DataSource.LoadCallback<Answer>() {
            @Override
            public void onDataLoaded(List<Answer> tags) {
                Log.d(TAG, "onTagsLoaded");
                view.setAnswers(tags);
                view.hideProgress();
            }

            @Override
            public void onFailure() {
                view.hideProgress();
                view.onRequestError("Unable to load tags");
            }
        });
    }
}
