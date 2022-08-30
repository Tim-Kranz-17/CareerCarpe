package Data;
import java.util.ArrayList;
/**
 * sets up the internships of the program
 */
public class Internship extends Datum implements Tableable {

    private String employerUuid;
    private String title;
    private String description;
    private double payRate;
    private int  hoursPerWeek;
    private int numberOfWeeks;
    private ArrayList<String> skills;
    private ArrayList<String> applications;
    private boolean displayed;
    /**
     * sets information of internship
     * @param employerUuid Employer's UUID
     * @param title title of internship
     * @param description description of internship
     * @param payRate pay rate of job
     * @param hoursPerWeek hours of week for job
     * @param numberOfWeeks number of weeks
     */
    public Internship(String employerUuid, String title, String description, double payRate, int hoursPerWeek, int numberOfWeeks) {
        this("undefined", employerUuid, title, description, payRate, hoursPerWeek, numberOfWeeks, false);
    }
    
    public Internship(String employerUuid, String title, String description, double payRate, int hoursPerWeek, int numberOfWeeks, ArrayList<String> skills, boolean displayed) {
        this("undefined", employerUuid, title, description, payRate, hoursPerWeek, numberOfWeeks, skills, new ArrayList<String>(), displayed);
    }

    public Internship(String uuid, String employerUuid, String title, String description, double payRate, int hoursPerWeek, int numberOfWeeks) {
        this(uuid, employerUuid, title, description, payRate, hoursPerWeek, numberOfWeeks, false);
    }

    public Internship(String uuid, String employerUuid, String title, String description, double payRate, int hoursPerWeek, int numberOfWeeks, boolean displayed) {
        this(uuid, employerUuid, title, description, payRate, hoursPerWeek, numberOfWeeks, new ArrayList<String>(), new ArrayList<String>(), displayed);
    }

    public Internship(String uuid, String employerUuid, String title, String description, double payRate, int hoursPerWeek, int numberOfWeeks, ArrayList<String> skills, ArrayList<String> applications, boolean displayed) {
        super(uuid);
        this.employerUuid = employerUuid;
        this.title = title;
        this.description = description;
        this.payRate = payRate;
        this.hoursPerWeek = hoursPerWeek;
        this.numberOfWeeks = numberOfWeeks;
        this.applications = applications;
        this.skills = skills;
        this.displayed = displayed;
    }
    /**
     * gets Employer UUID
     * @return uuid of employer
     */
    public String getEmployerUuid() {
        return this.employerUuid;
    }
    /**
     * gets title of internship
     * @return title of internship
     */
    public String getTitle() {
        return this.title;
    }
    /**
     * sets title of internship
     * @param title
     */
    public void setTitle(String title) {
        this.title = title;
    }
    /**
     * gets description of internship
     * @return description
     */
    public String getDescription() {
        return this.description;
    }
    /**
     * sets title of internship
     * @param description
     */
    public void setDescription(String description) {
        this.description = description;
    }
    /**
     * gets pay rate of internship
     * @return double pay rate
     */
    public double getPayRate() {
        return this.payRate;
    }
    /**
     * sets pay rate of internship
     * @param payRate
     */
    public void setPayRate(double payRate) {
        this.payRate = payRate;
    }
    /**
     * gets hours of week for internship
     * @return hours of work per week
     */
    public int getHoursPerWeek() {
        return this.hoursPerWeek;
    }
    /**
     * sets hours per week
     * @param hoursPerWeek
     */
    public void setHoursPerWeek(int hoursPerWeek) {
        this.hoursPerWeek = hoursPerWeek;
    }
    /**
     * gets numbers of week of work
     * @return number of weeks of work
     */
    public int getNumberOfWeeks() {
        return this.numberOfWeeks;
    }
    /**
     * sets number of weeks of work
     * @param numberOfWeeks
     */
    public void setNumberOfWeeks(int numberOfWeeks) {
        this.numberOfWeeks = numberOfWeeks;
    }
    /**
     * gets total amount of pay
     * @return total amount of pay
     */
    public int getTotalPayout() {
        return (int) (this.numberOfWeeks * this.hoursPerWeek * this.payRate);
    }
    /**
     * applications for internship
     * @return arraylist of apps.
     */
    public ArrayList<String> getApplications() {
        return this.applications;
    }
    /**
     * adds applications of internship
     * @param applicationUuid apps uuid
     */
    public void addApplication(String applicationUuid) {
        if (!this.applications.contains(applicationUuid)) this.applications.add(applicationUuid);
    }    
    /**
     * gets number of applications
     * @return number of applications
     */
    public int getNumberOfApplications() {
        return this.applications.size();
    }
    /**
     * removes application
     * @param applicationUuid apps uuid
     */
    public void removeApplication(String applicationUuid) {
        this.applications.remove(applicationUuid);
    }    
    /**
     * skills needed for internship
     * @return AL of skills
     */
    public ArrayList<String> getSkills() {
        return this.skills;
    }
    /**
     * sets skills for internship
     */
    public void setSkills(ArrayList<String> skills) {
        this.skills = skills;
    }
    /**
     * gets whether the internship is displayed or not
     * @return yes or no
     */
    public String getDisplayedStatus() {
        if (this.displayed) return "Yes";
        else return "No";
    }
    /**
     * gets whether the internship is displayed or not
     * @return bool
     */
    public boolean getDisplayed() {
        return this.displayed;
    }
    /**
     * sets whether the internship is displayed or not
     * @param displayed true/false
     */
    public void setDisplayed(boolean displayed) {
        this.displayed = displayed;
    }
    /**
     * how internship is displayed
     */
    public String toString() {
        String returnValue = "";
        returnValue += "UUID: " + this.getUuid();
        returnValue += "\nTitle: " + this.title;        
        returnValue += "\nDescription: " + this.description;
        returnValue += "\nPay Rate: " + this.payRate;
        returnValue += "\nNumber of Weeks: " + this.numberOfWeeks;
        returnValue += "\nNumber of Hours per Week: " + this.hoursPerWeek;
        returnValue += "\nRequired Skills: ";
        for (String skill: skills) {
            returnValue += "\n - " + skill;
        }
        return returnValue;
    }
    /**
     * displays the internship
     */
    public String toView() {
        return this.toString();
    }
}
