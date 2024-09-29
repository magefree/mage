package mage.cards.e;

import mage.abilities.Ability;
import mage.abilities.common.ActivateAsSorceryActivatedAbility;
import mage.abilities.common.DiesCreatureTriggeredAbility;
import mage.abilities.common.EntersBattlefieldAbility;
import mage.abilities.costs.common.TapSourceCost;
import mage.abilities.effects.common.counter.AddCountersSourceEffect;
import mage.abilities.effects.common.counter.MoveCountersFromSourceToTargetEffect;
import mage.cards.CardImpl;
import mage.cards.CardSetInfo;
import mage.constants.CardType;
import mage.counters.CounterType;
import mage.filter.StaticFilters;
import mage.target.common.TargetCreaturePermanent;

import java.util.UUID;

/**
 * @author xenohedron
 */
public final class ExplorersCache extends CardImpl {

    public ExplorersCache(UUID ownerId, CardSetInfo setInfo) {
        super(ownerId, setInfo, new CardType[]{CardType.ARTIFACT}, "{1}{G}");

        // Explorer's Cache enters the battlefield with two +1/+1 counters on it.
        this.addAbility(new EntersBattlefieldAbility(
                new AddCountersSourceEffect(CounterType.P1P1.createInstance(2)),
                "with two +1/+1 counters on it"
        ));

        // Whenever a creature you control with a +1/+1 counter on it dies, put a +1/+1 counter on Explorer's Cache.
        this.addAbility(new DiesCreatureTriggeredAbility(
                new AddCountersSourceEffect(CounterType.P1P1.createInstance()),
                false,
                StaticFilters.FILTER_A_CONTROLLED_CREATURE_P1P1));

        // {T}: Move a +1/+1 counter from Explorer's Cache onto target creature. Activate only as a sorcery.
        Ability ability = new ActivateAsSorceryActivatedAbility(new MoveCountersFromSourceToTargetEffect(), new TapSourceCost());
        ability.addTarget(new TargetCreaturePermanent());
        this.addAbility(ability);

    }

    private ExplorersCache(final ExplorersCache card) {
        super(card);
    }

    @Override
    public ExplorersCache copy() {
        return new ExplorersCache(this);
    }
}
