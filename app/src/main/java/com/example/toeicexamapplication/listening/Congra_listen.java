package com.example.toeicexamapplication.listening;

import androidx.appcompat.app.AppCompatActivity;

import android.content.Intent;
import android.os.Bundle;
import android.view.View;
import android.widget.Button;
import android.widget.TextView;

import com.example.toeicexamapplication.MainActivity;
import com.example.toeicexamapplication.R;

public class Congra_listen extends AppCompatActivity {

    TextView tvTrue, tvPoint;
    Button btnReturn;
    int point, atrue;
    @Override
    protected void onCreate(Bundle savedInstanceState) {
        getSupportActionBar().hide();
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_congra_listen);
        addControls();
        addEvents();

    }

    private void addEvents() {
        Intent intent = getIntent();
        point = intent.getIntExtra("point", 0);
        String t = String.valueOf(intent.getIntExtra("true", atrue)) + "/5";
        tvTrue.setText(t) ;
        tvPoint.setText(String.valueOf(point));

        btnReturn.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                Intent i = new Intent(Congra_listen.this, MainActivity.class);
                startActivity(i);
            }
        });
    }

    private void addControls() {
        tvTrue = findViewById(R.id.txtQuestiontrue);
        tvPoint = findViewById(R.id.tvPoints);
        btnReturn = findViewById(R.id.btnReturn);
    }
}