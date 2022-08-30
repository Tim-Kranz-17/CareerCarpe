package Tables;

import java.io.File;
import java.io.FileWriter;
import java.io.IOException;
import java.io.PrintWriter;
import java.nio.file.Files;
import java.nio.file.Paths;
import java.util.ArrayList;
import Data.User;
import Data.Resume;
import Handlers.DatabaseHandler;
import Handlers.InputHandler;
/**
 * A class to manage the different resumes
 */
public class ResumeManager extends Table implements Modifiable, Deletable {

    private ArrayList<Resume> resumes;
    private String studentUuid;

    /**
     * A public accessor method that will show the different resumes
     * that are aligned with a specific unique student id
     * @param studentUuid the unique id of a student
     */
    public ResumeManager(String studentUuid) {
        super(
            new String[] { "Resume", "Resumes" },
            new String[] { "Title", "Description" },
            new int[] { 32, 56 }
        );
        this.studentUuid = studentUuid;
        this.updateList();
        this.paginator(0);
    }

    /** 
     * A method to view a resume.
     * @return the specific string at point toView
     */
    public Resume view(int toView) {
        return this.resumes.get(toView);        
    }

    /**
     * A method to update the list for resume and to update the
     * list size of the resume.
     */
    public void updateList() {
        this.resumes = DatabaseHandler.getResumesByStudent(studentUuid);
        this.setListSize(this.resumes.size());
    }

     /**
     * A method to build the resume that will then be held within
     * different variables and will be used to construct how the table
     * would actually look like on screen.
     */
    public void builder() {
        String title = InputHandler.getInputAsString("Title");
        String description = InputHandler.getInputAsString("Description");
        ArrayList<String> skills = InputHandler.getInputAsArrayList("skills");
        ArrayList<String> experience = InputHandler.getInputAsArrayList("experience");
        ArrayList<String> education = InputHandler.getInputAsArrayList("education");
        Resume resume = new Resume(studentUuid, title, description, skills, experience, education);
        DatabaseHandler.setResume(resume);
        this.updateList();
    }    

    /**
     * A method that allows the student to edit the resume while also
     * constantly running until the student decides to quit. This method
     * will also call update to allow the resume to be updated in
     * real time.
     */
    public void edit(int toEdit) {
        Resume resume = this.resumes.get(toEdit);
        String[] values = new String[] { "Title", "Description", "Skills", "Experience", "Education" };                
        int response = InputHandler.iterateOverOptionsAndGetSelectionWithQuit(values);                                 
        if (response == -1) return;            
        String valueToChange = "New " + values[response];

        switch (response) {
            case 0: resume.setTitle(InputHandler.getInputAsString(valueToChange)); break;
            case 1: resume.setDescription(InputHandler.getInputAsString(valueToChange)); break;
            case 2: resume.setSkills(InputHandler.getInputAsArrayList(valueToChange)); break;
            case 3: resume.setExperience(InputHandler.getInputAsArrayList(valueToChange)); break;
            case 4: resume.setEducation(InputHandler.getInputAsArrayList(valueToChange)); break;
            default: InputHandler.invalidArgument(); break;
        }
        DatabaseHandler.setResume(resume);
        this.updateList();
    }

    /**
     * A way to delete a resume before calling to update the list.
     */
    public void delete(int toDelete) {
        DatabaseHandler.deleteResume(this.resumes.get(toDelete).getUuid());
        this.updateList();
    }

    
    /**
     * A method to format to a toString
     * @return the string filled with the different information
     */
    public String toString(int toPrint) {
        int displayedSize = this.getToIndex() - this.getFromIndex() + 1;
        Resume[] displayed = new Resume[displayedSize];  
        for (int i = this.getFromIndex(), j = 0; i <= this.getToIndex(); i++, j++) {
            displayed[j] = this.resumes.get(i);
        }
        String returnValue = "";
        returnValue += "        " + displayed[toPrint].getTitle();
        returnValue += "\nDescription: " + displayed[toPrint].getDescription();
        returnValue += "\nRelated Skils: ";
        
        for (String skill: displayed[toPrint].getSkills()) {
            returnValue += "\n - " + skill;
        }

        returnValue += "\nRelated Experience: ";
        for(String exp : displayed[toPrint].getExperience()) {
            returnValue += "\n - " + exp;
        }

        returnValue += "\nPrior Education: ";
        for(String edu : displayed[toPrint].getEducation()) {
            returnValue += "\n - " + edu;
        }
        return returnValue;
    }

    /**
     * A method to format the table rows.
     * @return the string formatted for the table
     */
    public String tableRows() {
        String returnValue = "";
        if (this.resumes.size() == 0) {
            returnValue += "You have no " + this.getTypePlural() +".\n";
            returnValue += this.printRowSeparators();
            return returnValue;
        }

        int displayedSize = this.getToIndex() - this.getFromIndex() + 1;
        Resume[] displayed = new Resume[displayedSize];  
        for (int i = this.getFromIndex(), j = 0; i <= this.getToIndex(); i++, j++) {
            displayed[j] = this.resumes.get(i);
        }      

        returnValue += this.printColumnHeaders();
        returnValue += "\n";
        returnValue += this.printRowSeparators();
        for (int i = 0; i < displayedSize; i++) { 
            String[] columns = new String[] 
            {
                ((i + getFromIndex() + 1) + ". " + displayed[i].getTitle()),
                displayed[i].getDescription()
            }; 
            returnValue += "\n";
            returnValue += this.printColumns(columns);
            returnValue += "\n";
            returnValue += this.printRowSeparators();
        }
        return returnValue;
    }
 
    /**
     * A way to print out a specific part of each of the different
     * sections before stoping from printing everything.
     * @param toPrint where to stop printing out the strings
     * @return the string value of the resume file format
     */
    public String getResumeFileFormat(int toPrint) {
        int displayedSize = this.getToIndex() - this.getFromIndex() + 1;
        Resume[] displayed = new Resume[displayedSize];  
        for (int i = this.getFromIndex(), j = 0; i <= this.getToIndex(); i++, j++) {
            displayed[j] = this.resumes.get(i);
        }
        String returnValue = "";
        returnValue += "        " + displayed[toPrint].getTitle();
        returnValue += "\nDescription: " + displayed[toPrint].getDescription();
        returnValue += "\nRelated Skils: ";
        
        for (String skill: displayed[toPrint].getSkills()) {
            returnValue += "\n - " + skill;
        }

        returnValue += "\nRelated Experience: ";
        for(String exp : displayed[toPrint].getExperience()) {
            returnValue += "\n - " + exp;
        }

        returnValue += "\nPrior Education: ";
        for(String edu : displayed[toPrint].getEducation()) {
            returnValue += "\n - " + edu;
        }
        return returnValue;
    }
}