package com.mgi.kelasku;

import android.content.Intent;
import android.support.v7.app.AppCompatActivity;
import android.os.Bundle;
import android.support.v7.widget.Toolbar;
import android.text.TextUtils;
import android.view.MotionEvent;
import android.view.View;
import android.widget.Button;
import android.widget.EditText;
import android.widget.Toast;

import com.google.android.gms.tasks.OnCompleteListener;
import com.google.android.gms.tasks.Task;
import com.google.firebase.auth.AuthResult;
import com.google.firebase.auth.FirebaseAuth;
import com.google.firebase.database.DatabaseReference;
import com.google.firebase.database.FirebaseDatabase;
import com.mgi.kelasku.utility.Utility;

public class LoginActivity extends AppCompatActivity {

    Button btnLogin,btnregister,btnforgotpass;
    EditText etemail,etpassword;
    String Email,Password;
    FirebaseAuth firebaseAuth;
    DatabaseReference mDatabase;
    Utility util=new Utility();

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_login);
        Toolbar toolbar = (Toolbar) findViewById(R.id.toolbar);
        setSupportActionBar(toolbar);

        firebaseAuth = FirebaseAuth.getInstance();
        mDatabase = FirebaseDatabase.getInstance().getReference();


        etemail = (EditText) findViewById(R.id.ETEmail);
        etpassword = (EditText) findViewById(R.id.ETPassword);

        btnLogin=(Button)findViewById(R.id.btnlogin);
        btnLogin.setClickable(true);
        btnLogin.setOnTouchListener(new View.OnTouchListener() {
            @Override
            public boolean onTouch(View view, MotionEvent motionEvent) {
                if(motionEvent.getAction()==MotionEvent.ACTION_UP){
                    loginuser();
                }
                return false;
            }
        });

        btnregister=(Button)findViewById(R.id.btnloginregister);
        btnregister.setClickable(true);
        btnregister.setOnTouchListener(new View.OnTouchListener() {
            @Override
            public boolean onTouch(View view, MotionEvent motionEvent) {
                if(motionEvent.getAction()==MotionEvent.ACTION_UP){
                    startActivity(new Intent(LoginActivity.this,RegisterActivity.class));
                    finish();
                }
                return false;
            }
        });

        btnforgotpass=(Button)findViewById(R.id.btnloginrecover);
        btnforgotpass.setClickable(true);
        btnforgotpass.setOnTouchListener(new View.OnTouchListener() {
            @Override
            public boolean onTouch(View view, MotionEvent motionEvent) {
                if(motionEvent.getAction()==MotionEvent.ACTION_UP){
                    startActivity(new Intent(getApplicationContext(),RecoveryActivity.class));
                    finish();
                }
                return false;
            }
        });
    }

    public void loginuser(){
        //progressDialog.setMessage("Silahkan tunggu sejenak ..");
        //progressDialog.show();

        Email = etemail.getText().toString().trim();
        Password = etpassword.getText().toString().trim();

        /*boolean cancel = false;
        View focusView = null;

        if (!TextUtils.isEmpty(Password) && !isPasswordValid(Password)) {
            etemail.setError(getString(R.string.error_invalid_password));
            focusView = etemail;
            cancel = true;
        }

        // Check for a valid email address.
        if (TextUtils.isEmpty(Email)) {
            etemail.setError(getString(R.string.error_field_required));
            focusView = etemail;
            cancel = true;
        } else if (!isEmailValid(Email)) {
            etemail.setError(getString(R.string.error_invalid_email));
            focusView = etemail;
            cancel = true;
        }



*/


        firebaseAuth.signInWithEmailAndPassword(Email,Password)
                .addOnCompleteListener(this, new OnCompleteListener<AuthResult>() {
                    @Override
                    public void onComplete(Task<AuthResult> task) {
                        if(task.isSuccessful()){
                            Toast.makeText(LoginActivity.this,"User berhasil login",Toast.LENGTH_LONG).show();
                            util.setEmail(LoginActivity.this,Email);
                            startActivity(new Intent(getApplicationContext(),HomeActivity.class));
                            finish();
                            //return;
                        }else{
                            Toast.makeText(LoginActivity.this,"User gagal login",Toast.LENGTH_LONG).show();
                        }
                    }
                });
    }

   /* private boolean isEmailValid(String email) {
        //TODO: Replace this with your own logic
        return email.contains("@");
    }

    private boolean isPasswordValid(String password) {
        //TODO: Replace this with your own logic
        return password.length() > 6;
    }*/


}
