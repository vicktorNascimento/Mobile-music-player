package com.example.trabajov3;

import androidx.appcompat.app.AppCompatActivity;

import android.annotation.SuppressLint;
import android.content.Intent;
import android.os.Bundle;
import android.os.Handler;
import android.view.View;
import android.widget.Button;
import android.widget.EditText;

public class LoginActivity extends AppCompatActivity {
    protected String user;
    protected String password;

    @SuppressLint("MissingInflatedId")
    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_login);

        EditText etx1,etx2;
        Button btn;

        etx1= (EditText)findViewById(R.id.editTextTextUser);
        etx2= (EditText)findViewById(R.id.editTextTextPassword);
        btn= (Button)findViewById(R.id.button);

        btn.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                String usuario = etx1.getText().toString();
                String contrasena = etx2.getText().toString();

                if (usuario.equals("admin") && contrasena.equals("1234")) {

                    new Handler().postDelayed(new Runnable() {
                        @Override
                        public void run() {
                            finish();
                        }
                    },2000);
                }else{
                    Intent i = new Intent(LoginActivity.this, ErrorActivity.class);
                    startActivity(i);
                }
            }

    });
}}