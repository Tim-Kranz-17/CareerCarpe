import java.io.BufferedWriter;
import java.io.FileWriter;

import Data.Administrator;
import Data.Application;
import Data.Employer;
import Data.Student;
import Data.Tableable;
import Data.User;
import Handlers.DatabaseHandler;
import Handlers.InputHandler;
import Tables.ApplicationManager;
import Tables.Deletable;
import Tables.InternshipListings;
import Tables.InternshipManager;
import Tables.Modifiable;
import Tables.ResumeManager;
import Tables.Table;
import Tables.UserManager;

public class UserInterface {

    private static final String DEFAULT_HEADER = "Select One of the Following Options";
    private static final int MAX_LOGIN_ATTEMPTS = 3;

    private static UserInterface userInterface = new UserInterface();
    private User user;

    private UserInterface() {
    
    }

    public static UserInterface getSingleton() {
        return userInterface;
    }

    public void welcome() {
        while (true) {   
            InputHandler.resetScreen(DEFAULT_HEADER);     
            int response = InputHandler.iterateOverOptionsAndGetSelection(new String[] {"Login", "Sign Up", "Exit"});
            switch (response) {
                case (0): if (this.login()) this.mainMenu(this.user); break;
                case (1): this.signUp(); break;
                case (2): this.exit(); break;
                default: InputHandler.invalidArgument(); break;
            }
        }
    }

    public void mainMenu(User user) {

        if (this.user instanceof Student) {
            this.mainMenuStudent();
        } else if (this.user instanceof Employer) {
            this.mainMenuEmployer();
        } else if (this.user instanceof Administrator) {
            this.mainMenuAdministrator();
        } else {
            InputHandler.invalidArgument();
        }        
    }

    public void mainMenuStudent() { 
        menu: while (true) {
            InputHandler.resetScreen(DEFAULT_HEADER);     
            int response = InputHandler.iterateOverOptionsAndGetSelection(
                new String[] { "View Profile", "Edit Profile", "View Internships", "Manage Resumes", "Print Resumes to File", "Logout" }
            );
            switch (response) {
                case (0): this.viewProfile(this.user); break;
                case (1): this.editProfile(this.user); break;
                case (2): this.viewInternsips(); break;
                case (3): this.managerModAndDel(new ResumeManager(this.user.getUuid())); break;
                case (4): this.printResumesToFile(new ResumeManager(this.user.getUuid())); break;
                case (5): this.logout(); break menu;
                default: InputHandler.invalidArgument(); break;
            }
            this.user = DatabaseHandler.getUser(this.user.getUuid());
        } 
    }

    public void mainMenuEmployer() {     
        menu: while (true) {
            InputHandler.resetScreen(DEFAULT_HEADER);     
            int response = InputHandler.iterateOverOptionsAndGetSelection(
                new String[] { "View Profile", "Edit Profile", "Manage Internships", "Manage Applications", "Logout" }
            );
            switch (response) {
                case (0): this.viewProfile(this.user); break;
                case (1): this.editProfile(this.user); break;
                case (2): this.managerModAndDel(new InternshipManager(this.user.getUuid())); break;
                case (3): this.managerDel(new ApplicationManager(this.user.getUuid())); break;
                case (4): this.logout(); break menu;
                default: InputHandler.invalidArgument(); break;
            }
            this.user = DatabaseHandler.getUser(this.user.getUuid());
        } 
    }
  
    public void mainMenuAdministrator() {
        menu: while (true) {
            InputHandler.resetScreen(DEFAULT_HEADER);     
            int response = InputHandler.iterateOverOptionsAndGetSelection(
                new String[] { "View Profile", "Edit Profile", "Manage Users", "Create Administrator", "Logout" }
            );
            switch (response) {
                case (0): this.viewProfile(this.user); break;
                case (1): this.editProfile(this.user); break;
                case (2): this.managerModAndDel(new UserManager()); break;
                case (3): this.createNewAdmin(); break;
                case (4): this.logout(); break menu;
                default: InputHandler.invalidArgument(); break;
            }
            this.user = DatabaseHandler.getUser(this.user.getUuid());
        } 
    }

    private boolean login() {
        InputHandler.resetScreen("Enter your Email and Password");
        for (int i = 0; i < MAX_LOGIN_ATTEMPTS; i++) {            
            String email = InputHandler.getInputAsString("Email");
            String password = InputHandler.getInputAsString("Password");
            String uuid = DatabaseHandler.login(email, password);
            if (!uuid.equals("")) {
                this.user = DatabaseHandler.getUser(uuid);
                return true;
            } else { 
                System.out.println("Wrong email/password combo.");
            }
        }         
        return false;
    }
    
