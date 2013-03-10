package mage.db.model;

import com.j256.ormlite.field.DatabaseField;
import com.j256.ormlite.table.DatabaseTable;
import java.util.ArrayList;
import java.util.Date;
import java.util.List;

/**
 * @author noxx, North
 */
@DatabaseTable(tableName = "logs")
public class Log {

    @DatabaseField
    private String key;
    @DatabaseField(columnName = "created_dt")
    private Date createdDate;
    @DatabaseField
    private String arg0;
    @DatabaseField
    private String arg1;
    @DatabaseField
    private String arg2;
    @DatabaseField
    private String arg3;
    @DatabaseField
    private String arg4;
    @DatabaseField
    private String arg5;

    public Log() {
    }

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
        ArrayList<String> arguments = new ArrayList<String>();
        if (arg0 != null) {
            arguments.add(arg0);
        }
        if (arg1 != null) {
            arguments.add(arg1);
        }
        if (arg2 != null) {
            arguments.add(arg2);
        }
        if (arg3 != null) {
            arguments.add(arg3);
        }
        if (arg4 != null) {
            arguments.add(arg4);
        }
        if (arg5 != null) {
            arguments.add(arg5);
        }
        return arguments;
    }

    public void setArguments(String... arguments) {
        if (arguments.length > 0) {
            arg0 = arguments[0];
        }
        if (arguments.length > 1) {
            arg1 = arguments[1];
        }
        if (arguments.length > 2) {
            arg2 = arguments[2];
        }
        if (arguments.length > 3) {
            arg3 = arguments[3];
        }
        if (arguments.length > 4) {
            arg4 = arguments[4];
        }
        if (arguments.length > 5) {
            arg5 = arguments[5];
        }
    }
}
