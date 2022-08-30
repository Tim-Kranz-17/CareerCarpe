
 package Tables;
/**
 * An interface that will detect whether something is deleteable.
 */
public interface Deletable {
    /**
     * An abstract class that can be modified to delete different 
     * entries.
     * @param toDelete a way for the code to look for a specific spot 
     * in the array to  delete
     */
    public void delete(int toDelete);

}
