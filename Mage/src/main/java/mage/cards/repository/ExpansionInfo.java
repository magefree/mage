package mage.cards.repository;

import com.j256.ormlite.field.DataType;
import com.j256.ormlite.field.DatabaseField;
import com.j256.ormlite.table.DatabaseTable;
import java.util.Date;
import mage.cards.ExpansionSet;
import mage.constants.SetType;

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
    @DatabaseField
    protected boolean boosters;
    @DatabaseField
    protected boolean basicLands;

    public ExpansionInfo() {
    }

    public ExpansionInfo(ExpansionSet expansionSet) {
        this.name = expansionSet.getName();
        this.code = expansionSet.getCode();
        this.blockName = expansionSet.getBlockName();
        this.releaseDate = expansionSet.getReleaseDate();
        this.type = expansionSet.getSetType();
        this.boosters = expansionSet.hasBoosters();
        this.basicLands = expansionSet.hasBasicLands();
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

    public boolean hasBoosters() {
        return boosters;
    }

    public boolean hasBasicLands() {
        return basicLands;
    }

    @Override
    public String toString() {
        return name;
    }

}
