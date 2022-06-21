package com.example.toeicexamapplication.vocabulary;

import androidx.annotation.NonNull;
import androidx.appcompat.app.AppCompatActivity;

import android.content.Intent;
import android.graphics.drawable.ColorDrawable;
import android.os.Bundle;
import android.view.View;
import android.widget.AdapterView;
import android.widget.ListView;
import android.widget.TextView;

import com.example.toeicexamapplication.R;
import com.google.firebase.database.DataSnapshot;
import com.google.firebase.database.DatabaseError;
import com.google.firebase.database.DatabaseReference;
import com.google.firebase.database.FirebaseDatabase;
import com.google.firebase.database.ValueEventListener;

import java.util.ArrayList;
import java.util.List;

public class activity_list_word extends AppCompatActivity implements ValueEventListener {

    DatabaseReference databaseReference;
    ListTopicAdapter adapterword;
    List<Topic> wordList;
    ListView listView;
    TextView tv_topic;
    String ChuDe;
    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_list_word);

        getSupportActionBar().setBackgroundDrawable(new ColorDrawable(getResources().getColor(R.color.cPrimary)));
        getSupportActionBar().setDisplayHomeAsUpEnabled(true);

        listView = (ListView) findViewById(R.id.list_word);
        tv_topic = findViewById(R.id.tV_topic);

        Intent intent = getIntent();
        String ChuDe = intent.getStringExtra("ChuDe");
        tv_topic.setText(ChuDe);

        databaseReference = FirebaseDatabase.getInstance().getReference().child("Vocabulary").child(ChuDe);
        wordList = new ArrayList<>();
        adapterword = new ListTopicAdapter(this,R.layout.item_reading,wordList);
        listView.setAdapter(adapterword);
        databaseReference.addValueEventListener(this);

        listView.setOnItemClickListener(new AdapterView.OnItemClickListener() {
            @Override
            public void onItemClick(AdapterView<?> adapterView, View view, int i, long l) {
                Intent intent=new Intent(activity_list_word.this, activity_vocabulary_words.class);
                String ChuDe1 = wordList.get(i).getName_topic();
                intent.putExtra("ChuDe", ChuDe);
                intent.putExtra("ChuDe1",ChuDe1);
                startActivity(intent);
            }
        });
    }

    @Override
    public void onDataChange(@NonNull DataSnapshot snapshot) {
        Iterable<DataSnapshot> nodeChild = snapshot.getChildren();
        for (DataSnapshot data : nodeChild) {
            String name = data.getKey();
            Topic topic = new Topic(name);
            wordList.add(topic);
            adapterword.notifyDataSetChanged();
        }

    }
    public void onCancelled(@NonNull DatabaseError error) {

    }
}