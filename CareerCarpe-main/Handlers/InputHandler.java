package Handlers;
import java.util.ArrayList;
import java.util.Scanner;


/**
 * handles input for the program.
 */
public class InputHandler {

    /**
     * resets the screen.
     */
    public static void resetScreen() {
        System.out.print("\033[H\033[2J");
        System.out.print("\f");
        System.out.flush();
        InputHandler.printACIIArt("Career Carpe");
    }
    /**
     * resets the screen.
     * @param header, is used as initial text for the driver.
     */
    public static void resetScreen(String header) {
        System.out.print("\033[H\033[2J");
        System.out.flush();
        InputHandler.printACIIArt("Career Carpe");
        InputHandler.printHeader(header);
    }
    /**
     * prints the header of the screen.
     * @param header, the header to be printed.
     */
    public static void printHeader(String header) {
        System.out.println("< **** " + header + " **** >\n");        
    }
    /**
     * prints different ascii text depending on the input text.
     * @param text, values to be referenced to determine which ascii text to be printed.
     */
    public static void printACIIArt(String text) {
        //https://fsymbols.com/generators/carty/
        switch (text) {
            case "Career Carpe":
                System.out.println(
                    "░█████╗░░█████╗░██████╗░███████╗███████╗██████╗░  ░█████╗░░█████╗░██████╗░██████╗░███████╗\n" +
                    "██╔══██╗██╔══██╗██╔══██╗██╔════╝██╔════╝██╔══██╗  ██╔══██╗██╔══██╗██╔══██╗██╔══██╗██╔════╝\n" +
                    "██║░░╚═╝███████║██████╔╝█████╗░░█████╗░░██████╔╝  ██║░░╚═╝███████║██████╔╝██████╔╝█████╗░░\n" +
                    "██║░░██╗██╔══██║██╔══██╗██╔══╝░░██╔══╝░░██╔══██╗  ██║░░██╗██╔══██║██╔══██╗██╔═══╝░██╔══╝░░\n" +
                    "╚█████╔╝██║░░██║██║░░██║███████╗███████╗██║░░██║  ╚█████╔╝██║░░██║██║░░██║██║░░░░░███████╗\n" +
                    "░╚════╝░╚═╝░░╚═╝╚═╝░░╚═╝╚══════╝╚══════╝╚═╝░░╚═╝  ░╚════╝░╚═╝░░╚═╝╚═╝░░╚═╝╚═╝░░░░░╚══════╝\n"
                );
                break;
            case "Student":
                System.out.println(
                    "░██████╗████████╗██╗░░░██╗██████╗░███████╗███╗░░██╗████████╗\n" +
                    "██╔════╝╚══██╔══╝██║░░░██║██╔══██╗██╔════╝████╗░██║╚══██╔══╝\n" +
                    "╚█████╗░░░░██║░░░██║░░░██║██║░░██║█████╗░░██╔██╗██║░░░██║░░░\n" +
                    "░╚═══██╗░░░██║░░░██║░░░██║██║░░██║██╔══╝░░██║╚████║░░░██║░░░\n" +
                    "██████╔╝░░░██║░░░╚██████╔╝██████╔╝███████╗██║░╚███║░░░██║░░░\n" +
                    "╚═════╝░░░░╚═╝░░░░╚═════╝░╚═════╝░╚══════╝╚═╝░░╚══╝░░░╚═╝░░░\n"
                );
                break;
            case "Employer":
                System.out.println(
                    "███████╗███╗░░░███╗██████╗░██╗░░░░░░█████╗░██╗░░░██╗███████╗██████╗░\n" +
                    "██╔════╝████╗░████║██╔══██╗██║░░░░░██╔══██╗╚██╗░██╔╝██╔════╝██╔══██╗\n" +
                    "█████╗░░██╔████╔██║██████╔╝██║░░░░░██║░░██║░╚████╔╝░█████╗░░██████╔╝\n" +
                    "██╔══╝░░██║╚██╔╝██║██╔═══╝░██║░░░░░██║░░██║░░╚██╔╝░░██╔══╝░░██╔══██╗\n" +
                    "███████╗██║░╚═╝░██║██║░░░░░███████╗╚█████╔╝░░░██║░░░███████╗██║░░██║\n" +
                    "╚══════╝╚═╝░░░░░╚═╝╚═╝░░░░░╚══════╝░╚════╝░░░░╚═╝░░░╚══════╝╚═╝░░╚═╝\n"
                );
                break;
            case "Administrator":
                System.out.println(
                    "░█████╗░██████╗░███╗░░░███╗██╗███╗░░██╗██╗░██████╗████████╗██████╗░░█████╗░████████╗░█████╗░██████╗░\n" +
                    "██╔══██╗██╔══██╗████╗░████║██║████╗░██║██║██╔════╝╚══██╔══╝██╔══██╗██╔══██╗╚══██╔══╝██╔══██╗██╔══██╗\n" +
                    "███████║██║░░██║██╔████╔██║██║██╔██╗██║██║╚█████╗░░░░██║░░░██████╔╝███████║░░░██║░░░██║░░██║██████╔╝\n" +
                    "██╔══██║██║░░██║██║╚██╔╝██║██║██║╚████║██║░╚═══██╗░░░██║░░░██╔══██╗██╔══██║░░░██║░░░██║░░██║██╔══██╗\n" +
                    "██║░░██║██████╔╝██║░╚═╝░██║██║██║░╚███║██║██████╔╝░░░██║░░░██║░░██║██║░░██║░░░██║░░░╚█████╔╝██║░░██║\n" +
                    "╚═╝░░╚═╝╚═════╝░╚═╝░░░░░╚═╝╚═╝╚═╝░░╚══╝╚═╝╚═════╝░░░░╚═╝░░░╚═╝░░╚═╝╚═╝░░╚═╝░░░╚═╝░░░░╚════╝░╚═╝░░╚═╝\n"
                );
                break;
            default:
                InputHandler.invalidArgument();
                break;
        }
    }
    /**
     * used as a means of implementing the printSpae header param method.
     */
    public static void printSpacer() {
        InputHandler.printSpacer("");
    }

