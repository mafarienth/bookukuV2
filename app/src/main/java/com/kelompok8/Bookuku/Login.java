package com.kelompok8.Bookuku;

import android.content.Intent;
import android.os.Bundle;
import android.support.annotation.NonNull;
import android.support.design.widget.TextInputLayout;
import android.support.v7.app.AppCompatActivity;
import android.support.v7.widget.Toolbar;
import android.view.View;
import android.widget.Button;
import android.widget.TextView;
import android.widget.Toast;

import com.kelompok8.Bookuku.model.User;
import com.google.android.gms.tasks.OnCompleteListener;
import com.google.android.gms.tasks.Task;
import com.google.firebase.auth.AuthResult;
import com.google.firebase.auth.FirebaseAuth;
import com.google.firebase.auth.FirebaseUser;
import com.google.firebase.database.DatabaseReference;
import com.google.firebase.database.FirebaseDatabase;
import com.kelompok8.Bookuku.R;

public class Login extends AppCompatActivity {

    private static final String TAG = "Authentication Email";
    TextInputLayout mEmail, mPassword;
    Button mDaftar, mMasuk;

    TextView mReset;

    String email, password;

    private FirebaseAuth mAuth;
    DatabaseReference databaseUser;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_login);

        findViewById(R.id.btn_play_again).setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                // We normally won't show the welcome slider again in real app
                // but this is for testing
                PrefManager prefManager = new PrefManager(getApplicationContext());

                // make first time launch TRUE
                prefManager.setFirstTimeLaunch(true);

                startActivity(new Intent(Login.this, WelcomeActivity.class));
                finish();
            }
        });

        mAuth = FirebaseAuth.getInstance();

        mEmail = findViewById(R.id.et_email);
        mPassword = findViewById(R.id.et_password);

        databaseUser = FirebaseDatabase.getInstance().getReference(MainActivity.table3);

        mReset = findViewById(R.id.forgot_pass);
        mReset.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                Intent i = new Intent(Login.this, ForgotPass.class);
                startActivity(i);
            }
        });

        mMasuk = findViewById(R.id.btn_masuk);
        mMasuk.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                email = mEmail.getEditText().getText().toString();
                password = mPassword.getEditText().getText().toString();

                if (validateForm()) {
                    signIn(email, password);
                } else {
                    Toast.makeText(Login.this, "Please Fill the Form",
                            Toast.LENGTH_SHORT).show();
                }
            }
        });

        mDaftar = findViewById(R.id.btn_daftar);
        mDaftar.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                startActivity(new Intent(Login.this, SignUp.class));
            }

            // @Override
           // public void onClick(View v) {
              //  email = mEmail.getEditText().getText().toString();
              //  password = mPassword.getEditText().getText().toString();

              //  if (validateForm()) {
              //      createAccount(email, password);
              //  }
              //  else
              //      {
              //      Toast.makeText(Login.this, "Please Fill the Form",
              //              Toast.LENGTH_SHORT).show();
              //  }
//            }
        });
    }

    @Override
    protected void onStart() {
        super.onStart();
        FirebaseUser currentUser = mAuth.getCurrentUser();
        if (currentUser != null) {
            sendToMain();
        }

    }

//    private void createAccount(final String email, final String password) {
//
//        mAuth.createUserWithEmailAndPassword(email, password)
//                .addOnCompleteListener(this, new OnCompleteListener<AuthResult>() {
//                    @Override
//                    public void onComplete(@NonNull Task<AuthResult> task) {
//                        if (task.isSuccessful()) {
//                            String id = mAuth.getUid();
//                            String[] username = email.split("@");
//                            User user = new User(id, username[0], email);
//                            databaseUser.child(id).setValue(user);
//                            Intent i = new Intent(Login.this, MainActivity.class);
//                            startActivity(i);
//                        } else {
//                            Toast.makeText(Login.this, "Authentication failed.",
//                                    Toast.LENGTH_SHORT).show();
//                        }
//                    }
//                });
//    }

    private void signIn(String email, String password) {

        mAuth.signInWithEmailAndPassword(email, password)
                .addOnCompleteListener(this, new OnCompleteListener<AuthResult>() {
                    @Override
                    public void onComplete(@NonNull Task<AuthResult> task) {
                        if (task.isSuccessful()) {

                            FirebaseUser user = mAuth.getCurrentUser();
                            Intent i = new Intent(Login.this, MainActivity.class);
                            startActivity(i);
                        } else {

                            Toast.makeText(Login.this, "Akun Belum Terdaftar",
                                    Toast.LENGTH_SHORT).show();
                        }
                    }
                });

    }

    private boolean validateForm() {
        boolean valid = false;

        if (email.isEmpty() || password.isEmpty()) {
            valid = false;
        } else {
            valid = true;
        }
        return valid;

    }

    private void sendToMain() {
        Intent mainIntent = new Intent(Login.this, MainActivity.class);
        startActivity(mainIntent);
        finish();
    }
}
