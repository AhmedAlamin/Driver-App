package com.ship99_official.ship99_driver.Ui;

import android.app.ProgressDialog;
import android.content.Intent;
import android.os.Bundle;

import android.text.TextUtils;
import android.util.Log;
import android.view.View;
import android.widget.Button;
import android.widget.EditText;
import android.widget.Toast;

import androidx.annotation.NonNull;
import androidx.appcompat.app.AppCompatActivity;

import com.google.android.gms.tasks.OnCompleteListener;
import com.google.android.gms.tasks.Task;
import com.google.firebase.auth.AuthResult;
import com.google.firebase.auth.FirebaseAuth;
import com.google.firebase.auth.FirebaseUser;

import com.ship99_official.ship99_driver.R;


public class login extends AppCompatActivity {
    EditText Email, Password;
    Button LogInButton;
    FirebaseAuth mAuth;
    FirebaseAuth.AuthStateListener mAuthListner;
    FirebaseUser mUser;
    String email, password;
    ProgressDialog dialog;
    public static final String userEmail="";

    public static final String TAG="LOGIN";
    @Override
    protected void onCreate( Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.login);
        LogInButton = (Button) findViewById(R.id.buttonLogin);


        Email = (EditText) findViewById(R.id.editEmail);
        Password = (EditText) findViewById(R.id.editPassword);
        dialog = new ProgressDialog(this);
        mAuth = FirebaseAuth.getInstance();
        mUser = FirebaseAuth.getInstance().getCurrentUser();
        mAuthListner = new FirebaseAuth.AuthStateListener() {
            @Override
            public void onAuthStateChanged(@NonNull FirebaseAuth firebaseAuth) {
                if (mUser != null) {
                    Intent intent = new Intent(login.this, LoginPickNameAddress.class);
                    intent.addFlags(Intent.FLAG_ACTIVITY_CLEAR_TOP);
//                    intent.putExtra("notFirst","2");
                    startActivity(intent);
                    finish();
                }
                else
                {
                    Log.d(TAG,"AuthStateChanged:Logout");
                }

            }
        };
        // LogInButton.setOnClickListener((View.OnClickListener) this);
        //RegisterButton.setOnClickListener((View.OnClickListener) this);
        //Adding click listener to log in button.
        LogInButton.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {

                // Calling EditText is empty or no method.
                userSign();
            }
        });



    }

    @Override
    protected void onStart() {
        super.onStart();

        mAuth = FirebaseAuth.getInstance();
        if (mAuth.getCurrentUser() != null)
        {
            Intent i = new Intent(this,LoginPickNameAddress.class);
            i.setFlags(Intent.FLAG_ACTIVITY_NEW_TASK | Intent.FLAG_ACTIVITY_CLEAR_TASK);
            startActivity(i);
            finish();
        }
    }

    @Override
    protected void onStop() {
        super.onStop();
//        if (mAuthListner != null) {
//            mAuth.removeAuthStateListener(mAuthListner);
//        }

    }

    @Override
    public void onBackPressed() {
        login.super.finish();
    }



    private void userSign() {
        email = Email.getText().toString().trim();
        password = Password.getText().toString().trim();
        if (TextUtils.isEmpty(email)) {
            Toast.makeText(login.this, "Enter the correct Email", Toast.LENGTH_SHORT).show();
            return;
        } else if (TextUtils.isEmpty(password)) {
            Toast.makeText(login.this, "Enter the correct password", Toast.LENGTH_SHORT).show();
            return;
        }
        dialog.setMessage("Loging in please wait...");
        dialog.setIndeterminate(true);
        dialog.show();

        mAuth.signInWithEmailAndPassword(email, password).addOnCompleteListener(new OnCompleteListener<AuthResult>() {
            @Override
            public void onComplete(Task<AuthResult> task) {
                if (!task.isSuccessful()) {
                    dialog.dismiss();

                    Toast.makeText(login.this, "Login not successfull", Toast.LENGTH_SHORT).show();

                } else {
                    dialog.dismiss();

                    checkIfEmailVerified();

                }
            }
        });

    }

    //Email verification code using FirebaseUser object and using isSucccessful()function.
    private void sendEmailVerification() {
        FirebaseUser user = FirebaseAuth.getInstance().getCurrentUser();
        if (user!=null){
            user.sendEmailVerification().addOnCompleteListener(new OnCompleteListener<Void>() {
                @Override
                public void onComplete(Task<Void> task) {
                    if (task.isSuccessful()){
                        Toast.makeText(login.this,"Check your Email for verification",Toast.LENGTH_SHORT).show();
//                        FirebaseAuth.getInstance().signOut();
                    }
                }
            });
        }
    }
    //This function helps in verifying whether the email is verified or not.
    private void checkIfEmailVerified(){
        FirebaseUser users=FirebaseAuth.getInstance().getCurrentUser();

        boolean emailVerified=users.isEmailVerified();
        if(!emailVerified){
            Toast.makeText(this,"Verify the Email Id and try to login again",Toast.LENGTH_LONG).show();
            sendEmailVerification();
            mAuth.signOut();
            finish();
        }
        else {
            Email.getText().clear();

            Password.getText().clear();
            Intent intent = new Intent(login.this, LoginPickNameAddress.class);

            // Sending Email to Dashboard Activity using intent.
            intent.putExtra(userEmail,email);

            startActivity(intent);

        }
    }

    }

