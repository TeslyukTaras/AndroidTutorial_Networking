package com.teslyuk.android.androidtutorial_volley_retrofit.ui.answer;

import android.content.Context;
import android.content.Intent;
import android.net.Uri;
import android.os.Bundle;
import android.support.v7.app.AppCompatActivity;
import android.util.Log;
import android.view.View;
import android.view.inputmethod.InputMethodManager;
import android.widget.ArrayAdapter;
import android.widget.AutoCompleteTextView;
import android.widget.Button;
import android.widget.ListView;
import android.widget.RadioButton;
import android.widget.TextView;
import android.widget.Toast;

import com.teslyuk.android.androidtutorial_volley_retrofit.R;
import com.teslyuk.android.androidtutorial_volley_retrofit.data.model.Answer;
import com.teslyuk.android.androidtutorial_volley_retrofit.data.model.Question;
import com.teslyuk.android.androidtutorial_volley_retrofit.data.model.Tag;
import com.teslyuk.android.androidtutorial_volley_retrofit.data.source.ApiRepository;
import com.teslyuk.android.androidtutorial_volley_retrofit.ui.Contract;
import com.teslyuk.android.androidtutorial_volley_retrofit.ui.MainActivity;
import com.teslyuk.android.androidtutorial_volley_retrofit.ui.MainPresenter;
import com.teslyuk.android.androidtutorial_volley_retrofit.ui.adapter.AnswerListAdapter;
import com.teslyuk.android.androidtutorial_volley_retrofit.ui.adapter.QuestionListAdapter;
import com.teslyuk.android.androidtutorial_volley_retrofit.utils.Logger;

import java.util.ArrayList;
import java.util.List;

public class AnswerActivity extends AppCompatActivity implements AnswerContract.IAnswerView {

    private static final String TAG = AnswerActivity.class.getSimpleName();


    private ListView answerListView;

    private AnswerListAdapter answersAdapter;

    private ApiRepository apiRepository;

    private AnswerContract.IAnswerPresenter presenter;

    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_answer);

        initView();
        initData();
        initAdapters();
        initListeners();
        initPresenter();
    }

    private void initView() {
        answerListView = findViewById(R.id.questions_list);
    }

    private void initData() {
        apiRepository = ApiRepository.getInstance();
    }

    private void initAdapters() {
        List<Answer> answers = new ArrayList<>();

        answersAdapter = new AnswerListAdapter(this, answers);
        answerListView.setAdapter(answersAdapter);
    }

    private void initListeners() {

    }

    private void initPresenter() {
        presenter = new AnswerPresenter(apiRepository, this);
    }

    @Override
    protected void onResume() {
        super.onResume();
        presenter.start();
    }

    public void showProgressLoaderWithBackground(boolean visibility, String text) {
        if (text == null)
            text = "";
        ((TextView) findViewById(R.id.progress_bar_text)).setText(text);

        findViewById(R.id.container_progress_bar).setVisibility(visibility ? View.VISIBLE : View.GONE);

        Logger.d("Progressloader", visibility ? "start" : "finish");
    }

    @Override
    public void showProgress() {
        showProgressLoaderWithBackground(true, "loading data...");
    }

    @Override
    public void hideProgress() {
        showProgressLoaderWithBackground(false, null);
    }

    @Override
    public void setAnswers(List<Answer> answers) {
        Log.d(TAG, "setQuestions");
        answersAdapter.updateData(answers);
    }

    @Override
    public void onRequestError(String error) {
        Toast.makeText(AnswerActivity.this, error, Toast.LENGTH_SHORT).show();
    }
}
