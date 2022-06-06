package mage.game.permanent.token;

import mage.MageInt;
import mage.abilities.keyword.TrampleAbility;
import mage.constants.CardType;
import mage.constants.SubType;

import java.util.Arrays;

/**
 * @author LoneFox
 */
public final class WurmWithTrampleToken extends TokenImpl {

    public WurmWithTrampleToken() {
        super("Wurm Token", "5/5 green Wurm creature token with trample");
        cardType.add(CardType.CREATURE);
        color.setGreen(true);
        subtype.add(SubType.WURM);
        power = new MageInt(5);
        toughness = new MageInt(5);
        addAbility(TrampleAbility.getInstance());

        availableImageSetCodes = Arrays.asList("RTR", "MM3", "GK1");
    }

    public WurmWithTrampleToken(final WurmWithTrampleToken token) {
        super(token);
    }

    public WurmWithTrampleToken copy() {
        return new WurmWithTrampleToken(this);
    }
}
