package com.mgi.kelasku;

import android.content.Intent;
import android.support.v7.app.AppCompatActivity;
import android.os.Bundle;
import android.view.MotionEvent;
import android.view.View;
import android.widget.Button;
import android.widget.EditText;
import android.widget.Toast;

import com.google.android.gms.tasks.OnCompleteListener;
import com.google.android.gms.tasks.Task;
import com.google.firebase.auth.AuthResult;
import com.google.firebase.auth.FirebaseAuth;
import com.google.firebase.auth.FirebaseUser;
import com.google.firebase.database.DatabaseReference;
import com.google.firebase.database.FirebaseDatabase;

public class RegisterActivity extends AppCompatActivity {

    Button btnregister,btnback;
    EditText etemail,etpassword;
    FirebaseAuth firebaseAuth;
    DatabaseReference mDatabase;
    String Email,Password;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_register);

        firebaseAuth = FirebaseAuth.getInstance();
        mDatabase = FirebaseDatabase.getInstance().getReference();

        etemail = (EditText) findViewById(R.id.ETEmail);
        etpassword = (EditText) findViewById(R.id.ETPassword);


        btnregister=(Button)findViewById(R.id.btnregister);
        btnregister.setClickable(true);
        btnregister.setOnTouchListener(new View.OnTouchListener() {
            @Override
            public boolean onTouch(View view, MotionEvent motionEvent) {
                if(motionEvent.getAction()==MotionEvent.ACTION_UP){
                    registeruser();
                }
                return false;
            }
        });

        btnback=(Button)findViewById(R.id.btnback);
        btnback.setClickable(true);
        btnback.setOnTouchListener(new View.OnTouchListener() {
            @Override
            public boolean onTouch(View view, MotionEvent motionEvent) {
                if(motionEvent.getAction()==MotionEvent.ACTION_UP){
                    startActivity(new Intent(getApplicationContext(),LoginActivity.class));
                    finish();
                }
                return false;
            }
        });

    }

    public void registeruser(){
        Email = etemail.getText().toString().trim();
        Password = etpassword.getText().toString().trim();
        //Toast.makeText(RegisterActivity.this,"Email : " + Email + " Password : " + Password,Toast.LENGTH_LONG).show();

        firebaseAuth.createUserWithEmailAndPassword(Email,Password)
                .addOnCompleteListener(this, new OnCompleteListener<AuthResult>() {
                    @Override
                    public void onComplete(Task<AuthResult> task) {
                        if(task.isSuccessful()){
                            Toast.makeText(RegisterActivity.this,"User berhasil registrasi",Toast.LENGTH_LONG).show();
                            onAuthSuccess(task.getResult().getUser());
                            //return;
                        }else{
                            Toast.makeText(RegisterActivity.this,"User gagal registrasi",Toast.LENGTH_LONG).show();
                        }
                    }
                });


    }

    public void onAuthSuccess(FirebaseUser user){
        System.out.println("User Id:"+user.getUid());
        PojoRegister pojoPost = new PojoRegister(Email,Password);

        mDatabase.child("users").child(user.getUid()).setValue(pojoPost);
        Toast.makeText(RegisterActivity.this,"User terdaftar di db",Toast.LENGTH_LONG).show();
    }
}
