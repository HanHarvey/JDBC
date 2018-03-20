/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package jdbc.ah;

/**
 *
 * @author Alec
 */
public class Book {
    private String groupName; //Foreign Key
    private String bookTitle; // Foreign Key
    private String publisherName;
    private String yearPublished;
    private int numberPages;
    
    public Book() {
        this.groupName = "";
        this.bookTitle = "";
        this.publisherName = "";
        this.yearPublished = "";
        this.numberPages = 0;
    }
    
    public Book(String groupName, String bookTitle, String publisherName,
            String yearPublished, int numberPages) {
        this.groupName = groupName;
        this.bookTitle = bookTitle;
        this.publisherName = publisherName;
        this.yearPublished = yearPublished;
        this.numberPages = numberPages;
    }

    /* Accessor for yearPublished */
    public String getGroupName() {
        return groupName;
    }

    /* Accessor for yearPubli */
    public String getBookTitle() {
        return bookTitle;
    }
    
    /* Accessor for yearPublished */
    public String getYearPublished() {
        return yearPublished;
    }

    /* Mutator for BookTitle */
    public void setBookTitle(String bookTitle) {
        this.bookTitle = bookTitle;
    }

    /* Accessor for yearPublished */
    public String getPublisherName() {
        return publisherName;
    }

    /* Mutator for publisherName */
    public void setPublisherName(String publisherName) {
        this.publisherName = publisherName;
    }

    /* Mutator for groupName */
    public void setGroupName(String groupName) {
        this.groupName = groupName;
    }

    public void setYearPublished(String yearPublished) {
        this.yearPublished = yearPublished;
    }

    public int getNumberPages() {
        return numberPages;
    }

    public void setNumberPages(int numberPages) {
        this.numberPages = numberPages;
    }
    
    
}