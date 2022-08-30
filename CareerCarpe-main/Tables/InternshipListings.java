package Tables;
import java.util.ArrayList;
import java.util.Collections;
import java.util.Comparator;

import Data.Internship;
import Handlers.DatabaseHandler;

/**
 * A class that wil extend the abstract class of table to
 * set up the display for the internship listings.
 */
public class InternshipListings extends Table {

    private static final String[] DIRECTIONS = { "Ascending", "Descending" };
    
    public static Internship internship;
    private ArrayList<Internship> internships;
    private ArrayList<Internship> filtered;
    private String filter;
    private int sortingByColumn;
    private int sortingByDirection;

    /**
     * An intership listings public accessor class that will grab the super class
     * of table before intializing it to fit the internship listings
     * display.
     */
    public InternshipListings() {
        super(
            new String[] { "Internship Listing", "Internship Listings" },
            new String[] { "Title", "PayRate", "Hrs/Wk", "Num Wks", "Payout" }, 
            new int[] { 48, 8, 8, 8, 16 }
        );
        this.internships = DatabaseHandler.getDisplayedInternships();
        this.updateFilterd(this.internships);
        this.filter = "";
        this.sort(0, 0);
        this.paginator(0);
    }
    public static Internship getInstance() {
        if(internship == null) {
            return null;
        }
        return internship;
    }
    
    /**
     * To get the directions and then store them within a string array.
     * @return directions
     */
    public String[] getDirections() {
        return DIRECTIONS;
    }

    /**
     * To get the size of the internships array.
     * @return internships array size
     */
    public int getSize() {
        return this.internships.size();
    }

    /**
     * A way to sort all the parts of the internship information.
     * @param column how many columns there are
     * @param direction how many directions there are
     */
    public void sort(int column, int direction) {
        this.sortingByColumn = column;
        this.sortingByDirection = direction;
        Comparator comparator = null;
        switch (column) {
            case 0:
                comparator = new SortByTitle();
                break;
            case 1:
                comparator = new SortByPayRate();
                break;
            case 2:
                comparator = new SortByHoursPerWeek();
                break;
            case 3:
                comparator = new SortByNumberOfWeeks();
                break;
            case 4:
                comparator = new SortByTotalPayOut();
                break;
            default:
        }
        if (direction == 0) {
            Collections.sort(filtered, comparator);
        } else {
            Collections.sort(filtered, Collections.reverseOrder(comparator));
        }   
        this.paginator(0);     
    }

    /**
     * A filter class that can take user input and filter by that.
     * @param filter what to filter the interships by
     */
    public void filter(String filter) {
        if (filter.isEmpty() || filter.isBlank()) {
            this.filter = "";
            this.updateFilterd(this.internships);
        } else {
            this.filter = filter; 
            ArrayList<Internship> updatedFilterd = new ArrayList<Internship>();
            for (Internship internship : this.internships) {
                if (internship.getTitle().contains(filter) || internship.getDescription().contains(filter)) {
                    updatedFilterd.add(internship);
                }  
            }
            this.updateFilterd(updatedFilterd);                    
        }
        this.paginator(0);
    } 

    /**
     * A way to view the internships by the calling the class internship.
     * @param toView a way to view all the internships
     * @return the filtered version of the internships
     */
    public Internship view(int toView) {
        return this.filtered.get(toView);        
    }

    /**
     * A way to apply to an intership.
     * @param toApply a variable to store the int for applying
     * @return the filtered version of applying while looking at the specific 
     * space within the location of toApply and grabbing the 
     * uuid of it
     */
    public String apply(int toApply) {
        return this.filtered.get(toApply).getUuid();        
    }

