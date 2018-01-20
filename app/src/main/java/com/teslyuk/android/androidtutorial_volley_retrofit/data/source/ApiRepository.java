package com.teslyuk.android.androidtutorial_volley_retrofit.data.source;

public class ApiRepository implements DataSource {

    private static ApiRepository instance;

    private boolean isRetrofit;

    private RetrofitDataSource retrofitDataSource;
    private VolleyDataSource volleyDataSource;

    private ApiRepository() {
        retrofitDataSource = new RetrofitDataSource();
        volleyDataSource = new VolleyDataSource();
    }

    public static ApiRepository getInstance() {
        if (instance == null) {
            instance = new ApiRepository();
        }
        return instance;
    }

    public void setFrameworkType(boolean isRetrofit) {
        this.isRetrofit = isRetrofit;
    }

    @Override
    public void getQuestions(String tag, LoadQuestionsCallback callback) {
        if (isRetrofit) {
            retrofitDataSource.getQuestions(tag, callback);
        } else {
            volleyDataSource.getQuestions(tag, callback);
        }
    }

    @Override
    public void getTags(LoadTagsCallback callback) {
        if (isRetrofit) {
            retrofitDataSource.getTags(callback);
        } else {
            volleyDataSource.getTags(callback);
        }
    }
}
