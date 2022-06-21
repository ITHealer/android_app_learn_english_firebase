package com.example.toeicexamapplication.reading;

import android.content.Context;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.ArrayAdapter;
import android.widget.TextView;

import androidx.annotation.LayoutRes;
import androidx.annotation.NonNull;
import androidx.annotation.Nullable;

import com.example.toeicexamapplication.R;
import com.example.toeicexamapplication.vocabulary.Topic;

import java.util.List;

public class ListReading extends ArrayAdapter {
    Context context;
    private static int resource;
    List<Topic> objects;
    public ListReading(@NonNull Context context, int resource, @NonNull List objects) {
        super(context, resource, objects);
        this.context = context;
        this.resource = resource;
        this.objects = objects;
    }
    @NonNull
    public View getView(int position, @Nullable View convertView, @NonNull ViewGroup parent) {
        LayoutInflater layoutInflater = (LayoutInflater) context.getSystemService(Context.LAYOUT_INFLATER_SERVICE);
        View view = layoutInflater.inflate(resource, parent, false);
        TextView tvName = view.findViewById(R.id.tvTenBo);
        Topic topic = objects.get(position);
        tvName.setText(topic.getName_topic());
        return view;
    }
}
