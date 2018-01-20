package com.teslyuk.android.androidtutorial_volley_retrofit.data.source;

import com.teslyuk.android.androidtutorial_volley_retrofit.AndroidTutorialApplication;
import com.teslyuk.android.androidtutorial_volley_retrofit.data.model.Question;
import com.teslyuk.android.androidtutorial_volley_retrofit.data.model.StackOverflowQuestions;
import com.teslyuk.android.androidtutorial_volley_retrofit.data.model.StackOverflowTags;
import com.teslyuk.android.androidtutorial_volley_retrofit.data.model.Tag;
import com.teslyuk.android.androidtutorial_volley_retrofit.data.source.volley_api.WebRequest;
import com.teslyuk.android.androidtutorial_volley_retrofit.utils.Logger;

/**
 * Created by teslyuk.taras on 1/16/18.
 */

public class VolleyDataSource implements DataSource {

    @Override
    public void getQuestions(String tag, final LoadQuestionsCallback callback) {
        AndroidTutorialApplication.getRequestQueue().getQuestionByTag(tag, new WebRequest.ResponseListener() {
            @Override
            public void onResponseSuccess(Object response) {
                if (response != null && response instanceof StackOverflowQuestions) {
                    StackOverflowQuestions SOQuestions = (StackOverflowQuestions) response;
                    callback.onQuestionsLoaded(SOQuestions.getItems());
                } else {
                    callback.onFailure();
                }
            }

            @Override
            public void onResponseFail(String errorMessage) {
                callback.onFailure();
            }
        });
    }

    @Override
    public void getTags(final LoadTagsCallback callback) {
        AndroidTutorialApplication.getRequestQueue().getPopularTags(new WebRequest.ResponseListener() {
            @Override
            public void onResponseSuccess(Object response) {
                if (response != null && response instanceof StackOverflowTags) {
                    StackOverflowTags SOTags = (StackOverflowTags) response;
                    callback.onTagsLoaded(SOTags.getItems());
                } else {
                    callback.onFailure();
                }
            }

            @Override
            public void onResponseFail(String errorMessage) {
                callback.onFailure();
            }
        });
    }
}