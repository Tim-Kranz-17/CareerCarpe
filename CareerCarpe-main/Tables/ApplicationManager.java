
/**
 *  Code to help manage the different applications
 */
 
package Tables;

import java.util.ArrayList;

import Data.Application;
import Data.Internship;
import Data.Resume;
import Handlers.DatabaseHandler;
import Data.Student;
import Data.Tableable;

/**
 * The public class that will be able to delete specific applications from 
 * the table.
 */

public class ApplicationManager extends Table implements Deletable {
    
    private ArrayList<Application> applications;
    private String employerUuid;
    
    /***
     * The public class that will manage an application.
     * @param employerUuid before caling from the super class of table
     */
    public ApplicationManager(String employerUuid) {
        super(
            new String[] { "Application", "Applications" },
            new String[] { "Internship", "Name", "Resume" },
            new int[] { 40, 32, 32 }
        );
        this.employerUuid = employerUuid;
        this.updateList();
        this.paginator(0);
    }

    /**
     * A class that will return the formatted version of the applications
     * in the console to be able to see.
     */
    public Tableable view(int toView) {
        return new FormattedApplication(this.applications.get(toView));
    }

    /**
     * A public class that will update the list by grabbing the employer Uuid 
     * from the database before being stored within the applications and the
     * list size will then be changed to fit the size of the  spplications.
     */
    public void updateList() {
        this.applications = DatabaseHandler.getApplicationsByEmployer(employerUuid);
        this.setListSize(this.applications.size());
    }

    /**
     * A public class that will be able to delete any applications.
     */
    
    public void delete(int toDelete) {
        DatabaseHandler.deleteApplication(this.applications.get(toDelete).getUuid());
        this.updateList();
    }    

    /**
     * A class that will take the table rows and print out the correct infromation
     * within a particular format. The string value will return a specific message if
     * there isn't anything to populate the table rows with.
     */
    public String tableRows() {
        String returnValue = "";

        if(this.applications.size() == 0) {
            returnValue += "You have no " + this.getTypePlural() + ".\n";
            returnValue += this.printRowSeparators();
            return returnValue;
        }

        int displayedSize = this.getToIndex() - this.getFromIndex() + 1;
        Application[] displayed = new Application[displayedSize];
        for (int i = this.getFromIndex(), j = 0; i <= this.getToIndex(); i++, j++) {
            displayed[j] = this.applications.get(i);
        }

        returnValue += this.printColumnHeaders();
        returnValue += "\n";
        returnValue += this.printRowSeparators();
        for (int i = 0; i < displayedSize; i++) { 
            FormattedApplication formattedApplication = new FormattedApplication(displayed[i]);
            String[] columns = new String[] 
            {
                ((i + getFromIndex() + 1) + ". " + formattedApplication.getinternshipTitle()),
                formattedApplication.getStudentName(),
                formattedApplication.getResumeTitle(),
            }; 
            returnValue += "\n";
            returnValue += this.printColumns(columns);
            returnValue += "\n";
            returnValue += this.printRowSeparators();
        }
        return returnValue;
    }

    /** 
     * A private accessor class that will be able to formate the application
     * in a specific way while implementing table.
     */
    private class FormattedApplication implements Tableable {

        private Application application;
        private String internshipTitle;
        private String studentName;
        private String studentToString;
        private String resumeTitle;
        private String resumeToString;

        /**
         * The protected version of the private class that will not
         * be changed by the different users.
         * @param application What the application is
         */
        protected FormattedApplication(Application application) {
            this.application = application;
            this.setInternshipData();
            this.setStudentData();
            this.setResumeData();            
        }
        
        /**
         * A getter the internship title.
         * @return the internship title
         */
        protected String getinternshipTitle() {
            return this.internshipTitle;
        }
        
        /**
         *  A getter for the student name.
         *  @return the student name
         */
        protected String getStudentName() {
            return this.studentName;
        }
        
        /**
         * A getter for the resume title.
         * @return the resume title
         */
        protected String getResumeTitle() {
            return this.resumeTitle;
        }
        
        /**
         * A setter for the internship data.
         */
        private void setInternshipData() {
            Internship internship = DatabaseHandler.getInternship(application.getInternshipUuid());
            this.internshipTitle = internship.getTitle();
        }

        /**
         * A setter for the student data.
         */
        private void setStudentData() {
            Student student = DatabaseHandler.getStudent(application.getStudentUuid());
            this.studentName = student.getFirstName() + " "
                            + student.getMiddleName().charAt(0) + ". "
                            + student.getLastName() + " ";
            this.studentToString = student.toStringNoUUID();
        }
    
        /**
         * A setter for resume data.
         */
        private void setResumeData() {
            Resume resume = DatabaseHandler.getResume(application.getResumeUuid());
            this.resumeTitle = resume.getTitle();
            this.resumeToString = resume.toStringNoUUID();
        }

        /**
         * A string that returns the different parts to get the resume,
         * the student, and the internship within strings.
         */
        public String toView() {
            String returnValue = "";
            returnValue += "Internship";        
            returnValue += "\n" + this.internshipTitle;        
            returnValue += "\n\nStudent";                          
            returnValue += "\n" + this.studentToString;                          
            returnValue += "\n\nResume";                          
            returnValue += "\n" + this.resumeToString;                          
            return returnValue;
        }
    }
}