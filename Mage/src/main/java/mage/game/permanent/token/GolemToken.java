package mage.game.permanent.token;

import mage.MageInt;
import mage.constants.CardType;
import mage.constants.SubType;

/**
 * @author North
 */
public final class GolemToken extends TokenImpl {

    public GolemToken() {
        super("Golem Token", "3/3 colorless Golem artifact creature token");
        cardType.add(CardType.ARTIFACT);
        cardType.add(CardType.CREATURE);
        subtype.add(SubType.GOLEM);
        power = new MageInt(3);
        toughness = new MageInt(3);
    }

    public GolemToken(final GolemToken token) {
        super(token);
    }

    public GolemToken copy() {
        return new GolemToken(this);
    }
}
