/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package jdbc.ah;

/**
 * @author Alec
 */
public class Publisher {
    private String publisherName;
    private String publisherAddress;
    private String publisherPhone;
    private String publisherEmail;
    
    public Publisher() {
        this.publisherName = "";
        this.publisherAddress = "";
        this.publisherPhone = "";
        this.publisherEmail = "";
    }
    
    public Publisher(String publisherName, String publisherAddress,
        String publisherPhone, String publisherEmail) {
        this.publisherName = publisherName;
        this.publisherAddress = publisherAddress;
        this.publisherPhone = publisherPhone;
        this.publisherEmail = publisherEmail;
    }
    
    public String getPublisherName() {
        return publisherName;
    }

    public void setPublisherName(String publisherName) {
        this.publisherName = publisherName;
    }

    public String getPublisherAddress() {
        return publisherAddress;
    }

    public void setPublisherAddress(String publisherAddress) {
        this.publisherAddress = publisherAddress;
    }

    public String getPublisherPhone() {
        return publisherPhone;
    }

    public void setPublisherPhone(String publisherPhone) {
        this.publisherPhone = publisherPhone;
    }

    public String getPublisherEmail() {
        return publisherEmail;
    }

    public void setPublisherEmail(String publisherEmail) {
        this.publisherEmail = publisherEmail;
    }
}