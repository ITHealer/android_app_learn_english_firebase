package com.example.toeicexamapplication.account;

import androidx.annotation.NonNull;
import androidx.appcompat.app.AppCompatActivity;

import android.app.Dialog;
import android.app.ProgressDialog;
import android.content.Intent;
import android.os.Bundle;
import android.text.TextUtils;
import android.view.View;
import android.view.Window;
import android.widget.Button;
import android.widget.EditText;
import android.widget.LinearLayout;
import android.widget.TextView;
import android.widget.Toast;

import com.example.toeicexamapplication.MainActivity;
import com.example.toeicexamapplication.R;
import com.google.android.gms.tasks.OnCompleteListener;
import com.google.android.gms.tasks.Task;
import com.google.firebase.auth.AuthResult;
import com.google.firebase.auth.FirebaseAuth;

public class SignInActivity extends AppCompatActivity {

    private TextView tvForgotPassword, tvRegister;
    private EditText edtUserName, edtPassword;
    private Button btnSignIn;

    private ProgressDialog mProgressDialog;
    private FirebaseAuth mAuth;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        getSupportActionBar().hide();
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_sign_in);

        getWidgets();
        handleEvent();

    }

    private void handleEvent() {
        btnSignIn.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                onClickSignIn();
            }
        });

        tvRegister.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                startActivity(new Intent(SignInActivity.this, SignUpActivity.class));
            }
        });

        tvForgotPassword.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                //chuyển sang activity khác
                startActivity(new Intent(SignInActivity.this, ForgotPasswordActivity.class));
                //cách 1
                //dialogSendEmailForgotPassword();
                //cách 2
                //onClickForgotPassword();
            }
        });
    }

//    private void onClickForgotPassword() {
//        mProgressDialog.show();
//        FirebaseAuth auth = FirebaseAuth.getInstance();
//
//        //tạo một dialog để user xác nhận email
//        String emailAddress = "ungminhhoai29@gmail.com";
//
//        auth.sendPasswordResetEmail(emailAddress)
//                .addOnCompleteListener(new OnCompleteListener<Void>() {
//                    @Override
//                    public void onComplete(@NonNull Task<Void> task) {
//                        mProgressDialog.dismiss();
//                        if (task.isSuccessful()) {
//                            Toast.makeText(SignInActivity.this, "Success.",
//                                    Toast.LENGTH_SHORT).show();
//                        }
//                    }
//                });
//    }

    private void onClickSignIn() {
        String email = edtUserName.getText().toString().trim();
        String matkhau = edtPassword.getText().toString().trim();

        mAuth = FirebaseAuth.getInstance();
        mProgressDialog.show();
        // validations for input email and password
        if (TextUtils.isEmpty(email)) {
            mProgressDialog.dismiss();
            Toast.makeText(getApplicationContext(), "Vui lòng nhập Email của bạn!", Toast.LENGTH_LONG).show();
            return;
        }

        if (TextUtils.isEmpty(matkhau)) {
            mProgressDialog.dismiss();
            Toast.makeText(getApplicationContext(), "Vui lòng nhập mật khẩu của bạn!!", Toast.LENGTH_LONG).show();
            return;
        }

        // signin existing user
        mAuth.signInWithEmailAndPassword(email, matkhau)
                .addOnCompleteListener(
                        new OnCompleteListener<AuthResult>() {
                            @Override
                            public void onComplete(@NonNull Task<AuthResult> task)
                            {
                                mProgressDialog.dismiss();
                                if (task.isSuccessful()) {
                                    Toast.makeText(getApplicationContext(), "Đăng nhập thành công!", Toast.LENGTH_LONG).show();
                                    Intent intent = new Intent(SignInActivity.this, MainActivity.class);
                                    startActivity(intent);
                                }
                                else {
                                    Toast.makeText(getApplicationContext(), "Vui lòng kiểm tra lại email hoặc mật khẩu!", Toast.LENGTH_LONG).show();
                                }
                            }
                        });
    }

//    private void dialogSendEmailForgotPassword(){
//        Dialog dialog = new Dialog(this);
//        dialog.requestWindowFeature(Window.FEATURE_NO_TITLE);
//        dialog.setContentView(R.layout.dialog_forgot_password);
//
//        EditText edtSendEmail = (EditText) dialog.findViewById(R.id.edit_send_email);
//        Button btnSend = (Button) dialog.findViewById(R.id.btn_send);
//        Button btnHuy = (Button) dialog.findViewById(R.id.btn_huy);
//
//        btnSend.setOnClickListener(new View.OnClickListener() {
//            @Override
//            public void onClick(View view) {
//                String dialogEnail = edtSendEmail.getText().toString().trim();
//                if(dialogEnail.equals(""))
//                {
//                    Toast.makeText(SignInActivity.this, "Vui lòng nhập email của bạn!", Toast.LENGTH_SHORT).show();
//                }else {
//                    mProgressDialog.show();
//                    mAuth = FirebaseAuth.getInstance();
//                    String emailAddress = edtSendEmail.getText().toString().trim();
//                    mAuth.sendPasswordResetEmail(emailAddress)
//                            .addOnCompleteListener(new OnCompleteListener<Void>() {
//                                @Override
//                                public void onComplete(@NonNull Task<Void> task) {
//                                    if (task.isSuccessful()) {
//                                        mProgressDialog.dismiss();
//                                        Toast.makeText(SignInActivity.this, "Mã xác nhận đã được gửi qua email!", Toast.LENGTH_SHORT).show();
//                                    }
//                                }
//                            });
//                    Toast.makeText(SignInActivity.this, "Mật khẩu mới đã gửi qua email của bạn!", Toast.LENGTH_SHORT).show();
//                    dialog.dismiss();
//                    finish();
//                }
//            }
//        });
//
//        //setCanceledOnTouchOutside khi chạm bên ngoài dialog sẽ k bị tắt; mặc định là true
//        dialog.setCanceledOnTouchOutside(false);
//        btnHuy.setOnClickListener(new View.OnClickListener() {
//            @Override
//            public void onClick(View view) {
//                dialog.dismiss();
//            }
//        });
//        dialog.show();
//    }

    private void getWidgets() {
        tvForgotPassword = (TextView) findViewById(R.id.tv_login_forgot_password);
        tvRegister = (TextView) findViewById(R.id.tv_register);
        edtUserName = (EditText) findViewById(R.id.edt_username);
        edtPassword = (EditText) findViewById(R.id.edt_password);
        btnSignIn = (Button) findViewById(R.id.btn_sign_in);
        mProgressDialog = new ProgressDialog(this);
    }
}