package Data;
/**
 * Sets up the Admin for the program
 */
public class Administrator extends User {    
    private String adminCreatedBy;
    private String username;
    /**
     * Sets up the admins information
     * @param email Admin's email
     * @param phoneNumber Admin's phone number
     * @param password Admin's password
     * @param city Admin's city
     * @param state Admin's state
     * @param adminCreatedBy who created this admin
     * @param username Admin's username
     */
    public Administrator(String email, String phoneNumber, String password, String city, String state, String adminCreatedBy, String username) {
        super(email, phoneNumber, password, city, state);
        this.adminCreatedBy = adminCreatedBy;
        this.username = username;
    }

    public Administrator(String uuid, String email, String phoneNumber, String password, String city, String state, String adminCreatedBy, String username) {
        super(uuid, email, phoneNumber, password, city, state);
        this.adminCreatedBy = adminCreatedBy;
        this.username = username;
    }

    public String getUsername() {
        return this.username;
    }
    /**
     * sets admin's username
     * @param userName
     */
    public void setUsername(String userName) {
        this.username = userName;
    }
    /**
     * gets who created the admin
     * @return
     */
    public String getAdminCreatedBY() {
        return this.adminCreatedBy;
    }
    /**
     * how the admin class will be displayed
     */
    public String toString() {
        String returnValue = super.toString();
        returnValue += "\nAdmininstrator Created By: " + adminCreatedBy;
        returnValue += "\nUsername: " + username;
        return returnValue;
    }
    /**
     * how the admin class will be displayed without uuid
     */
    public String toStringNoUUID() {
        String returnValue = super.toStringNoUUID();
        returnValue += "\nAdmininstrator Created By: " + adminCreatedBy;
        returnValue += "\nUsername: " + username;
        return returnValue;
    }
    /**
     * Displays the toString
     */
    public String toView() {
        return this.toString();
    }
}
