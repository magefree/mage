package mage.cards.repository;

import com.j256.ormlite.field.DataType;
import com.j256.ormlite.field.DatabaseField;
import com.j256.ormlite.table.DatabaseTable;
import mage.cards.ExpansionSet;
import mage.constants.SetType;

import java.util.Date;

/**
 *
 * @author North
 */
@DatabaseTable(tableName = "expansion")
public class ExpansionInfo {

    @DatabaseField(unique = true)
    protected String name;
    @DatabaseField(unique = true)
    protected String code;
    @DatabaseField
    protected String blockName;
    @DatabaseField
    protected Date releaseDate;
    @DatabaseField(dataType = DataType.ENUM_STRING)
    protected SetType type;

    public ExpansionInfo() {
    }

    public ExpansionInfo(ExpansionSet expansionSet) {
        this.name = expansionSet.getName();
        this.code = expansionSet.getCode();
        this.blockName = expansionSet.getBlockName();
        this.releaseDate = expansionSet.getReleaseDate();
        this.type = expansionSet.getSetType();
    }

    public String getName() {
        return name;
    }

    public String getCode() {
        return code;
    }

    public String getBlockName() {
        return blockName;
    }

    public Date getReleaseDate() {
        return releaseDate;
    }

    public SetType getType() {
        return type;
    }
}