    /**
     * used to print a spacer element between headers.
     * @param header, the header which the spacers are placed in between.
     */
    public static void printSpacer(String header) {
        System.out.println();        
        if (!header.isEmpty()) {
        System.out.println(header);
        }
        System.out.println("<:----------------------------------------------------:>");
    }

    /**
     * waits for input from user to return a given value.
     */
    public static void waitForEnter() {
        InputHandler.printSpacer();
        System.out.println("Press enter to return.");
        InputHandler.getInputAsString();
    }
    /**
     * waits for the selection along with a quit function.
     * @param message, message to be printed
     * @param size, size of the message.
     * @return returnValue, an integer value which determines if a value is correct or not.
     */
    public static int waitForSelectionWithQuit(String message, int size) {
        int returnValue = -1;
        while (true) {
            InputHandler.printSpacer();
            System.out.println(message + ", or press q to quit.");
            try {
                String response = InputHandler.getInputAsString();
                if (response.trim().equalsIgnoreCase("q")) {
                    returnValue = -1;
                    break;
                }
                returnValue = Integer.parseInt(response);
                returnValue--;
            } catch (Exception e) {
                System.out.println("You entered an invalid number. Try again.");
                continue;
            }
            if (returnValue < 0 || returnValue >= size) {
                System.out.println("You entered an invalid number. Try again.");
                continue;
            } else {
                break;
            }
        }
        return returnValue;
    }

    /**
     * iterates over the different options and the gets the selection from user.
     * @param options, the values to be iterated over.
     * @return returnValue, an integer value which determines if a value is correct or not.
     */
    public static int iterateOverOptionsAndGetSelection(String[] options) {
        int returnValue = -1;
        for (int i = 0; i < options.length; i++) {
            System.out.println((i + 1) + ". " + options[i]);
        }
        while (true) {
            InputHandler.printSpacer();
            System.out.println("Select your option, then press enter.");
            try {
                returnValue = Integer.parseInt(InputHandler.getInputAsString());
                returnValue--;
            } catch (Exception e) {
                System.out.println("You entered an invalid number. Try again.");
                continue;
            }
            if (returnValue < 0 || returnValue >= options.length) {
                System.out.println("You entered an invalid number. Try again.");
                continue;
            } else {
                break;
            }
        }
        return returnValue;
    }

