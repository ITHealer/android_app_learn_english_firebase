package com.example.toeicexamapplication.reading;

import androidx.annotation.NonNull;
import androidx.appcompat.app.AppCompatActivity;

import android.app.ActionBar;
import android.content.Intent;
import android.graphics.drawable.ColorDrawable;
import android.os.Bundle;
import android.view.View;
import android.widget.AdapterView;
import android.widget.ListView;
import android.widget.TextView;

import com.example.toeicexamapplication.R;
import com.example.toeicexamapplication.R;
import com.example.toeicexamapplication.account.MyProfileActivity;
import com.example.toeicexamapplication.databinding.FragmentHomeBinding;
import com.example.toeicexamapplication.grammar.GrammarActivity;
import com.example.toeicexamapplication.listening.ListeningActivity;
import com.example.toeicexamapplication.reading.ReadingActivity;
import com.example.toeicexamapplication.vocabulary.ListTopicAdapter;
import com.example.toeicexamapplication.vocabulary.Topic;
import com.example.toeicexamapplication.vocabulary.VocabularyActivity;

import com.example.toeicexamapplication.grammar.GrammarActivity;
import com.example.toeicexamapplication.vocabulary.activity_list_word;
import com.google.firebase.database.DataSnapshot;
import com.google.firebase.database.DatabaseError;
import com.google.firebase.database.DatabaseReference;
import com.google.firebase.database.FirebaseDatabase;
import com.google.firebase.database.ValueEventListener;

import java.util.ArrayList;
import java.util.List;

public class ReadingActivity extends AppCompatActivity implements ValueEventListener {

    DatabaseReference databaseReference;
    ListReading adaptertopic;
    List<Topic> topicList;
    ListView listView;
    String ChuDe;
    private TextView Reading;
    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_reading);
        getSupportActionBar().setDisplayHomeAsUpEnabled(true);
        getSupportActionBar().setBackgroundDrawable(new ColorDrawable(getResources().getColor(R.color.cPrimary)));

        databaseReference = FirebaseDatabase.getInstance().getReference().child("Reading");
        listView = (ListView) findViewById(R.id.list_reading);
        topicList = new ArrayList<>();
        adaptertopic = new ListReading(this,R.layout.item_reading,topicList);
        listView.setAdapter(adaptertopic);
        databaseReference.addValueEventListener(this);

        listView.setOnItemClickListener(new AdapterView.OnItemClickListener() {
            @Override
            public void onItemClick(AdapterView<?> adapterView, View view, int i, long l) {
                Intent intent=new Intent(ReadingActivity.this, activity_reading_answers.class);
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