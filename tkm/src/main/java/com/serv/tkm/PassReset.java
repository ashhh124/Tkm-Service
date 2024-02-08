package com.serv.tkm;

import androidx.annotation.NonNull;
import androidx.appcompat.app.AppCompatActivity;

import android.os.Bundle;
import android.view.View;
import android.widget.Button;
import android.widget.EditText;
import android.widget.Toast;

import com.google.android.gms.tasks.OnCompleteListener;
import com.google.android.gms.tasks.Task;
import com.google.firebase.auth.FirebaseAuth;
import com.serv.tkm.R;

public class PassReset extends AppCompatActivity {

    EditText mEmailres;
    Button btnres;
    FirebaseAuth mAuth=FirebaseAuth.getInstance();

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_pass_reset);

        mEmailres=findViewById(R.id.emailres);
        btnres=findViewById(R.id.btnres);

        btnres.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                String email;
                email=mEmailres.getText().toString();

                resetpass(email);
            }
        });

    }

    private void resetpass(String email) {

        mAuth.sendPasswordResetEmail(email)
                .addOnCompleteListener(new OnCompleteListener<Void>() {
                    @Override
                    public void onComplete(@NonNull Task<Void> task) {
                        if (task.isSuccessful()) {
                            // Сброс пароля успешен, отправлено письмо на электронную почту
                            Toast.makeText(PassReset.this, "Письмо со сбросом пароля отправлено на вашу почту.", Toast.LENGTH_SHORT).show();
                            // Здесь вы можете выполнить дополнительные действия после успешного сброса пароля
                        } else {
                            // Сброс пароля не удался, выводим ошибку
                            Toast.makeText(PassReset.this, "Ошибка сброса пароля: " + task.getException().getMessage(), Toast.LENGTH_SHORT).show();
                        }
                    }
                });

    }
}