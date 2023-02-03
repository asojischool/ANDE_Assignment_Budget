package com.example.ande_assignment_budget;

import androidx.annotation.NonNull;
import androidx.appcompat.app.AppCompatActivity;

import android.content.Intent;
import android.content.SharedPreferences;
import android.os.Bundle;
import android.text.TextUtils;
import android.util.Log;
import android.view.View;
import android.widget.EditText;
import android.widget.ProgressBar;
import android.widget.TextView;
import android.widget.Toast;

import com.example.ande_assignment_budget.DatabaseHandler.SqliteDbHandler;
import com.google.android.gms.tasks.OnCompleteListener;
import com.google.android.gms.tasks.Task;
import com.google.firebase.auth.AuthResult;
import com.google.firebase.auth.FirebaseAuth;
import com.google.firebase.auth.FirebaseUser;

public class Register extends AppCompatActivity implements View.OnClickListener {
    SharedPreferences preference;
    private TextView tvErrorMessage;
    private EditText etName, etEmail, etPassword, etConfirmPassword;
    private ProgressBar pbLoginProgress;

    private FirebaseAuth mAuth;
    private SqliteDbHandler db;

    @Override
    public void onStart() {
        super.onStart();
        mAuth = FirebaseAuth.getInstance();

        // Check if user is signed in (non-null) and update UI accordingly.
        FirebaseUser currentUser = mAuth.getCurrentUser();
        if (currentUser != null) {
            Intent i = new Intent(Register.this, MainActivity.class);
            startActivity(i);
        }
    }

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_register);

        mAuth = FirebaseAuth.getInstance();
    }

    @Override
    public void onClick(View v) {
        Intent i;
        switch (v.getId()) {
            case R.id.btnRegister:
                Log.d("CLICKTEST", "Onclick successful, trying to login");
                signUpUserAccount();
                break;
            case R.id.tvLogin:
                i = new Intent(Register.this, Login.class);
                finish();
                startActivity(i);
                break;
        }
    }

    private void signUpUserAccount() {
        // init all
        etName = findViewById(R.id.etName);
        etEmail = findViewById(R.id.etEmail);
        etPassword = findViewById(R.id.etPassword);
        etConfirmPassword = findViewById(R.id.etConfirmPassword);
        tvErrorMessage = findViewById(R.id.tvErrorMessage);
        pbLoginProgress = findViewById(R.id.pbLoginProgress);

        // set loading screen
        pbLoginProgress.setVisibility(View.VISIBLE);

        // get all the edit text
        String name = etName.getText().toString();
        String email = etEmail.getText().toString();
        String password = etPassword.getText().toString();
        String confirmPassword = etConfirmPassword.getText().toString();

        // check if empty
        if (TextUtils.isEmpty(name)) {
            Toast.makeText(getApplicationContext(), "Please enter your name...", Toast.LENGTH_LONG).show();
            pbLoginProgress.setVisibility(View.GONE);
            return;
        }
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
        if (TextUtils.isEmpty(confirmPassword)) {
            Toast.makeText(getApplicationContext(), "Please confirm password!", Toast.LENGTH_LONG).show();
            pbLoginProgress.setVisibility(View.GONE);
            return;
        }
        if (!TextUtils.equals(password, confirmPassword)) {
            Toast.makeText(getApplicationContext(), "The password does not match!", Toast.LENGTH_LONG).show();
            pbLoginProgress.setVisibility(View.GONE);
            return;
        }

        db = new SqliteDbHandler(this);

        mAuth.createUserWithEmailAndPassword(email, password)
                .addOnCompleteListener(this, new OnCompleteListener<AuthResult>() {
                    @Override
                    public void onComplete(@NonNull Task<AuthResult> task) {
                        if (task.isSuccessful()) {
                            // Sign in success, update UI with the signed-in user's information
                            FirebaseUser user = mAuth.getCurrentUser();

                            // store in database
                            db.createUser(user.getUid(), "picture.jpg", name);

                            preference = getSharedPreferences("User", MODE_PRIVATE);
                            SharedPreferences.Editor editor = preference.edit();

                            // Store into the SharedPreferences
                            editor.putString("userId", user.getUid());
                            editor.commit();

                            Toast.makeText(getApplicationContext(), "Login successful!", Toast.LENGTH_LONG).show();
                            tvErrorMessage.setVisibility(View.GONE);
                            pbLoginProgress.setVisibility(View.GONE);

                            Intent i = new Intent(Register.this, Login.class);
                            finish();
                            startActivity(i);
                        } else {
                            // If sign in fails, display a message to the user.
                            Toast.makeText(Register.this, "Sign Up Unsuccessful", Toast.LENGTH_SHORT).show();
                            tvErrorMessage.setVisibility(View.VISIBLE);
                            pbLoginProgress.setVisibility(View.GONE);
                        }
                    }
                });
    }
}