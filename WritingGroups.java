/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package jdbc.ah;

/**
 *
 * @author alexanderfielding
 */
public class WritingGroups {
    private String groupName;
    private String headWriter;
    private String yearFormed;
    private String subject;
    
    public WritingGroups() {
        this.groupName = "";
        this.headWriter = "";
        this.yearFormed = "";
        this.subject = "";
    }
    
    public WritingGroups(String groupName, String headWriter, String yearFormed, String subject) {
        this.groupName = groupName;
        this.headWriter = headWriter;
        this.yearFormed = yearFormed;
        this.subject = subject;
    }

    public String getGroupName() {
        return groupName;
    }

    public void setGroupName(String groupName) {
        this.groupName = groupName;
    }

    public String getHeadWriter() {
        return headWriter;
    }

    public void setHeadWriter(String headWriter) {
        this.headWriter = headWriter;
    }

    public String getYearFormed() {
        return yearFormed;
    }

    public void setYearFormed(String yearFormed) {
        this.yearFormed = yearFormed;
    }

    public String getSubject() {
        return subject;
    }

    public void setSubject(String subject) {
        this.subject = subject;
    }
    
}