package mage.game.permanent.token;

import mage.MageInt;
import mage.constants.CardType;
import mage.constants.SubType;

/**
 * @author spjspj
 */
public final class SylvanOfferingTreefolkToken extends TokenImpl {

    public SylvanOfferingTreefolkToken() {
        this(1);
    }

    public SylvanOfferingTreefolkToken(int xValue) {
        super("Treefolk Token", "X/X green Treefolk creature token");
        cardType.add(CardType.CREATURE);
        subtype.add(SubType.TREEFOLK);
        color.setGreen(true);
        power = new MageInt(xValue);
        toughness = new MageInt(xValue);
    }

    private SylvanOfferingTreefolkToken(final SylvanOfferingTreefolkToken token) {
        super(token);
    }

    public SylvanOfferingTreefolkToken copy() {
        return new SylvanOfferingTreefolkToken(this);
    }
}
