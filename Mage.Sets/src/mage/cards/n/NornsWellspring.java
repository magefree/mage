package mage.cards.n;

import java.util.UUID;

import mage.abilities.Ability;
import mage.abilities.common.DiesCreatureTriggeredAbility;
import mage.abilities.common.SimpleActivatedAbility;
import mage.abilities.costs.common.RemoveCountersSourceCost;
import mage.abilities.costs.common.TapSourceCost;
import mage.abilities.costs.mana.GenericManaCost;
import mage.abilities.effects.common.DrawCardSourceControllerEffect;
import mage.abilities.effects.common.counter.AddCountersSourceEffect;
import mage.abilities.effects.keyword.ScryEffect;
import mage.cards.CardImpl;
import mage.cards.CardSetInfo;
import mage.constants.CardType;
import mage.counters.CounterType;
import mage.filter.StaticFilters;

/**
 * @author TheElk801
 */
public final class NornsWellspring extends CardImpl {

    public NornsWellspring(UUID ownerId, CardSetInfo setInfo) {
        super(ownerId, setInfo, new CardType[]{CardType.ARTIFACT}, "{1}{W}");

        // Whenever a creature you control dies, scry 1 and put an oil counter on Norn's Wellspring.
        Ability ability = new DiesCreatureTriggeredAbility(
                new ScryEffect(1, false),
                false, StaticFilters.FILTER_CONTROLLED_A_CREATURE
        );
        ability.addEffect(new AddCountersSourceEffect(CounterType.OIL.createInstance()).concatBy("and"));
        this.addAbility(ability);

        // {1}, {T}, Remove two oil counters from Norn's Wellspring: Draw a card.
        ability = new SimpleActivatedAbility(new DrawCardSourceControllerEffect(1), new GenericManaCost(1));
        ability.addCost(new TapSourceCost());
        ability.addCost(new RemoveCountersSourceCost(CounterType.OIL.createInstance(2)));
        this.addAbility(ability);
    }

    private NornsWellspring(final NornsWellspring card) {
        super(card);
    }

    @Override
    public NornsWellspring copy() {
        return new NornsWellspring(this);
    }
}
