package com.example.ande_assignment_budget;

import androidx.annotation.NonNull;
import androidx.appcompat.app.AppCompatActivity;

import android.content.Intent;
import android.os.Bundle;
import android.text.TextUtils;
import android.util.Log;
import android.view.View;
import android.widget.EditText;
import android.widget.ProgressBar;
import android.widget.TextView;
import android.widget.Toast;

import com.google.android.gms.tasks.OnCompleteListener;
import com.google.android.gms.tasks.Task;
import com.google.firebase.auth.AuthResult;
import com.google.firebase.auth.FirebaseAuth;
import com.google.firebase.auth.FirebaseUser;

public class Login extends AppCompatActivity implements View.OnClickListener {

    private TextView tvErrorMessage;
    private EditText etEmail, etPassword;
    private ProgressBar pbLoginProgress;

    private FirebaseAuth mAuth;

    @Override
    public void onStart() {
        super.onStart();
        mAuth = FirebaseAuth.getInstance();

        // Check if user is signed in (non-null) and update UI accordingly.
        FirebaseUser currentUser = mAuth.getCurrentUser();
        if(currentUser != null) {
            Intent i = new Intent(Login.this, MainActivity.class);
            startActivity(i);
        }
    }

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_login);

        mAuth = FirebaseAuth.getInstance();
    }

    @Override
    public void onClick(View v) {
        if (v.getId() == R.id.btnRegister) {
            Log.d("CLICKTEST", "Onclick successful, trying to login");
            loginUserAccount();
        }
    }

    private void loginUserAccount() {
        pbLoginProgress = findViewById(R.id.pbLoginProgress);
        etEmail = findViewById(R.id.etEmail);
        etPassword = findViewById(R.id.etPassword);
        tvErrorMessage = findViewById(R.id.tvErrorMessage);

        pbLoginProgress.setVisibility(View.VISIBLE);

        String email = etEmail.getText().toString();
        String password = etPassword.getText().toString();

        if (TextUtils.isEmpty(email)) {
            Toast.makeText(getApplicationContext(), "Please enter your email...", Toast.LENGTH_LONG).show();
            pbLoginProgress.setVisibility(View.GONE);
            return;
        }
        if (TextUtils.isEmpty(password)) {
            Toast.makeText(getApplicationContext(), "Please enter your password!", Toast.LENGTH_LONG).show();
            pbLoginProgress.setVisibility(View.GONE);
            return;
        }

        mAuth.signInWithEmailAndPassword(email, password)
                .addOnCompleteListener(this, new OnCompleteListener<AuthResult>() {
                    @Override
                    public void onComplete(@NonNull Task<AuthResult> task) {
                        if (task.isSuccessful()) {
                            Toast.makeText(getApplicationContext(), "Login successful!", Toast.LENGTH_LONG).show();
                            tvErrorMessage.setVisibility(View.GONE);
                            pbLoginProgress.setVisibility(View.GONE);

                            Log.d("STATE", "Login is successful, going to Index");

                            Intent i = new Intent(Login.this, MainActivity.class);
                            startActivity(i);
                        } else {
                            Toast.makeText(getApplicationContext(), "Login failed! Please try again later", Toast.LENGTH_LONG).show();
                            tvErrorMessage.setVisibility(View.VISIBLE);
                            pbLoginProgress.setVisibility(View.GONE);
                        }
                    }
                });
    }


}