    private void signUp() {
        InputHandler.resetScreen("What Kind of Account Would You Like to Make");
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

    private void logout() {
        this.user = null;
    }

    private void exit() {
        InputHandler.resetScreen("Goodbye");
        System.exit(0);
    }

    private void viewProfile(User user) {
        InputHandler.resetScreen("Your Profile");
        System.out.println(user.toString());
        InputHandler.waitForEnter();
    }

    private void editProfile(User user) {
        InputHandler.resetScreen("Edit Profile");
        UserManager.editProfile(user);
    }    

    private void printResumesToFile(ResumeManager resumeManager) {
        Student student = DatabaseHandler.getStudent(this.user.getUuid());
        if (student.getResumes().size() == 0) return;
        int resumeIndex = InputHandler.waitForSelectionWithQuit("Select which resume you want to use", resumeManager.getListSize());
        if (resumeIndex == -1) return; 
        String fileName = InputHandler.getInputAsString("Enter the filename you want to save the resume as.");
        int ending = fileName.indexOf(".");
        if (ending != -1) fileName = fileName.substring(0, ending);
        fileName += ".txt";
        try {
            BufferedWriter writer = new BufferedWriter(new FileWriter(fileName));
            writer.write(resumeManager.getResumeFileFormat(resumeIndex) + "\n" + student.toStringNoUUID());
            writer.close();
        } catch (Exception e) {
            e.printStackTrace();
            System.exit(1);
        }
        InputHandler.waitForEnter();
    }

    private void viewInternsips() {
        InternshipListings internshipListings = new InternshipListings();
        menu: while (true) {
            InputHandler.resetScreen();   
            System.out.println(internshipListings.table() + "\n");
            InputHandler.printHeader(DEFAULT_HEADER);
            int response = InputHandler.iterateOverOptionsAndGetSelectionWithQuit(
                new String[] { 
                    "View Internship", "Change Page", "Sort Internships", "Filter Internships",
                    "Apply to Internships"            
                }
            );
            switch (response) {
                case -1: break menu;
                case 0: this.viewTableable(internshipListings); break;
                case 1: this.changeTablePage(internshipListings); break;
                case 2: this.sortInternships(internshipListings); break;
                case 3: this.filterInternships(internshipListings); break;
                case 4: this.applyToInternship(internshipListings); break;
                default: InputHandler.invalidArgument(); break;
            }        
        }        
    }

    private void sortInternships(InternshipListings internshipListings) {
        InputHandler.resetScreen("Sort by One of the Options, Ascending or Descending");     
        int column = InputHandler.iterateOverOptionsAndGetSelectionWithQuit(internshipListings.getColumns());
        if (column == -1) return;
        int direction = InputHandler.iterateOverOptionsAndGetSelectionWithQuit(internshipListings.getDirections());
        if (direction == -1) return;        
        internshipListings.sort(column, direction);
    }

    private void filterInternships(InternshipListings internshipListings) {
        InputHandler.printHeader("Filter by one of the Options.  Press Enter alone to clear the filter."); 
        String filter = InputHandler.getInputAsString("Filter for");
        internshipListings.filter(filter);
    }

    private void applyToInternship(InternshipListings internshipListings) {
        Student student = (Student) user;

        if (student.getResumes().size() == 0) return;
        int toApply = InputHandler.waitForSelectionWithQuit("Select the Internship to Apply for", internshipListings.getSize());
        if (toApply == -1) return; 
        String internshipUuid = internshipListings.apply(toApply);

        ResumeManager resumeManager = new ResumeManager(student.getUuid());
        InputHandler.resetScreen();        
        System.out.println(resumeManager.table());
        int resumeIndex = InputHandler.waitForSelectionWithQuit("Select which resume you want to use", resumeManager.getListSize());
        if (resumeIndex == -1) return; 

        Application application = new Application(
            student.getUuid(),
            student.getResumes().get(resumeIndex),
            internshipUuid
        );
        DatabaseHandler.setApplication(application);

        InputHandler.resetScreen();        
        System.out.println("Successfully created application.");
        InputHandler.waitForEnter();
    }

    public <ManagedTable extends Table & Modifiable & Deletable> void managerModAndDel(ManagedTable managedTable) {
        menu: while (true) {
            InputHandler.resetScreen();
            System.out.println(managedTable.table());
            System.out.println();
            InputHandler.printHeader(DEFAULT_HEADER);
            int response = InputHandler.iterateOverOptionsAndGetSelectionWithQuit(
                new String[] { 
                    ("View " + managedTable.getTypeSingular()),
                    ("Change Page"),
                    (managedTable.getTypeSingular() + " Builder"),
                    (managedTable.getTypeSingular() + " Editor"),
                    ("Delete " + managedTable.getTypeSingular())            
                }
            );
            switch (response) {
                case -1: break menu;
                case 0: this.viewTableable(managedTable); break;
                case 1: this.changeTablePage(managedTable); break;
                case 2: this.buildTableable(managedTable); break;
                case 3: this.editTableable(managedTable); break;
                case 4: this.deleteTableable(managedTable); break;
                default: InputHandler.invalidArgument(); break;
            }        
        }    
    }

    public <ManagedTable extends Table & Deletable> void managerDel(ManagedTable managedTable) {
        menu: while (true) {
            InputHandler.resetScreen();
            System.out.println(managedTable.table());
            System.out.println();
            InputHandler.printHeader(DEFAULT_HEADER);
            int response = InputHandler.iterateOverOptionsAndGetSelectionWithQuit(
                new String[] { 
                    ("View " + managedTable.getTypeSingular()),
                    ("Change Page"),
                    ("Delete " + managedTable.getTypeSingular())            
                }
            );
            switch (response) {
                case -1: break menu;
                case 0: this.viewTableable(managedTable); break;
                case 1: this.changeTablePage(managedTable); break;
                case 2: this.deleteTableable(managedTable); break;
                default: InputHandler.invalidArgument(); break;
            }        
        }    
    }

    private void viewTableable(Table table) {
        int toView = InputHandler.waitForSelectionWithQuit("Select the " + table.getTypeSingular() + " you want to view", table.getListSize());
        if (toView == -1) return;
        Tableable tableable = table.view(toView);
        InputHandler.resetScreen("View " + table.getTypeSingular());
        InputHandler.printSpacer(table.getTypeSingular());
        System.out.println(tableable.toView());
        InputHandler.waitForEnter();
    }

    private void changeTablePage(Table table) {         
        InputHandler.resetScreen("Change the page.");     
        int page = table.getCurrentPage();
        int lastPage = table.getLastPage();
        if (page == 0) {
            int response = InputHandler.iterateOverOptionsAndGetSelectionWithQuit(
                new String[] { "Go Forward One Page" }
            );
            switch (response) {
                case (-1): break;            
                case (0):
                    page++;
                    if (page > lastPage) page = lastPage;
                    break;
                default: InputHandler.invalidArgument(); break;
                }
        } else if (page == lastPage) {
            int response = InputHandler.iterateOverOptionsAndGetSelectionWithQuit(
                new String[] { "Go Back One Page" }
            );
            switch (response) {
                case (-1): break;            
                case (0):
                    page--;
                    if (page < 0) page = 0;
                    break;
                default: InputHandler.invalidArgument(); break;
                }
        } else {
            int response = InputHandler.iterateOverOptionsAndGetSelectionWithQuit(
                new String[] { "Go Back One Page", "Go Forward One Page" }
            );
            switch (response) {
                case (-1): break;            
                case (0):
                    page--;
                    if (page < 0) page = 0;
                    break;
                case (1):
                    page++;
                    if (page > lastPage) page = lastPage;
                    break;
                default: InputHandler.invalidArgument(); break;
            }
        }
        table.paginator(page);
    }

    private <ManagedTable extends Table & Modifiable> void buildTableable(ManagedTable managedTable) {
        InputHandler.resetScreen(managedTable.getTypeSingular() + "Builder"); 
        managedTable.builder();                   
        managedTable.updateList();
    }

    private <ManagedTable extends Table & Modifiable> void editTableable(ManagedTable managedTable) {
        InputHandler.resetScreen("Edit " + managedTable.getTypeSingular());
        int toEdit = InputHandler.waitForSelectionWithQuit(
            "Select which " + managedTable.getTypeSingular() + " you want to edit", managedTable.getListSize()
        );        
        if (toEdit == -1) return;    
        managedTable.edit(toEdit);
    }

    private <ManagedTable extends Table & Deletable> void deleteTableable(ManagedTable managedTable) {
        int toDelete = InputHandler.waitForSelectionWithQuit(
            "Select which " + managedTable.getTypeSingular() + " you want to delete", managedTable.getListSize()
            );
            if (toDelete == -1) return;    
        while (true) {
            InputHandler.printSpacer("Enter q to quit or yes to delete " + managedTable.getTypeSingular() + ".");
            String response = InputHandler.getInputAsString();
            if (response.equalsIgnoreCase("q")) {
                break;
            } else if (response.equalsIgnoreCase("yes")) {
                managedTable.delete(toDelete);
                managedTable.updateList();
                break;
            }
        }
    }

    private void createNewAdmin() {
        InputHandler.resetScreen("Please Fill Out The Information For The New Administrator.");                    
        if (!InputHandler.getInputAsYesOrNo("Would you like to create a new manager?")) return;
        String email = InputHandler.getAndVerifyInput("Email");
        String password = InputHandler.getAndVerifyInput("Password");
        String phoneNumber = InputHandler.getInputAsString("Phone Number");
        String city = InputHandler.getInputAsString("City");
        String state = InputHandler.getInputAsString("State");
        Administrator administrator = new Administrator(email, phoneNumber, password, city, state,
            this.user.getUuid(),
            InputHandler.getInputAsString("username")
        );
        DatabaseHandler.signUp(administrator);
    }
}