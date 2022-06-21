package com.example.toeicexamapplication.account;

import androidx.annotation.NonNull;
import androidx.appcompat.app.AppCompatActivity;

import android.app.ProgressDialog;
import android.content.Intent;
import android.os.Bundle;
import android.view.View;
import android.widget.Button;
import android.widget.EditText;
import android.widget.TextView;
import android.widget.Toast;

import com.example.toeicexamapplication.MainActivity;
import com.example.toeicexamapplication.R;
import com.google.android.gms.tasks.OnCompleteListener;
import com.google.android.gms.tasks.Task;
import com.google.firebase.auth.AuthResult;
import com.google.firebase.auth.FirebaseAuth;
import com.google.firebase.auth.SignInMethodQueryResult;
import com.google.firebase.database.DatabaseReference;
import com.google.firebase.database.FirebaseDatabase;

import org.w3c.dom.Text;

public class SignUpActivity extends AppCompatActivity {

    private EditText edtName, edtEmail, edtPhone, edtPassword, edtConfirmPassword;
    private Button btnSignUp;
    private TextView tvRefLogin;

    private ProgressDialog mProgressDialog;
    private FirebaseAuth mAuth;
    FirebaseDatabase rootNode;
    DatabaseReference myRef;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        getSupportActionBar().hide();
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_sign_up);
        // khởi tạo các widget
        getWidgets();
        handleEvent();

    }

    private void handleEvent() {
        btnSignUp.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                onCLickSignUp();
            }
        });

        tvRefLogin.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                startActivity(new Intent(SignUpActivity.this,SignInActivity.class));
            }
        });
    }

    private void onCLickSignUp() {
        // lấy dữ liệu
        String name = edtName.getText().toString().trim();
        String email = edtEmail.getText().toString().trim();
        String phone = edtPhone.getText().toString().trim();
        String password = edtPassword.getText().toString().trim();
        String confirmPassword = edtConfirmPassword.getText().toString().trim();

        mAuth = FirebaseAuth.getInstance();
        mProgressDialog.show();
        // check validation
        if(name.equals("") || email.equals("") || phone.equals("") || password.equals("")) {
            Toast.makeText(SignUpActivity.this, "Vui lòng điền đầy đủ thông tin!", Toast.LENGTH_SHORT).show();
        } else {
            if (password.equals(confirmPassword)) {
                mAuth.fetchSignInMethodsForEmail(email)
                        .addOnCompleteListener(new OnCompleteListener<SignInMethodQueryResult>() {
                            @Override
                            public void onComplete(@NonNull Task<SignInMethodQueryResult> task) {
                                boolean isNewUser = task.getResult().getSignInMethods().isEmpty();
                                if (isNewUser) {
                                    mAuth.createUserWithEmailAndPassword(email, password)
                                            .addOnCompleteListener(SignUpActivity.this, new OnCompleteListener<AuthResult>() {
                                                @Override
                                                public void onComplete(@NonNull Task<AuthResult> task) {
                                                    mProgressDialog.dismiss();
                                                    //trả về một đối tượng task
                                                    if (task.isSuccessful()) {
                                                        // push data lên realtime database
                                                        rootNode = FirebaseDatabase.getInstance();
                                                        myRef= rootNode.getReference("User");
                                                        User newUser = new User(mAuth.getCurrentUser().getUid(), name, email, phone, 0, 0, 0);
                                                        myRef.child(FirebaseAuth.getInstance().getCurrentUser().getUid()).setValue(newUser);

                                                        // chuyển sang màn hình đăng nhập
                                                        Intent intent = new Intent(getApplicationContext(),SignInActivity.class);
                                                        startActivity(intent);
                                                        //đóng tất cả các activity trước tg main: finishAffinity();
                                                        finish();
                                                    } else {
                                                        Toast.makeText(SignUpActivity.this, "Đăng ký thất bại!", Toast.LENGTH_SHORT).show();
                                                    }
                                                }
                                            });
                                } else {
                                    mProgressDialog.dismiss();
                                    Toast.makeText(SignUpActivity.this, "Tài khoản đã tồn tại!", Toast.LENGTH_SHORT).show();
                                }

                            }
                        });
            }else{
                Toast.makeText(SignUpActivity.this, "Mật khẩu và mật khẩu nhập lại không khớp!", Toast.LENGTH_SHORT).show();
                edtPassword.setText("");
                edtConfirmPassword.setText("");
            }
        }

    }

    private void getWidgets() {
        edtName = (EditText) findViewById(R.id.edt_name);
        edtEmail = (EditText) findViewById(R.id.edt_email);
        edtPhone = (EditText) findViewById(R.id.edt_phone);
        edtPassword = (EditText) findViewById(R.id.edt_password);
        edtConfirmPassword = (EditText) findViewById(R.id.edt_confirm_password);

        tvRefLogin = (TextView) findViewById(R.id.tv_ref_login);
        btnSignUp = (Button) findViewById(R.id.btn_sign_up);
        mProgressDialog = new ProgressDialog(this);
    }
}