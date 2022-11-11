package mage.game.permanent.token;

import mage.MageInt;
import mage.constants.CardType;
import mage.constants.SubType;

import java.util.Arrays;

/**
 * @author BetaSteward_at_googlemail.com
 */
public final class BearToken extends TokenImpl {

    public BearToken() {
        super("Bear Token", "2/2 green Bear creature token");
        cardType.add(CardType.CREATURE);
        color.setGreen(true);
        subtype.add(SubType.BEAR);
        power = new MageInt(2);
        toughness = new MageInt(2);

        availableImageSetCodes = Arrays.asList("C15", "JUD", "ODY", "VMA", "MH1", "ELD", "KHM", "DMC");
    }

    public BearToken(final BearToken token) {
        super(token);
    }

    public BearToken copy() {
        return new BearToken(this);
    }
}
