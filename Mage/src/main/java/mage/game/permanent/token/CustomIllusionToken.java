package mage.game.permanent.token;

import mage.MageInt;
import mage.constants.CardType;
import mage.constants.SubType;

/**
 * @author TheElk801
 */
public final class CustomIllusionToken extends TokenImpl {

    public CustomIllusionToken() {
        this(0);
    }

    public CustomIllusionToken(int xValue) {
        super("Illusion Token", "X/X blue Illusion creature token");
        cardType.add(CardType.CREATURE);
        color.setBlue(true);
        subtype.add(SubType.ILLUSION);
        power = new MageInt(xValue);
        toughness = new MageInt(xValue);
    }

    public CustomIllusionToken(final CustomIllusionToken token) {
        super(token);
    }

    public CustomIllusionToken copy() {
        return new CustomIllusionToken(this);
    }
}
