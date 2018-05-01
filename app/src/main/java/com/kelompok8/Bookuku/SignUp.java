package com.kelompok8.Bookuku;

import android.content.Intent;
import android.os.Bundle;
import android.support.annotation.NonNull;
import android.support.design.widget.TextInputLayout;
import android.support.v7.app.AppCompatActivity;
import android.view.View;
import android.widget.Button;
import android.widget.TextView;
import android.widget.Toast;

import com.google.android.gms.tasks.OnCompleteListener;
import com.google.android.gms.tasks.Task;
import com.google.firebase.auth.AuthResult;
import com.google.firebase.auth.FirebaseAuth;
import com.google.firebase.auth.FirebaseUser;
import com.google.firebase.database.DatabaseReference;
import com.google.firebase.database.FirebaseDatabase;
import com.kelompok8.Bookuku.model.User;

public class SignUp extends AppCompatActivity {


    private static final String TAG = "Authentication Email";
    TextInputLayout mEmail, mPassword;
    Button mDaftar;
    String email, password;

    TextView mLogin;

    private FirebaseAuth mAuth;
    DatabaseReference databaseUser;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_signup);

        mAuth = FirebaseAuth.getInstance();

        databaseUser = FirebaseDatabase.getInstance().getReference(MainActivity.table3);

        mEmail = findViewById(R.id.et_email);
        mPassword = findViewById(R.id.et_password);

        mLogin = findViewById(R.id.text_login);
        mLogin.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                Intent i = new Intent(SignUp.this, Login.class);
                startActivity(i);
            }
        });

        mDaftar = findViewById(R.id.btn_daftar);
        mDaftar.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                email = mEmail.getEditText().getText().toString();
                password = mPassword.getEditText().getText().toString();

                if (validateForm()) {
                    createAccount(email, password);
                } else {
                    Toast.makeText(SignUp.this, "Please Fill the Form",
                            Toast.LENGTH_SHORT).show();
                }
            }
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

    private void createAccount(final String email, final String password) {

        mAuth.createUserWithEmailAndPassword(email, password)
                .addOnCompleteListener(this, new OnCompleteListener<AuthResult>() {
                    @Override
                    public void onComplete(@NonNull Task<AuthResult> task) {
                        if (task.isSuccessful()) {
                            String id = mAuth.getUid();
                            String[] username = email.split("@");
                            User Users = new User(id, username[0], email);
                            databaseUser.child(id).setValue(Users);
                            Intent i = new Intent(SignUp.this, MainActivity.class);
                            startActivity(i);
                        } else {
                            Toast.makeText(SignUp.this, "Authentication failed.",
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
        Intent mainIntent = new Intent(SignUp.this, MainActivity.class);
        startActivity(mainIntent);
        finish();
    }
}