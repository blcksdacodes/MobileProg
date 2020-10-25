package com.example.mobprogprogress;

import androidx.annotation.NonNull;
import androidx.appcompat.app.AppCompatActivity;

import android.content.Intent;
import android.os.Bundle;
import android.os.TestLooperManager;
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

public class MainActivity extends AppCompatActivity implements View.OnClickListener{

    FirebaseAuth mAuth;

    private EditText Username;
    private EditText Password;
    private Button Login;
    Button Registerr;
    private TextView Info;
    private int counter=5;
    EditText Email;
    ProgressBar ProgressBar;


    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_main);



        Password=(EditText) findViewById(R.id.password1);
        Login=(Button) findViewById(R.id.login);
        Registerr=(Button) findViewById(R.id.regis);
        Email=(EditText) findViewById(R.id.email);
        ProgressBar =(ProgressBar) findViewById(R.id.progressbar);

        mAuth=FirebaseAuth.getInstance();




        findViewById(R.id.regis).setOnClickListener(this);
        findViewById(R.id.login).setOnClickListener(this);


//        Login.setOnClickListener(new View.OnClickListener() {
//            @Override
//            public void onClick(View view) {
//
//            }
//        });

//        Registerr.setOnClickListener(new View.OnClickListener() {
//            @Override
//            public void onClick(View view) {
//                Intent intent=new Intent(MainActivity.this, RegisterPage.class);
//                startActivity(intent);
//            }
//        });



    }

    private void userlogin(){

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

        mAuth.signInWithEmailAndPassword(email, password).addOnCompleteListener(new OnCompleteListener<AuthResult>() {
            @Override
            public void onComplete(@NonNull Task<AuthResult> task) {
                if(task.isSuccessful()){
                    Intent intent= new Intent(MainActivity.this, OtherActivity.class);
                    intent.addFlags(Intent.FLAG_ACTIVITY_CLEAR_TOP);
                    startActivity(intent);
                }else{
                    Toast.makeText(getApplicationContext(), task.getException().getMessage(), Toast.LENGTH_SHORT).show();
                }
            }
        });
    }

    @Override
    public void onClick(View view) {
        switch (view.getId()){
            case R.id.regis:
                startActivity(new Intent(this, RegisterPage.class));
                break;
            case R.id.login:
                userlogin();
                break;
        }

    }
}



