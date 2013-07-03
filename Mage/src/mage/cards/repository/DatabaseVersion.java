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
    protected String entity;

    @DatabaseField
    protected Long version;

    public String getEntity() {
        return entity;
    }

    public void setEntity(String entity) {
        this.entity = entity;
    }

    public Long getVersion() {
        return version;
    }

    public void setVersion(Long version) {
        this.version = version;
    }
}
