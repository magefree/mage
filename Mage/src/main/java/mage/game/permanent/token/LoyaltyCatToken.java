package mage.game.permanent.token;

import mage.MageInt;
import mage.abilities.common.SimpleActivatedAbility;
import mage.abilities.costs.common.TapSourceCost;
import mage.abilities.effects.common.counter.AddCountersAllEffect;
import mage.constants.CardType;
import mage.constants.SubType;
import mage.counters.CounterType;
import mage.filter.StaticFilters;

/**
 * @author PurpleCrowbar
 */
public final class LoyaltyCatToken extends TokenImpl {

    public LoyaltyCatToken() {
        super("Cat Token", "1/1 white Cat creature token with \"{T}: Put a loyalty counter on each planeswalker you control.\"");
        cardType.add(CardType.CREATURE);
        color.setWhite(true);
        subtype.add(SubType.CAT);
        power = new MageInt(1);
        toughness = new MageInt(1);

        // {T}: Put a loyalty counter on each planeswalker you control.
        this.addAbility(new SimpleActivatedAbility(
                new AddCountersAllEffect(CounterType.LOYALTY.createInstance(), StaticFilters.FILTER_CONTROLLED_PERMANENT_PLANESWALKER),
                new TapSourceCost()
        ));
    }

    private LoyaltyCatToken(final LoyaltyCatToken token) {
        super(token);
    }

    @Override
    public LoyaltyCatToken copy() {
        return new LoyaltyCatToken(this);
    }
}
