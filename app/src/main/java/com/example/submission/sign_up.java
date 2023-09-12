package com.example.submission;

import androidx.annotation.NonNull;
import androidx.appcompat.app.AppCompatActivity;

import android.annotation.SuppressLint;
import android.app.ProgressDialog;
import android.content.Intent;
import android.content.SharedPreferences;
import android.os.Bundle;
import android.text.TextUtils;
import android.view.View;
import android.widget.Button;
import android.widget.EditText;
import android.widget.TextView;
import android.widget.Toast;

import com.example.submission.users.User;
import com.google.android.gms.tasks.OnCompleteListener;
import com.google.android.gms.tasks.Task;
import com.google.firebase.auth.AuthResult;
import com.google.firebase.auth.FirebaseAuth;
import com.google.firebase.database.FirebaseDatabase;

public class sign_up extends AppCompatActivity {
    EditText userName;
    EditText password;
    EditText email;
    Button signUp;
    TextView login_in;
    private FirebaseAuth mAuth;
    FirebaseDatabase firebaseDatabase;
    ProgressDialog progressDialog;
    SharedPreferences sharedPreferences;

    @SuppressLint("MissingInflatedId")
    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_sign_up);
        userName = findViewById(R.id.username);
        password = findViewById(R.id.password);
        email = findViewById(R.id.email);
        signUp = findViewById(R.id.sign_up_btn);
        login_in = findViewById(R.id.sign_in);
        mAuth = FirebaseAuth.getInstance();
        firebaseDatabase = FirebaseDatabase.getInstance();
        progressDialog = new ProgressDialog(sign_up.this);
        progressDialog.setTitle("Creating account");
        progressDialog.setMessage("We are creating your account");
//        getSupportActionBar().hide();

        signUp.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                String getname = userName.getText().toString().trim();
                String getmail = email.getText().toString().trim();
                String getPass = password.getText().toString().trim();

                sharedPreferences = getSharedPreferences("OnBoardActivity", MODE_PRIVATE);
                boolean isFirstTime = sharedPreferences.getBoolean("FirstTime", true);

                if(isFirstTime) {
                    SharedPreferences.Editor editor = sharedPreferences.edit();
                    editor.putBoolean("FirstTime", false);
                    editor.commit();

                    Intent intent = new Intent(sign_up.this, OnBoardActivity.class);
                    startActivity(intent);
                    finish();
                }
                if (TextUtils.isEmpty(getmail) || TextUtils.isEmpty(getPass) || TextUtils.isEmpty(getname)) {
                    Toast.makeText(sign_up.this, "Enter your details", Toast.LENGTH_SHORT).show();
                }
                try {
                    progressDialog.show();

                    mAuth.createUserWithEmailAndPassword(email.getText().toString(), password.getText().toString()).addOnCompleteListener(new OnCompleteListener<AuthResult>() {
                        @Override
                        public void onComplete(@NonNull Task<AuthResult> task) {
                            progressDialog.dismiss();
                            if (task.isSuccessful()) {
                                User user = new User(userName.getText().toString(), email.getText().toString(), password.getText().toString());
                                String id = task.getResult().getUser().getUid();
                                firebaseDatabase.getReference().child("Users").child(id).setValue(user);
                                Intent intent2 = new Intent(sign_up.this, MainActivity.class);
                                startActivity(intent2);
                                Toast.makeText(sign_up.this, "User created successfully", Toast.LENGTH_SHORT).show();
                            } else {
                                Toast.makeText(sign_up.this, task.getException().getMessage(), Toast.LENGTH_SHORT).show();
                            }
                        }
                    });
                }
                catch (Exception e) {
                    progressDialog.dismiss();
                    e.printStackTrace();
                }
            }
        });



        login_in.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                Intent intent2 = new Intent(sign_up.this, MainActivity.class);
                startActivity(intent2);
            }
        });
    }
}