package com.example.toeicexamapplication.vocabulary;

import androidx.annotation.NonNull;
import androidx.appcompat.app.AppCompatActivity;

import android.content.Intent;
import android.graphics.drawable.ColorDrawable;
import android.os.Bundle;
import android.view.View;
import android.widget.AdapterView;
import android.widget.ListView;

import com.example.toeicexamapplication.R;
import com.google.firebase.database.DataSnapshot;
import com.google.firebase.database.DatabaseError;
import com.google.firebase.database.DatabaseReference;
import com.google.firebase.database.FirebaseDatabase;
import com.google.firebase.database.ValueEventListener;

import java.util.ArrayList;
import java.util.List;

public class VocabularyActivity extends AppCompatActivity implements ValueEventListener {

    DatabaseReference databaseReference;
    ListTopicAdapter adaptertopic;
    List<Topic> topicList;
    ListView listView;
    String ChuDe;
    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_vocabulary);

        getSupportActionBar().setDisplayHomeAsUpEnabled(true);
        getSupportActionBar().setBackgroundDrawable(new ColorDrawable(getResources().getColor(R.color.cPrimary)));

        databaseReference = FirebaseDatabase.getInstance().getReference().child("Vocabulary");
        listView = (ListView) findViewById(R.id.listView_Topic);
        topicList = new ArrayList<>();
        adaptertopic = new ListTopicAdapter(this,R.layout.item_reading,topicList);
        listView.setAdapter(adaptertopic);
        databaseReference.addValueEventListener(this);

        listView.setOnItemClickListener(new AdapterView.OnItemClickListener() {
            @Override
            public void onItemClick(AdapterView<?> adapterView, View view, int i, long l) {
                Intent intent=new Intent(VocabularyActivity.this, activity_list_word.class);
                ChuDe = topicList.get(i).getName_topic();
                intent.putExtra("ChuDe", ChuDe);
                startActivity(intent);
            }
        });
    }

    @Override
    public void onDataChange(@NonNull DataSnapshot snapshot) {
        Iterable<DataSnapshot> nodeChild = snapshot.getChildren();
        for(DataSnapshot data : nodeChild)
        {
            String name = data.getKey();
            Topic topic = new Topic(name);
//            Topic topic = data.getValue(Topic.class);
            topicList.add(topic);
            adaptertopic.notifyDataSetChanged();

        }
    }
    @Override
    public void onCancelled(@NonNull DatabaseError error) {

    }
}