package Data;
import java.util.ArrayList;
/**
 * sets up the resumes of the program
 */
public class Resume extends Datum implements Tableable {

    private String studentUuid;
    private String title;
    private String description;
    private ArrayList<String> skills;
    private ArrayList<String> experience;
    private ArrayList<String> education;
    /**
     * gathers the information of the resume
     * @param studentUuid Student's uuid
     * @param title title of resume
     * @param description student's descrip.
     * @param skills skills of student
     * @param experience exp of student
     * @param education edu of student
     */
    public Resume(String studentUuid, String title, String description, ArrayList<String> skills, ArrayList<String> experience, ArrayList<String> education) {
        this("undefined", studentUuid, title, description, skills, experience, education);        
    }

    public Resume(String uuid, String studentUuid, String title, String description, ArrayList<String> skills, ArrayList<String> experience, ArrayList<String> education) {
        super(uuid);
        this.studentUuid = studentUuid;
        this.title = title;
        this.description = description;
        this.skills = skills;
        this.experience = experience;
        this.education = education;
    }
    /**
     * gets uuid of student
     * @return uuid of student
     */
    public String getStudentUuid() {
        return this.studentUuid;
    }
    /**
     * gets title of resume
     * @return title of resume
     */
    public String getTitle() {
        return this.title;
    }
    /**
     * sets title of resume
     * @param title of resume
     */
    public void setTitle(String title) {
        this.title = title;
    }
    /**
     * gets description of res
     * @return description of res
     */
    public String getDescription() {
        return this.description;
    }
    /**
     * sets desc. of res
     * @param description
     */
    public void setDescription(String description) {
        this.description = description;
    }
    /**
     * gets students skills
     * @return AL of skills
     */
    public ArrayList<String> getSkills() {
        return skills;
    }
    /**
     * sets skill of student
     * @param skills 
     */
    public void setSkills(ArrayList<String> skills) {
        this.skills = skills;
    }
    /**
     * gets exp of student
     * @return AL of exp
     */
    public ArrayList<String> getExperience() {
        return experience;
    }
    /**
     * sets exp of student
     * @param experience
     */
    public void setExperience(ArrayList<String> experience) {
        this.experience = experience;
    }
    /**
     * gets edu of student
     * @return edu of student
     */
    public ArrayList<String> getEducation() {
        return education;
    }
    /**
     * sets edu of of student
     * @param education
     */
    public void setEducation(ArrayList<String> education) {
        this.education = education;
    }
    /**
     * how the resume will be printed
     */
    public String toString() {
        String returnValue = "";
        returnValue += "UUID: " + this.getUuid();
        returnValue += "\nStudent UUID: " + this.studentUuid;
        returnValue += "\nTitle: " + this.title;
        returnValue += "\nDescription: " + this.description;
        returnValue += "\nSkils: ";
        
        for (String skill: skills) {
            returnValue += "\n - " + skill;
        }

        returnValue += "\nExperience: ";
        for(String exp : experience) {
            returnValue += "\n - " + exp;
        }

        returnValue += "\nEducation: ";
        for(String edu : education) {
            returnValue += "\n - " + edu;
        }

        return returnValue;
    }
    /**
     * how resume will be printed without uuid
     * @return
     */
    public String toStringNoUUID() {
        String returnValue = "";
        returnValue += "Title: " + this.title;
        returnValue += "\nDescription: " + this.description;
        returnValue += "\nSkils: ";
        
        for (String skill: skills) {
            returnValue += "\n - " + skill;
        }

        returnValue += "\nExperience: ";
        for(String exp : experience) {
            returnValue += "\n - " + exp;
        }

        returnValue += "\nEducation: ";
        for(String edu : education) {
            returnValue += "\n - " + edu;
        }
        returnValue += "\n";
        return returnValue;
    }
    /**
     * displays resume
     */
    public String toView() {
        return this.toString();
    }
}
