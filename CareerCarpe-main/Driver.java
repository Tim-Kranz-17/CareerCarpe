/**
 * Runs the Career Carpe program
 */
public class Driver {

    public static void main(String[] args) {
        UserInterface userInterface = UserInterface.getSingleton();
        userInterface.welcome();        
    }
}
