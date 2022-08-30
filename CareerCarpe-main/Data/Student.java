package Data;
import java.util.ArrayList;
/**
 * sets up student for the program
 */
public class Student extends User {
    
    private String firstName;
    private String middleName;
    private String lastName;
    private String university;
    private String graduationDate;
    private ArrayList<String> resumes;
    private ArrayList<String> applications;
    /**
     * sets up information of student
     * @param email Student's email
     * @param phoneNumber Student's phone numb
     * @param password Student's password
     * @param city Student's city
     * @param state Student's state
     * @param firstName Student's first name
     * @param middleName Student's middle name
     * @param lastName Student's last name
     * @param university Student's uni
     * @param graduationDate Student's grad date
     */
    public Student(String email, String phoneNumber, String password, String city, String state,
        String firstName, String middleName, String lastName, String university, String graduationDate
    ) {        
        this(email, phoneNumber, password, city, state, firstName, middleName, lastName, university, graduationDate,
            new ArrayList<String>(), new ArrayList<String>()
        );
    }
   
    public Student(String email, String phoneNumber, String password, String city, String state,
        String firstName, String middleName, String lastName, String university, String graduationDate,
        ArrayList<String> resumes, ArrayList<String> applications
    ) {        
        super(email, phoneNumber, password, city, state);
        this.firstName = firstName;
        this.middleName = middleName;
        this.lastName = lastName;
        this.university = university;
        this.graduationDate = graduationDate;
        this.resumes = resumes;
        this.applications = applications;
    }

    public Student(String uuid, String email, String phoneNumber, String password, String city, String state,
        String firstName, String middleName, String lastName, String university, String graduationDate
    ) {        
        this(uuid, email, phoneNumber, password, city, state, firstName, middleName, lastName, university, graduationDate,
            new ArrayList<String>(), new ArrayList<String>()
        );
    }

    public Student(String uuid, String email, String phoneNumber, String password, String city, String state,
        String firstName, String middleName, String lastName, String university, String graduationDate,
        ArrayList<String> resumes, ArrayList<String> applications
    ) {        
        super(uuid, email, phoneNumber, password, city, state);
        this.firstName = firstName;
        this.middleName = middleName;
        this.lastName = lastName;
        this.university = university;
        this.graduationDate = graduationDate;
        this.resumes = resumes;
        this.applications = applications;
    }
    /**
     * gets student's firstname
     * @return name
     */
    public String getFirstName() {
        return this.firstName;
    }
    /**
     * sets students first name
     * @param firstName
     */
    public void setFirstName(String firstName) {
        this.firstName = firstName;
    }
    /**
     * gets students middle name
     * @return middle name
     */
    public String getMiddleName() {
        return this.middleName;
    }
    /**
     * sets students middle name
     * @param middleName
     */
    public void setMiddleName(String middleName) {
        this.middleName = middleName;
    }
    /**
     * gets students last anme
     * @return last name
     */
    public String getLastName() {
        return this.lastName;
    }
    /**
     * sets student's last name
     * @param lastName
     */
    public void setLastName(String lastName) {
        this.lastName = lastName;
    }
    /**
     * gets students uni
     * @return university
     */
    public String getUniversity() {
        return this.university;
    }
    /**
     * sets uni of student
     * @param university
     */
    public void setUniversity(String university) {
        this.university = university;
    }
    /**
     * gets grad date of student
     * @return grad date
     */
    public String getGraduationDate() {
        return this.graduationDate;
    }
    /**
     * set grad date
     * @param graduationDate
     */
    public void setGraduationDate(String graduationDate) {
        this.graduationDate = graduationDate;
    }
    /**
     * gets resumes of student
     * @return AL of resumes
     */
    public ArrayList<String> getResumes() {
        return this.resumes;
    }
    /**
     * adds student's resume
     * @param resumeUuid resumes uuid
     * @return
     */
    public boolean addResume(String resumeUuid) {
        if (this.resumes.contains(resumeUuid)) {
            return false;
        }
        this.resumes.add(resumeUuid);
        return true;
    }    
    /**
     * removes student's resume
     * @param resumeUuid ID
     */
    public void removeResume(String resumeUuid) {
        this.resumes.remove(resumeUuid);
    }    
    /**
     * gets applications of students
     * @return AL of apps
     */
    public ArrayList<String> getApplications() {
        return this.applications;
    }
    /**
     * add application of student
     * @param applicationUuid app ID
     */
    public void addApplication(String applicationUuid) {
        if (!this.applications.contains(applicationUuid)) this.applications.add(applicationUuid);
    }    
    /**
     * removes students applications
     * @param applicationUuid app ID
     */
    public void removeApplication(String applicationUuid) {
        this.applications.remove(applicationUuid);
    }    
    /**
     * how student will be displayed
     */
    public String toString() {
        String returnValue = super.toString();
        returnValue += "First Name: " + this.firstName;
        returnValue += "\nMiddle Name: " + this.middleName;
        returnValue += "\nLast Name: " + this.lastName;
        returnValue += "\nUniversity: " + this.university;
        returnValue += "\nGraduation Date: " + this.graduationDate;
        return returnValue;
    }
    /**
     * how student will be displayed without uuid
     */
    public String toStringNoUUID() {
        String returnValue = super.toStringNoUUID();
        returnValue += "\nFirst Name: " + this.firstName;
        returnValue += "\nMiddle Name: " + this.middleName;
        returnValue += "\nLast Name: " + this.lastName;
        returnValue += "\nUniversity: " + this.university;
        returnValue += "\nGraduation Date: " + this.graduationDate;
        return returnValue;
    }
    /**
     * displays student
     */
    public String toView() {
        return this.toString();
    }
}
