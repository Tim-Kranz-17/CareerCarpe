package Tables;

import Data.User;
import Data.Administrator;
import Data.DataTypes;
import Data.Employer;
import Data.Student;
import Handlers.DatabaseHandler;
import Handlers.InputHandler;

import java.util.ArrayList;

/**
 * A class to manage the different users
 */
public class UserManager extends Table implements Modifiable, Deletable {

    private ArrayList<User> users;

    /**
     * An accessor method for user managers
     */
    public UserManager() {
        super(
            new String[] { "User", "Users" }, 
            new String[] { "Account Type", "UUID", "Email", "Phone Number", "City"}, 
            new int[] {8, 5, 16, 8, 8}
        );
        this.updateList();
        this.paginator(0);
    }
    /**
     * A method to update the list of users and to update the
     * list size of users.
     */
    public void updateList() {
        this.users = DatabaseHandler.getUsers();
        this.setListSize(this.users.size());
    }

    /**
     * A method to build a user that will then be held within
     * different variables and will be used to construct how the table
     * would actually look like on screen. Would be different depending
     * on what kind of account the user wil create.
     */
    public void builder() {
        System.out.println("What Kind of Account Would You Like to Make");
        int response = InputHandler.iterateOverOptionsAndGetSelectionWithQuit(new String[] {"Employer", "Student"});       
        if (response == -1) return;
        System.out.println("Give us your email and password. ");
        String email = InputHandler.getAndVerifyInput("Email");
        String password = InputHandler.getAndVerifyInput("Password");
        String phoneNumber = InputHandler.getInputAsString("Phone Number");
        String city = InputHandler.getInputAsString("City");
        String state = InputHandler.getInputAsString("State");
        switch (response) {
            case 0: 
                Employer employer = new Employer(email, phoneNumber, password, city, state,
                    InputHandler.getInputAsString("Company Name")
                );
                DatabaseHandler.signUp(employer);
                break;
            case 1: 
                Student student = new Student(email, phoneNumber, password, city, state, 
                    InputHandler.getInputAsString("First Name"), 
                    InputHandler.getInputAsString("Middle Name"), 
                    InputHandler.getInputAsString("Last Name"), 
                    InputHandler.getInputAsString("University"), 
                    InputHandler.getInputAsString("Graduation Date")
                );           
                DatabaseHandler.signUp(student);
                break;
            default: InputHandler.invalidArgument(); break;
        }
    }    

    /**
     * A method that allows the user to edit their profile while also
     * constantly running until the user decides to quit. This method
     * will also call update to allow the profile to be updated in
     * real time.
     */
    public void edit(int toEdit) {
        User user = this.users.get(toEdit);
        switch (DatabaseHandler.getDataTypeFromUUID(user.getUuid())) {
            case ADMINISTRATOR:
                UserManager.editProfile(DatabaseHandler.getAdministrator(user.getUuid()));
                break;
            case STUDENT:
                UserManager.editProfile(DatabaseHandler.getStudent(user.getUuid()));
                break;
            case EMPLOYER:
                UserManager.editProfile(DatabaseHandler.getEmployer(user.getUuid()));
                break;
            default:
                InputHandler.invalidArgument();
                break;
        }
        this.updateList();
    }

