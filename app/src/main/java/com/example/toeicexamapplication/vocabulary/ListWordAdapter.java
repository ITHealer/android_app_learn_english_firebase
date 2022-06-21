package com.example.toeicexamapplication.vocabulary;

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

import java.util.List;

public class ListWordAdapter extends ArrayAdapter {
    Context context;
    private static int resource;
    List<Word> objects;
    public ListWordAdapter(@NonNull Context context, @LayoutRes int resource, @NonNull List<Word> objects) {
        super(context, resource, objects);
        this.context = context;
        this.resource = resource;
        this.objects = objects;
    }

    public ListWordAdapter(@NonNull Context context, int resource, int textViewResourceId, @NonNull Object[] objects) {
        super(context, resource, textViewResourceId, objects);
    }

    @NonNull
    public View getView(int position, @Nullable View convertView, @NonNull ViewGroup parent) {
        LayoutInflater layoutInflater = (LayoutInflater) context.getSystemService(Context.LAYOUT_INFLATER_SERVICE);
        View view = layoutInflater.inflate(resource, parent, false);
        TextView tvmean = view.findViewById(R.id.tV_mean);
        Word word = objects.get(position);
        tvmean.setText(word.getNghia());
        return view;
    }
}
