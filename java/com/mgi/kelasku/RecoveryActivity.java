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
import com.google.firebase.auth.FirebaseAuth;

public class RecoveryActivity extends AppCompatActivity {

    EditText etmail;
    String Email;
    Button btnforgotpass, btnback;
    FirebaseAuth firebaseAuth;



    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_recovery);

        firebaseAuth = FirebaseAuth.getInstance();

        etmail = (EditText) findViewById(R.id.ETEmail);

        btnforgotpass = (Button)findViewById(R.id.btnlostpass);
        btnforgotpass.setClickable(true);
        btnforgotpass.setOnTouchListener(new View.OnTouchListener() {
            @Override
            public boolean onTouch(View view, MotionEvent motionEvent) {
                if(motionEvent.getAction()==MotionEvent.ACTION_UP){
                    forgotpass();
                }
                return false;
            }
        });

        btnback = (Button)findViewById(R.id.btnback);
        btnback.setClickable(true);
        btnback.setOnTouchListener(new View.OnTouchListener() {
            @Override
            public boolean onTouch(View view, MotionEvent motionEvent) {
                if(motionEvent.getAction()==MotionEvent.ACTION_UP){
                  startActivity(new Intent(RecoveryActivity.this,LoginActivity.class));
                }
                return false;
            }
        });
    }

    public void forgotpass(){
        String Email = etmail.getText().toString();

        firebaseAuth.sendPasswordResetEmail(Email).addOnCompleteListener(new OnCompleteListener<Void>() {
            @Override
            public void onComplete(Task<Void> task) {
                if(task.isSuccessful()){
                    Toast.makeText(getApplicationContext(),"Silahkan Cek email Anda ..",Toast.LENGTH_LONG);
                }else{
                    Toast.makeText(getApplicationContext(),"Reset Password Gagal ..",Toast.LENGTH_LONG);
                }
            }
        });

    }
}
