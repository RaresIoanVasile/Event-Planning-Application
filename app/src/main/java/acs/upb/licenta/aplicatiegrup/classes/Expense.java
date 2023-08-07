package acs.upb.licenta.aplicatiegrup.classes;

public class Expense {
    private String expenseId;
    private String eventId;
    private String name;
    private String cost;

    public Expense(){};

    public Expense(String expenseId, String eventId, String name, String cost) {
        this.expenseId = expenseId;
        this.eventId = eventId;
        this.name = name;
        this.cost = cost;
    }

    public String getExpenseId() {
        return expenseId;
    }

    public void setExpenseId(String expenseId) {
        this.expenseId = expenseId;
    }

    public String getEventId() {
        return eventId;
    }

    public void setEventId(String eventId) {
        this.eventId = eventId;
    }

    public String getName() {
        return name;
    }

    public void setName(String name) {
        this.name = name;
    }

    public String getCost() {
        return cost;
    }

    public void setCost(String cost) {
        this.cost = cost;
    }
}
