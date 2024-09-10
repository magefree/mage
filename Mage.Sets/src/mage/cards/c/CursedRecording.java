package mage.cards.c;

import mage.abilities.Ability;
import mage.abilities.common.SimpleActivatedAbility;
import mage.abilities.common.SpellCastControllerTriggeredAbility;
import mage.abilities.common.delayed.CopyNextSpellDelayedTriggeredAbility;
import mage.abilities.condition.Condition;
import mage.abilities.condition.common.SourceHasCounterCondition;
import mage.abilities.costs.common.TapSourceCost;
import mage.abilities.decorator.ConditionalOneShotEffect;
import mage.abilities.effects.common.CreateDelayedTriggeredAbilityEffect;
import mage.abilities.effects.common.DamageControllerEffect;
import mage.abilities.effects.common.RemoveAllCountersSourceEffect;
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
public final class CursedRecording extends CardImpl {

    private static final Condition condition = new SourceHasCounterCondition(CounterType.TIME, 7);

    public CursedRecording(UUID ownerId, CardSetInfo setInfo) {
        super(ownerId, setInfo, new CardType[]{CardType.ARTIFACT}, "{2}{R}{R}");

        // Whenever you cast an instant or sorcery spell, put a time counter on Cursed Recording. Then if there are seven or more time counters on it, remove those counters and it deals 20 damage to you.
        Ability ability = new SpellCastControllerTriggeredAbility(
                new AddCountersSourceEffect(CounterType.TIME.createInstance()),
                StaticFilters.FILTER_SPELL_AN_INSTANT_OR_SORCERY, true
        );
        ability.addEffect(new ConditionalOneShotEffect(
                new RemoveAllCountersSourceEffect(CounterType.TIME),
                condition, "then if there are seven or more time counters on it, " +
                "remove those counters and it deals 20 damage to you"
        ).addEffect(new DamageControllerEffect(20)));
        this.addAbility(ability);

        // {T}: When you next cast an instant or sorcery spell this turn, copy that spell. You may choose new targets for the copy.
        this.addAbility(new SimpleActivatedAbility(
                new CreateDelayedTriggeredAbilityEffect(new CopyNextSpellDelayedTriggeredAbility()), new TapSourceCost()
        ));
    }

    private CursedRecording(final CursedRecording card) {
        super(card);
    }

    @Override
    public CursedRecording copy() {
        return new CursedRecording(this);
    }
}