    /**
     * A way to print out the different table rows.
     * @return the string with the result of the table rows
     * if there is any
     */
    public String tableRows() { 
        String returnValue = "";
        if (this.internships.size() == 0) {
            returnValue += "No internships have been listed yet.\n";
            returnValue += this.printRowSeparators();
            return returnValue;
        } else if (this.filtered.size() == 0) {
            returnValue +=  "No internships found for filter: \"" + this.filter + "\"\n";
            returnValue += this.printRowSeparators();
            return returnValue;
        }

        int displayedSize = this.getToIndex() - this.getFromIndex() + 1;
        Internship[] displayed = new Internship[displayedSize];  
        for (int i = this.getFromIndex(), j = 0; i <= this.getToIndex(); i++, j++) {
            displayed[j] = this.filtered.get(i);
        }      

        returnValue += this.printColumnHeaders();
        returnValue += "\n";
        returnValue += this.printRowSeparators();
        for (int i = 0; i < displayedSize; i++) {
            String[] columns = new String[] 
            {
                ((i + getFromIndex() + 1) + ". " + displayed[i].getTitle()),
                String.valueOf(displayed[i].getPayRate()),
                String.valueOf(displayed[i].getHoursPerWeek()), 
                String.valueOf(displayed[i].getNumberOfWeeks()), 
                String.valueOf(displayed[i].getTotalPayout()),
            }; 
            returnValue += "\n";
            returnValue += this.printColumns(columns);
            returnValue += "\n";
            returnValue += this.printRowSeparators();
        }        
        return returnValue;
    }

    /**
     * A way to update the list while also grabbing the new
     * list size and setting it.
     */
    public void updateList() {
        this.setListSize(this.filtered.size());
    }

    /**
     * A way to update the ArrayList of filtered interrnships.
     * @param filtered the array list regarding the filtered internships
     */
    private void updateFilterd(ArrayList<Internship> filtered) {
        this.filtered = filtered;
        this.updateList();
    }

    /**
     * A private class that will sort the internships by title.
     */
    private class SortByTitle implements Comparator<Internship> {
        /**
         * A public class that will compare titles between two different
         * internships.
         * @return the title of both the internships
         */
        public int compare(Internship a, Internship b) {
            return a.getTitle().compareTo(b.getTitle());
        }
    }

    /**
     * A private class that will sort the internships by pay rate.
     */
    private class SortByPayRate implements Comparator<Internship> {
        /**
         * A public class that will compare pay rates between
         * two different internships.
         * @return the pay rate difference
         */
        public int compare(Internship a, Internship b) {
            return (int) (a.getPayRate() - b.getPayRate());
        }
    }

    /**
     * A private class that will sort the internships by hours per week.
     */
    private class SortByHoursPerWeek implements Comparator<Internship> {
        /**
         * A public class that will compare the hours between internship a
         * and internship b.
         * @return the difference of hours between internships
         */
        public int compare(Internship a, Internship b) {
            return a.getHoursPerWeek() - b.getHoursPerWeek();
        }
    }

    /**
     * A private class to sort the internships by duration.
     */
    private class SortByNumberOfWeeks implements Comparator<Internship> {
        /**
         * A public class that will compare the number of weeks of the internships.
         * @return the difference of number of weeks
         */
        public int compare(Internship a, Internship b) {
            return a.getNumberOfWeeks() - b.getNumberOfWeeks();
        }
    }
     /**
     * A private class to sort the internships by total payout.
     */
    private class SortByTotalPayOut implements Comparator<Internship> {
        /**
         * A public class that will compare the total payout of the internships.
         * @return the difference of total payout
         */
        public int compare(Internship a, Internship b) {
            return a.getTotalPayout() - b.getTotalPayout();
        }
    }

    /**
     * Overriding the table footer and allowing to print out a specific footer
     * for this method.
     * @return the string with all the different information that would
     * go into the footer for this specific method
     */
    @Override 
    protected String tableFooter() {
        String returnValue = "";
        // returnValue += "Page " + (this.getCurrentPage() + 1) + " of " + (this.getLastPage() + 1);
        returnValue += super.tableFooter();
        returnValue += " | Sorting by: " + this.getColumns()[this.sortingByColumn] + " " + DIRECTIONS[this.sortingByDirection];
        if (!this.filter.isEmpty()) {
            returnValue += " | Filtering for: \"" + this.filter + "\"";
        }
        return returnValue;
    }
}