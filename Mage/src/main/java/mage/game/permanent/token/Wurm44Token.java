package mage.game.permanent.token;

import mage.MageInt;
import mage.constants.CardType;
import mage.constants.SubType;

import java.util.Arrays;

/**
 * @author TheElk801
 */
public final class Wurm44Token extends TokenImpl {

    public Wurm44Token() {
        super("Wurm Token", "4/4 green Wurm creature token");
        cardType.add(CardType.CREATURE);
        color.setGreen(true);
        subtype.add(SubType.WURM);
        power = new MageInt(4);
        toughness = new MageInt(4);
        setOriginalExpansionSetCode("DMC");
    }

    public Wurm44Token(final Wurm44Token token) {
        super(token);
    }

    public Wurm44Token copy() {
        return new Wurm44Token(this);
    }
}
