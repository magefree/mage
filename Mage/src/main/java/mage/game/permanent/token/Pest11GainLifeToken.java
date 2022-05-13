package mage.game.permanent.token;

import mage.MageInt;
import mage.abilities.common.DiesSourceTriggeredAbility;
import mage.abilities.effects.common.GainLifeEffect;
import mage.constants.CardType;
import mage.constants.SubType;

import java.util.Arrays;

/**
 * @author TheElk801
 */
public final class Pest11GainLifeToken extends TokenImpl {

    public Pest11GainLifeToken() {
        super("Pest Token", "1/1 black and green Pest creature token with \"When this creature dies, you gain 1 life.\"");
        cardType.add(CardType.CREATURE);
        color.setBlack(true);
        color.setGreen(true);
        subtype.add(SubType.PEST);
        power = new MageInt(1);
        toughness = new MageInt(1);

        this.addAbility(new DiesSourceTriggeredAbility(new GainLifeEffect(1)));

        availableImageSetCodes = Arrays.asList("STX");
    }

    private Pest11GainLifeToken(final Pest11GainLifeToken token) {
        super(token);
    }

    public Pest11GainLifeToken copy() {
        return new Pest11GainLifeToken(this);
    }
}
