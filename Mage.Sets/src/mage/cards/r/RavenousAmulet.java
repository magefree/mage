package mage.cards.r;

import mage.abilities.Ability;
import mage.abilities.common.ActivateAsSorceryActivatedAbility;
import mage.abilities.common.SimpleActivatedAbility;
import mage.abilities.costs.common.SacrificeSourceCost;
import mage.abilities.costs.common.SacrificeTargetCost;
import mage.abilities.costs.common.TapSourceCost;
import mage.abilities.costs.mana.GenericManaCost;
import mage.abilities.dynamicvalue.DynamicValue;
import mage.abilities.dynamicvalue.common.CountersSourceCount;
import mage.abilities.effects.common.DrawCardSourceControllerEffect;
import mage.abilities.effects.common.LoseLifeOpponentsEffect;
import mage.abilities.effects.common.counter.AddCountersSourceEffect;
import mage.cards.CardImpl;
import mage.cards.CardSetInfo;
import mage.constants.CardType;
import mage.counters.CounterType;
import mage.filter.StaticFilters;

import java.util.UUID;

/**
 * @author TheElk801
 */
public final class RavenousAmulet extends CardImpl {

    private static final DynamicValue xValue = new CountersSourceCount(CounterType.SOUL);

    public RavenousAmulet(UUID ownerId, CardSetInfo setInfo) {
        super(ownerId, setInfo, new CardType[]{CardType.ARTIFACT}, "{2}");

        // {1}, {T}, Sacrifice a creature: Draw a card and put a soul counter on this artifact. Activate only as a sorcery.
        Ability ability = new ActivateAsSorceryActivatedAbility(
                new DrawCardSourceControllerEffect(1), new GenericManaCost(1)
        );
        ability.addCost(new TapSourceCost());
        ability.addCost(new SacrificeTargetCost(StaticFilters.FILTER_PERMANENT_A_CREATURE));
        ability.addEffect(new AddCountersSourceEffect(CounterType.SOUL.createInstance()).concatBy("and"));
        this.addAbility(ability);

        // {4}, {T}, Sacrifice this artifact: Each opponent loses life equal to the number of soul counters on this artifact.
        ability = new SimpleActivatedAbility(new LoseLifeOpponentsEffect(xValue), new GenericManaCost(4));
        ability.addCost(new TapSourceCost());
        ability.addCost(new SacrificeSourceCost());
        this.addAbility(ability);
    }

    private RavenousAmulet(final RavenousAmulet card) {
        super(card);
    }

    @Override
    public RavenousAmulet copy() {
        return new RavenousAmulet(this);
    }
}
