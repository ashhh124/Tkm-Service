package com.serv.tkm;

import android.app.ProgressDialog;
import android.content.Context;
import android.os.AsyncTask;

import java.util.Properties;
import javax.mail.Message;
import javax.mail.MessagingException;
import javax.mail.Session;
import javax.mail.Transport;
import javax.mail.internet.AddressException;
import javax.mail.internet.InternetAddress;
import javax.mail.internet.MimeMessage;

public class JavaMailAPI extends AsyncTask<Void,Void,Void>  {
    String mailhost = "smtp.gmail.com";
    private Context mContext;
    //private Session mSession;
    String mailto = "worldofminecraft856@gmail.com";
    private String mEmail, mPhone, mName, mAddress, mLongtext;

    private ProgressDialog mProgressDialog;

    //Constructor
    public JavaMailAPI(Context mContext, String mEmail, String mName,  String mLongtext) {
        this.mContext = mContext;
        this.mEmail = mEmail;
        this.mName = mName;
        this.mLongtext = mLongtext;
    }


    @Override
    protected Void doInBackground(Void... params) {

        Properties props = new Properties();
        props.put("mail.smtp.user", Utils.EMAIL);
        props.put("mail.smtp.host", "smtp.gmail.com");
        props.put("mail.smtp.port", "465");
        props.put("mail.smtp.starttls.enable","true");
        props.put("mail.smtp.debug", "true");
        props.put("mail.smtp.auth", "true");
        props.put("mail.smtp.socketFactory.port", "465");
        props.put("mail.smtp.socketFactory.class", "javax.net.ssl.SSLSocketFactory");
        props.put("mail.smtp.socketFactory.fallback", "false");
        props.put("mail.smtp.ssl.enable", "true");


        Session session = Session.getInstance(props);
        session.setDebug(true);

        //Creating a new session
        /*mSession = Session.getDefaultInstance(props,
                new javax.mail.Authenticator() {
                    //Authenticating the password
                    protected PasswordAuthentication getPasswordAuthentication() {
                        return new PasswordAuthentication(Utils.EMAIL, Utils.PASSWORD);
                    }
                });*/

        MimeMessage mm = new MimeMessage(session);

        try {
            mm.setFrom(new InternetAddress(Utils.EMAIL));
            mm.addRecipient(Message.RecipientType.TO, new InternetAddress(mEmail));

            mm.setSubject(mName);

            //mm.setText(mName);
            //mm.setText(mPhone);
            //mm.setText(mAddress);
            mm.setText(mLongtext);

            Transport t = session.getTransport("smtps");
            t.connect("smtp.gmail.com", 465, mName, Utils.PASSWORD);
            t.sendMessage(mm, mm.getAllRecipients());
            t.close();

        } catch (AddressException e) {
            e.printStackTrace();

        } catch (MessagingException e) {
            e.printStackTrace();

        }

        return null;
    }
}