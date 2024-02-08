package com.serv.tkm;

import androidx.annotation.NonNull;
import androidx.appcompat.app.AppCompatActivity;

import android.content.Intent;
import android.content.SharedPreferences;
import android.os.Bundle;
import android.view.View;
import android.widget.Button;
import android.widget.EditText;
import android.widget.Toast;

import com.google.android.gms.tasks.OnCompleteListener;
import com.google.android.gms.tasks.Task;
import com.google.firebase.auth.AuthResult;
import com.google.firebase.auth.FirebaseAuth;
import com.google.firebase.auth.FirebaseUser;
import com.serv.tkm.R;

public class Register extends AppCompatActivity {

    EditText mEmailreg, mPassreg;
    Button mBtnsend3, mBtnsend5;

    private FirebaseAuth firebaseAuth;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_register);
        firebaseAuth = FirebaseAuth.getInstance();
        mEmailreg = findViewById(R.id.emailreg);
        mPassreg = findViewById(R.id.passreg);
        mBtnsend3 = findViewById(R.id.btnsend3);
        mBtnsend5 = findViewById(R.id.btnsend5);

        mBtnsend3.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                String email = mEmailreg.getText().toString();
                String password = mPassreg.getText().toString();

                registerUserWithEmail(email, password);
            }
        });

        mBtnsend5.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                Intent intent = new Intent(Register.this, Login.class);
                startActivity(intent);
            }
        });
    }

    private void registerUserWithEmail(String email, String password) {
        firebaseAuth.createUserWithEmailAndPassword(email, password)
                .addOnCompleteListener(this, new OnCompleteListener<AuthResult>() {
                    @Override
                    public void onComplete(@NonNull Task<AuthResult> task) {
                        if (task.isSuccessful()) {
                            // Регистрация успешна, пользователь зарегистрирован

                            FirebaseUser user = FirebaseAuth.getInstance().getCurrentUser();
                            if (user != null) {
                                String uid = user.getUid();

                                SharedPreferences sharedPreferences = getSharedPreferences("user_data", MODE_PRIVATE);
                                SharedPreferences.Editor editor = sharedPreferences.edit();
                                editor.putString("uid", uid);
                                editor.apply();

                            Toast.makeText(Register.this, "Регистрация успешна.", Toast.LENGTH_SHORT).show();
                            Intent intent = new Intent(Register.this, MainActivity.class);
                            startActivity(intent);
                            // Здесь вы можете перейти на следующий экран вашего приложения
                        } else {
                            // Регистрация не удалась, выводим ошибку
                            Toast.makeText(Register.this, "Ошибка регистрации: " + task.getException().getMessage(), Toast.LENGTH_SHORT).show();
                        }
                    }
                };
    });
}
    }