package com.serv.tkm;


import retrofit2.Call;
import retrofit2.http.Body;
import retrofit2.http.Header;
import retrofit2.http.POST;

public interface GmailAPIService {
    @POST("gmail/v1/users/me/messages/send")
    Call<Void> sendMessage(@Header("Authorization") String authorization, @Body MessageBody messageBody);
}
