package mage.cards.q;

import mage.abilities.common.BecomesTappedTriggeredAbility;
import mage.abilities.common.SimpleStaticAbility;
import mage.abilities.condition.Condition;
import mage.abilities.condition.common.SourceHasCounterCondition;
import mage.abilities.decorator.ConditionalContinuousEffect;
import mage.abilities.effects.common.continuous.UntapAllDuringEachOtherPlayersUntapStepEffect;
import mage.abilities.effects.common.counter.AddCountersSourceEffect;
import mage.cards.CardImpl;
import mage.cards.CardSetInfo;
import mage.constants.CardType;
import mage.counters.CounterType;
import mage.filter.StaticFilters;

import java.util.UUID;

/**
 * @author jeffwadsworth
 */
public final class QuestForRenewal extends CardImpl {

    private static final Condition condition = new SourceHasCounterCondition(CounterType.QUEST, 4);

    public QuestForRenewal(UUID ownerId, CardSetInfo setInfo) {
        super(ownerId, setInfo, new CardType[]{CardType.ENCHANTMENT}, "{1}{G}");

        // Whenever a creature you control becomes tapped, you may put a quest counter on Quest for Renewal.
        this.addAbility(new BecomesTappedTriggeredAbility(
                new AddCountersSourceEffect(CounterType.QUEST.createInstance()),
                true, StaticFilters.FILTER_CONTROLLED_A_CREATURE
        ));

        // As long as there are four or more quest counters on Quest for Renewal, untap all creatures you control during each other player's untap step.
        this.addAbility(new SimpleStaticAbility(new ConditionalContinuousEffect(
                new UntapAllDuringEachOtherPlayersUntapStepEffect(StaticFilters.FILTER_CONTROLLED_CREATURES),
                condition, "As long as there are four or more quest counters on {this}, " +
                "untap all creatures you control during each other player's untap step."
        )));
    }

    private QuestForRenewal(final QuestForRenewal card) {
        super(card);
    }

    @Override
    public QuestForRenewal copy() {
        return new QuestForRenewal(this);
    }
}
