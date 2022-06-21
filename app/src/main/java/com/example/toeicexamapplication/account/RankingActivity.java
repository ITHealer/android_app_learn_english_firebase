package com.example.toeicexamapplication.account;

import static java.util.Collections.sort;

import androidx.annotation.NonNull;
import androidx.appcompat.app.AppCompatActivity;

import android.content.Intent;
import android.graphics.drawable.ColorDrawable;
import android.os.Bundle;
import android.view.View;
import android.widget.AdapterView;
import android.widget.ImageView;
import android.widget.ListView;
import android.widget.TextView;
import android.widget.Toast;

import com.example.toeicexamapplication.R;
import com.example.toeicexamapplication.grammar.Grammar;
import com.example.toeicexamapplication.grammar.GrammarActivity;
import com.example.toeicexamapplication.grammar.Grammar_List;
import com.example.toeicexamapplication.grammar.ListGrammarAdapter;
import com.example.toeicexamapplication.listening.Listening_Quiz;
import com.example.toeicexamapplication.reading.Reading;
import com.google.firebase.auth.FirebaseAuth;
import com.google.firebase.auth.FirebaseUser;
import com.google.firebase.database.DataSnapshot;
import com.google.firebase.database.DatabaseError;
import com.google.firebase.database.DatabaseReference;
import com.google.firebase.database.FirebaseDatabase;
import com.google.firebase.database.Query;
import com.google.firebase.database.ValueEventListener;

import java.util.ArrayList;
import java.util.Collections;
import java.util.Comparator;
import java.util.HashMap;
import java.util.List;
import java.util.Map;

public class RankingActivity extends AppCompatActivity {

    private TextView tvHoTenRank, tvEmailRank, tvPointRank, tvpointrank3, tvpointrank2, tvpointrank1;
    DatabaseReference myRef;
    List<Ranking> userList;
    List<Ranking> userList2;
    ListView lvUserRank;
    RankingAdapter listUserAdapter;
    Ranking ranking;

    private FirebaseDatabase rootNode;

    int max1 = 0;
    String idUser;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_ranking);
        getSupportActionBar().setDisplayHomeAsUpEnabled(true);
        getSupportActionBar().setBackgroundDrawable(new ColorDrawable(getResources().getColor(R.color.cPrimary)));

        getWidgets();

        myRef = FirebaseDatabase.getInstance().getReference().child("User");
        lvUserRank = (ListView) findViewById(R.id.userranklist);
        userList = new ArrayList<>();
        getListUserFromRealtimeDatabase();
        listUserAdapter = new RankingAdapter(this,R.layout.row_ranking,userList);
        lvUserRank.setAdapter(listUserAdapter);

    }

    public void getListUserFromRealtimeDatabase() {
        FirebaseUser user = FirebaseAuth.getInstance().getCurrentUser();
        if (user == null) {
            return;
        }
        if (user != null) {

        }
        rootNode = FirebaseDatabase.getInstance();
        myRef = rootNode.getReference("User");
        Query query = myRef.orderByChild("sumPoint");

        query.addValueEventListener(new ValueEventListener() {
            @Override
            public void onDataChange(@NonNull DataSnapshot snapshot) {
                for(DataSnapshot dataSnapshot : snapshot.getChildren()) {
                    User user = dataSnapshot.getValue(User.class);

                    String name = user.getHoTen();
//                        int pointReading = user.getPointReading();
//                        int pointListening = user.getPointListening();
                    int sumPoint = user.getSumPoint();
                    ranking = new Ranking(name, sumPoint);

                    userList.add(0, ranking);
                    listUserAdapter.notifyDataSetChanged();
                }
                tvpointrank1.setText(String.valueOf(userList.get(0).getPoint()));
                tvpointrank2.setText(String.valueOf(userList.get(1).getPoint()));
                tvpointrank3.setText(String.valueOf(userList.get(2).getPoint()));
            }

            @Override
            public void onCancelled(@NonNull DatabaseError error) {
                Toast.makeText(RankingActivity.this, "Fail!", Toast.LENGTH_SHORT).show();
            }
        });
    }

    private void getWidgets() {
        tvHoTenRank = (TextView) findViewById(R.id.tvhotenrank);
        tvPointRank = (TextView) findViewById(R.id.tvpointrank);
        lvUserRank = findViewById(R.id.userranklist);
        tvpointrank1 = findViewById(R.id.tvpointrank1);
        tvpointrank2 = (TextView) findViewById(R.id.tvpointrank2);
        tvpointrank3 = (TextView) findViewById(R.id.tvpointrank3);
    }

}