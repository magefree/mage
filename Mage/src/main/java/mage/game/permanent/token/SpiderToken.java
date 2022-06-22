package mage.game.permanent.token;

import mage.MageInt;
import mage.abilities.keyword.ReachAbility;
import mage.constants.CardType;
import mage.constants.SubType;

import java.util.Arrays;

/**
 * @author fireshoes
 */
public final class SpiderToken extends TokenImpl {

    public SpiderToken() {
        super("Spider Token", "1/2 green Spider creature token with reach");
        cardType.add(CardType.CREATURE);
        color.setGreen(true);
        subtype.add(SubType.SPIDER);
        power = new MageInt(1);
        toughness = new MageInt(2);

        this.addAbility(ReachAbility.getInstance());

        availableImageSetCodes.addAll(Arrays.asList("C15", "EMN", "ISD", "SHM", "MH1", "THB", "MID", "UMA", "NCC", "CMA", "CLB"));
    }

    public SpiderToken(final SpiderToken token) {
        super(token);
    }

    public SpiderToken copy() {
        return new SpiderToken(this);
    }
}
