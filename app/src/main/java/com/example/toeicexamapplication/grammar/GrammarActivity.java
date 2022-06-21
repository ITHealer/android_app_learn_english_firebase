package com.example.toeicexamapplication.grammar;

import androidx.annotation.NonNull;
import androidx.appcompat.app.AppCompatActivity;

import android.content.Intent;
import android.graphics.drawable.ColorDrawable;
import android.os.Bundle;
import android.view.View;
import android.widget.AdapterView;
import android.widget.ListView;
import android.widget.Toast;

import com.example.toeicexamapplication.R;
import com.google.firebase.database.DataSnapshot;
import com.google.firebase.database.DatabaseError;
import com.google.firebase.database.DatabaseReference;
import com.google.firebase.database.FirebaseDatabase;
import com.google.firebase.database.ValueEventListener;

import java.util.ArrayList;
import java.util.List;

public class GrammarActivity extends AppCompatActivity implements ValueEventListener {

    DatabaseReference myRef;
    List<Grammar> grammarList;
    ListView listView;
    ListGrammarAdapter listGrammarAdapter;
    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_grammar);
        getSupportActionBar().setDisplayHomeAsUpEnabled(true);
        getSupportActionBar().setBackgroundDrawable(new ColorDrawable(getResources().getColor(R.color.cPrimary)));
        myRef = FirebaseDatabase.getInstance().getReference().child("Grammar");
        listView = (ListView) findViewById(R.id.lvListgrammar);
        grammarList = new ArrayList<>();
        listGrammarAdapter = new ListGrammarAdapter(this,R.layout.row_grammar,grammarList);
        listView.setAdapter(listGrammarAdapter);
        myRef.addValueEventListener(this);
        listView.setOnItemClickListener(new AdapterView.OnItemClickListener() {
            @Override
            public void onItemClick(AdapterView<?> adapterView, View view, int i, long l) {
                Intent intent=new Intent(GrammarActivity.this, Grammar_List.class);
                Grammar grammar = grammarList.get(i);
                intent.putExtra("Tenthi", grammar);
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
            Grammar gramar = data.getValue(Grammar.class);
            grammarList.add(gramar);
            listGrammarAdapter.notifyDataSetChanged();

        }
    }

    @Override
    public void onCancelled(@NonNull DatabaseError error) {
        Toast.makeText(GrammarActivity.this, "Fail", Toast.LENGTH_SHORT).show();
    }
}