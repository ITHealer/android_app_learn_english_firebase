package com.example.toeicexamapplication.listening;

import androidx.annotation.NonNull;
import androidx.appcompat.app.AppCompatActivity;

import android.content.Intent;
import android.graphics.drawable.ColorDrawable;
import android.media.AudioManager;
import android.media.MediaPlayer;
import android.net.Uri;
import android.os.Bundle;
import android.os.CountDownTimer;
import android.view.View;
import android.widget.Button;
import android.widget.ImageButton;
import android.widget.ImageView;
import android.widget.RadioButton;
import android.widget.RadioGroup;
import android.widget.TextView;
import android.widget.Toast;

import com.bumptech.glide.Glide;
import com.example.toeicexamapplication.R;
import com.example.toeicexamapplication.account.User;
import com.google.android.gms.tasks.OnSuccessListener;
import com.google.firebase.auth.FirebaseAuth;
import com.google.firebase.auth.FirebaseUser;
import com.google.firebase.database.DataSnapshot;
import com.google.firebase.database.DatabaseError;
import com.google.firebase.database.DatabaseReference;
import com.google.firebase.database.FirebaseDatabase;
import com.google.firebase.database.ValueEventListener;
import com.google.firebase.storage.FirebaseStorage;
import com.google.firebase.storage.StorageReference;

import java.util.ArrayList;
import java.util.HashMap;
import java.util.Map;

public class Listening_Quiz extends AppCompatActivity {

    TextView tvscore, tvquestcount;
    RadioGroup rdgchoices;
    RadioButton rdbA,rdbB,rdbC,rdbD;
    Button btnconfirm, btnquit;
    ImageView imglisten;
    ArrayList<Listening> listen;
    MediaPlayer mediaPlayer;
    ImageButton imgBT;
    User user;
    FirebaseAuth myAuth;
    int questioncurrent = 0;
    int questiontrue = 0;
    String answer;
    int score=0;

    private FirebaseDatabase rootNode;
    private DatabaseReference myRef;

    String idUser;
    int pointReading;

