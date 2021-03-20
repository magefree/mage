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
        super("Bear", "2/2 green Bear creature token");
        cardType.add(CardType.CREATURE);
        color.setGreen(true);
        subtype.add(SubType.BEAR);
        power = new MageInt(2);
        toughness = new MageInt(2);

        availableImageSetCodes = Arrays.asList("C15", "JUD", "ODY", "ONS", "VMA", "MH1", "ELD", "KHM");
    }

    public BearToken(final BearToken token) {
        super(token);
    }

    public BearToken copy() {
        return new BearToken(this);
    }
}
