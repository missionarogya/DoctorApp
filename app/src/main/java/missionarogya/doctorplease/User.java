package missionarogya.doctorplease;

/**
 * Created by Sonali Sinha on 11/19/2015.
 */
public class User {

    private static User ourInstance = new User();
    private String type;
    private String phoneNumber;
    private String email;
    private String name;

    private User(){
    }

    public static User getUserObject(){
        return ourInstance;
    }

    public static void setUserObject(User user){
        User.ourInstance = user;
    }

    public String getType() {
        return type;
    }

    public void setType(String type) {
        this.type = type;
    }

    public String getPhoneNumber() {
        return phoneNumber;
    }

    public void setPhoneNumber(String phoneNumber) {
        this.phoneNumber = phoneNumber;
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
}
