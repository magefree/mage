package mage.game.permanent.token;

import mage.MageInt;
import mage.abilities.common.DiesSourceTriggeredAbility;
import mage.abilities.effects.common.GainLifeEffect;
import mage.constants.CardType;
import mage.constants.SubType;

/**
 * @author TheElk801
 */
public final class PestToken2 extends TokenImpl {

    public PestToken2() {
        super("Pest", "1/1 black and green Pest creature token with \"When this creature dies, you gain 1 life.\"");
        cardType.add(CardType.CREATURE);
        color.setBlack(true);
        color.setGreen(true);
        subtype.add(SubType.PEST);
        power = new MageInt(1);
        toughness = new MageInt(1);

        this.addAbility(new DiesSourceTriggeredAbility(new GainLifeEffect(1)));
    }

    private PestToken2(final PestToken2 token) {
        super(token);
    }

    public PestToken2 copy() {
        return new PestToken2(this);
    }
}
