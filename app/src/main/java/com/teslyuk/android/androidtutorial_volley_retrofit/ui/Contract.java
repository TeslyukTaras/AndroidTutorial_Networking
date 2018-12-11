package com.teslyuk.android.androidtutorial_volley_retrofit.ui;

import com.teslyuk.android.androidtutorial_volley_retrofit.data.model.Question;
import com.teslyuk.android.androidtutorial_volley_retrofit.data.model.Tag;

import java.util.List;

/**
 * Created by teslyuk.taras on 1/16/18.
 */

public interface Contract {

    interface IMainView {
        void showProgress();

        void hideProgress();

        void setTags(List<Tag> tags);

        void setQuestions(List<Question> questions);

        boolean isRetrofitFlag();

        String getTag();

        void onRequestError(String error);
    }

    interface IMainPresenter {

        void onSearchClick();

        void onStart();
    }

}
