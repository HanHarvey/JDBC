package jdbc.ah;
import java.sql.*;
import java.util.ArrayList;
import java.util.Scanner;
/**
 * CECS 323 Opkins
 * JDBC Project
 * Book Class
 * Purpose: To be used in test file for accessing and mutating values present in the tables
 * Input: User requests info from the database
 * OutPut: Database Info
 * @author Alec and Harvey
 */

public class JDBCAH {
    //  Database credentials
    static String USER;
    static String PASS;
    static String DBNAME;
    static final String displayFormat="%-30s%-30s%-30s%-30s + \n";
    static final String JDBC_DRIVER = "org.apache.derby.jdbc.ClientDriver";
    static String DB_URL = "jdbc:derby://localhost:1527/";

    /* Handles null values in database*/
    public static String dispNull (String input) 
    {
        if (input == null || input.length() == 0)
            return "N/A";
        else
            return input;
    }
    
   /* reorganizes the SQL statements to be executed*/
    public static ResultSet performQuery(String tableColumns, String tableName, Connection conn, Statement stmt) {
        String query = "SELECT " + tableColumns + " FROM " + tableName;
        ResultSet results = null;
       
        try {
            stmt = conn.createStatement();
            results = stmt.executeQuery(query);
        } catch (Exception ex) {
            System.out.println("Unable to perform query '" + query + "'.");
        }     
        return results;
    }
   
    /* reorganizes the SQL statements that also have a where clause*/
    public static ResultSet performWhereQuery(String tableColumns, String tableName, String whereClauseColumn, String whereClauseValue, 
            Connection conn, PreparedStatement preStmt) {
        String query = "SELECT " + tableColumns + " FROM " + tableName + " WHERE " + whereClauseColumn + " = ?";
        ResultSet results = null;
        
        try {
            preStmt = conn.prepareStatement(query);
            preStmt.setString(1, whereClauseValue);
            results = preStmt.executeQuery();
        } catch(Exception ex) {
            System.out.println("Unable to perform query '" + query + "'.");
        }
        return results;
    }
    
    /* handles user input within a given max range*/
    public static String getInputWithinRange(String prompt, Scanner reader, int max) {
        boolean inputIsInvalid = true;
        String input = "";
        
        while (inputIsInvalid) {
            System.out.println(prompt);
            input = reader.nextLine();
            
            if (input.length() <= max) {
                inputIsInvalid = false;
            }
        }
        
        return input;
    }
    
    /* handles user input within a given min - max range*/
    public static int getInputWithinRange(String prompt, Scanner reader, int rangeMin, int rangeMax) {
        boolean inputIsInvalid = true;
        int userNumChoice = -1;
        
        while (inputIsInvalid) {
            try {
                System.out.print(prompt);
                userNumChoice = reader.nextInt();
                
                if (userNumChoice < rangeMin || userNumChoice > rangeMax) {
                    throw new Exception("Invalid input out of range");
                }
                inputIsInvalid = false;
            } catch (Exception ex) {
                System.out.println("Please enter an integer within the range of " + rangeMin +
                        " and " + rangeMax + ":");
            } 
            reader.nextLine();
        }    
        return userNumChoice;
    } 
    /* handles user input within a given min - max length range*/
    public static String getInputWithinLengthRange(String prompt, Scanner reader, int rangeMin, int rangeMax) {
        boolean inputIsInvalid = true;
        String userNumChoice = "";
        
        while (inputIsInvalid) {
            try {
                System.out.print(prompt);
                userNumChoice = reader.nextLine();
                
                if (userNumChoice.length() < rangeMin || userNumChoice.length() > rangeMax) {
                    throw new Exception("Invalid input out of range");
                }
                inputIsInvalid = false;
            } catch (Exception ex) {
                System.out.println("Please enter an integer within the range of " + rangeMin +
                        " and " + rangeMax + ":");
            } 
            reader.nextLine();
        }    
        return userNumChoice;
    } 

