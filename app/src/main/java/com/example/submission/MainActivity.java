package com.example.submission;

import androidx.annotation.NonNull;
import androidx.appcompat.app.AppCompatActivity;

import android.annotation.SuppressLint;
import android.app.ProgressDialog;
import android.content.Intent;
import android.os.Bundle;
import android.text.TextUtils;
import android.view.View;
import android.widget.Button;
import android.widget.EditText;
import android.widget.TextView;
import android.widget.Toast;

import com.google.android.gms.tasks.OnCompleteListener;
import com.google.android.gms.tasks.Task;
import com.google.firebase.auth.AuthResult;
import com.google.firebase.auth.FirebaseAuth;

public class MainActivity extends AppCompatActivity {
EditText email;
EditText password;
Button signIn;
TextView signUp;
TextView forgot_pass;
ProgressDialog progressDialog;
FirebaseAuth mAuth;


    @SuppressLint({"WrongViewCast", "MissingInflatedId"})
    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_main);
        email = findViewById(R.id.email);
        password = findViewById(R.id.password);
        signIn = findViewById(R.id.sign_in_btn);
        signUp = findViewById(R.id.sign_up);
        forgot_pass = findViewById(R.id.forgot_password);
        progressDialog = new ProgressDialog(MainActivity.this);
        progressDialog.setTitle("Logging in");
        progressDialog.setMessage("Logging in to your account");
        mAuth = FirebaseAuth.getInstance();
//        getSupportActionBar().hide();


        signIn.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                String getmail = email.getText().toString().trim();
                String getPass = password.getText().toString().trim();
                if (TextUtils.isEmpty(getmail) && !TextUtils.isEmpty(getPass)) {
                    Toast.makeText(MainActivity.this, "Enter your email", Toast.LENGTH_SHORT).show();
                }
                else if (TextUtils.isEmpty(getPass) && !TextUtils.isEmpty(getmail)) {
                    Toast.makeText(MainActivity.this, "Enter your password", Toast.LENGTH_SHORT).show();
                }
                else if (TextUtils.isEmpty(getmail) && TextUtils.isEmpty(getPass)) {
                    Toast.makeText(MainActivity.this, "Enter your email and password", Toast.LENGTH_SHORT).show();
                }

                try {
                    progressDialog.show();
                    mAuth.signInWithEmailAndPassword(email.getText().toString(), password.getText().toString()).addOnCompleteListener(new OnCompleteListener<AuthResult>() {
                        @Override
                        public void onComplete(@NonNull Task<AuthResult> task) {
                            progressDialog.dismiss();
                            if (task.isSuccessful()) {
                                Intent intent = new Intent(MainActivity.this, HomePage.class);
                                startActivity(intent);
                            } else {
                                Toast.makeText(MainActivity.this, task.getException().getMessage(), Toast.LENGTH_SHORT).show();
                            }
                        }
                    });
                } catch (Exception e) {
                    progressDialog.dismiss();
                    e.printStackTrace();
            }
            }
        });


        signUp.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                Intent intent = new Intent(MainActivity.this, sign_up.class);
                startActivity(intent);
            }
        });

        forgot_pass.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                mAuth.sendPasswordResetEmail(email.getText().toString()).addOnCompleteListener(new OnCompleteListener<Void>() {
                    @Override
                    public void onComplete(@NonNull Task<Void> task) {

                        if(task.isSuccessful()) {
                            Toast.makeText(MainActivity.this, "Password reset email sent", Toast.LENGTH_SHORT).show();
                        }
                        else{
                            Toast.makeText(MainActivity.this, task.getException().getMessage(), Toast.LENGTH_SHORT).show();
                        }
                    }
                });
            }
        });

//      if(mAuth.getCurrentUser()!=null) {
//          Intent intent = new Intent(MainActivity.this, Thankyou.class);
//          startActivity(intent);
//      }


    }
}