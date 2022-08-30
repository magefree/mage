package mage.game.permanent.token;

import mage.MageInt;
import mage.abilities.keyword.ProwessAbility;
import mage.constants.CardType;
import mage.constants.SubType;

import java.util.Arrays;

/**
 * @author TheElk801
 */
public final class MonkRedToken extends TokenImpl {

    public MonkRedToken() {
        super("Monk Token", "1/1 red Monk creature token with prowess");
        cardType.add(CardType.CREATURE);
        color.setRed(true);
        subtype.add(SubType.MONK);
        power = new MageInt(1);
        toughness = new MageInt(1);
        addAbility(new ProwessAbility());

        availableImageSetCodes = Arrays.asList("DMU");
    }

    public MonkRedToken(final MonkRedToken token) {
        super(token);
    }

    public MonkRedToken copy() {
        return new MonkRedToken(this);
    }
}
