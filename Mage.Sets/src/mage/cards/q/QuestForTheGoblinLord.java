package mage.cards.q;

import java.util.UUID;
import mage.abilities.common.EntersBattlefieldControlledTriggeredAbility;
import mage.abilities.common.SimpleStaticAbility;
import mage.abilities.condition.common.SourceHasCounterCondition;
import mage.abilities.decorator.ConditionalContinuousEffect;
import mage.abilities.effects.common.continuous.BoostAllEffect;
import mage.abilities.effects.common.counter.AddCountersSourceEffect;
import mage.cards.CardImpl;
import mage.cards.CardSetInfo;
import mage.constants.*;
import mage.counters.CounterType;
import mage.filter.FilterPermanent;
import mage.filter.StaticFilters;
import mage.filter.common.FilterControlledPermanent;

/**
 *
 * @author jeffwadsworth
 */
public final class QuestForTheGoblinLord extends CardImpl {

    private static final String rule = "As long as {this} has five or more quest counters on it, creatures you control get +2/+0";
    private static final FilterPermanent goblinFilter = new FilterControlledPermanent(SubType.GOBLIN, "a Goblin");

    public QuestForTheGoblinLord(UUID ownerId, CardSetInfo setInfo) {
        super(ownerId, setInfo, new CardType[]{CardType.ENCHANTMENT}, "{R}");

        // Whenever a Goblin enters the battlefield under your control, you may put a quest counter on Quest for the Goblin Lord.
        this.addAbility(new EntersBattlefieldControlledTriggeredAbility(Zone.BATTLEFIELD, new AddCountersSourceEffect(CounterType.QUEST.createInstance()), goblinFilter, true));

        // As long as Quest for the Goblin Lord has five or more quest counters on it, creatures you control get +2/+0.
        this.addAbility(new SimpleStaticAbility(new ConditionalContinuousEffect(
                new BoostAllEffect(2, 0, Duration.WhileOnBattlefield, StaticFilters.FILTER_CONTROLLED_CREATURES, false),
                new SourceHasCounterCondition(CounterType.QUEST, 5, Integer.MAX_VALUE), rule)));
    }

    private QuestForTheGoblinLord(final QuestForTheGoblinLord card) {
        super(card);
    }

    @Override
    public QuestForTheGoblinLord copy() {
        return new QuestForTheGoblinLord(this);
    }
}
