package mage.game.permanent.token;

import mage.MageInt;
import mage.constants.CardType;
import mage.constants.SubType;

/**
 * @author NinthWorld
 */
public final class TrooperBlackToken extends TokenImpl {

    public TrooperBlackToken() {
        super("Trooper Token", "1/1 black Trooper creature token");

        cardType.add(CardType.CREATURE);
        subtype.add(SubType.TROOPER);

        color.setBlack(true);
        power = new MageInt(1);
        toughness = new MageInt(1);
    }

    protected TrooperBlackToken(final TrooperBlackToken token) {
        super(token);
    }

    public TrooperBlackToken copy() {
        return new TrooperBlackToken(this);
    }
}
