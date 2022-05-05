package mage.game.permanent.token;

import mage.MageInt;
import mage.abilities.common.DiesSourceTriggeredAbility;
import mage.abilities.effects.common.counter.AddCountersAllEffect;
import mage.constants.CardType;
import mage.constants.SubType;
import mage.counters.CounterType;
import mage.filter.FilterPermanent;
import mage.filter.common.FilterControlledPermanent;

/**
 * @author TheElk801
 */
public final class GarrukCursedHuntsmanToken extends TokenImpl {

    private static final FilterPermanent filter
            = new FilterControlledPermanent(SubType.GARRUK, "Garruk you control");

    public GarrukCursedHuntsmanToken() {
        super("Wolf Token", "2/2 black and green Wolf creature token with \"When this creature dies, put a loyalty counter on each Garruk you control.\"");
        cardType.add(CardType.CREATURE);
        color.setBlack(true);
        color.setGreen(true);
        subtype.add(SubType.WOLF);
        power = new MageInt(2);
        toughness = new MageInt(2);

        this.addAbility(new DiesSourceTriggeredAbility(new AddCountersAllEffect(CounterType.LOYALTY.createInstance(), filter)));
    }

    public GarrukCursedHuntsmanToken(final GarrukCursedHuntsmanToken token) {
        super(token);
    }

    public GarrukCursedHuntsmanToken copy() {
        return new GarrukCursedHuntsmanToken(this);
    }
}
