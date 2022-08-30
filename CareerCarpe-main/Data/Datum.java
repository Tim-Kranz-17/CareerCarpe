package Data;
/**
 * deals with the uuid of the program
 */
public abstract class Datum {
    
    private String uuid;
    /**
     * if nothing is taken in it will make the uuid undefined
     */
    protected Datum() {
        this.uuid = "undefined";
    }
    /**
     * sets up the uuids of the program
     * @param uuid ID
     */
    protected Datum(String uuid) {
        this.uuid = uuid;
    }
    /**
     * gets uuids of the program
     * @return a uuid
     */
    public String getUuid() {
        return this.uuid;
    }
    /**
     * sets the uuids of the program
     * @param uuid ID
     */
    public void setUuid(String uuid) {
        this.uuid = uuid;
    }

}
