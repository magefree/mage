package mage.game.command;

import mage.game.permanent.token.TokenImpl;
import mage.util.GameLog;

import java.util.UUID;

/**
 * @author JayDi85
 */
public abstract class CommandObjectImpl implements CommandObject {

    private UUID id;
    private String name = "";

    private String expansionSetCode = "";
    private boolean usesVariousArt = false;
    private String cardNumber = "";
    private String imageFileName = "";
    private int imageNumber;

    public CommandObjectImpl(String name) {
        this.id = UUID.randomUUID();
        this.name = name;
    }

    protected CommandObjectImpl(final CommandObjectImpl object) {
        this.id = object.id;
        this.name = object.name;
        this.expansionSetCode = object.expansionSetCode;
        this.usesVariousArt = object.usesVariousArt;
        this.cardNumber = object.cardNumber;
        this.imageFileName = object.imageFileName;
        this.imageNumber = object.imageNumber;
    }

    @Override
    public UUID getId() {
        return this.id;
    }

    @Override
    public String getExpansionSetCode() {
        return expansionSetCode;
    }

    @Override
    public void setExpansionSetCode(String expansionSetCode) {
        this.expansionSetCode = expansionSetCode;
    }

    @Override
    public boolean getUsesVariousArt() {
        return usesVariousArt;
    }

    @Override
    public void setUsesVariousArt(boolean usesVariousArt) {
        this.usesVariousArt = usesVariousArt;
    }

    @Override
    public String getCardNumber() {
        return cardNumber;
    }

    @Override
    public void setCardNumber(String cardNumber) {
        this.cardNumber = cardNumber;
    }

    @Override
    public String getImageFileName() {
        return imageFileName;
    }

    @Override
    public void setImageFileName(String imageFileName) {
        this.imageFileName = imageFileName;
    }

    @Override
    public Integer getImageNumber() {
        return imageNumber;
    }

    @Override
    public void setImageNumber(Integer imageNumber) {
        this.imageNumber = imageNumber;
    }

    @Override
    public void assignNewId() {
        this.id = UUID.randomUUID();
    }

    @Override
    public String getName() {
        return name;
    }

    @Override
    public void setName(String name) {
        this.name = name;
    }

    @Override
    public String getIdName() {
        return getName() + " [" + getId().toString().substring(0, 3) + ']';
    }

    @Override
    public String getLogName() {
        return GameLog.getColoredObjectIdName(this);
    }
}
