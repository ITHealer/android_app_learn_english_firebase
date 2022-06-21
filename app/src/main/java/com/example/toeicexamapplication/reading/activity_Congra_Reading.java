package com.example.toeicexamapplication.reading;

import androidx.appcompat.app.AppCompatActivity;

import android.content.Intent;
import android.os.Bundle;
import android.view.View;
import android.widget.Button;
import android.widget.TextView;

import com.example.toeicexamapplication.MainActivity;
import com.example.toeicexamapplication.R;

public class activity_Congra_Reading extends AppCompatActivity {

    TextView tvTrue, tvPoint;
    Button btnReturn;
    int point, atrue;
    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_congra_reading);
        addControls();
        addEvents();

    }

    private void addEvents() {
        Intent intent = getIntent();
        point = intent.getIntExtra("Score", 0);
        int countQuestion = intent.getIntExtra("countQuestion",0);
            int coutCorrectAnswer = point/20;
            String t = String.valueOf(coutCorrectAnswer + "/" + countQuestion);
            tvTrue.setText(t) ;
        tvPoint.setText(String.valueOf(point));

        btnReturn.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                Intent i = new Intent(activity_Congra_Reading.this, MainActivity.class);
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
