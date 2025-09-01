
package mage.cards.d;

import mage.abilities.Ability;
import mage.abilities.common.CycleTriggeredAbility;
import mage.abilities.common.SpellCastOpponentTriggeredAbility;
import mage.abilities.condition.Condition;
import mage.abilities.condition.common.SourceHasCounterCondition;
import mage.abilities.costs.mana.ManaCostsImpl;
import mage.abilities.decorator.ConditionalOneShotEffect;
import mage.abilities.effects.common.CounterTargetEffect;
import mage.abilities.effects.common.SacrificeSourceEffect;
import mage.abilities.effects.common.counter.AddCountersSourceEffect;
import mage.abilities.keyword.CyclingAbility;
import mage.cards.CardImpl;
import mage.cards.CardSetInfo;
import mage.constants.CardType;
import mage.constants.SetTargetPointer;
import mage.constants.Zone;
import mage.counters.CounterType;
import mage.filter.StaticFilters;
import mage.target.TargetSpell;

import java.util.UUID;

/**
 * @author LoneFox
 */
public final class DecreeOfSilence extends CardImpl {

    private static final Condition condition = new SourceHasCounterCondition(CounterType.DEPLETION, 3);

    public DecreeOfSilence(UUID ownerId, CardSetInfo setInfo) {
        super(ownerId, setInfo, new CardType[]{CardType.ENCHANTMENT}, "{6}{U}{U}");

        // Whenever an opponent casts a spell, counter that spell and put a depletion counter on Decree of Silence. If there are three or more depletion counters on Decree of Silence, sacrifice it.
        Ability ability = new SpellCastOpponentTriggeredAbility(
                Zone.BATTLEFIELD, new CounterTargetEffect().setText("counter that spell"),
                StaticFilters.FILTER_SPELL_A, false, SetTargetPointer.SPELL
        );
        ability.addEffect(new AddCountersSourceEffect(CounterType.DEPLETION.createInstance()).concatBy("and"));
        ability.addEffect(new ConditionalOneShotEffect(
                new SacrificeSourceEffect(), condition, "If there are " +
                "three or more depletion counters on {this}, sacrifice it"
        ));
        this.addAbility(ability);

        // Cycling {4}{U}{U}
        this.addAbility(new CyclingAbility(new ManaCostsImpl<>("{4}{U}{U}")));

        // When you cycle Decree of Silence, you may counter target spell.
        ability = new CycleTriggeredAbility(new CounterTargetEffect(), true);
        ability.addTarget(new TargetSpell());
        this.addAbility(ability);
    }

    private DecreeOfSilence(final DecreeOfSilence card) {
        super(card);
    }

    @Override
    public DecreeOfSilence copy() {
        return new DecreeOfSilence(this);
    }
}
