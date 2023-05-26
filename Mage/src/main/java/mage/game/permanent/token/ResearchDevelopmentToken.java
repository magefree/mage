package mage.game.permanent.token;

import mage.MageInt;
import mage.constants.CardType;
import mage.constants.SubType;

/**
 * @author JayDi85
 */
public final class ResearchDevelopmentToken extends TokenImpl {

    public ResearchDevelopmentToken() {
        super("Elemental Token", "3/1 red Elemental creature token");
        cardType.add(CardType.CREATURE);
        color.setRed(true);
        subtype.add(SubType.ELEMENTAL);
        power = new MageInt(3);
        toughness = new MageInt(1);
    }

    public ResearchDevelopmentToken(final ResearchDevelopmentToken token) {
        super(token);
    }

    public ResearchDevelopmentToken copy() {
        return new ResearchDevelopmentToken(this);
    }
}
