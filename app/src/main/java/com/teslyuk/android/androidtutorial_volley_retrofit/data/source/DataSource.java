package com.teslyuk.android.androidtutorial_volley_retrofit.data.source;

import com.teslyuk.android.androidtutorial_volley_retrofit.data.model.Question;
import com.teslyuk.android.androidtutorial_volley_retrofit.data.model.Tag;

import java.util.List;

/**
 * Created by teslyuk.taras on 1/16/18.
 */

public interface DataSource {

    interface LoadQuestionsCallback {
        void onQuestionsLoaded(List<Question> questions);

        void onFailure();
    }

    interface LoadTagsCallback {
        void onTagsLoaded(List<Tag> tags);

        void onFailure();
    }

    void getQuestions(String tag, LoadQuestionsCallback callback);

    void getTags(LoadTagsCallback callback);
}
