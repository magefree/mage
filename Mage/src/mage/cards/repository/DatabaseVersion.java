package mage.cards.repository;

import com.j256.ormlite.field.DatabaseField;
import com.j256.ormlite.table.DatabaseTable;

/**
 *
 * @author North
 */
@DatabaseTable(tableName = "version")
public class DatabaseVersion {

    @DatabaseField
    protected Long version;

    public Long getVersion() {
        return version;
    }

    public void setVersion(Long version) {
        this.version = version;
    }
}
