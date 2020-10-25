package com.example.mobprogprogress;

import androidx.annotation.NonNull;
import androidx.appcompat.app.AppCompatActivity;

import android.content.Intent;
import android.os.Bundle;
import android.util.Patterns;
import android.view.View;
import android.widget.Button;
import android.widget.EditText;
import android.widget.ProgressBar;
import android.widget.TextView;
import android.widget.Toast;

import com.google.android.gms.tasks.OnCompleteListener;
import com.google.android.gms.tasks.Task;
import com.google.firebase.auth.AuthResult;
import com.google.firebase.auth.FirebaseAuth;
import com.google.firebase.auth.FirebaseAuthUserCollisionException;

public class RegisterPage extends AppCompatActivity implements View.OnClickListener{

     EditText Email;
     EditText Password;
     ProgressBar ProgressBar;
     TextView LogLog;
     EditText Username;
     Button Register;


    private FirebaseAuth mAuth;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_register_page);


        Password=(EditText) findViewById(R.id.password);
        Email=(EditText) findViewById(R.id.email);
        ProgressBar =(ProgressBar) findViewById(R.id.progressbar);
        LogLog=(TextView) findViewById(R.id.Loginhere);


        mAuth = FirebaseAuth.getInstance();

        findViewById(R.id.registerbtn).setOnClickListener(this);
        //findViewById(R.id.login).setOnClickListener(this);
        findViewById(R.id.Loginhere).setOnClickListener(this);




    }

    private void signupuser(){
        String email = Email.getText().toString().trim();
        String password = Password.getText().toString().trim();

        if(email.isEmpty()){
            Email.setError("E-mail is required!");
            Email.requestFocus();
            return;
        }

        if(!Patterns.EMAIL_ADDRESS.matcher(email).matches()){
            Email.setError("Enter a valid E-mail!");
            Email.requestFocus();
            return;

        }

        if(password.isEmpty()){
            Password.setError("Password should be filled!");
            Password.requestFocus();
            return;
        }

        if(password.length()<6){
            Password.setError("Password should be 6 characters or above");
            Password.requestFocus();
            return;
        }

        ProgressBar.setVisibility(View.VISIBLE);

        mAuth.createUserWithEmailAndPassword(email, password).addOnCompleteListener(new OnCompleteListener<AuthResult>() {
            @Override
            public void onComplete(@NonNull Task<AuthResult> task) {
                ProgressBar.setVisibility(View.GONE);
                if(task.isSuccessful()){
                    Toast.makeText(getApplicationContext(), "Registered!", Toast.LENGTH_SHORT).show();

                }
                else{
                    if(task.getException() instanceof FirebaseAuthUserCollisionException){
                        Toast.makeText(getApplicationContext(), "You are already registered", Toast.LENGTH_SHORT).show();
                    }
                    else{
                        Toast.makeText(getApplicationContext(), task.getException().getMessage(),Toast.LENGTH_SHORT).show();
                    }
                }
            }
        });


    }

    @Override
    public void onClick(View view) {
        switch (view.getId()){
            case R.id.registerbtn:
                signupuser();
                break;

            case R.id.Loginhere:
                startActivity(new Intent(this, MainActivity.class));
                break;
        }
    }
}