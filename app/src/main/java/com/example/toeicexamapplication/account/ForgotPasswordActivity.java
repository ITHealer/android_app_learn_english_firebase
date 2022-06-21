package com.example.toeicexamapplication.account;

import android.content.Intent;
import android.os.Bundle;
import android.util.Patterns;
import android.view.View;
import android.widget.Button;
import android.widget.EditText;
import android.widget.TextView;
import android.widget.Toast;

import androidx.annotation.NonNull;
import androidx.appcompat.app.AppCompatActivity;

import com.example.toeicexamapplication.R;
import com.google.android.gms.tasks.OnCompleteListener;
import com.google.android.gms.tasks.Task;
import com.google.firebase.auth.FirebaseAuth;

public class ForgotPasswordActivity extends AppCompatActivity {

    private EditText edtEmailForgotPassword;
    private Button btnSendEmail;
    private TextView tvBackLogin;

    FirebaseAuth mAuth;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        getSupportActionBar().hide();
        super.onCreate(savedInstanceState);
        setContentView(R.layout.dialog_forgot_password);

        edtEmailForgotPassword = (EditText) findViewById(R.id.edt_send_email);
        btnSendEmail = (Button) findViewById(R.id.btn_send_email);
        tvBackLogin = (TextView) findViewById(R.id.tv_back_login);

        mAuth = FirebaseAuth.getInstance();

        tvBackLogin.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                startActivity(new Intent(ForgotPasswordActivity.this, SignInActivity.class));
            }
        });

        btnSendEmail.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                resetPassword();
            }
        });
    }

    private void resetPassword(){
        String email = edtEmailForgotPassword.getText().toString().trim();

        if(email.isEmpty()){
            edtEmailForgotPassword.setError("Hãy nhập Email của bạn!");
            edtEmailForgotPassword.requestFocus();
            return;
        }

        if (!Patterns.EMAIL_ADDRESS.matcher(email).matches()){
            edtEmailForgotPassword.setError("Hãy nhập đúng Email!");
            edtEmailForgotPassword.requestFocus();
        }

        mAuth.sendPasswordResetEmail(email).addOnCompleteListener(new OnCompleteListener<Void>() {
            @Override
            public void onComplete(@NonNull Task<Void> task) {
                if(task.isSuccessful()){
                    Toast.makeText(ForgotPasswordActivity.this,"Hãy kiểm tra (Hộp thư đến) của bạn để tiến hành thiết lập lại mật khẩu!", Toast.LENGTH_LONG).show();
                    Intent intent = new Intent(getApplicationContext(),SignInActivity.class);
                    startActivity(intent);
                }
                else {
                    Toast.makeText(ForgotPasswordActivity.this,"KHÔNG THÀNH CÔNG!Hãy kiểm tra lại Email của bạn và thử lại!", Toast.LENGTH_LONG).show();
                }

            }
        });
    }
}