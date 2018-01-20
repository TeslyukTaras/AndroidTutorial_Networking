package com.teslyuk.android.androidtutorial_volley_retrofit.ui;

import android.app.Activity;
import android.content.Context;
import android.content.Intent;
import android.net.Uri;
import android.os.Bundle;
import android.view.View;
import android.view.inputmethod.InputMethodManager;
import android.widget.ArrayAdapter;
import android.widget.AutoCompleteTextView;
import android.widget.Button;
import android.widget.ListView;
import android.widget.RadioButton;
import android.widget.TextView;
import android.widget.Toast;
import android.util.Log;

import com.teslyuk.android.androidtutorial_volley_retrofit.R;
import com.teslyuk.android.androidtutorial_volley_retrofit.data.source.ApiRepository;
import com.teslyuk.android.androidtutorial_volley_retrofit.ui.adapter.QuestionListAdapter;
import com.teslyuk.android.androidtutorial_volley_retrofit.data.model.Tag;
import com.teslyuk.android.androidtutorial_volley_retrofit.data.model.Question;
import com.teslyuk.android.androidtutorial_volley_retrofit.utils.Logger;

import java.util.ArrayList;
import java.util.List;

public class MainActivity extends Activity implements
        QuestionListAdapter.QuestionListAdapterEventListener,
        Contract.IMainView {

    private static final String TAG = MainActivity.class.getSimpleName();
    private ListView questionListView;
    private RadioButton retrofitBtn, volleyBtn;
    private AutoCompleteTextView tagView;
    private Button searchBtn;

    private ArrayAdapter<String> tagAdapter;
    private QuestionListAdapter questionAdapter;

    private ApiRepository apiRepository;

    private Contract.IMainPresenter presenter;

    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_retrofit);

        initView();
        initData();
        initAdapters();
        initListeners();
        initPresenter();
    }

    private void initView() {
        questionListView = findViewById(R.id.questions_list);
        tagView = findViewById(R.id.tag_auto_tv);
        retrofitBtn = findViewById(R.id.radio_btn_retrofit);
        volleyBtn = findViewById(R.id.radio_btn_volley);
        searchBtn = findViewById(R.id.search_btn);

        retrofitBtn.setChecked(true);
    }

    private void initData() {
        apiRepository = ApiRepository.getInstance();
    }

    private void initAdapters() {
        List<Question> questions = new ArrayList<>();
        List<String> tagsStr = new ArrayList<>();

        tagAdapter = new ArrayAdapter<>(this, android.R.layout.simple_list_item_1, tagsStr);
        tagView.setAdapter(tagAdapter);
        tagView.setThreshold(1);

        questionAdapter = new QuestionListAdapter(this, questions);
        questionAdapter.addListener(this);
        questionListView.setAdapter(questionAdapter);
    }

    private void initListeners() {
        searchBtn.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                presenter.onGetQuestions();
            }
        });
    }

    private void initPresenter() {
        presenter = new MainPresenter(apiRepository, this);
    }

    @Override
    protected void onResume() {
        super.onResume();
        presenter.onGetTags();
    }

    @Override
    public void openQuestion(int position) {
        String link = ((Question) questionAdapter.getItem(position)).getLink();
        Intent intent = new Intent(Intent.ACTION_VIEW);
        intent.setData(Uri.parse(link));
        startActivity(intent);
    }

    public void showProgressLoaderWithBackground(boolean visibility, String text) {
        if (text == null)
            text = "";
        ((TextView) findViewById(R.id.progress_bar_text)).setText(text);

        findViewById(R.id.container_progress_bar).setVisibility(visibility ? View.VISIBLE : View.GONE);

        Logger.d("Progressloader", visibility ? "start" : "finish");

    }

    private void hideSoftKeyboard(View view) {
        InputMethodManager imm = (InputMethodManager) getSystemService(Context.INPUT_METHOD_SERVICE);
        imm.hideSoftInputFromWindow(view.getWindowToken(), 0);
    }

    @Override
    public void showProgress() {
        showProgressLoaderWithBackground(true, " loading data...");
    }

    @Override
    public void hideProgress() {
        showProgressLoaderWithBackground(false, " loading data...");
    }

    @Override
    public void setTags(List<Tag> tags) {
        Log.d(TAG, "setTags");
        tagAdapter.clear();
        for (Tag tag : tags) {
            tagAdapter.add(tag.getName());
        }
        tagAdapter.getFilter().filter(tagView.getText(), null);

        hideSoftKeyboard(tagView);
    }

    @Override
    public void setQuestions(List<Question> questions) {
        Log.d(TAG, "setQuestions");
        questionAdapter.updateData(questions);
        questionAdapter.notifyDataSetChanged();
        hideSoftKeyboard(tagView);
    }

    @Override
    public boolean isRetrofitFlag() {
        return retrofitBtn.isChecked();
    }

    @Override
    public String getTag() {
        String tag = tagView.getText().toString().trim();
        return tag;
    }

    @Override
    public void onRequestError(String error) {
        Toast.makeText(MainActivity.this, error, Toast.LENGTH_SHORT).show();
    }
}