package mage.game.permanent.token;

import mage.MageInt;
import mage.constants.CardType;
import mage.constants.SubType;

/**
 * @author Susucr
 */
public final class GolemWhiteBlueToken extends TokenImpl {

    public GolemWhiteBlueToken() {
        super("Golem Token", "4/4 white and blue Golem artifact creature token");
        cardType.add(CardType.ARTIFACT);
        cardType.add(CardType.CREATURE);
        subtype.add(SubType.GOLEM);
        color.setWhite(true);
        color.setBlue(true);
        power = new MageInt(4);
        toughness = new MageInt(4);
    }

    protected GolemWhiteBlueToken(final GolemWhiteBlueToken token) {
        super(token);
    }

    public GolemWhiteBlueToken copy() {
        return new GolemWhiteBlueToken(this);
    }
}
