package com.example.toeicexamapplication.listening;

import androidx.appcompat.app.AppCompatActivity;

import android.content.Intent;
import android.graphics.drawable.ColorDrawable;
import android.os.Bundle;
import android.view.View;
import android.widget.AdapterView;
import android.widget.ListView;

import com.example.toeicexamapplication.R;

import java.util.ArrayList;
import java.util.List;

public class ListeningActivity extends AppCompatActivity {

    List<Listen_list> item = new ArrayList<>();
    ListeningAdapter adapter;
    ListView lvListen;
    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_listening);
        getSupportActionBar().setDisplayHomeAsUpEnabled(true);
        getSupportActionBar().setBackgroundDrawable(new ColorDrawable(getResources().getColor(R.color.cPrimary)));
        Listen_list de1 = new Listen_list(R.drawable.icon_listening,R.color.white,"Đề luyện nghe số 1");
        Listen_list de2 = new Listen_list(R.drawable.icon_listening,R.color.white,"Đề luyện nghe số 2");
        Listen_list de3 = new Listen_list(R.drawable.icon_listening,R.color.white,"Đề luyện nghe số 3");
        Listen_list de4 = new Listen_list(R.drawable.icon_listening,R.color.white,"Đề luyện nghe số 4");
        item.add(de1);
        item.add(de2);
        item.add(de3);
        item.add(de4);
        adapter = new ListeningAdapter(this,item);
        addControls();
        addEvents();
    }

    private void addEvents() {
        lvListen.setOnItemClickListener(new AdapterView.OnItemClickListener() {
            @Override
            public void onItemClick(AdapterView<?> adapterView, View view, int i, long l) {
                Intent intent=new Intent(ListeningActivity.this, Listening_Quiz.class);
                startActivity(intent);
            }
        });
    }

    private void addControls() {
        lvListen = findViewById(R.id.lvluyennghe);
        lvListen.setAdapter(adapter);
    }
}