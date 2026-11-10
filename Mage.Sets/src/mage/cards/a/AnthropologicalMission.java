package mage.cards.a;

import mage.abilities.Ability;
import mage.abilities.common.SpellCastControllerTriggeredAbility;
import mage.abilities.condition.InvertCondition;
import mage.abilities.condition.common.SourceHasCounterCondition;
import mage.abilities.decorator.ConditionalOneShotEffect;
import mage.abilities.effects.common.DrawCardSourceControllerEffect;
import mage.abilities.effects.common.counter.AddCountersSourceEffect;
import mage.abilities.effects.common.discard.DiscardControllerEffect;
import mage.cards.CardImpl;
import mage.cards.CardSetInfo;
import mage.constants.CardType;
import mage.counters.CounterType;
import mage.filter.StaticFilters;

import java.util.UUID;

/**
 * @author muz
 */
public final class AnthropologicalMission extends CardImpl {

    public AnthropologicalMission(UUID ownerId, CardSetInfo setInfo) {
        super(ownerId, setInfo, new CardType[]{CardType.ENCHANTMENT}, "{2}{U}");

        // Whenever you cast a noncreature spell, put a quest counter on this enchantment. Draw a card. Then discard a card unless this enchantment has three or more quest counters on it.
        Ability ability = new SpellCastControllerTriggeredAbility(
            new AddCountersSourceEffect(CounterType.QUEST.createInstance()),
            StaticFilters.FILTER_SPELL_A_NON_CREATURE, false
        );
        ability.addEffect(new DrawCardSourceControllerEffect(1));
        ability.addEffect(new ConditionalOneShotEffect(
            new DiscardControllerEffect(1),
            new InvertCondition(new SourceHasCounterCondition(CounterType.QUEST, 3)),
            "then discard a card unless {this} has three or more quest counters on it")
        );
        this.addAbility(ability);
    }

    private AnthropologicalMission(final AnthropologicalMission card) {
        super(card);
    }

    @Override
    public AnthropologicalMission copy() {
        return new AnthropologicalMission(this);
    }
}
