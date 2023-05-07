package mage.game.permanent.token;

import mage.MageInt;
import mage.constants.CardType;
import mage.constants.SubType;

/**
 * @author TheElk801
 */
public final class ShrineToken extends TokenImpl {

    public ShrineToken() {
        super("Shrine Token", "1/1 colorless Shrine enchantment creature token");
        cardType.add(CardType.ENCHANTMENT);
        cardType.add(CardType.CREATURE);
        subtype.add(SubType.SHRINE);
        power = new MageInt(1);
        toughness = new MageInt(1);
    }

    public ShrineToken(final ShrineToken token) {
        super(token);
    }

    @Override
    public ShrineToken copy() {
        return new ShrineToken(this);
    }
}
