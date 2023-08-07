package acs.upb.licenta.aplicatiegrup.classes;

public class Task {
    private String taskId;
    private String eventId;
    private String taskName;
    private String assigned;
    private String checked;
    private String uid;
    long time;

    public Task(){};

    public Task(String taskId, String eventId, String taskName, String assigned, String checked, String uid, long time) {
        this.taskId = taskId;
        this.eventId = eventId;
        this.taskName = taskName;
        this.assigned = assigned;
        this.checked = checked;
        this.uid = uid;
        this.time = time;
    }

    public String getTaskId() {
        return taskId;
    }

    public void setTaskId(String taskId) {
        this.taskId = taskId;
    }

    public String getEventId() {
        return eventId;
    }

    public void setEventId(String eventId) {
        this.eventId = eventId;
    }

    public String getTaskName() {
        return taskName;
    }

    public void setTaskName(String taskName) {
        this.taskName = taskName;
    }

    public String getAssigned() {
        return assigned;
    }

    public void setAssigned(String assigned) {
        this.assigned = assigned;
    }

    public String getChecked() {
        return checked;
    }

    public void setChecked(String checked) {
        this.checked = checked;
    }

    public String getUid() {
        return uid;
    }

    public void setUid(String uid) {
        this.uid = uid;
    }

    public long getTime() {
        return time;
    }

    public void setTime(long time) {
        this.time = time;
    }
}
