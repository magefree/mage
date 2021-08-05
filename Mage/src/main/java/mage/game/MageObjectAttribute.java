package mage.game;

import mage.MageObject;
import mage.ObjectColor;
import mage.constants.CardType;
import mage.util.SubTypes;

import java.io.Serializable;
import java.util.ArrayList;
import java.util.List;

/**
 * This class saves changed attributes of mage objects (e.g. in command zone, graveyard, exile or
 * player hands or libraries).
 *
 * @author LevelX2
 */
public class MageObjectAttribute implements Serializable {

    protected ObjectColor color;
    protected SubTypes subtype;
    protected List<CardType> cardType;

    public MageObjectAttribute(MageObject mageObject, Game game) {
        color = mageObject.getColor().copy();
        subtype = new SubTypes(mageObject.getSubtype(game));
        cardType = new ArrayList<>(mageObject.getCardType(game));
    }

    public MageObjectAttribute(MageObjectAttribute mageObjectAttribute) {
        this.color = mageObjectAttribute.color;
        this.subtype = mageObjectAttribute.subtype;
        this.cardType = mageObjectAttribute.cardType;
    }

    public MageObjectAttribute copy() {
        return new MageObjectAttribute(this);
    }

    public ObjectColor getColor() {
        return color;
    }

    public SubTypes getSubtype() {
        return subtype;
    }

    public List<CardType> getCardType() {
        return cardType;
    }
}
