package mage.game.permanent.token;

import mage.MageInt;
import mage.constants.CardType;
import mage.constants.SubType;

/**
 * @author spjspj
 */
public final class NestOfScarabsBlackInsectToken extends TokenImpl {

    public NestOfScarabsBlackInsectToken() {
        super("Insect Token", "1/1 black Insect creature token");
        cardType.add(CardType.CREATURE);
        color.setBlack(true);
        subtype.add(SubType.INSECT);
        power = new MageInt(1);
        toughness = new MageInt(1);
    }

    public NestOfScarabsBlackInsectToken(final NestOfScarabsBlackInsectToken token) {
        super(token);
    }

    public NestOfScarabsBlackInsectToken copy() {
        return new NestOfScarabsBlackInsectToken(this);
    }
}
