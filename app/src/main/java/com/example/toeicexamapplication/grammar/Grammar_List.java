package com.example.toeicexamapplication.grammar;

import androidx.annotation.NonNull;
import androidx.appcompat.app.AppCompatActivity;

import android.content.Intent;
import android.graphics.drawable.ColorDrawable;
import android.os.Bundle;
import android.widget.ListView;
import android.widget.TextView;
import android.widget.Toast;

import com.example.toeicexamapplication.R;
import com.example.toeicexamapplication.listening.Listening_Quiz;
import com.google.firebase.database.DataSnapshot;
import com.google.firebase.database.DatabaseError;
import com.google.firebase.database.DatabaseReference;
import com.google.firebase.database.FirebaseDatabase;
import com.google.firebase.database.ValueEventListener;

public class Grammar_List extends AppCompatActivity {

    TextView tvTense;
    TextView tvDinhnghia;
    TextView tvKhangdinh;
    TextView tvNghivan;
    TextView tvPhudinh;
    TextView tvChuthich;
    TextView tvDauhieu;
    TextView tvVidu;
    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_grammar_list);
        getSupportActionBar().setDisplayHomeAsUpEnabled(true);
        getSupportActionBar().setBackgroundDrawable(new ColorDrawable(getResources().getColor(R.color.cPrimary)));
        addControls();
        Intent intent = getIntent();
        Grammar grammar = (Grammar) intent.getSerializableExtra("Tenthi");
        tvTense.setText(grammar.getTenThi());
        tvDinhnghia.setText(grammar.getDinhnghia());
        tvKhangdinh.setText(grammar.getKhangDinh());
        tvNghivan.setText(grammar.getNghiVan());
        tvPhudinh.setText(grammar.getPhuDinh());
        tvDauhieu.setText(grammar.getDauHieu());
        tvChuthich.setText(grammar.getChuThich());
        tvVidu.setText(grammar.getViDu());
    }

    private void addControls() {
        tvTense = findViewById(R.id.tvTitle);
        tvDinhnghia = findViewById(R.id.tvDinhnghia);
        tvKhangdinh = findViewById(R.id.tvKhangdinh);
        tvNghivan = findViewById(R.id.tvNghivan);
        tvPhudinh = findViewById(R.id.tvPhudinh);
        tvChuthich = findViewById(R.id.tvChuthich);
        tvDauhieu = findViewById(R.id.tvDauhieu);
        tvVidu = findViewById(R.id.tvVidu);
    }
}