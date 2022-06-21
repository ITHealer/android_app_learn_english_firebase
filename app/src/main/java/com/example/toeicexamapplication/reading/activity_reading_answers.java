package com.example.toeicexamapplication.reading;

import androidx.annotation.NonNull;
import androidx.appcompat.app.AppCompatActivity;

import android.content.Intent;
import android.graphics.drawable.ColorDrawable;
import android.os.Bundle;
import android.os.CountDownTimer;
import android.view.View;
import android.widget.Button;
import android.widget.RadioButton;
import android.widget.RadioGroup;
import android.widget.TextView;
import android.widget.Toast;

import com.example.toeicexamapplication.MainActivity;
import com.example.toeicexamapplication.R;
import com.example.toeicexamapplication.account.User;
import com.example.toeicexamapplication.listening.Listening;
import com.example.toeicexamapplication.listening.ListeningActivity;
import com.example.toeicexamapplication.listening.Listening_Quiz;
import com.example.toeicexamapplication.vocabulary.Topic;
import com.google.firebase.auth.FirebaseAuth;
import com.google.firebase.auth.FirebaseUser;
import com.google.firebase.database.DataSnapshot;
import com.google.firebase.database.DatabaseError;
import com.google.firebase.database.DatabaseReference;
import com.google.firebase.database.FirebaseDatabase;
import com.google.firebase.database.ValueEventListener;

import java.util.ArrayList;
import java.util.HashMap;
import java.util.Map;


public class activity_reading_answers extends AppCompatActivity {

    DatabaseReference databaseReference;

    TextView tvscore, tvquestcount, cauhoi, tvContent;

    RadioGroup rdgchoices;

    RadioButton rdbA,rdbB,rdbC,rdbD;

    Button btnconfirm, btnquit;

    ArrayList<Reading> readings;

    String ketqua = "";

    int score=0, questioncurrent=1;
    int pointListening;

    private FirebaseDatabase rootNode;
    private DatabaseReference myRef;

