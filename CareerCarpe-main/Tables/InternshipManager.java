package Tables;

import java.util.ArrayList;

import Data.Internship;
import Handlers.DatabaseHandler;
import Handlers.InputHandler;

/**
 * A public class that will manage the different internships.
 */
public class InternshipManager extends Table implements Modifiable, Deletable {
    private ArrayList<Internship> internships;
    private String employerUuid;    

    /**
     * A public accessor method for the internship manager that
     * shows specific internships that an employer has posted.
     * @param employerUuid a unique employer id
     */
    public InternshipManager(String employerUuid) {
        super(
            new String[] { "Internship", "Internships" },
            new String[] { "Title", "Applications", "Hrs/Wk", "Num Wks", "Pay Rate", "Displayed" },
            new int[] { 24, 8, 8, 8, 8, 8 }
        );
        this.employerUuid = employerUuid;
        this.updateList();
        this.paginator(0);
    }

    /**
     * A method to view internships.
     * @return the internships at point toView
     */
    public Internship view(int toView) {
        return this.internships.get(toView);        
    }
    
    /**
     * A method to update the list of internships while updating the
     * size of the list.
     */
    public void updateList() {        
        this.internships = DatabaseHandler.getInternshipsByEmployer(employerUuid);
        this.setListSize(this.internships.size());
    }

    /**
     * A method to build the internship that will then be held within
     * different variables and will be used to construct how the table
     * would actually look like on screen.
     */
    public void builder() {
        String title = InputHandler.getInputAsString("Title");
        String description = InputHandler.getInputAsString("Description");
        double payRate = InputHandler.getInputAsDouble("Pay Rate");
        int  hoursPerWeek = InputHandler.getInputAsInteger("Hours Per Week");
        int numberOfWeeks = InputHandler.getInputAsInteger("Number of Weeks");
        ArrayList<String> skills = InputHandler.getInputAsArrayList("skills");
        boolean displayed = InputHandler.getInputAsYesOrNo("Display internship");
        Internship internship = new Internship(employerUuid, title, description, payRate, hoursPerWeek, numberOfWeeks, skills, displayed);
        DatabaseHandler.setInternship(internship);
    }    

    /**
     * A method that allows the employer to edit the internship while also
     * constantly running until the employer decides to quit. This method
     * will also call update to allow the internship to be updated in
     * real time.
     */
    public void edit(int toEdit) {
        Internship internship = this.internships.get(toEdit);
        String[] values = new String[] { "Title", "Description", "Pay Rate", "Hours Per Week", "Number of Weeks", "Skills", "Display Internsip" };                
        int response = InputHandler.iterateOverOptionsAndGetSelectionWithQuit(values);                                 
        if (response == -1) return;            
        String valueToChange = "New " + values[response];
        switch (response) {
            case 0: internship.setTitle(InputHandler.getInputAsString(valueToChange)); break;
            case 1: internship.setDescription(InputHandler.getInputAsString(valueToChange)); break;
            case 2: internship.setPayRate(InputHandler.getInputAsDouble(valueToChange)); break;
            case 3: internship.setHoursPerWeek(InputHandler.getInputAsInteger(valueToChange)); break;
            case 4: internship.setNumberOfWeeks(InputHandler.getInputAsInteger(valueToChange)); break;
            case 5: internship.setSkills(InputHandler.getInputAsArrayList(valueToChange)); break;
            case 6: internship.setDisplayed(InputHandler.getInputAsYesOrNo("Display Internship")); break;
            default: InputHandler.invalidArgument(); break;
        }
        DatabaseHandler.setInternship(internship);
        this.updateList();
    }

    /**
     * A way to delete an internship before calling to update the list
     */
    public void delete(int toDelete) {
        DatabaseHandler.deleteInternship(this.internships.get(toDelete).getUuid());
        this.updateList();
    }

    /**
     * A method to format the table rows.
     * @return the string formatted for the table
     */
    public String tableRows() {
        String returnValue = "";
        if (this.internships.size() == 0) {
            returnValue += "You have no " + this.getTypePlural() + ".\n";
            returnValue += this.printRowSeparators();
            return returnValue;
        }

        int displayedSize = this.getToIndex() - this.getFromIndex() + 1;
        Internship[] displayed = new Internship[displayedSize];  
        for (int i = this.getFromIndex(), j = 0; i <= this.getToIndex(); i++, j++) {
            displayed[j] = this.internships.get(i);
        }                   
        returnValue += this.printColumnHeaders();
        returnValue += "\n";
        returnValue += this.printRowSeparators();
        for (int i = 0; i < displayedSize; i++) { 
            String[] columns = new String[] 
            {
                ((i + getFromIndex() + 1) + ". " + displayed[i].getTitle()),
                String.valueOf(displayed[i].getNumberOfApplications()),
                String.valueOf(displayed[i].getHoursPerWeek()),
                String.valueOf(displayed[i].getNumberOfWeeks()),
                String.valueOf(displayed[i].getPayRate()),
                String.valueOf(displayed[i].getDisplayedStatus()),
            }; 
            returnValue += "\n";
            returnValue += this.printColumns(columns);
            returnValue += "\n";
            returnValue += this.printRowSeparators();
        }
        return returnValue;
    }
}
