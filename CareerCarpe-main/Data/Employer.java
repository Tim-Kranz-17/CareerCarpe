package Data;
import java.util.ArrayList;
/**
 * sets up the employers for the program
 */
public class Employer extends User{

    private String companyName;
    private ArrayList<String> internships;
    /**
     * sets up the information of the employers
     * @param email Employer's Email
     * @param phoneNumber Employer's Phone #
     * @param password Employer's Password
     * @param city Employer's City
     * @param state Employer's State
     * @param companyName Employer's company name
     */
    public Employer(String email, String phoneNumber, String password, String city, String state, String companyName) {
        this(email, phoneNumber, password, city, state, companyName, new ArrayList<String>());
    }

    public Employer(String email, String phoneNumber, String password, String city, String state, String companyName,
        ArrayList<String> internships
    ) {
        super(email, phoneNumber, password, city, state);
        this.companyName = companyName;
        this.internships = internships;
    }

    public Employer(String uuid, String email, String phoneNumber, String password, String city, String state, String companyName) {
        this(uuid, email, phoneNumber, password, city, state, companyName, new ArrayList<String>());
    }

    public Employer(String uuid, String email, String phoneNumber, String password, String city, String state, String companyName,
        ArrayList<String> internships
    ) {
        super(uuid, email, phoneNumber, password, city, state);
        this.companyName = companyName;
        this.internships = internships;
    }
    /**
     * gets Employer's comp name
     * @return company name
     */
    public String getCompanyName() {
        return this.companyName;
    }
    /**
     * sets Employer's company name
     */
    public void setCompanyName(String companyName) {
        this.companyName = companyName;
    }
    /**
     * gets the internships of the program
     * @return arraylist of internships
     */
    public ArrayList<String> getInternships() {
        return this.internships;
    }
    /**
     * adds internships for employers
     * @param internshipUuid uuid of internship
     */
    public void addInternship(String internshipUuid) {
        if (!this.internships.contains(internshipUuid)) this.internships.add(internshipUuid);
    }
    /**
     * removes internships for employers
     * @param internshipUuid uuid of internship
     */
    public void removeInternship(String internshipUuid) {
        this.internships.remove(internshipUuid);
    }
    /**
     * how the internship will be displayed
     */
    public String toString() {
        String returnValue = super.toString();
        returnValue += "\nCompany Name: " + this.companyName;
        return returnValue;
    }   
    /**
     * how the internship will be displayed without uuid
     */
    public String toStringNoUUID() {
        String returnValue = super.toStringNoUUID();
        returnValue += "\nCompany Name: " + this.companyName;
        return returnValue;
    }   
    /**
     * displays
     */
    public String toView() {
        return this.toString();
    }
}
