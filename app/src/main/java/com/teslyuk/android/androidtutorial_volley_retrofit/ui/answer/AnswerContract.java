package com.teslyuk.android.androidtutorial_volley_retrofit.ui.answer;

import com.teslyuk.android.androidtutorial_volley_retrofit.data.model.Answer;

import java.util.List;

public interface AnswerContract {

    interface IAnswerView {
        void showProgress();

        void hideProgress();

        void setAnswers(List<Answer> tags);


        void onRequestError(String error);
    }

    interface IAnswerPresenter {
        void start();
    }
}
