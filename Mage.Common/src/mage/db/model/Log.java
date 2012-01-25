package mage.db.model;

import java.util.Date;
import java.util.List;

/**
 * @author noxx
 */
public class Log {

    private String key;

    private Date createdDate;

    private List<String> arguments;

    public Log(String key, Date createdDate) {
        this.key = key;
        this.createdDate = createdDate;
    }

    public String getKey() {
        return key;
    }

    public void setKey(String key) {
        this.key = key;
    }

    public Date getCreatedDate() {
        return createdDate;
    }

    public void setCreatedDate(Date createdDate) {
        this.createdDate = createdDate;
    }

    public List<String> getArguments() {
        return arguments;
    }

    public void setArguments(List<String> arguments) {
        this.arguments = arguments;
    }
}
