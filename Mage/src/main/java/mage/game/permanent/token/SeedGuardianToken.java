package mage.game.permanent.token;

import mage.MageInt;
import mage.constants.CardType;
import mage.constants.SubType;

/**
 * @author spjspj
 */
public final class SeedGuardianToken extends TokenImpl {

    public SeedGuardianToken() {
        this(1);
    }

    public SeedGuardianToken(int xValue) {
        super("Elemental Token", "X/X green Elemental creature token");
        cardType.add(CardType.CREATURE);
        color.setGreen(true);
        subtype.add(SubType.ELEMENTAL);
        power = new MageInt(xValue);
        toughness = new MageInt(xValue);
    }

    public SeedGuardianToken(final SeedGuardianToken token) {
        super(token);
    }

    public SeedGuardianToken copy() {
        return new SeedGuardianToken(this);
    }
}
