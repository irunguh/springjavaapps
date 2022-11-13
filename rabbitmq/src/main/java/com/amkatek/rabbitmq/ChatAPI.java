package com.amkatek.rabbitmq;
import com.fasterxml.jackson.annotation.JsonIgnoreProperties;

@JsonIgnoreProperties(ignoreUnknown = true)
public class ChatAPI {
    private String chatId;
    private String phone;
    private String message;

    public String getChatId(){
        return chatId;
    }
    public void setChatId(String chatId){
        this.chatId = chatId ;
    }
    //
    public String getPhone(){
        return phone ;
    }
    public void setPhone(String phone){
        this.phone = phone ;
    }
    //
    public String getMessage(){
        return message;
    }
    public void setMessage(String message){
        this.message = message ;
    }
}
