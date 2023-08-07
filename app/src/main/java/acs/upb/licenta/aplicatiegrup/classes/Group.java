package acs.upb.licenta.aplicatiegrup.classes;

public class Group {
    private String name;
    private String admin;
    private String code;
    private String members;

    public Group(){};

    public Group(String name, String admin, String code, String members) {
        this.name = name;
        this.admin = admin;
        this.code = code;
        this.members = members;
    }

    public String getName() {
        return name;
    }

    public void setName(String name) {
        this.name = name;
    }

    public String getAdmin() {
        return admin;
    }

    public void setAdmin(String admin) {
        this.admin = admin;
    }

    public String getCode() {
        return code;
    }

    public void setCode(String code) {
        this.code = code;
    }

    public String getMembers() {
        return members;
    }

    public void setMembers(String members) {
        this.members = members;
    }
}
