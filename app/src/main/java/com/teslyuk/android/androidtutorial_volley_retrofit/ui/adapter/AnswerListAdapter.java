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
import com.teslyuk.android.androidtutorial_volley_retrofit.data.model.Answer;
import com.teslyuk.android.androidtutorial_volley_retrofit.data.model.Question;
import com.teslyuk.android.androidtutorial_volley_retrofit.utils.Logger;

import java.util.List;

/**
 * Created by taras on 15.02.16.
 */
public class AnswerListAdapter extends BaseAdapter {

    private final String TAG = getClass().getSimpleName();

    private List<Answer> list;
    private Context context;

    public AnswerListAdapter(Context context, List<Answer> list) {
        this.context = context;
        this.list = list;
        Logger.d(TAG, "items count: " + list.size());
    }

    static class ViewHolder {
        protected TextView title;
    }

    public void updateData(List<Answer> questions) {
        list.clear();
        list.addAll(questions);
        notifyDataSetChanged();
    }

    @Override
    public View getView(final int position, View convertView, ViewGroup parent) {
        View view = null;
        if (convertView == null) {
            LayoutInflater inflator = ((Activity) context).getLayoutInflater();
            final ViewHolder viewHolder = new ViewHolder();
            view = inflator.inflate(R.layout.item_answer, null);
            viewHolder.title = view.findViewById(R.id.item_title);
            view.setTag(viewHolder);
        } else {
            view = convertView;
        }

        ViewHolder holder = (ViewHolder) view.getTag();
        if (holder.title != null) {
            holder.title.setText("Answer with id: " + list.get(position).getAnswerId());
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