package Data;
/**
 * sets up and displays the applications for the program
 */
public class Application extends Datum {
    
    private String studentUuid;
    private String resumeUuid;
    private String internshipUuid;
    /**
     * Sets up the information for the applications
     * @param studentUuid
     * @param resumeUuid
     * @param internshipUuid
     */
    public Application(String studentUuid, String resumeUuid, String internshipUuid) {
        this("undefined", studentUuid, resumeUuid, internshipUuid);
    }

    public Application(String uuid, String studentUuid, String resumeUuid, String internshipUuid) {
        super(uuid);
        this.studentUuid = studentUuid;
        this.resumeUuid = resumeUuid;
        this.internshipUuid = internshipUuid;
    }
    /**
     * gets student's uuid
     * @return student's uuid
     */
    public String getStudentUuid() {
        return this.studentUuid;
    }
    /**
     * gets student's uuid
     * @return resume's uuid
     */
    public String getResumeUuid() {
        return this.resumeUuid;
    }
    /**
     * get internship's uuid
     * @return internship's uuid
     */
    public String getInternshipUuid() {
        return this.internshipUuid;
    }
    /**
     * how the application will be displayed
     */
    public String toString() {
        String returnValue = "";
        returnValue += "UUID: " + this.getUuid();
        returnValue += "\nStudent UUID: " + studentUuid;
        returnValue += "\nReusme UUID: " + resumeUuid;
        returnValue += "\nInternship UUID: " + internshipUuid;
        return returnValue;
    }

}
