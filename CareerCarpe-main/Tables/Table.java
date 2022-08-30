package Tables;

import Data.Tableable;

public abstract class Table {

    private static final int ROW_LENGTH = 8 * 15;
    private static final int ITEMS_PER_PAGE = 5;

    private String[] type;
    private String[] columns;
    private int[] columnLengths;
    private int listSize;
    private int page;
    private int toIndex;
    private int fromIndex;

    protected Table(String[] type, String[] columns, int[] columnLenths) {
        this.type = type;
        this.columns = columns;
        this.setColumnLengths(columnLenths);
        this.listSize = 0;
        this.paginator(0);
    }

    public String getTypeSingular() {
        return this.type[0];
    }

    public String getTypePlural() {
        return this.type[1];
    }

    public String[] getColumns() {
        return this.columns;
    }

    protected int getToIndex() {
        return this.toIndex;
    }

    protected int getFromIndex() {
        return this.fromIndex;
    }
        
    public int getListSize() {
        return this.listSize;
    }

    protected void setListSize(int listSize) {
        this.listSize = listSize;
        this.paginator(0);
    }

    public int getCurrentPage() {
        return this.page;
    }

    public int getLastPage() {
        int returnValue;
        if (this.listSize == 0) return 0;
        returnValue = ((this.listSize - 1) / ITEMS_PER_PAGE);
        return returnValue;
    }

    protected int getLengthOfRow() {
        return ROW_LENGTH;
    }

    protected int[] getColumnLengths() {
        return this.columnLengths;
    } 

    private void setColumnLengths(int[] columnLengths) {
        int length = columnLengths.length;
        int diff = ROW_LENGTH;
        for (int i = 0; i < length ; i++) {
            diff -= columnLengths[i];
        }

        if (diff > 0) {
            for (int i = 0; diff > 0; i = ++i % length) {                
                if (columnLengths[i] + 1 <= ROW_LENGTH) {
                    columnLengths[i] += 1;
                    diff -= 1;
                }   
            }            
        } else if (diff < 0) {
            for (int i = length - 1; diff < 0; i = ((i - 1) % length < 0) ? (length - 1) : ((i - 1) % length)) {                
                if (columnLengths[i] - 1 >= 1) {                
                    columnLengths[i] -= 1;
                    diff += 1;      
                }
            }          
        }
        this.columnLengths = columnLengths;
    } 

    public void paginator(int page) {
        if (page > this.getLastPage()) {
            System.out.println("Page " + page + " (0-based) is out of bounds for internships with size of " + this.listSize);
            System.exit(1);
        }
        this.page = page;
        this.fromIndex = this.page * ITEMS_PER_PAGE;
        this.toIndex = this.fromIndex + ITEMS_PER_PAGE - 1;
        if (this.toIndex >= this.listSize) this.toIndex = this.listSize - 1;
    }

    public String table() {
        String returnValue = "";
        returnValue += this.tableTitle() + "\n";
        returnValue += this.tableRows()+ "\n";
        returnValue += this.tableFooter();
        return returnValue;
    }

    protected String printColumnHeaders() {
        return printColumns(this.columns);
    }

    protected String printColumns(String[] columns) {
        String returnValue = "";
        for (int i = 0; i < this.columns.length; i++) {
            returnValue += this.printToMaxNumber(columns[i], i);
        }   
        return returnValue;
    }

    private String printToMaxNumber(String string, int columnIndex) {
        String returnValue = string;
        if (string.length() + 3 > this.columnLengths[columnIndex]) {
            returnValue = string.substring(0, this.columnLengths[columnIndex] - 6);
            returnValue += "...   ";
        } else {
            for (int i = string.length(); i < this.columnLengths[columnIndex]; i++) {
                returnValue += " ";  
            }
        }
        return returnValue;
    }

    protected String printRowSeparators() {
        String returnValue = "";
        for (int i = 0; i < ROW_LENGTH; i++) {
            returnValue += "-";
        }
        return returnValue;
    }

    private String tableTitle() {
        String returnValue = "";
        String title = this.getTypePlural().toUpperCase();
        int count = 0;
        int halfLength = ((ROW_LENGTH - title.length()) / 2);
        for (; count < halfLength; count++) {
            returnValue += "-";
        }
        returnValue += title;
        count += title.length();
        for (; count < ROW_LENGTH; count++) {
            returnValue += "-";
        }
        return returnValue;
    }

    protected String tableFooter() {
        String returnValue = "";
        returnValue += "Page " + (this.page + 1) + " of " + (this.getLastPage() + 1);
        if (this.listSize == 0) return returnValue;        
        if (this.fromIndex == this.toIndex) {
            returnValue += " | Showing " + this.type[0] + " " + (this.fromIndex + 1);
        } else {
            returnValue += " | Showing " + this.type[1] + " from " + (this.fromIndex + 1) + " to " + (this.toIndex + 1);
        } 
        return returnValue;
    }

    public abstract String tableRows();

    public abstract Tableable view(int toView);

    public abstract void updateList();
}
