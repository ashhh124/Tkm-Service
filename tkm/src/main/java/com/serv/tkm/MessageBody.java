package com.serv.tkm;

import com.google.gson.annotations.SerializedName;

public class MessageBody {
    @SerializedName("raw")
    private String rawMessage;

    public MessageBody(String rawMessage) {
        this.rawMessage = rawMessage;
    }
}
