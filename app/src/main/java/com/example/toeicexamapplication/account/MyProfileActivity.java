package com.example.toeicexamapplication.account;

import android.os.Bundle;
import android.view.View;
import android.widget.Button;
import android.widget.EditText;
import android.widget.TextView;
import android.widget.Toast;

import androidx.annotation.NonNull;
import androidx.appcompat.app.AppCompatActivity;

import com.example.toeicexamapplication.R;
import com.google.firebase.auth.FirebaseAuth;
import com.google.firebase.auth.FirebaseUser;
import com.google.firebase.database.DataSnapshot;
import com.google.firebase.database.DatabaseError;
import com.google.firebase.database.DatabaseReference;
import com.google.firebase.database.FirebaseDatabase;
import com.google.firebase.database.ValueEventListener;

import java.util.HashMap;
import java.util.Map;

public class MyProfileActivity extends AppCompatActivity {

    private TextView tvName, tvUserName, tvSumPoint;
    private EditText edtNameProfile, edtEmailProfile, edtPhoneProfile, edtUID;
    private Button btnUpdateProfile;

    private FirebaseDatabase rootNode;
    private DatabaseReference myRef;

    String idUser;
    @Override
    protected void onCreate(Bundle savedInstanceState) {
        getSupportActionBar().hide();
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_my_profile);

        getWidgets();
        getListUserFromRealtimeDatabase();

        btnUpdateProfile.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                onClickUpdateMyProfile();
            }
        });

    }

    private void onClickUpdateMyProfile() {
        String name = edtNameProfile.getText().toString().trim();
        String phone = edtPhoneProfile.getText().toString().trim();

        rootNode = FirebaseDatabase.getInstance();
        myRef = rootNode.getReference("User").child(idUser);
        Map<String, Object> map = new HashMap<>();
        map.put("hoTen", name);
        map.put("sdt", phone);
        myRef.updateChildren(map);
        Toast.makeText(MyProfileActivity.this, "Cập nhật thành công!", Toast.LENGTH_SHORT).show();
    }

    private void getListUserFromRealtimeDatabase() {
        FirebaseUser user = FirebaseAuth.getInstance().getCurrentUser();
        if (user == null) {
            return;
        }
        if (user != null) {
            String email = user.getEmail().toString();
            idUser = user.getUid().toString();
            String uid = user.getUid().toString();

            tvUserName.setText(email);
            edtEmailProfile.setText(email);
            edtUID.setText(uid);
        }

        rootNode = FirebaseDatabase.getInstance();
        myRef = rootNode.getReference("User").child(user.getUid());

        myRef.addValueEventListener(new ValueEventListener() {
            @Override
            public void onDataChange(@NonNull DataSnapshot snapshot) {
                User user = snapshot.getValue(User.class);
                tvName.setText(user.getHoTen().toString());
                edtNameProfile.setText(user.getHoTen().toString());
                edtPhoneProfile.setText(user.getSdt().toString());;
                int sum = user.getSumPoint();
                tvSumPoint.setText(String.valueOf(sum));
            }

            @Override
            public void onCancelled(@NonNull DatabaseError error) {
                Toast.makeText(MyProfileActivity.this, "Fail!", Toast.LENGTH_SHORT).show();
            }
        });
    }

    private void getWidgets() {
        tvName = (TextView) findViewById(R.id.tv_name);
        tvUserName = (TextView) findViewById(R.id.tv_username);
        tvSumPoint = (TextView) findViewById(R.id.tv_sum_point);
        edtNameProfile = (EditText) findViewById(R.id.edt_name_profile);
        edtEmailProfile = (EditText) findViewById(R.id.edt_email_profile);
        edtPhoneProfile = (EditText) findViewById(R.id.edt_phone_profile);
        edtUID = (EditText) findViewById(R.id.edt_UID);
        btnUpdateProfile = (Button) findViewById(R.id.btn_update_profile);

        edtUID.setEnabled(false);
        edtEmailProfile.setEnabled(false);

    }
}
