package com.serv.tkm;

import static android.content.ContentValues.TAG;

import android.content.Intent;
import android.content.SharedPreferences;
import android.os.Bundle;

import androidx.appcompat.app.AppCompatActivity;

import android.text.TextUtils;
import android.util.Log;
import android.view.View;
import android.widget.Button;
import android.widget.EditText;
import android.widget.TextView;
import android.widget.Toast;
import androidx.annotation.NonNull;


import com.google.android.gms.tasks.OnCompleteListener;
import com.google.android.gms.tasks.Task;
import com.google.firebase.auth.AuthResult;
import com.google.firebase.auth.FirebaseAuth;
import com.serv.tkm.R;

public class Login extends AppCompatActivity {
    EditText mEmailauth, mPassauth;
    Button mBtnsend2, mBtnsend4;
    FirebaseAuth mAuth;

    TextView reset;
    public static final String SHARED_PREFS = "sharedPrefs";


    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_login);
        mAuth = FirebaseAuth.getInstance();
        mEmailauth = findViewById(R.id.emailauth);
        mPassauth = findViewById(R.id.passauth);
        mBtnsend2 = findViewById(R.id.btnsend2);
        mBtnsend4 = findViewById(R.id.btnsend4);
        reset = findViewById(R.id.textView2);


        
        checkBox();

        reset.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                Intent intent = new Intent(Login.this, PassReset.class);
                startActivity(intent);
            }
        });

        mBtnsend2.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                String email, password;
                email = mEmailauth.getText().toString();
                password = mPassauth.getText().toString();

                if (TextUtils.isEmpty(email)) {
                    Toast.makeText(Login.this, "Пустое поле эл. почты.", Toast.LENGTH_SHORT).show();
                    return;
                }
                if (TextUtils.isEmpty(password)) {
                    Toast.makeText(Login.this, "Пустое поле пароля.", Toast.LENGTH_SHORT).show();
                    return;
                }

                signwithEmail(email, password);
            }
        });

        mBtnsend4.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                Intent intent = new Intent(Login.this, Register.class);
                startActivity(intent);
            }
        });
    }

    private void checkBox() {
        SharedPreferences sharedPreferences = getSharedPreferences(SHARED_PREFS, MODE_PRIVATE);
        String uid = sharedPreferences.getString("uid", "");

        if (uid.equals("true")) {
            // Пользователь уже авторизован, перейдите на главный экран приложения
            Toast.makeText(Login.this, "Вход выполнен успешно.", Toast.LENGTH_SHORT).show();
            Intent mainActivityIntent = new Intent(Login.this, MainActivity.class);
            startActivity(mainActivityIntent);
        }
    }


    private void signwithEmail(String email, String password) {
        mAuth.signInWithEmailAndPassword(email, password)
                .addOnCompleteListener(this, new OnCompleteListener<AuthResult>() {
                    @Override
                    public void onComplete(@NonNull Task<AuthResult> task) {
                        if (task.isSuccessful()) {
                            // Аутентификация успешна, пользователь вошел

                                SharedPreferences sharedPreferences = getSharedPreferences(SHARED_PREFS, MODE_PRIVATE);
                                SharedPreferences.Editor editor = sharedPreferences.edit();
                                editor.putString("uid", "true");
                                editor.apply();

                                Toast.makeText(Login.this, "Вход выполнен успешно.", Toast.LENGTH_SHORT).show();
                                Intent mainActivityIntent = new Intent(Login.this, MainActivity.class);
                                startActivity(mainActivityIntent);
                                // Здесь вы можете перейти на следующий экран вашего приложения
                            } else {
                                // Аутентификация не удалась, выводим ошибку
                                Log.e(TAG, "signInWithEmail:failure", task.getException());
                                Toast.makeText(Login.this, "Ошибка входа: " + task.getException().getMessage(), Toast.LENGTH_SHORT).show();
                            }
                        }
                    });

                }
    }
