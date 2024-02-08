package com.serv.tkm;

import static android.content.ContentValues.TAG;

import android.os.Bundle;
import android.text.TextUtils;
import android.util.Log;
import android.view.View;
import android.widget.Adapter;
import android.widget.AdapterView;
import android.widget.ArrayAdapter;
import android.widget.Button;
import android.widget.EditText;
import android.widget.Spinner;
import android.widget.Toast;

import androidx.annotation.NonNull;
import androidx.appcompat.app.AppCompatActivity;



import com.google.android.gms.tasks.OnFailureListener;
import com.google.android.gms.tasks.OnSuccessListener;
import com.google.firebase.firestore.FirebaseFirestore;
import com.serv.tkm.R;

import java.util.Arrays;
import java.util.HashMap;
import java.util.Map;


public class activity_email extends AppCompatActivity implements AdapterView.OnItemSelectedListener {
    //private static final String KEY_SUBJECT = "subjectkey";
    //private static final String KEY_MESSAGE = "messagekey";

    public static final String EMAIL = "tokyoto8k@gmail.com";
    public static final String PASSWORD = "zuvobkzgjioavvza";
    public EditText mEmail, mPhone, mName, mAddress, mLongtext, mSubject, mJk;
    private FirebaseFirestore db = FirebaseFirestore.getInstance();
    Button mBtnsend;
    String Spintext;


    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_email);

        //mEmail = findViewById(R.id.email);
        mPhone = findViewById(R.id.phone);
        mName = findViewById(R.id.name);
        mAddress = findViewById(R.id.address);
        mLongtext = findViewById(R.id.longtext);
        mBtnsend = findViewById(R.id.btnsend);
        mSubject = findViewById(R.id.subject);
        mJk = findViewById(R.id.jk);
        Spinner spinner = findViewById(R.id.spinner);
        ArrayAdapter<CharSequence> adapter = ArrayAdapter.createFromResource(this, R.array.spinner, android.R.layout.simple_spinner_item);
        adapter.setDropDownViewResource(android.R.layout.simple_spinner_dropdown_item);
        spinner.setAdapter(adapter);
        spinner.setOnItemSelectedListener(this);




        mBtnsend.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {

               /* String message = "ФИО: "+mName.getText().toString()+"\n\n"+"Номер телефона: "+mPhone.getText().toString()+"\n\n"+"" +
                        "Эл. почта: "+mEmail.getText().toString()+"\n\n"+"Адрес: " +mAddress.getText().toString()
                        +"\n\n"+"ЖК: " +mJk.getText().toString()+"\n\n"+ mLongtext.getText().toString();*/
               String subject = mSubject.getText().toString().trim();
                String htmlContent = "<p><b>Вид услуги:</b> " + Spintext + "</p>" +
                        "<p><b>Имя:</b> " + mName.getText().toString() + "</p>" +
                        "<p><b>Номер телефона:</b> " + mPhone.getText().toString() + "</p>" +
                        "<p><b>Адрес:</b> " + mAddress.getText().toString() + "</p>" +
                        "<p><b>ЖК:</b> " + mJk.getText().toString() + "</p>" +
                        "<p><b>Текст:</b> " + mLongtext.getText().toString() + "</p>";

                if (TextUtils.isEmpty(Spintext)){
                    Toast.makeText(activity_email.this, "Выберите вид услуги", Toast.LENGTH_SHORT).show();
                } else if (TextUtils.isEmpty((mName.getText()))){
                    Toast.makeText(activity_email.this, "Пустое поле имени.", Toast.LENGTH_SHORT).show();
                } else if (TextUtils.isEmpty(mPhone.getText())) {
                    Toast.makeText(activity_email.this, "Пустое поле номера телефона.", Toast.LENGTH_SHORT).show();
                } else if (TextUtils.isEmpty(mAddress.getText())) {
                    Toast.makeText(activity_email.this, "Пустое поле адреса.", Toast.LENGTH_SHORT).show();
                } else if (TextUtils.isEmpty(mJk.getText())) {
                    Toast.makeText(activity_email.this, "Пустое поле ЖК.", Toast.LENGTH_SHORT).show();
                //} else if (TextUtils.isEmpty((mSubject.getText()))){
                    //Toast.makeText(activity_email.this, "Пустое поле темы.", Toast.LENGTH_SHORT).show();
                } else if (TextUtils.isEmpty(mLongtext.getText())) {
                    Toast.makeText(activity_email.this, "Пустое поле текста.", Toast.LENGTH_SHORT).show();
                } else {
                    SendEmail(subject, htmlContent);
                }

            }
        });
    }

    private void SendEmail (String subject, String htmlContent){

        /*Map<String, Object> innerMap = new HashMap<>();
        innerMap.put("Name", mName.getText().toString());
        innerMap.put("Phone", mPhone.getText().toString());
        innerMap.put("Email", mEmail.getText().toString());
        innerMap.put("Address", mAddress.getText().toString());
        innerMap.put("JK", mJk.getText().toString());
        innerMap.put("Text", mLongtext.getText().toString());*/


        Map<String, Object> messageMap = new HashMap<>();
        messageMap.put("subject", Spintext);
        messageMap.put("html", htmlContent);

        // Создаем Map для передачи данных в Firestore
        Map<String, Object> data = new HashMap<>();
        data.put("to", Arrays.asList("zayavka.tkm@bk.ru")); // Пример адреса получателя
        data.put("message", messageMap);

        db.collection("mail").document("user1").set(data)
                .addOnSuccessListener(new OnSuccessListener<Void>() {
            @Override
            public void onSuccess(Void unused) {
                Toast.makeText(activity_email.this, "Заявка принята. С вами скоро свяжутся.", Toast.LENGTH_SHORT).show();
            }
        })
                .addOnFailureListener(new OnFailureListener() {
            @Override
            public void onFailure(@NonNull Exception e) {
                Toast.makeText(activity_email.this, "Произошла ошибка", Toast.LENGTH_SHORT).show();
                Log.d(TAG, e.toString());
            }
        });


        }


    @Override
    public void onItemSelected(AdapterView<?> parent, View view, int position, long id) {
        Spintext = parent.getItemAtPosition(position).toString();
    }

    @Override
    public void onNothingSelected(AdapterView<?> parent) {

    }
}


        /*Map<String, Object> note = new HashMap<>();
        note.put(KEY_SUBJECT, subject);
        note.put(KEY_MESSAGE, message);

        db.collection("mail").document("user1").set(note)
                .addOnSuccessListener(new OnSuccessListener<Void>() {
                    @Override
                    public void onSuccess(Void unused) {
                        Toast.makeText(activity_email.this, "Заявка отправлена", Toast.LENGTH_SHORT).show();
                    }
                })
                .addOnFailureListener(new OnFailureListener() {
                    @Override
                    public void onFailure(@NonNull Exception e) {
                        Toast.makeText(activity_email.this, "Произошло ошибка", Toast.LENGTH_SHORT).show();
                        Log.d(TAG, e.toString());
                    }
                });*/




        /*Intent intent = new Intent(Intent.ACTION_SENDTO);
        intent.setType("message/rfc822");
        intent.setData(Uri.parse("mailto:"));
        intent.putExtra(Intent.EXTRA_TEXT, message);
        intent.putExtra(Intent.EXTRA_EMAIL, new String[]{"lcsskz@bk.ru"});
        intent.putExtra(Intent.EXTRA_SUBJECT, subject);
        intent.addFlags(Intent.FLAG_ACTIVITY_NEW_TASK);

        if (TextUtils.isEmpty(mEmail.getText())){
            Toast.makeText(activity_email.this, "Пустое поле эл. почты", Toast.LENGTH_SHORT).show();
        } else if (TextUtils.isEmpty((mName.getText()))){
            Toast.makeText(activity_email.this, "Пустое поле имени.", Toast.LENGTH_SHORT).show();
        } else if (TextUtils.isEmpty(mPhone.getText())) {
            Toast.makeText(activity_email.this, "Пустое поле номера телефона.", Toast.LENGTH_SHORT).show();
        } else if (TextUtils.isEmpty(mAddress.getText())) {
            Toast.makeText(activity_email.this, "Пустое поле адреса.", Toast.LENGTH_SHORT).show();
        } else if (TextUtils.isEmpty(mJk.getText())) {
            Toast.makeText(activity_email.this, "Пустое поле ЖК.", Toast.LENGTH_SHORT).show();
        } else if (TextUtils.isEmpty((mSubject.getText()))){
            Toast.makeText(activity_email.this, "Пустое поле темы.", Toast.LENGTH_SHORT).show();
        } else if (TextUtils.isEmpty(mLongtext.getText())) {
            Toast.makeText(activity_email.this, "Пустое поле текста.", Toast.LENGTH_SHORT).show();
        } else {
            startActivity(Intent.createChooser(intent, "Выберите подходящее приложение.."));
        }
    }*/
