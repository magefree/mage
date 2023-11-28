package mage.game.permanent.token;

import mage.MageInt;
import mage.constants.CardType;
import mage.constants.SubType;

/**
 * @author spjspj
 */
public final class StitchersApprenticeHomunculusToken extends TokenImpl {

    public StitchersApprenticeHomunculusToken() {
        super("Homunculus Token", "2/2 blue Homunculus creature token");
        cardType.add(CardType.CREATURE);
        color.setBlue(true);
        subtype.add(SubType.HOMUNCULUS);
        power = new MageInt(2);
        toughness = new MageInt(2);
    }

    protected StitchersApprenticeHomunculusToken(final StitchersApprenticeHomunculusToken token) {
        super(token);
    }

    public StitchersApprenticeHomunculusToken copy() {
        return new StitchersApprenticeHomunculusToken(this);
    }
}
