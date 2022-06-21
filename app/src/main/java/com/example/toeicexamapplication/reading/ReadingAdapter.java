package com.example.toeicexamapplication.reading;

import android.content.Context;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.ArrayAdapter;
import android.widget.Button;
import android.widget.RadioButton;
import android.widget.TextView;

import androidx.annotation.NonNull;
import androidx.annotation.Nullable;
import androidx.recyclerview.widget.RecyclerView;

import com.example.toeicexamapplication.R;
import com.example.toeicexamapplication.vocabulary.Topic;

import java.util.List;

public class ReadingAdapter extends ArrayAdapter<Reading> {
    Context context;
    private static int resource;
    List<Reading> objects;
    public ReadingAdapter(@NonNull Context context, int resource, @NonNull List objects) {
        super(context, resource, objects);
        this.context = context;
        this.resource = resource;
        this.objects = objects;
    }

    public View getView(int position, @Nullable View convertView, @NonNull ViewGroup parent) {
        LayoutInflater layoutInflater = (LayoutInflater) context.getSystemService(Context.LAYOUT_INFLATER_SERVICE);
        View view = layoutInflater.inflate(resource, parent, false);
        TextView conent = view.findViewById(R.id.tV_question);
        RadioButton op1 = view.findViewById(R.id.op1);
        RadioButton op2 = view.findViewById(R.id.op1);
        RadioButton op3 = view.findViewById(R.id.op1);
        RadioButton op4 = view.findViewById(R.id.op1);
        Reading topic = objects.get(position);
        conent.setText(topic.getCauHoi());
        op1.setText(topic.getA());
        op2.setText(topic.getB());
        op3.setText(topic.getC());
        op4.setText(topic.getD());
        return view;
    }

}
