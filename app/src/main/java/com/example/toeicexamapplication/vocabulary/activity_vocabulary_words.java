package com.example.toeicexamapplication.vocabulary;

import androidx.annotation.NonNull;
import androidx.appcompat.app.AppCompatActivity;

import android.content.Intent;
import android.graphics.drawable.ColorDrawable;
import android.os.Bundle;
import android.view.MenuItem;
import android.view.View;
import android.widget.Button;
import android.widget.ImageButton;
import android.widget.ListView;
import android.widget.TextView;
import android.widget.Toast;

import com.example.toeicexamapplication.MainActivity;
import com.example.toeicexamapplication.R;
import com.example.toeicexamapplication.reading.Reading;
import com.example.toeicexamapplication.reading.activity_reading_answers;
import com.google.firebase.database.DataSnapshot;
import com.google.firebase.database.DatabaseError;
import com.google.firebase.database.DatabaseReference;
import com.google.firebase.database.FirebaseDatabase;
import com.google.firebase.database.ValueEventListener;

import java.util.ArrayList;
import java.util.List;

public class activity_vocabulary_words extends AppCompatActivity implements ValueEventListener {

    DatabaseReference databaseReference,databaseReference1,databaseReference2;
    Button btnDone,btnBefore,btnNext;
    ImageButton imbtnRemember;
    String ChuDe;
    String word;
    int index;
    ArrayList<Topic> wordList;
    TextView txtTuVung,txtLoaiTu,txtPhienAm,txtNghia, txtTopicName;
    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_vocabulary_words);
        getSupportActionBar().setDisplayHomeAsUpEnabled(true);
        getSupportActionBar().setBackgroundDrawable(new ColorDrawable(getResources().getColor(R.color.cPrimary)));

        getWidgets();
        Intent intent = getIntent();
        ChuDe = intent.getStringExtra("ChuDe");
        index = intent.getIntExtra("index",0);
        word = intent.getStringExtra("ChuDe1");
        txtTopicName.setText(ChuDe);
        txtTuVung.setText(word);

        databaseReference = FirebaseDatabase.getInstance().getReference().child("Vocabulary").child(ChuDe).child(word);
        databaseReference.addValueEventListener(this);
        getListWord();
        imbtnRemember.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                Intent intent=new Intent(activity_vocabulary_words.this, activity_list_word.class);
                intent.putExtra("ChuDe", ChuDe);
                startActivity(intent);
            }
        });

        btnNext.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                if(index >= wordList.size()){
                    return;
                }else{
                    index++;
                }

                if (index < wordList.size()) {
                    getWordShow(index, wordList);
                }
                else
                {
                    Toast.makeText(activity_vocabulary_words.this, "Đã xong!", Toast.LENGTH_SHORT).show();
                }
            }
        });

        btnBefore.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                if(index <= 0){
                    return;
                }else{
                    index--;
                }

                if (index >= 0) {
                    getWordShow(index, wordList);
                }
                else
                {
                    Toast.makeText(activity_vocabulary_words.this, "Đã xong!", Toast.LENGTH_SHORT).show();
                }
            }
        });
    }


    public void getListWord() {
        databaseReference1 = FirebaseDatabase.getInstance().getReference().child("Vocabulary").child(ChuDe);
        wordList = new ArrayList<>();
        databaseReference1.addValueEventListener(new ValueEventListener() {
            @Override
            public void onDataChange(@NonNull DataSnapshot snapshot) {
                Iterable<DataSnapshot> nodeChild = snapshot.getChildren();
                for (DataSnapshot data : nodeChild) {
                    String name = data.getKey();
                    Topic topic = new Topic(name);
                    wordList.add(topic);
                }
            }

            @Override
            public void onCancelled(@NonNull DatabaseError error) {

            }
        });
    }
    public void getWordShow(int i, ArrayList<Topic> topicList)
    {
        String word = wordList.get(i).getName_topic();
        databaseReference2 = FirebaseDatabase.getInstance().getReference().child("Vocabulary").child(ChuDe).child(word);
        databaseReference2.addValueEventListener(new ValueEventListener() {
            @Override
            public void onDataChange(@NonNull DataSnapshot snapshot) {
                Word tu = snapshot.getValue(Word.class);
                txtTuVung.setText(word);
                txtLoaiTu.setText(tu.getLoaiTu());
                txtPhienAm.setText(tu.getPhienAm());
                txtNghia.setText(tu.getNghia());
            }

            @Override
            public void onCancelled(@NonNull DatabaseError error) {

            }
        });
    }
    @Override
    public void onDataChange(@NonNull DataSnapshot snapshot) {
        Word tu = snapshot.getValue(Word.class);
        txtLoaiTu.setText(tu.getLoaiTu());
        txtPhienAm.setText(tu.getPhienAm());
        txtNghia.setText(tu.getNghia());

    }

    @Override
    public void onCancelled(@NonNull DatabaseError error) {

    }

    public void getWidgets()
    {
        txtTuVung = findViewById(R.id.tV_word);
        txtLoaiTu = findViewById(R.id.tV_type);
        txtNghia = findViewById(R.id.tV_mean);
        txtPhienAm = findViewById(R.id.tV_pronun);
        txtTopicName = findViewById(R.id.tV_topic);
        imbtnRemember = findViewById(R.id.btn_remember);
        btnBefore = findViewById(R.id.btn_Before);
        btnNext = findViewById(R.id.btn_Next);
    }
}