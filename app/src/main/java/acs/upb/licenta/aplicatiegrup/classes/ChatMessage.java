package acs.upb.licenta.aplicatiegrup.classes;

import java.util.Date;

public class ChatMessage {
    private String eventId;
    private String messageText;
    private String messageUser;
    private long messageTime;

    public ChatMessage(String eventId, String messageText, String messageUser) {
        this.eventId = eventId;
        this.messageText = messageText;
        this.messageUser = messageUser;
        messageTime = new Date().getTime();
    }
    public ChatMessage(){
    }

    public String getEventId() {
        return eventId;
    }

    public void setEventId(String eventId) {
        this.eventId = eventId;
    }
    public String getMessageText() {
        return messageText;
    }
    public void setMessageText(String messageText) {
        this.messageText = messageText;
    }
    public String getMessageUser() {
        return messageUser;
    }
    public void setMessageUser(String messageUser) {
        this.messageUser = messageUser;
    }
    public long getMessageTime() {
        return messageTime;
    }
    public void setMessageTime(long messageTime) {
        this.messageTime = messageTime;
    }
}