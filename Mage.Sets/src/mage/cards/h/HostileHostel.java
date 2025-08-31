package mage.cards.h;

import mage.abilities.Ability;
import mage.abilities.common.ActivateAsSorceryActivatedAbility;
import mage.abilities.condition.Condition;
import mage.abilities.condition.common.SourceHasCounterCondition;
import mage.abilities.costs.common.SacrificeTargetCost;
import mage.abilities.costs.common.TapSourceCost;
import mage.abilities.costs.mana.ManaCostsImpl;
import mage.abilities.decorator.ConditionalOneShotEffect;
import mage.abilities.effects.common.RemoveAllCountersSourceEffect;
import mage.abilities.effects.common.TransformSourceEffect;
import mage.abilities.effects.common.UntapSourceEffect;
import mage.abilities.effects.common.counter.AddCountersSourceEffect;
import mage.abilities.keyword.TransformAbility;
import mage.abilities.mana.ColorlessManaAbility;
import mage.cards.CardImpl;
import mage.cards.CardSetInfo;
import mage.constants.CardType;
import mage.counters.CounterType;
import mage.filter.StaticFilters;

import java.util.UUID;

/**
 * @author LePwnerer
 */
public final class HostileHostel extends CardImpl {

    private static final Condition condition = new SourceHasCounterCondition(CounterType.SOUL, 3);

    public HostileHostel(UUID ownerId, CardSetInfo setInfo) {
        super(ownerId, setInfo, new CardType[]{CardType.LAND}, "");
        this.secondSideCardClazz = mage.cards.c.CreepingInn.class;

        // {T}: Add {C}.
        this.addAbility(new ColorlessManaAbility());

        // {1}, {T}, Sacrifice a creature: Put a soul counter on Hostile Hostel. Then if there are three or more soul counters on it, remove those counters, transform it, then untap it. Activate only as a sorcery.
        this.addAbility(new TransformAbility());
        Ability ability = new ActivateAsSorceryActivatedAbility(new AddCountersSourceEffect(CounterType.SOUL.createInstance()), new ManaCostsImpl<>("{1}"));
        ability.addCost(new TapSourceCost());
        ability.addCost(new SacrificeTargetCost(StaticFilters.FILTER_PERMANENT_CREATURE));
        ability.addEffect(new ConditionalOneShotEffect(
                new RemoveAllCountersSourceEffect(CounterType.SOUL), condition, "Then if there are three " +
                "or more soul counters on it, remove those counters, transform it, then untap it"
        ).addEffect(new TransformSourceEffect()).addEffect(new UntapSourceEffect()));
        this.addAbility(ability);
    }

    private HostileHostel(final HostileHostel card) {
        super(card);
    }

    @Override
    public HostileHostel copy() {
        return new HostileHostel(this);
    }
}
