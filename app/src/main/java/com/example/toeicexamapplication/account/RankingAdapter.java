package com.example.toeicexamapplication.account;

import android.content.Context;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.ArrayAdapter;
import android.widget.ImageView;
import android.widget.TextView;

import androidx.annotation.NonNull;
import androidx.annotation.Nullable;

import com.example.toeicexamapplication.R;
import com.example.toeicexamapplication.grammar.Grammar;

import org.w3c.dom.Text;

import java.util.List;

public class RankingAdapter extends ArrayAdapter {
    Context context;
    private static int resource;
    List<Ranking> objects;
    public RankingAdapter(@NonNull Context context, int resource, List<Ranking> objects) {
        super(context, resource, objects);
        this.context = context;
        this.resource = resource;
        this.objects = objects;
    }

    @NonNull
    @Override
    public View getView(int position, @Nullable View convertView, @NonNull ViewGroup parent) {
        LayoutInflater layoutInflater = (LayoutInflater) context.getSystemService(Context.LAYOUT_INFLATER_SERVICE);
        View view = layoutInflater.inflate(resource, parent, false);
        TextView tvHoTenRank = view.findViewById(R.id.tvhotenrank);
        TextView tvPointRank = view.findViewById(R.id.tvpointrank);
        ImageView img = view.findViewById(R.id.imagerank);
        Ranking r = objects.get(position);
        tvHoTenRank.setText(r.getName());
        tvPointRank.setText(String.valueOf(r.getPoint()));
        img.setImageResource(R.drawable.ic_rank);
        return view;
    }
}