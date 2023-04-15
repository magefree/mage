package mage.game.permanent.token;

import mage.MageInt;
import mage.ObjectColor;
import mage.constants.CardType;
import mage.constants.SubType;

/**
 * @author spjspj
 */
public final class VolrathsLaboratoryToken extends TokenImpl {

    public VolrathsLaboratoryToken() {
        this(new ObjectColor(), SubType.BEAR);
    }

    public VolrathsLaboratoryToken(ObjectColor color, SubType type) {
        super(type.getDescription() + " Token", "2/2 creature token of the chosen color and type");
        cardType.add(CardType.CREATURE);
        this.color.setColor(color);
        subtype.add(type);
        power = new MageInt(2);
        toughness = new MageInt(2);
    }

    public VolrathsLaboratoryToken(final VolrathsLaboratoryToken token) {
        super(token);
    }

    public VolrathsLaboratoryToken copy() {
        return new VolrathsLaboratoryToken(this);
    }
}
