package mage.cards.t;

import java.util.UUID;

import mage.abilities.Ability;
import mage.abilities.common.EntersBattlefieldAbility;
import mage.abilities.common.SimpleActivatedAbility;
import mage.abilities.costs.common.RemoveCountersSourceCost;
import mage.abilities.costs.common.TapSourceCost;
import mage.abilities.effects.common.TapTargetEffect;
import mage.abilities.effects.common.counter.AddCountersSourceEffect;
import mage.cards.CardImpl;
import mage.cards.CardSetInfo;
import mage.constants.CardType;
import mage.counters.CounterType;
import mage.filter.StaticFilters;
import mage.target.TargetPermanent;

/**
 * @author TheElk801
 */
public final class TamiyosImmobilizer extends CardImpl {

    public TamiyosImmobilizer(UUID ownerId, CardSetInfo setInfo) {
        super(ownerId, setInfo, new CardType[]{CardType.ARTIFACT}, "{3}{U}");

        // Tamiyo's Immobilizer enters the battlefield with four oil counters.
        this.addAbility(new EntersBattlefieldAbility(
                new AddCountersSourceEffect(CounterType.OIL.createInstance(4)),
                "with four oil counters on it"
        ));

        // {T}, Remove an oil counter from Tamiyo's Immobilizer: Tap target artifact or creature.
        Ability ability = new SimpleActivatedAbility(new TapTargetEffect(), new TapSourceCost());
        ability.addCost(new RemoveCountersSourceCost(CounterType.OIL.createInstance()));
        ability.addTarget(new TargetPermanent(StaticFilters.FILTER_PERMANENT_ARTIFACT_OR_CREATURE));
        this.addAbility(ability);
    }

    private TamiyosImmobilizer(final TamiyosImmobilizer card) {
        super(card);
    }

    @Override
    public TamiyosImmobilizer copy() {
        return new TamiyosImmobilizer(this);
    }
}