    String idUser;
    int countQuestion = 0;
    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_reading_answers);
        getSupportActionBar().setBackgroundDrawable(new ColorDrawable(getResources().getColor(R.color.cPrimary)));
        getSupportActionBar().setDisplayHomeAsUpEnabled(true);
        getWidgets();
        getListUserFromRealtimeDatabase();
        getContext();
        getQuesion();
        addEvent();

    }

    private String getSet() {
        Intent intent = getIntent();
        String ChuDe = intent.getStringExtra("ChuDe");
        return ChuDe;
    }

    private void getContext(){
        getSet();
        FirebaseDatabase firebaseDatabase = FirebaseDatabase.getInstance();
        DatabaseReference myRef= firebaseDatabase.getReference().child("Reading").child(getSet()).child("Context");
        myRef.addValueEventListener(new ValueEventListener() {
            @Override
            public void onDataChange(@NonNull DataSnapshot snapshot) {
                tvContent.setText(snapshot.getValue().toString());
            }

            @Override
            public void onCancelled(@NonNull DatabaseError error) {
                Toast.makeText(activity_reading_answers.this, "Get context fail", Toast.LENGTH_SHORT).show();
            }
        });
    }


    private void getQuesion(){
        getSet();
        databaseReference = FirebaseDatabase.getInstance().getReference().child("Reading").child(getSet()).child("Question");
        databaseReference.addValueEventListener(new ValueEventListener() {
            @Override
            public void onDataChange(@NonNull DataSnapshot snapshot) {
                Iterable<DataSnapshot> nodeChild = snapshot.getChildren();
                for (DataSnapshot data : nodeChild) {
                    Reading ls = data.getValue(Reading.class);
                    readings.add(ls);
                }
                showReading(countQuestion, readings);

            }

            @Override
            public void onCancelled(@NonNull DatabaseError error) {
                Toast.makeText(activity_reading_answers.this, "Get context fail", Toast.LENGTH_SHORT).show();
            }
        });
    }

    private void addEvent() {

        CountDownTimer countDownTimer = new CountDownTimer(3000,300) {
            @Override
            public void onTick(long millisUntilFinished) {
                showanswer();
            }

            @Override
            public void onFinish() {
                btnconfirm.setEnabled(true);
                showReading(countQuestion,readings);
            }
        };
        btnconfirm.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                checkans();
                countQuestion++;
                countDownTimer.start();
            }
        });
        btnquit.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                Intent intent = new Intent(activity_reading_answers.this, ReadingActivity.class);
                startActivity(intent);
            }
        });
    }
    public void showReading(int cout, ArrayList<Reading> rd){

        tvquestcount.setText("Question: "+(countQuestion+1)+"/"+rd.size()+"");
        rdbA.setBackground(this.getResources().getDrawable(R.drawable.bgbtn));
        rdbB.setBackground(this.getResources().getDrawable(R.drawable.bgbtn));
        rdbC.setBackground(this.getResources().getDrawable(R.drawable.bgbtn));
        rdbD.setBackground(this.getResources().getDrawable(R.drawable.bgbtn));
        if(cout == rd.size()) {
            Intent intent=new Intent(activity_reading_answers.this, activity_Congra_Reading.class);
            intent.putExtra("Score",score);
            intent.putExtra("countQuestion",countQuestion);
            startActivity(intent);
            onClickUpdateMyProfile();
        }
        else {
            rdgchoices.clearCheck();
            cauhoi.setText(readings.get(cout).getCauHoi());
            rdbA.setText(readings.get(cout).getA());
            rdbB.setText(readings.get(cout).getB());
            rdbC.setText(readings.get(cout).getC());
            rdbD.setText(readings.get(cout).getD());
            ketqua = readings.get(cout).getTrue();
        }
    }


    public void checkans(){
        btnconfirm.setEnabled(false);
        if(rdbA.isChecked()){
            if(ketqua.equals("A")) {
                score+=20;
            }
        }
        if(rdbB.isChecked()){
            if(ketqua.equals("B")) {
                score+=20;
            }
        }
        if(rdbC.isChecked()){
            if(ketqua.equals("C")) {
                score+=20;
            }
        }
        if(rdbD.isChecked()){
            if(ketqua.equals("D")) {
                score+=20;
            }
        }
        tvscore.setText("Score: "+score+"");
    }
    public void showanswer(){
        if(ketqua.equals("A")) {
            rdbA.setBackground(this.getResources().getDrawable(R.drawable.button_true));
            rdbB.setBackground(this.getResources().getDrawable(R.drawable.button_false));
            rdbC.setBackground(this.getResources().getDrawable(R.drawable.button_false));
            rdbD.setBackground(this.getResources().getDrawable(R.drawable.button_false));
        }
        if(ketqua.equals("B")) {
            rdbA.setBackground(this.getResources().getDrawable(R.drawable.button_false));
            rdbB.setBackground(this.getResources().getDrawable(R.drawable.button_true));
            rdbC.setBackground(this.getResources().getDrawable(R.drawable.button_false));
            rdbD.setBackground(this.getResources().getDrawable(R.drawable.button_false));
        }
        if(ketqua.equals("C")) {
            rdbA.setBackground(this.getResources().getDrawable(R.drawable.button_false));
            rdbB.setBackground(this.getResources().getDrawable(R.drawable.button_false));
            rdbC.setBackground(this.getResources().getDrawable(R.drawable.button_true));
            rdbD.setBackground(this.getResources().getDrawable(R.drawable.button_false));
        }
        if(ketqua.equals("D")) {
            rdbA.setBackground(this.getResources().getDrawable(R.drawable.button_false));
            rdbB.setBackground(this.getResources().getDrawable(R.drawable.button_false));
            rdbC.setBackground(this.getResources().getDrawable(R.drawable.button_false));
            rdbD.setBackground(this.getResources().getDrawable(R.drawable.button_true));
        }
    }

    public void onClickUpdateMyProfile() {
        rootNode = FirebaseDatabase.getInstance();
        myRef = rootNode.getReference("User").child(idUser);
        Map<String, Object> map = new HashMap<>();
        map.put("pointReading", score);
        map.put("sumPoint", score + pointListening);
        myRef.updateChildren(map);
        Toast.makeText(activity_reading_answers.this, "Cập nhật thành công!", Toast.LENGTH_SHORT).show();
    }

    public void getListUserFromRealtimeDatabase() {
        FirebaseUser user = FirebaseAuth.getInstance().getCurrentUser();
        if (user == null) {
            return;
        }
        if (user != null) {
            idUser = user.getUid().toString();
        }
        rootNode = FirebaseDatabase.getInstance();
        myRef = rootNode.getReference("User").child(user.getUid());

        myRef.addValueEventListener(new ValueEventListener() {
            @Override
            public void onDataChange(@NonNull DataSnapshot snapshot) {
                User user = snapshot.getValue(User.class);
                pointListening = user.getPointListening();
            }

            @Override
            public void onCancelled(@NonNull DatabaseError error) {
            }
        });
    }

    private void getWidgets() {
        readings = new ArrayList<>();
        cauhoi = (TextView) findViewById(R.id.tV_question);
        tvContent = (TextView) findViewById(R.id.tv_content);
        tvscore = (TextView)findViewById(R.id.txtscoreLN);
        tvquestcount = (TextView) findViewById(R.id.txtquestcountLN);
        rdgchoices = (RadioGroup) findViewById(R.id.radiochoices);
        rdbA = (RadioButton) findViewById(R.id.rdbA);
        rdbB = (RadioButton) findViewById(R.id.rdbB);
        rdbC = (RadioButton) findViewById(R.id.rdbC);
        rdbD = (RadioButton) findViewById(R.id.rdbD);
        btnconfirm = (Button) findViewById(R.id.btnconfirmLN);
        btnquit = (Button)findViewById(R.id.btnQuitLN);
    }
}