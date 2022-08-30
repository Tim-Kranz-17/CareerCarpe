package Tables;

/**
 * An interface to allow the the different methods to be able
 * to change without having to restart the entire program.
 */
public interface Modifiable {

    /**
     * A builder that can be changed depending on which class
     * needs it.
     */
    public void builder();

    /**
     * A way to edit a class.
     * @param toEdit a specific point that would need to be
     * edited
     */
    public void edit(int toEdit);
    
}