    /**
     * iterates over the different options and the gets the selection from user along with a quit option.
     * @param options, the values to be iterated over.
     * @return returnValue, an integer value which determines if a value is correct or not.
     */
    public static int iterateOverOptionsAndGetSelectionWithQuit(String[] options) {
        int returnValue = -1;
        for (int i = 0; i < options.length; i++) {
            System.out.println((i + 1) + ". " + options[i]);
        }
        while (true) {
            InputHandler.printSpacer();
            System.out.println("Select your option or q to quit, then press enter.");
            try {
                String response = InputHandler.getInputAsString();
                if (response.trim().equalsIgnoreCase("q")) {
                    returnValue = -1;
                    break;
                }
                returnValue = Integer.parseInt(response);
                returnValue--;
            } catch (Exception e) {
                System.out.println("You entered an invalid number. Try again.");
                continue;
            }
            if (returnValue < 0 || returnValue >= options.length) {
                System.out.println("You entered an invalid number. Try again.");
                continue;
            } else {
                break;
            }
        }
        return returnValue;
    }
    /**
     * gets the inputs as an array list.
     * @param input, the value to be inputed.
     * @return returnValue, an integer value which determines if a value is correct or not.
     */
    public static ArrayList<String> getInputAsArrayList(String input) {
        ArrayList<String> returnValue = new ArrayList<String>();
        System.out.println("Enter your " + input + " as individual words or phrases.  Enter a blank line to finish.");
        while (true) {            
            String response = InputHandler.getInputAsString();
            if (response.isEmpty()) {
                break;
            }
            returnValue.add(response);
        }
        return returnValue;
    }

    /**
     * gets and verifies the input
     * @param input, the value to be inputed.
     * @return returnValue, the string value to be compared for verifying input.
     */
    public static String getAndVerifyInput(String input) {
        String returnValue = null;
        while (true) {
            returnValue = InputHandler.getInputAsString(input);
            String verifiedInput = InputHandler.getInputAsString("Verfiy " + input);
            if (returnValue.equals(verifiedInput)) break;            
            System.out.println(input + "s do not match bad boi uWu.");            
        }
        return returnValue;
    }

    /**
     * gets input as an integer
     * @param input, the value to be inputed.
     * @return returnValue, an integer version of the string input
     */
    public static int getInputAsInteger(String input) {
        int returnValue = -1;
        while (true) {
            System.out.println("Enter your value as an integer.");
            try {
                returnValue = Integer.parseInt(InputHandler.getInputAsString(input));
                break;
            } catch (Exception e) {
                continue;
            }
        }
        return returnValue;
    }    
    /**
     * gets input as double
     *@param input, the value to be inputed.
     *@return returnValue, a double version of the string input.
     */

    public static double getInputAsDouble(String input) {
        double returnValue = -1;
        while (true) {
            System.out.println("Enter your value as an double.");
            try {
                returnValue = Double.parseDouble(InputHandler.getInputAsString(input));
                break;
            } catch (Exception e) {
                continue;
            }
        }
        return returnValue;
    }

    /**
     * gets input as yes or no 
     * @param input, the value to be inputed.
     * @return boolean value of either yes or no depending on the string input.
     */
    public static boolean getInputAsYesOrNo(String input) {
        while (true) {
            System.out.println("Enter yes or no.");            
            String response = InputHandler.getInputAsString(input);
            if (response.equalsIgnoreCase("yes")) {
                return true;
            } else if (response.equalsIgnoreCase("no")) {
                return false;
            }
        }
    }

    /**
     * get input as a string.
     * @return inputed value as a string.
     */
    public static String getInputAsString() {
        return InputHandler.getInputAsString("");
    }

    /**
     * get input as a string depending on the input parameter.
     * @param input, the input which is being defined as a string.
     * @return returnValue, 
     */
    public static String getInputAsString(String input) {
        String returnValue = "";
        if (!input.isEmpty()) input += " ";
        System.out.print(input + "> ");
        Scanner scanner = new Scanner(System.in);
        try {            
            returnValue = scanner.nextLine();
            if (returnValue.isBlank()) returnValue = "";
        } catch (Exception e) {
            System.out.println("Input Handler : getInput(String input);");
            System.out.println("Error encounterd with Scanenr");
            System.exit(-1);
        }
        return returnValue;
    }
    /**
     * prints out whether an inputed value is bwoken or not. uwu.
     */
    public static void invalidArgument() {
        System.err.println("You suppwied an invawid argument that bwoke the system nwaughty uWu.");
        System.exit(1);
    }
}