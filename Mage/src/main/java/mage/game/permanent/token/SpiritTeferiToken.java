package mage.game.permanent.token;

import mage.MageInt;
import mage.abilities.common.DrawCardControllerTriggeredAbility;
import mage.abilities.effects.common.counter.AddCountersSourceEffect;
import mage.abilities.keyword.VigilanceAbility;
import mage.constants.CardType;
import mage.constants.SubType;
import mage.counters.CounterType;

/**
 * @author weirddan455
 */
public class SpiritTeferiToken extends TokenImpl {

    public SpiritTeferiToken() {
        super("Spirit Token", "2/2 blue Spirit creature token with vigilance and \"Whenever you draw a card, put a +1/+1 counter on this creature.\"");
        cardType.add(CardType.CREATURE);
        subtype.add(SubType.SPIRIT);
        color.setBlue(true);
        power = new MageInt(2);
        toughness = new MageInt(2);
        addAbility(VigilanceAbility.getInstance());
        addAbility(new DrawCardControllerTriggeredAbility(new AddCountersSourceEffect(CounterType.P1P1.createInstance()), false));
    }

    private SpiritTeferiToken(final SpiritTeferiToken token) {
        super(token);
    }

    @Override
    public SpiritTeferiToken copy() {
        return new SpiritTeferiToken(this);
    }
}
