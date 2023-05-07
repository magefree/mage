package mage.game.permanent.token;

import mage.MageInt;
import mage.constants.CardType;
import mage.constants.SubType;

/**
 * @author TheElk801
 */
public final class PhyrexianGolemToken extends TokenImpl {

    public PhyrexianGolemToken() {
        super("Phyrexian Golem Token", "3/3 colorless Phyrexian Golem artifact creature token");
        cardType.add(CardType.ARTIFACT);
        cardType.add(CardType.CREATURE);
        subtype.add(SubType.PHYREXIAN);
        subtype.add(SubType.GOLEM);
        power = new MageInt(3);
        toughness = new MageInt(3);
    }

    public PhyrexianGolemToken(final PhyrexianGolemToken token) {
        super(token);
    }

    public PhyrexianGolemToken copy() {
        return new PhyrexianGolemToken(this);
    }
}
