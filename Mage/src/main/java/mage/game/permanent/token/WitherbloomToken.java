package mage.game.permanent.token;

import mage.MageInt;
import mage.abilities.common.DiesSourceTriggeredAbility;
import mage.abilities.effects.common.GainLifeEffect;
import mage.constants.CardType;
import mage.constants.SubType;

/**
 * @author TheElk801
 */
public final class WitherbloomToken extends TokenImpl {

    public WitherbloomToken() {
        super("Pest", "1/1 black and green Pest creature token with \"When this creature dies, you gain 1 life.\"");
        cardType.add(CardType.CREATURE);
        color.setBlack(true);
        color.setGreen(true);
        subtype.add(SubType.PEST);
        power = new MageInt(1);
        toughness = new MageInt(1);

        this.addAbility(new DiesSourceTriggeredAbility(new GainLifeEffect(1)));
    }

    private WitherbloomToken(final WitherbloomToken token) {
        super(token);
    }

    public WitherbloomToken copy() {
        return new WitherbloomToken(this);
    }
}