    /**
     * A method that will show a menu of what the user can edit
     * within their profile before being updated on the screen.
     * @param user
     */
    public static void editProfile(User user) {
        menu: while (true) {       
            InputHandler.resetScreen("User Editor");
            if (user instanceof Administrator) {
                Administrator administrator = (Administrator) user;
                String[] values = new String[] { "Email", "Password", "Phone Number", "City", "State", "Username" };
                int response = InputHandler.iterateOverOptionsAndGetSelectionWithQuit(values);                                 
                if (response == -1) break menu;            
                String newValue = InputHandler.getInputAsString("New " + values[response]);
                switch (response) {
                    case 0: administrator.setEmail(newValue); break;
                    case 1: administrator.setPassword(newValue); break;
                    case 2: administrator.setPhoneNumber(newValue); break;
                    case 3: administrator.setCity(newValue); break;
                    case 4: administrator.setState(newValue); break;
                    case 5: administrator.setUsername(newValue); break;
                    default: InputHandler.invalidArgument(); break;
                }
                DatabaseHandler.setAdministrator(administrator);
            } else if (user instanceof Employer) {
                Employer employer = (Employer) user;
                String[] values = new String[] { "Email", "Password", "Phone Number", "City", "State", "Company Name" };
                int response = InputHandler.iterateOverOptionsAndGetSelectionWithQuit(values);                             
                if (response == -1) break menu;            
                String newValue = InputHandler.getInputAsString("New " + values[response]);
                switch (response) {
                    case 0: employer.setEmail(newValue); break;
                    case 1: employer.setPassword(newValue); break;
                    case 2: employer.setPhoneNumber(newValue); break;
                    case 3: employer.setCity(newValue); break;
                    case 4: employer.setState(newValue); break;
                    case 5: employer.setCompanyName(newValue); break;
                    default: InputHandler.invalidArgument(); break;
                }
                DatabaseHandler.setEmployer(employer);
            
            } else if (user instanceof Student) {
                Student student = (Student) user;
                String[] values = new String[] { "Email", "Password", "Phone Number", "City", "State", "First Name", "Middle Name", "Last Name", "University", "Graduation Date" };
                int response = InputHandler.iterateOverOptionsAndGetSelectionWithQuit(values);                 
                if (response == -1) break menu;                
                String newValue = InputHandler.getInputAsString("New " + values[response]);
                switch (response) {
                    case 0: student.setEmail(newValue); break;
                    case 1: student.setPassword(newValue); break;
                    case 2: student.setPhoneNumber(newValue); break;
                    case 3: student.setCity(newValue); break;
                    case 4: student.setState(newValue); break;
                    case 5: student.setFirstName(newValue); break;
                    case 6: student.setMiddleName(newValue); break;
                    case 7: student.setLastName(newValue); break;
                    case 8: student.setUniversity(newValue); break;      
                    case 9: student.setGraduationDate(newValue); break;                 
                    default: InputHandler.invalidArgument(); break;
                }
                DatabaseHandler.setStudent(student);
            } else {
                InputHandler.invalidArgument();
            }
        }
    }

    /**
     * A way to delete a user before calling to update the list.
     */
    public void delete(int toDelete) {
        DatabaseHandler.deleteUser(this.users.get(toDelete).getUuid());
        this.updateList();
    }

    /**
     * A method that will pull up a specific user and all the information
     * within it.
     * @param userUpdate Who to pull up 
     * @return a null value so it runs in the background
     */
    public User getUser(int userUpdate) {
        User user = this.users.get(userUpdate);
        DataTypes dataType = DatabaseHandler.getDataTypeFromUUID(user.getUuid());
        switch (dataType) {
            case ADMINISTRATOR:
            return DatabaseHandler.getAdministrator(user.getUuid());
            case STUDENT:
            return DatabaseHandler.getStudent(user.getUuid());
            case EMPLOYER:
            return DatabaseHandler.getEmployer(user.getUuid());
        }
        return null;
    }

    /**
     * A method to view users.
     * @return the users at point toView
     */
    public User view(int toView) {
        return this.users.get(toView);        
    }
    
    /**
     * A method to format the table rows.
     * @return the string formatted for the table
     */
    public String tableRows() {
        String returnValue = "";
        if (this.users.size() == 0) {
            returnValue += "No " + this.getTypePlural() + " have been created yet.\n";
            returnValue += this.printRowSeparators();
            return returnValue;
        }
        int displayedSize = this.getToIndex() - this.getFromIndex() + 1;
        User[] displayed = new User[displayedSize];  
        for (int i = this.getFromIndex(), j = 0; i <= this.getToIndex(); i++, j++) {
            displayed[j] = this.users.get(i);
        }                   
        returnValue += this.printColumnHeaders();
        returnValue += "\n";
        returnValue += this.printRowSeparators();
        for (int i = 0; i < displayedSize; i++) { 
            int separatorIndex = displayed[i].getUuid().indexOf("-");
            String[] columns = new String[] 
            {
                ((i + getFromIndex() + 1) + ". " + displayed[i].getUuid().substring(0, separatorIndex)),
                displayed[i].getUuid().substring(separatorIndex + 1, separatorIndex + 9),
                displayed[i].getEmail(),
                displayed[i].getPhoneNumber(),
                displayed[i].getCity(),
            }; 
            returnValue += "\n";
            returnValue += this.printColumns(columns);
            returnValue += "\n";
            returnValue += this.printRowSeparators();
        }
        return returnValue;
    }
}