package mage.cards.a;

import mage.abilities.common.SimpleActivatedAbility;
import mage.abilities.common.SpellCastControllerTriggeredAbility;
import mage.abilities.common.delayed.CopyNextSpellDelayedTriggeredAbility;
import mage.abilities.condition.Condition;
import mage.abilities.condition.common.SourceHasCounterCondition;
import mage.abilities.costs.common.RemoveCountersSourceCost;
import mage.abilities.effects.common.CreateDelayedTriggeredAbilityEffect;
import mage.abilities.effects.common.counter.AddCountersSourceEffect;
import mage.cards.CardImpl;
import mage.cards.CardSetInfo;
import mage.constants.CardType;
import mage.constants.ComparisonType;
import mage.counters.CounterType;
import mage.filter.StaticFilters;

import java.util.UUID;

/**
 * @author TheElk801
 */
public final class AdaptiveTrainingPost extends CardImpl {

    private static final Condition condition = new SourceHasCounterCondition(CounterType.CHARGE, ComparisonType.FEWER_THAN, 3);

    public AdaptiveTrainingPost(UUID ownerId, CardSetInfo setInfo) {
        super(ownerId, setInfo, new CardType[]{CardType.ARTIFACT}, "{2}{U}");

        // Whenever you cast an instant or sorcery spell, if this artifact has fewer than three charge counters on it, put a charge counter on it.
        this.addAbility(new SpellCastControllerTriggeredAbility(
                new AddCountersSourceEffect(CounterType.CHARGE.createInstance()),
                StaticFilters.FILTER_SPELL_AN_INSTANT_OR_SORCERY, false
        ).withInterveningIf(condition).withRuleTextReplacement(true));

        // Remove three charge counters from this artifact: When you next cast an instant or sorcery spell this turn, copy it and you may choose new targets for the copy.
        this.addAbility(new SimpleActivatedAbility(
                new CreateDelayedTriggeredAbilityEffect(new CopyNextSpellDelayedTriggeredAbility()),
                new RemoveCountersSourceCost(CounterType.CHARGE.createInstance(3))
        ));
    }

    private AdaptiveTrainingPost(final AdaptiveTrainingPost card) {
        super(card);
    }

    @Override
    public AdaptiveTrainingPost copy() {
        return new AdaptiveTrainingPost(this);
    }
}
