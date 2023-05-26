package mage.game.permanent.token;

import mage.MageInt;
import mage.constants.CardType;
import mage.constants.SubType;

/**
 * @author spjspj
 */
public final class IllusionToken extends TokenImpl {

    public IllusionToken() {
        super("Illusion Token", "2/2 blue Illusion creature token");
        cardType.add(CardType.CREATURE);
        color.setBlue(true);

        subtype.add(SubType.ILLUSION);
        power = new MageInt(2);
        toughness = new MageInt(2);
    }

    public IllusionToken(final IllusionToken token) {
        super(token);
    }

    public IllusionToken copy() {
        return new IllusionToken(this);
    }
}
