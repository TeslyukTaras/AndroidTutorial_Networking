package com.teslyuk.android.androidtutorial_volley_retrofit.ui;

import android.util.Log;

import com.teslyuk.android.androidtutorial_volley_retrofit.data.model.Question;
import com.teslyuk.android.androidtutorial_volley_retrofit.data.model.Tag;
import com.teslyuk.android.androidtutorial_volley_retrofit.data.source.ApiRepository;
import com.teslyuk.android.androidtutorial_volley_retrofit.data.source.DataSource;
import com.teslyuk.android.androidtutorial_volley_retrofit.utils.Logger;

import java.util.List;

/**
 * Created by teslyuk.taras on 1/16/18.
 */

public class MainPresenter implements Contract.IMainPresenter {
    private static final String TAG = MainPresenter.class.getSimpleName();

    private Contract.IMainView view;
    private ApiRepository apiRepository;

    public MainPresenter(ApiRepository apiRepository, Contract.IMainView view) {
        this.apiRepository = apiRepository;
        this.view = view;
    }

    @Override
    public void onGetQuestions() {
        String tag = view.getTag();
        Log.d(TAG, "onGetQuestions tag: " + tag);
        view.showProgress();
        apiRepository.setFrameworkType(view.isRetrofitFlag());
        apiRepository.getQuestions(tag, new DataSource.LoadQuestionsCallback() {
            @Override
            public void onQuestionsLoaded(List<Question> questions) {
                Log.d(TAG, "onQuestionsLoaded");
                int number = 0;
                for (Question question : questions) {
                    Log.d(TAG, "question#" + number++ + " question.title: " + question.getTitle());
                }
                view.setQuestions(questions);
                view.hideProgress();
            }

            @Override
            public void onFailure() {
                Log.d(TAG, "onFailure");
                view.hideProgress();
                view.onRequestError("Unable to load questions");
            }
        });
    }

    @Override
    public void onGetTags() {
        Log.d(TAG, "onGetTags");
        view.showProgress();
        apiRepository.setFrameworkType(view.isRetrofitFlag());
        apiRepository.getTags(new DataSource.LoadTagsCallback() {
            @Override
            public void onTagsLoaded(List<Tag> tags) {
                Log.d(TAG, "onTagsLoaded");
                int number = 0;
                for (Tag tag : tags) {
                    Logger.d(TAG, "tag#" + number++ + " name: " + tag.getName() + " count: " + tag.getCount());
                }
                view.setTags(tags);
                view.hideProgress();
            }

            @Override
            public void onFailure() {
                Log.d(TAG, "onFailure");
                view.hideProgress();
                view.onRequestError("Unable to load tags");
            }
        });
    }
}