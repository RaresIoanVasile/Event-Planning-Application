package acs.upb.licenta.aplicatiegrup.classes;

public class Poll {
    private String pollName;
    private String description;
    private String eventId;
    private String options;
    private String percentages;
    private String voters;
    private String pollId;

    public Poll(String pollId, String pollName, String description, String eventId, String options, String percentages) {
        this.pollName = pollName;
        this.description = description;
        this.eventId = eventId;
        this.options = options;
        this.percentages = percentages;
        this.pollId = pollId;
        this.voters = "";
    }

    public Poll() {};

    public String getPollName() {
        return pollName;
    }

    public void setPollName(String pollName) {
        this.pollName = pollName;
    }

    public String getDescription() {
        return description;
    }

    public void setDescription(String description) {
        this.description = description;
    }

    public String getEventId() {
        return eventId;
    }

    public void setEventId(String eventId) {
        this.eventId = eventId;
    }

    public String getOptions() {
        return options;
    }

    public void setOptions(String options) {
        this.options = options;
    }

    public String getPercentages() {
        return percentages;
    }

    public void setPercentages(String percentages) {
        this.percentages = percentages;
    }

    public String getVoters() {
        return voters;
    }

    public void setVoters(String voters) {
        this.voters = voters;
    }

    public String getPollId() {
        return pollId;
    }

    public void setPollId(String pollId) {
        this.pollId = pollId;
    }
}
