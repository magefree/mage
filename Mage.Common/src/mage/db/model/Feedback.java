package mage.db.model;

import java.util.Date;

/**
 * Feedback entity.
 *
 * @author noxx
 */
public class Feedback {

    private String username;
    private String title;
    private String type;
    private String message;
    private String email;
    private String host;

    private Date createdDate;

    private String status;

    public Feedback() {
    }

    public String getUsername() {
        return username;
    }

    public void setUsername(String username) {
        this.username = username;
    }

    public String getTitle() {
        return title;
    }

    public void setTitle(String title) {
        this.title = title;
    }

    public String getType() {
        return type;
    }

    public void setType(String type) {
        this.type = type;
    }

    public String getMessage() {
        return message;
    }

    public void setMessage(String message) {
        this.message = message;
    }

    public String getEmail() {
        return email;
    }

    public void setEmail(String email) {
        this.email = email;
    }

    public Date getCreatedDate() {
        return createdDate;
    }

    public void setCreatedDate(Date createdDate) {
        this.createdDate = createdDate;
    }

    public String getStatus() {
        return status;
    }

    public void setStatus(String status) {
        this.status = status;
    }

    public String getHost() {
        return host;
    }

    public void setHost(String host) {
        this.host = host;
    }

    @Override
    public String toString() {
        StringBuilder sb = new StringBuilder();
        String msg = message != null && message.length() > 5 ? message.substring(0, 5) : message;
        sb.append("Feedback [username=").append(username)
                .append(", host=").append(host)
                .append(", title=").append(title)
                .append(", type=").append(type)
                .append(", message=").append(msg)
                .append("...]");
        return sb.toString();
    }
}
