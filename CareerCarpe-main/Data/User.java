package Data;
/**
 * sets up the users for the program
 */
public class User extends Datum implements Tableable {
    
    private String email;
    private String phoneNumber;
    private String password;
    private String city;
    private String state;
    /**
     * sets up the information of the user
     * @param email User's email
     * @param phoneNumber User's phone #
     * @param password User's password
     * @param city User's city
     * @param state User's state
     */
    public User(String email, String phoneNumber, String password, String city, String state) {
        this("undefined", email, phoneNumber, password, city, state);
    }

    public User(String uuid, String email, String phoneNumber, String password, String city, String state) {
        super(uuid);
        this.email = email;
        this.phoneNumber = phoneNumber;
        this.password = password;
        this.city = city;
        this.state = state;
    }
    /**
     * gets email of user
     * @return User's email
     */
    public String getEmail() {
        return this.email;
    }
    /**
     * sets email of user
     * @param email
     */
    public void setEmail(String email) {
        this.email = email;
    }
    /**
     * gets phone # of user
     * @return phone #
     */
    public String getPhoneNumber() {
        return this.phoneNumber;
    }
    /**
     * sets phone # of user
     * @param phoneNumber
     */
    public void setPhoneNumber(String phoneNumber) {
        this.phoneNumber = phoneNumber;
    }
    /**
     * sets password of user
     * @param password
     */
    public void setPassword(String password) {
        this.password = password;
    }
    /**
     * gets password of user
     * @return password
     */
    public String getPassword() {
        return this.password;
    }
    /**
     * gets city of user
     * @return city
     */
    public String getCity() {
        return this.city;
    }
    /**
     * sets city of user
     * @param city
     */
    public void setCity(String city) {
        this.city = city;
    }
    /**
     * get state of user
     * @return state
     */
    public String getState() {
        return this.state;
    }
    /**
     * sets state of user
     * @param state
     */
    public void setState(String state) {
        this.state = state;
        }
    /**
     * how the user will be displayed
     */
    public String toString() {
        String returnValue = "";
        returnValue += "UUID: " + this.getUuid();
        returnValue += "\nEmail: " + this.email;
        returnValue += "\nPhone Number: " + this.phoneNumber;
        returnValue += "\nCity: " + this.city;
        returnValue += "\nState: " + this.state;
        return returnValue;
    }
    /**
     * how user will be displayed with uuid
     * @return
     */
    public String toStringNoUUID() {
        String returnValue = "";
        returnValue += "Email: " + this.email;
        returnValue += "\nPhone Number: " + this.phoneNumber;
        returnValue += "\nCity: " + this.city;
        returnValue += "\nState: " + this.state;
        return returnValue;
    }
    /**
     * displays user
     */
    public String toView() {
        return this.toString();
    }
}

