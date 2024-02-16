package mage.cards.repository;

import com.j256.ormlite.field.DatabaseField;
import com.j256.ormlite.table.DatabaseTable;

/**
 * @author JayDi85
 */
@DatabaseTable(tableName = "build")
public class DatabaseBuild {

    @DatabaseField
    protected String entity;

    @DatabaseField(columnName = "last_build")
    protected String lastBuild;

    public String getEntity() {
        return entity;
    }

    public void setEntity(String entity) {
        this.entity = entity;
    }

    public void setLastBuild(String lastBuild) {
        this.lastBuild = lastBuild;
    }
}
