package com.serv.tkm;

import androidx.appcompat.app.AppCompatActivity;

import android.content.Intent;
import android.content.SharedPreferences;
import android.graphics.Color;
import android.net.Uri;
import android.os.Bundle;
import android.text.util.Linkify;
import android.view.View;
import android.widget.Button;
import android.widget.ImageView;
import android.widget.TextView;

import com.serv.tkm.R;


public class MainActivity extends AppCompatActivity {

    Button mbutton2, mbutton3,mbutton1, mbutton4;


    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_main);

        TextView phonecall = findViewById(R.id.phonenumber);
        String phonenumber1 = "+77055253108";

        phonecall.setText(phonenumber1);
        phonecall.setTextColor(Color.BLUE);

        Linkify.addLinks(phonecall, Linkify.PHONE_NUMBERS);
        mbutton1=findViewById(R.id.button1);
        mbutton3=findViewById(R.id.button3);
        mbutton3.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                String videoUrl = "https://www.youtube.com/watch?v=CfY5UBq1UMY";

                Intent intent = new Intent(Intent.ACTION_VIEW, Uri.parse(videoUrl));
                startActivity(intent);
            }
        });

        mbutton4=findViewById(R.id.button4);
        mbutton4.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                String url = "https://tkm-service.kz"; // Замените на нужный URL
                Intent intent = new Intent(Intent.ACTION_VIEW, Uri.parse(url));
                startActivity(intent);
            }
        });

        phonecall.setOnClickListener(view -> {
            // Выполняем звонок по нажатию на номер телефона
            Intent intent = new Intent(Intent.ACTION_DIAL);
            intent.setData(Uri.parse("tel:" + phonenumber1));
            startActivity(intent);
        });

        mbutton2 = findViewById(R.id.button2);

        mbutton2.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                Intent intent = new Intent(MainActivity.this, activity_email.class);
                startActivity(intent);
            }
        });

        mbutton1.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                Intent intent = new Intent(Intent.ACTION_SENDTO);
                intent.setData(Uri.parse("mailto:")); // Устанавливаете URI для почтового интента
                intent.putExtra(Intent.EXTRA_EMAIL, new String[]{"zayavka.tkm@bk.ru"});
                if (intent.resolveActivity(getPackageManager()) != null) {
                    startActivity(intent);
                }
                //openInstagramProfile();
            }
        });

        ImageView mExit = findViewById(R.id.exit);
        mExit.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                SharedPreferences sharedPreferences = getSharedPreferences(Login.SHARED_PREFS, MODE_PRIVATE);
                SharedPreferences.Editor editor = sharedPreferences.edit();
                editor.putString("uid", "");
                editor.apply();

                // Переходим на экран входа
                Intent intent = new Intent(MainActivity.this, Login.class);
                startActivity(intent);
                finish();
            }
        });

    }

    /*private void openInstagramProfile() {
        String instagramUsername = "lcsskz"; // Замените на свой Instagram-ник
        Uri uri = Uri.parse("http://instagram.com/" + instagramUsername);

        Intent intent = new Intent(Intent.ACTION_VIEW, uri);

        // Проверяем, есть ли приложение Instagram на устройстве
        if (intent.resolveActivity(getPackageManager()) != null) {
            startActivity(intent);
        } else {
            // Если Instagram не установлен, открываем ссылку в браузере
            Intent browserIntent = new Intent(Intent.ACTION_VIEW, uri);
            startActivity(browserIntent);
        }
    }*/
}