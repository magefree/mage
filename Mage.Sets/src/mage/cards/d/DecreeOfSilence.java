
package mage.cards.d;

import java.util.UUID;
import mage.abilities.Ability;
import mage.abilities.common.CycleTriggeredAbility;
import mage.abilities.common.SpellCastOpponentTriggeredAbility;
import mage.abilities.condition.common.SourceHasCounterCondition;
import mage.abilities.costs.mana.ManaCostsImpl;
import mage.abilities.decorator.ConditionalOneShotEffect;
import mage.abilities.effects.Effect;
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
import mage.filter.FilterSpell;
import mage.filter.StaticFilters;
import mage.target.TargetSpell;

/**
 *
 * @author LoneFox
 */
public final class DecreeOfSilence extends CardImpl {

    public DecreeOfSilence(UUID ownerId, CardSetInfo setInfo) {
        super(ownerId, setInfo, new CardType[]{CardType.ENCHANTMENT}, "{6}{U}{U}");

        // Whenever an opponent casts a spell, counter that spell and put a depletion counter on Decree of Silence. If there are three or more depletion counters on Decree of Silence, sacrifice it.
        Effect effect = new CounterTargetEffect();
        effect.setText("counter that spell");
        Ability ability = new SpellCastOpponentTriggeredAbility(Zone.BATTLEFIELD, effect, StaticFilters.FILTER_SPELL_A,
                false, SetTargetPointer.SPELL);
        effect = new AddCountersSourceEffect(CounterType.DEPLETION.createInstance());
        effect.setText("and put a depletion counter on {this}.");
        ability.addEffect(effect);
        ability.addEffect(new ConditionalOneShotEffect(new SacrificeSourceEffect(),
                new SourceHasCounterCondition(CounterType.DEPLETION, 3, Integer.MAX_VALUE),
                " If there are three or more depletion counters on {this}, sacrifice it"));
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