    public static void main(String[] args) {
        //Prompt the user for the database name, and the credentials.
        //If your database has no credentials, you can update this code to
        //remove that from the connection string.
        Scanner in = new Scanner(System.in);
        DBNAME = "JDBC";        
        
        
        System.out.print("Database user name: ");
        USER = in.nextLine();
        System.out.print("Database password: ");
        PASS = in.nextLine();
        
        //Constructing the database URL connection string
        DB_URL = DB_URL + DBNAME + ";user=" + USER + ";password=" + PASS;
        Connection conn = null; //initialize the connection
        Statement stmt = null;  //initialize the statement that we're using
        PreparedStatement preStmt = null;
        
        try {
            //STEP 2: Register JDBC driver
            Class.forName("org.apache.derby.jdbc.ClientDriver");

            //STEP 3: Open a connection
            System.out.println("Connecting to database...");
            conn = DriverManager.getConnection(DB_URL);
            System.out.println("Database successfully connected.");
            boolean end = false;
            
            Scanner reader = new Scanner(System.in);
            while(!end){
                int choice = -1;
                System.out.println("Please choose from the following options by entering the corresponding number \n"
                    + "0. Exit\n"
                    + "1. List all Writing Groups\n"
                    + "2. List all Data of a Group (User's input required)\n"
                    + "3. List all Publishers\n"
                    + "4. List all Data of a Publisher (User's input required)\n"
                    + "5. List all Book Titles (Titles Only)\n"
                    + "6. List all Data of a Book (User's input required)\n"
                    + "7. Insert a new Book\n"
                    + "8. Insert a new Publisher (Followed by a replacing of an old Publisher)\n"
                    + "9. Remove a Book\n");   
                
                choice = getInputWithinRange("Enter a number from 0 - 9: ", reader, 0, 9);

                switch(choice)
                {
                    //exits
                    case 0:
                    {
                        end = true;
                        break;
                    }
                    
                    //List all writing groups
                    case 1:
                    {
                        ResultSet rs = performQuery("groupName, headWriter, yearFormed, subject", "writingGroup",conn,stmt);

                        //STEP 5: Extract data from result set
                        System.out.printf(displayFormat, "Group Name", "Head Writer", "Year Formed", "Subject");
                        while (rs.next()) 
                        {
                            //Retrieve by column name
                            String cGroupName = rs.getString("groupName");
                            String cHeadWriter = rs.getString("headWriter");
                            String cYearFormed = rs.getString("yearFormed");
                            String cSubject = rs.getString("subject");

                            //Display values
                            System.out.printf(displayFormat,
                                    dispNull(cGroupName), dispNull(cHeadWriter), 
                                    dispNull(cYearFormed), dispNull(cSubject));
                        }
                        break;
                    }
                    
                    //list all data of a group (User's input required)
                    case 2:
                    {
                        System.out.print("Please enter a group name you want shown: ");
                        String gn = reader.nextLine();
                      
                        ResultSet rs = performWhereQuery("groupName, headWriter, yearFormed,subject", "writingGroup","groupName",gn,conn,preStmt);

                        //STEP 5: Extract data from result set
                        String groupDisplayFormat = displayFormat;
                        groupDisplayFormat = groupDisplayFormat.replaceAll("30", "35");
                        System.out.printf(groupDisplayFormat, "Group Name", "Head Writer", "Year Formed", "Subject");
                        while (rs.next()) 
                        {
                            //Retrieve by column name
                            String cGroupName = rs.getString("groupName");
                            String cHeadWriter = rs.getString("headWriter");
                            String cYearFormed = rs.getString("yearFormed");
                            String cSubject = rs.getString("subject");
                            
                                // Display values
                            System.out.printf(groupDisplayFormat,
                                    dispNull(cGroupName), dispNull(cHeadWriter), 
                                    dispNull(cYearFormed), dispNull(cSubject));
                        }
                        
                        System.out.println("\nCorresponding books that " + gn + " wrote include: \n"); 
                        ResultSet bookdata = performWhereQuery("bookTitle, groupName, pubName, yearPublished, numberPages",
                                "book", "groupName", gn, conn, preStmt);
                        
                        // Extract data from book result set
                        String bookDisplayFormat = "%-30s" + displayFormat;
                        bookDisplayFormat = bookDisplayFormat.replace("30", "35");
                        System.out.printf(bookDisplayFormat, "Book Title", "Group Name","Publisher Name", 
                                "Year Published", "Number Pages");
                        while (bookdata.next()) {
                            // Retrieve by column name
                            String bookTitle = bookdata.getString("bookTitle");
                            String groupName = bookdata.getString("groupName");
                            String publisherName = bookdata.getString("pubName");
                            String yearPublished = bookdata.getString("yearPublished");
                            String numberPages = bookdata.getString("numberPages");
                            
                            // display values
                            System.out.printf(bookDisplayFormat, dispNull(bookTitle), dispNull(groupName), dispNull(publisherName), 
                                    dispNull(yearPublished), dispNull(numberPages));
                        }
                        break;
                    }
                    //List all publishers
                    case 3:
                    {
                        stmt = conn.createStatement();
                        String sql;
                        sql = "SELECT pubName, pubAddress, pubPhone, pubEmail FROM publishers";
                        ResultSet rs = stmt.executeQuery(sql);
                        // display format changed to ensure alignment for columns
                        String publisherDisplayFormat = displayFormat;
                        publisherDisplayFormat = publisherDisplayFormat.replaceAll("30", "35");

                        //STEP 5: Extract data from result set
                        System.out.printf(publisherDisplayFormat, "Publisher Name", "Publisher Address", 
                                    "Publisher Phone", "Publisher Email");
                        while (rs.next()) {
                            //Retrieve by column name
                            String cPublisherName = rs.getString("pubName");
                            String cPublisherAddress = rs.getString("pubAddress");
                            String cPublisherPhone = rs.getString("pubPhone");
                            String cPublisherEmail = rs.getString("pubEmail");

                                //Display values
                            System.out.printf(publisherDisplayFormat,
                                    dispNull(cPublisherName), dispNull(cPublisherAddress), 
                                    dispNull(cPublisherPhone), dispNull(cPublisherEmail));
                        }
                        break;
                    }
                    //List all data of a Publisher (user's input required)
                    case 4:{
                        System.out.print("Enter a publisher name: ");
                        String publisher = reader.nextLine();
                        ResultSet rs = performWhereQuery("pubName,pubAddress, pubPhone, pubEmail", "publishers",
                                "pubName", publisher, conn, preStmt);

                        //STEP 5: Extract data from result set
                        String publisherDisplayFormat = displayFormat;
                        publisherDisplayFormat = publisherDisplayFormat.replaceAll("30", "40");
                        System.out.printf(publisherDisplayFormat, "Publisher Name", "Publisher Address", 
                                    "Publisher Phone", "Publisher Email");
                        while (rs.next()) {
                            //Retrieve by column name
                            String cPublisherName = rs.getString("pubName");
                            String cPublisherAddress = rs.getString("pubAddress");
                            String cPublisherPhone = rs.getString("pubPhone");
                            String cPublisherEmail = rs.getString("pubEmail");

                                //Display values
                            System.out.printf(publisherDisplayFormat,
                                    dispNull(cPublisherName), dispNull(cPublisherAddress), 
                                    dispNull(cPublisherPhone), dispNull(cPublisherEmail));
                        }
                        
                        System.out.println("\nCorresponding books that " + publisher + " published include: \n"); 
                        ResultSet bookResults = performWhereQuery("bookTitle, groupName, pubName, yearPublished, numberPages", "book",
                            "pubName", publisher, conn, preStmt);
                        
                        // Extract data from book result set
                        String bookDisplayFormat = "%-30s" + displayFormat;
                        bookDisplayFormat = bookDisplayFormat.replace("30", "40");
                        System.out.printf(bookDisplayFormat, "Book Title", "Group Name","Publisher Name", 
                                "Year Published", "Number Pages");
                        while (bookResults.next()) {
                            // Retrieve by column name
                            String bookTitle = bookResults.getString("bookTitle");
                            String groupName = bookResults.getString("groupName");
                            String pubName = bookResults.getString("pubName");
                            String yearPublished = bookResults.getString("yearPublished");
                            String numberPages = bookResults.getString("numberPages");
                            
                            // display values
                            System.out.printf(bookDisplayFormat, dispNull(bookTitle), dispNull(groupName), dispNull(pubName), 
                                    dispNull(yearPublished), dispNull(numberPages));
                        }

                        break;
                    }
                    //List all book titles (Titles Only)
                    case 5:{
                        int numBook = 1;
                        
                        stmt = conn.createStatement();
                        String sql;
                        sql = "SELECT bookTitle FROM book";
                        ResultSet rs = stmt.executeQuery(sql);
                        //STEP 5: Extract data from result set
                        System.out.println("Book Titles");
                        while (rs.next()) {
                            //Retrieve by column name
                            String cBookTitle = rs.getString("bookTitle");

                                //Display values
                            System.out.println(numBook + ") " + dispNull(cBookTitle));
                            numBook++;
                        }
                        break;
                    }
                    //List all data of a book (user input required)
                    case 6:{
                        System.out.print("Enter a book title: ");
                        String bookTitle = reader.nextLine();
                        
                        System.out.println("Creating statement...\n");

                        String sql;
                        sql = "SELECT * "
                                + "FROM book "
                                + "NATURAL JOIN writingGroup "
                                + "NATURAL JOIN publishers "
                                + "WHERE bookTitle = ?";
                        preStmt = conn.prepareStatement(sql);
                        preStmt.setString(1, bookTitle);
                        ResultSet rs = preStmt.executeQuery();

                        //STEP 5: Extract data from result set
                        String dsplyFrmt = "%-40s%-40s%-40s%-40s%-40s%-40s%-40s%-40s%-40s%-40s%-40s\n";
                        System.out.printf(dsplyFrmt, "Book Title", "Group Name", "Publisher Name", "Year Published", "Number of Pages",
                                "Head Writer", "Year Formed", "Subject", "Publisher Address", 
                                "Publisher Phone", "Publisher Email");
                        while (rs.next()){
                            String bBookTitle = rs.getString("bookTitle");
                            String bGroupName = rs.getString("groupName");
                            String bPublisherName = rs.getString("pubName");
                            String bYearPublished = rs.getString("yearPublished");
                            String bNumberOfPages = rs.getString("numberPages");
                            String bHeadWriter = rs.getString("headwriter");
                            String bYearFormed = rs.getString("yearFormed");
                            String bSubject = rs.getString("subject");
                            String bPublisherAddress = rs.getString("pubAddress");
                            String bPublisherPhone = rs.getString("pubPhone");
                            String bPublisherEmail = rs.getString("pubEmail");

                            System.out.printf(dsplyFrmt, dispNull(bBookTitle), dispNull(bGroupName), dispNull(bPublisherName), 
                                    dispNull(bYearPublished), dispNull(bNumberOfPages), dispNull(bHeadWriter), 
                                    dispNull(bYearFormed), dispNull(bSubject), dispNull(bPublisherAddress), 
                                    dispNull(bPublisherPhone), dispNull(bPublisherEmail));
                        }
                        break;
                    }
                    //Insert a new Book
                    case 7:{
                        String bookTitle = getInputWithinRange("Please input the new Book Title: ",  reader, 60);
                        String yearPublished = getInputWithinRange("Please input the new Book year of publish: ", reader, 4);
                        System.out.print("Please input the new Book number of pages: ");
                        int numPages = reader.nextInt();
                        
                        //Making the UI List for user to specify
                        ArrayList<String> gName = new ArrayList<String>();
                        ResultSet rsGN = performQuery("groupName", "writingGroup", conn, stmt);
                        int gnNumList = 1;
                        System.out.println("Group Name List:");
                        while(rsGN.next()){
                            System.out.println(gnNumList + ") " + dispNull(rsGN.getString("groupName")));
                            gName.add(rsGN.getString("groupName"));
                            gnNumList++;
                        }
                                  
                        int gChoiceName = getInputWithinRange("Please input the number corresponding to the "
                                + "new Book Group Name from above: ", reader, 1, gName.size());              
                        String groupName = gName.get(gChoiceName - 1);

                        //Making the List for Publisher Name
                        ArrayList<String> pName = new ArrayList<String>();
                        ResultSet rsPN = performQuery("pubName", "publishers", conn, stmt);
                        int pnNumList = 1;
                        System.out.println("Publisher Name List:");
                        while(rsPN.next()){
                            System.out.println(pnNumList + ") " + dispNull(rsPN.getString("pubName")));
                            pName.add(rsPN.getString("pubName"));
                            pnNumList++;
                        }
                        
                        int pChoiceName = getInputWithinRange("Please input the number corresponding to the new Book "
                                + "Publisher Name from above: ", reader, 1, pName.size());
                        String publisherName = pName.get(pChoiceName - 1);
                        
                       
                        System.out.println("Adding to database...\n");
                        String sql = "INSERT INTO book(bookTitle, yearPublished, numberPages, groupName, pubName) VALUES "
                                + "(?,?,?,?,?)";
                        preStmt = conn.prepareStatement(sql);
                        preStmt.setString(1, bookTitle);
                        preStmt.setString(2, yearPublished);
                        preStmt.setString(3, Integer.toString(numPages));
                        preStmt.setString(4, groupName);
                        preStmt.setString(5, publisherName);                    
                        preStmt.executeUpdate();

                        System.out.println("Successful add.");
                        System.out.println("Added: [" + bookTitle + ", " +
                                        yearPublished + ", " + 
                                        numPages + ", " + 
                                        groupName + ", " + 
                                        publisherName + "]");

                        System.out.println("Here are the books currently:\n");
                        ResultSet rs = performQuery("bookTitle", "book", conn, stmt);

                        //STEP 5: Extract data from result set
                        int bookNumbering = 1;
                        System.out.println("Book Titles");
                        while (rs.next()) {
                            //Retrieve by column name
                            String cBookTitle = rs.getString("bookTitle");

                                //Display values
                            System.out.println(bookNumbering + ") " + dispNull(cBookTitle));
                            bookNumbering++;
                        }
                        break;
                    }
                    //Insert a new Publisher (followed by a replacing of an old Publisher)
                    case 8:{
                        String newPublisher = getInputWithinRange("Enter in a new Publisher: ", reader, 60);
                        String newPublisherAddress = getInputWithinRange("Enter the new Publisher's address: ", reader, 80);
                        String newPublisherPhone = getInputWithinLengthRange("Enter the new Publisher's phone number: ", reader, 10, 12) ;
                        String newPublisherEmail = getInputWithinRange("Enter the new Publisher's email: ", reader, 80);
                        
                        //asks to see the what the publisher should be replaced
                        System.out.println("Below is a list of known Publisher Names.");
                        ArrayList<String> publisherName = new ArrayList<String>();
                        ResultSet rs = performQuery("pubName", "publishers", conn, stmt);
                        int publisherList = 1;
                        
                        while(rs.next()){
                            System.out.println(publisherList + ") " + dispNull(rs.getString("pubName")));
                            publisherName.add(rs.getString("pubName"));
                            publisherList++;
                        }
                        
                        int publisherChoice = getInputWithinRange("Please enter the number corresponding to the publisher "
                                + "name to be replaced: ", reader, 1, publisherList - 1);
                        String replacedPublisher = publisherName.get(publisherChoice - 1);
                        
                        System.out.println("\n");
                        
                        //makes a new tuple
                        String sqlMakeNew = "INSERT INTO Publishers(pubName, pubAddress, pubPhone, pubEmail) "
                                + "VALUES (?, ?, ?, ?)";
                        preStmt = conn.prepareStatement(sqlMakeNew);
                        preStmt.setString(1, newPublisher);
                        preStmt.setString(2, newPublisherAddress);
                        preStmt.setString(3, newPublisherPhone);
                        preStmt.setString(4, newPublisherEmail);
                        preStmt.executeUpdate();
                        
                        //Go to the child class (Book) to change the publisher indicated to the new insert
                        String sqlReplaceInBook = "UPDATE book SET pubName = ? "
                                + "WHERE pubName = ?";
                        preStmt = conn.prepareStatement(sqlReplaceInBook);
                        preStmt.setString(1, newPublisher);
                        preStmt.setString(2, replacedPublisher);
                        preStmt.executeUpdate();
                        
                        //Go back to the parent class (Publisher) to delete the old publisher
                        String sqlDeleteOldPublisher = "DELETE FROM publishers WHERE pubName = ?";
                        preStmt = conn.prepareStatement(sqlDeleteOldPublisher);
                        preStmt.setString(1, replacedPublisher);
                        preStmt.executeUpdate();
                        
                        //Print out the book list
                        ResultSet bookSet = performQuery("bookTitle, groupName, pubName, yearPublished, numberPages", "book", conn, stmt);
                        System.out.printf("%-40s%-40s\n", "Book Title", "Publisher Name");
                        while (bookSet.next()) {
                            System.out.printf("%-40s%-40s\n", dispNull(bookSet.getString("BookTitle")), dispNull(bookSet.getString("PublisherName")) );
                        }
                        
                        System.out.println("\n");
                        
                        //Print out the publisher list
                        rs = performQuery("pubName, pubAddress,pubPhone, pubEmail", "publishers", conn, stmt);
                        String publisherDisplayFormat = displayFormat;
                        publisherDisplayFormat = publisherDisplayFormat.replaceAll("30", "40");
                        System.out.printf(publisherDisplayFormat, "Publisher Name", "Publisher Address", 
                                    "Publisher Phone", "Publisher Email");
                        while (rs.next()) {
                            //Retrieve by column name
                            String cPublisherName = rs.getString("pubName");
                            String cPublisherAddress = rs.getString("pubAddress");
                            String cPublisherPhone = rs.getString("pubPhone");
                            String cPublisherEmail = rs.getString("pubEmail");

                                //Display values
                            System.out.printf(publisherDisplayFormat,
                                    dispNull(cPublisherName), dispNull(cPublisherAddress), 
                                    dispNull(cPublisherPhone), dispNull(cPublisherEmail));
                        }
                        break;
                    }
                    //remove a book
                    case 9:{
                        System.out.println("All books:\n");
                        ArrayList<String> bookName = new ArrayList<String>();
                        ResultSet rs = performQuery("bookTitle", "book", conn, stmt);
                        int bookNumList = 1;
                        System.out.println("Book Title List:");
                        while(rs.next()){
                            System.out.println(bookNumList + ") " + dispNull(rs.getString("bookTitle")));
                            bookName.add(rs.getString("bookTitle"));
                            bookNumList++;
                        }

                        int bookChoice = getInputWithinRange("\n\nPlease input the Book number to remove: ", reader, 1, bookNumList - 1);
                        String bookTitle = bookName.get(bookChoice - 1);
                        
                        String sql = "DELETE FROM book WHERE bookTitle = ?";
                        preStmt = conn.prepareStatement(sql);
                        preStmt.setString(1, bookTitle);
                        preStmt.executeUpdate();

                        System.out.println("Remaining Books :\n");
                        rs = performQuery("bookTitle", "book", conn, stmt);

                        //STEP 5: Extract data from result set
                        System.out.println("Book Title");
                        bookNumList = 1;
                        while (rs.next()) {
                            //Retrieve by column name
                            String cBookTitle = rs.getString("bookTitle");

                                //Display values
                            System.out.println(bookNumList + ") " + dispNull(cBookTitle));
                            bookNumList++;
                        }
                        break;
                    }
                    default: {
                        System.out.print("That is not an available option.");
                        break;
                    }
                };
                
                System.out.println("\n\n");
            }
            
            //STEP 6: Clean-up environment
            
            stmt.close();
            conn.close();
        } catch (SQLException se) {
            //Handle errors for JDBC
            se.printStackTrace();
        } catch (Exception e) {
            //Handle errors for Class.forName
            e.printStackTrace();
        } finally {
            //finally block used to close resources
            try {
                if (stmt != null) {
                    stmt.close();
                }
            } catch (SQLException se2) {
            }// nothing we can do
            try {
                if (conn != null) {
                    conn.close();
                }
            } catch (SQLException se) {
                se.printStackTrace();
            }//end finally try
        }//end try
        System.out.println("Goodbye!");
    }//end main
}//end FirstExample}