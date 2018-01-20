package com.teslyuk.android.androidtutorial_volley_retrofit.ui.adapter;

import android.app.Activity;
import android.content.Context;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.BaseAdapter;
import android.widget.ImageView;
import android.widget.TextView;

import com.teslyuk.android.androidtutorial_volley_retrofit.R;
import com.teslyuk.android.androidtutorial_volley_retrofit.data.model.Question;
import com.teslyuk.android.androidtutorial_volley_retrofit.utils.Logger;

import java.util.List;

/**
 * Created by taras on 15.02.16.
 */
public class QuestionListAdapter extends BaseAdapter {

    public interface QuestionListAdapterEventListener {
        void openQuestion(int position);
    }

    private final String TAG = getClass().getSimpleName();

    private List<Question> list;
    private Context context;
    QuestionListAdapterEventListener listener;

    public QuestionListAdapter(Context context, List<Question> list) {
        this.context = context;
        this.list = list;
        Logger.d(TAG, "items count: " + list.size());
    }

    public void addListener(QuestionListAdapterEventListener listener) {
        this.listener = listener;
    }

    static class ViewHolder {
        protected TextView title;
        protected ImageView open;
    }

    public void updateData(List<Question> questions) {
        list.clear();
        list.addAll(questions);
    }

    @Override
    public View getView(final int position, View convertView, ViewGroup parent) {
        View view = null;
        if (convertView == null) {
            LayoutInflater inflator = ((Activity) context).getLayoutInflater();
            final ViewHolder viewHolder = new ViewHolder();
            view = inflator.inflate(R.layout.item_question, null);
            viewHolder.title = view.findViewById(R.id.item_title);
            viewHolder.open = view.findViewById(R.id.item_open);
            view.setTag(viewHolder);
        } else {
            view = convertView;
        }

        ViewHolder holder = (ViewHolder) view.getTag();
        if (holder.title != null) {
            holder.title.setText(list.get(position).getTitle());
        }

        if (holder.open != null) {
            holder.open.setOnClickListener(new View.OnClickListener() {
                @Override
                public void onClick(View view) {
                    if (listener != null) {
                        listener.openQuestion(position);
                    }
                }
            });
        }

        if (view == null) {
            Logger.d(TAG, "NULL VIEW position:" + position);
        }
        return view;
    }

    @Override
    public int getCount() {
        return list.size();
    }

    @Override
    public Object getItem(int position) {
        return list.get(position);
    }

    @Override
    public long getItemId(int position) {
        return position;
    }
}