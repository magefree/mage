package mage.db.model;

import com.j256.ormlite.field.DatabaseField;
import com.j256.ormlite.table.DatabaseTable;
import java.util.Date;

/**
 *
 * @author noxx, North
 */
@DatabaseTable(tableName = "feedbacks")
public class Feedback {

    @DatabaseField
    private String username;
    @DatabaseField
    private String title;
    @DatabaseField
    private String type;
    @DatabaseField
    private String message;
    @DatabaseField
    private String email;
    @DatabaseField
    private String host;
    @DatabaseField(columnName = "created_dt")
    private Date createdDate;

    @DatabaseField
    private String status;

    public Feedback() {
    }

    public Feedback(String username, String title, String type, String message, String email, String host, Date createdDate, String status) {
        this.username = username;
        this.title = title;
        this.type = type;
        this.message = message;
        this.email = email;
        this.host = host;
        this.createdDate = createdDate;
        this.status = status;
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