    private static final String TAG = "Listening_Quiz.java";
    String URL = "https://github.com/Lap2000/songs/raw/main/Bay-Giua-Ngan-Ha-Nam-Cuong.mp3";
    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_listening_quiz);
        getSupportActionBar().setDisplayHomeAsUpEnabled(true);
        getSupportActionBar().setBackgroundDrawable(new ColorDrawable(getResources().getColor(R.color.cPrimary)));
        addControls();
        getListUserFromRealtimeDatabase();
        //layUser();
        AddArrayCLN();

        addEvents();

    }

    private void addEvents() {

//        shownextquestion(questioncurrent,listen, listen.size());
        // Create MediaPlayer.
        mediaPlayer=  new MediaPlayer();
        mediaPlayer.setAudioStreamType(AudioManager.STREAM_MUSIC);

        mediaPlayer.setOnPreparedListener(new MediaPlayer.OnPreparedListener() {
            @Override
            public void onPrepared(MediaPlayer mp) {mp.start();}
        });
        CountDownTimer countDownTimer=new CountDownTimer(3000,300) {
            @Override
            public void onTick(long millisUntilFinished) {
                showanswer();
            }

            @Override
            public void onFinish() {
                btnconfirm.setEnabled(true);
                shownextquestion(questioncurrent,listen);
            }
        };
        btnconfirm.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                checkans();
                questioncurrent++;
                countDownTimer.start();
            }
        });
        // When the video file ready for playback.
        this.imgBT.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                // http://example.coom/mysong.mp3
                //String mediaURL = MediaPlayerUtils.URL_MEDIA_SAMPLE;

                String mediaURL = URL;
                //Xử lý sự kiện phát audio thông qua link URL
                MediaPlayerUtils.playURLMedia(Listening_Quiz.this, mediaPlayer, mediaURL);
                //doStart();
            }
        });
        btnquit.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                finish();
                doStop();
                Intent intent
                        = new Intent(Listening_Quiz.this,
                        ListeningActivity.class);
                startActivity(intent);
            }
        });
    }
    public void shownextquestion(int pos, ArrayList<Listening> lt){

        if(pos > 0) doStop();
        tvquestcount.setText("Question: "+(questioncurrent+1)+"/"+lt.size()+"");
        rdgchoices.clearCheck();
        rdbA.setBackground(this.getResources().getDrawable(R.drawable.bgbtn));
        rdbB.setBackground(this.getResources().getDrawable(R.drawable.bgbtn));
        rdbC.setBackground(this.getResources().getDrawable(R.drawable.bgbtn));
        rdbD.setBackground(this.getResources().getDrawable(R.drawable.bgbtn));
        if(pos == listen.size()){
            Toast.makeText(Listening_Quiz.this, "Congratulation!!!", Toast.LENGTH_SHORT).show();
            onClickUpdateMyProfile();

            Intent intent=new Intent(Listening_Quiz.this, Congra_listen.class);

            intent.putExtra("point", score);
            intent.putExtra("true", questiontrue);
            startActivity(intent);
        }
        else {
            //Câu lệnh lấy Ảnh thông qua link uri được lưu trong Firebase store
            FirebaseStorage storage = FirebaseStorage.getInstance("gs://learningenglishproject-a5bc5.appspot.com");
            StorageReference httpsReference = storage.getReferenceFromUrl(lt.get(pos).getImage());
            httpsReference.getDownloadUrl().addOnSuccessListener(new OnSuccessListener<Uri>() {
                @Override
                public void onSuccess(Uri uri) {
                    Glide.with(Listening_Quiz.this)
                            .load(uri)
                            .error(R.drawable.cloud)
                            .into(imglisten);
                }
            });


            String URLaudio = lt.get(pos).getAudio();
            URL = URLaudio;
            answer = lt.get(pos).getTrue();
            rdbA.setText(lt.get(pos).getA());
            rdbB.setText(lt.get(pos).getB());
            rdbC.setText(lt.get(pos).getC());
            rdbD.setText(lt.get(pos).getD());
        }
    }
    //Lấy 1 list các object có trong Listening
    private void AddArrayCLN(){
        FirebaseDatabase firebaseDatabase = FirebaseDatabase.getInstance();
        DatabaseReference myRef= firebaseDatabase.getReference().child("Listening");
        myRef.addValueEventListener(new ValueEventListener() {
            @Override
            public void onDataChange(@NonNull DataSnapshot snapshot) {
                Iterable<DataSnapshot> nodeChild = snapshot.getChildren();
                for (DataSnapshot dataSnapshot : nodeChild){
                    Listening ls = dataSnapshot.getValue(Listening.class);
                    listen.add(ls);
                }
                shownextquestion(questioncurrent,listen);
            }

            @Override
            public void onCancelled(@NonNull DatabaseError error) {
                Toast.makeText(Listening_Quiz.this, "Get Listen_Quiz fail", Toast.LENGTH_SHORT).show();
            }
        });
    }

    //Hàm update data từ android lên firebase
    public void onClickUpdateMyProfile() {
        rootNode = FirebaseDatabase.getInstance();
        myRef = rootNode.getReference("User").child(idUser);
        Map<String, Object> map = new HashMap<>();
        map.put("pointListening", score);
        map.put("sumPoint", score + pointReading);
        myRef.updateChildren(map);
        Toast.makeText(Listening_Quiz.this, "Cập nhật thành công!", Toast.LENGTH_SHORT).show();
    }

    //Lấy user đang đăng nhập
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
                pointReading = user.getPointReading();
            }

            @Override
            public void onCancelled(@NonNull DatabaseError error) {
            }
        });
    }

    //Hàm check câu đúng
    public void checkans(){
        btnconfirm.setEnabled(false);
        if(rdbA.isChecked()){
            if(answer.equals("A")) {
                score+=20;
                questiontrue++;
            }
        }
        if(rdbB.isChecked()){
            if(answer.equals("B")) {
                score+=20;
                questiontrue++;
            }
        }
        if(rdbC.isChecked()){
            if(answer.equals("C")) {
                score+=20;
                questiontrue++;
            }
        }
        if(rdbD.isChecked()){
            if(answer.equals("D")) {
                score+=20;
                questiontrue++;
            }
        }
        tvscore.setText("Score: "+score+"");
    }
    //Hàm show câu đúng/sai
    public void showanswer(){
        if(answer.equals("A")) {
            rdbA.setBackground(this.getResources().getDrawable(R.drawable.button_true));
            rdbB.setBackground(this.getResources().getDrawable(R.drawable.button_false));
            rdbC.setBackground(this.getResources().getDrawable(R.drawable.button_false));
            rdbD.setBackground(this.getResources().getDrawable(R.drawable.button_false));
        }
        if(answer.equals("B")) {
            rdbA.setBackground(this.getResources().getDrawable(R.drawable.button_false));
            rdbB.setBackground(this.getResources().getDrawable(R.drawable.button_true));
            rdbC.setBackground(this.getResources().getDrawable(R.drawable.button_false));
            rdbD.setBackground(this.getResources().getDrawable(R.drawable.button_false));
        }
        if(answer.equals("C")) {
            rdbA.setBackground(this.getResources().getDrawable(R.drawable.button_false));
            rdbB.setBackground(this.getResources().getDrawable(R.drawable.button_false));
            rdbC.setBackground(this.getResources().getDrawable(R.drawable.button_true));
            rdbD.setBackground(this.getResources().getDrawable(R.drawable.button_false));
        }
        if(answer.equals("D")) {
            rdbA.setBackground(this.getResources().getDrawable(R.drawable.button_false));
            rdbB.setBackground(this.getResources().getDrawable(R.drawable.button_false));
            rdbC.setBackground(this.getResources().getDrawable(R.drawable.button_false));
            rdbD.setBackground(this.getResources().getDrawable(R.drawable.button_true));
        }
    }

    private void addControls() {
        listen = new ArrayList<>();
        tvscore = (TextView)findViewById(R.id.txtscoreLN);
        tvquestcount = (TextView) findViewById(R.id.txtquestcountLN);
        rdgchoices = (RadioGroup) findViewById(R.id.radiochoices);
        rdbA = (RadioButton) findViewById(R.id.rdbA);
        rdbB = (RadioButton) findViewById(R.id.rdbB);
        rdbC = (RadioButton) findViewById(R.id.rdbC);
        rdbD = (RadioButton) findViewById(R.id.rdbD);
        btnconfirm = (Button) findViewById(R.id.btnconfirmLN);
        btnquit = (Button)findViewById(R.id.btnQuitLN);
        imglisten = (ImageView) findViewById(R.id.imgHinh);
        imgBT = (ImageButton) findViewById(R.id.imgBT);
    }
    private void doStop()  {
        if(mediaPlayer.isPlaying()) {
            mediaPlayer.stop();
        }
        mediaPlayer.reset();
    }

    private void doStart( )  {
        if(this.mediaPlayer.isPlaying()) {
            //this.mediaPlayer.stop();
            this.mediaPlayer.pause();
            this.mediaPlayer.reset();
        }
        else {this.mediaPlayer.start();}
    }

}