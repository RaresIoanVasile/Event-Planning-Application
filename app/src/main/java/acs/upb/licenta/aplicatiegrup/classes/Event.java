package acs.upb.licenta.aplicatiegrup.classes;

public class Event {
    private String date;
    private String hour;
    private String name;
    private String group;
    private String groupId;
    private String owner;
    private String location;
    private String notAttending;
    private String shoppingList;
    private String checkedList;
    private String eventId;

    public Event(){};

    public Event(String date, String name, String group, String owner, String location, String groupId, String eventId, String hour) {
        this.date = date;
        this.name = name;
        this.group = group;
        this.owner = owner;
        this.location = location;
        this.groupId = groupId;
        this.eventId = eventId;
        this.hour = hour;
        this.shoppingList = "";
        this.checkedList = "";
        this.notAttending = "";
    }

    public String getDate() {
        return date;
    }

    public void setDate(String date) {
        this.date = date;
    }

    public String getHour() {
        return hour;
    }

    public void setHour(String hour) {
        this.hour = hour;
    }


    public String getName() {
        return name;
    }

    public void setName(String name) {
        this.name = name;
    }

    public String getGroup() {
        return group;
    }

    public void setGroup(String group) {
        this.group = group;
    }

    public String getGroupId() {
        return groupId;
    }

    public void setGroupId(String groupId) {
        this.groupId = groupId;
    }

    public String getOwner() {
        return owner;
    }

    public void setOwner(String owner) {
        this.owner = owner;
    }

    public String getLocation() {
        return location;
    }

    public void setLocation(String location) {
        this.location = location;
    }

    public String getShoppingList() {
        return shoppingList;
    }

    public void setShoppingList(String shoppingList) {
        this.shoppingList = shoppingList;
    }

    public String getCheckedList() {
        return checkedList;
    }

    public void setCheckedList(String checkedList) {
        this.checkedList = checkedList;
    }

    public String getEventId() {
        return eventId;
    }

    public void setEventId(String eventId) {
        this.eventId = eventId;
    }

    public String getNotAttending() {
        return notAttending;
    }

    public void setNotAttending(String notAttending) {
        this.notAttending = notAttending;
    }

}
