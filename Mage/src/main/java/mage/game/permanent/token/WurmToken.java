package mage.game.permanent.token;

import mage.MageInt;
import mage.constants.CardType;
import mage.constants.SubType;

/**
 * @author magenoxx_at_gmail.com
 */
public final class WurmToken extends TokenImpl {

    public WurmToken() {
        super("Wurm Token", "6/6 green Wurm creature token");
        cardType.add(CardType.CREATURE);
        color.setGreen(true);
        subtype.add(SubType.WURM);
        power = new MageInt(6);
        toughness = new MageInt(6);
    }

    public WurmToken(final WurmToken token) {
        super(token);
    }

    public WurmToken copy() {
        return new WurmToken(this);
    }
}
