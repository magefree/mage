package mage.game.permanent.token;

import mage.MageInt;
import mage.constants.CardType;
import mage.constants.SubType;

import java.util.Arrays;

/**
 * @author TheElk801
 */
public final class ShrineToken extends TokenImpl {

    public ShrineToken() {
        super("Spirit", "1/1 colorless Shrine enchantment creature token");
        cardType.add(CardType.ENCHANTMENT);
        cardType.add(CardType.CREATURE);
        subtype.add(SubType.SHRINE);
        power = new MageInt(1);
        toughness = new MageInt(1);

        availableImageSetCodes = Arrays.asList("NEC");
    }

    public ShrineToken(final ShrineToken token) {
        super(token);
    }

    @Override
    public ShrineToken copy() {
        return new ShrineToken(this);
    }
}
