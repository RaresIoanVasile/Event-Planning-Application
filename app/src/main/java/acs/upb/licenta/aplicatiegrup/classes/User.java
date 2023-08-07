package acs.upb.licenta.aplicatiegrup.classes;

public class User {
    private String name;
    private String email;
    private String phone;
    private String birthdate;
    private String skills;
    private String hobbies;
    private String uid;
    private String kid;

    public User(){};

    public User(String email, String name, String phone, String birthdate, String uid, String kid) {
        this.email = email;
        this.name = name;
        this.phone = phone;
        this.birthdate = birthdate;
        this.skills = null;
        this.hobbies = null;
        this.uid = uid;
        this.kid = kid;
    }

    public String getName() {
        return name;
    }

    public void setName(String name) {
        this.name = name;
    }

    public String getEmail() {
        return email;
    }

    public void setEmail(String email) {
        this.email = email;
    }

    public String getPhone() {
        return phone;
    }

    public void setPhone(String phone) {
        this.phone = phone;
    }

    public String getBirthdate() {
        return birthdate;
    }

    public void setBirthdate(String birthdate) {
        this.birthdate = birthdate;
    }

    public String getSkills() {
        return skills;
    }

    public void setSkills(String skills) {
        this.skills = skills;
    }

    public String getHobbies() {
        return hobbies;
    }

    public void setHobbies(String hobbies) {
        this.hobbies = hobbies;
    }

    public String getUid() {
        return uid;
    }

    public void setUid(String uid) {
        this.uid = uid;
    }

    public String getKid() {
        return kid;
    }

    public void setKid(String kid) {
        this.kid = kid;
    }
}
