package acs.upb.licenta.aplicatiegrup.classes;

import java.util.Date;

public class Notification {
    private String message;
    private String groupId;
    private String eventId;
    private long time;
    private String uid;

    public Notification(String message, String groupId, long time, String eventId, String uid) {
        this.message = message;
        this.groupId = groupId;
        this.time = time;
        this.eventId = eventId;
        this.uid = uid;
    }

    public Notification(){};

    public long getTime() {
        return time;
    }

    public void setTime(long time) {
        this.time = time;
    }

    public String getMessage() {
        return message;
    }

    public void setMessage(String message) {
        this.message = message;
    }

    public String getGroupId() {
        return groupId;
    }

    public void setGroupId(String groupId) {
        this.groupId = groupId;
    }

    public String getEventId() {
        return eventId;
    }

    public void setEventId(String eventId) {
        this.eventId = eventId;
    }

    public String getUid() {
        return uid;
    }

    public void setUid(String uid) {
        this.uid = uid;
    }
}
