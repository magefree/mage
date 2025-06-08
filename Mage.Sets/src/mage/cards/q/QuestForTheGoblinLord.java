package mage.cards.q;

import mage.abilities.common.EntersBattlefieldAllTriggeredAbility;
import mage.abilities.common.SimpleStaticAbility;
import mage.abilities.condition.Condition;
import mage.abilities.condition.common.SourceHasCounterCondition;
import mage.abilities.decorator.ConditionalContinuousEffect;
import mage.abilities.effects.common.continuous.BoostControlledEffect;
import mage.abilities.effects.common.counter.AddCountersSourceEffect;
import mage.cards.CardImpl;
import mage.cards.CardSetInfo;
import mage.constants.CardType;
import mage.constants.Duration;
import mage.constants.SubType;
import mage.counters.CounterType;
import mage.filter.FilterPermanent;
import mage.filter.common.FilterControlledPermanent;

import java.util.UUID;

/**
 * @author jeffwadsworth
 */
public final class QuestForTheGoblinLord extends CardImpl {

    private static final FilterPermanent goblinFilter = new FilterControlledPermanent(SubType.GOBLIN, "a Goblin you control");
    private static final Condition condition = new SourceHasCounterCondition(CounterType.QUEST, 5);

    public QuestForTheGoblinLord(UUID ownerId, CardSetInfo setInfo) {
        super(ownerId, setInfo, new CardType[]{CardType.ENCHANTMENT}, "{R}");

        // Whenever a Goblin you control enters, you may put a quest counter on Quest for the Goblin Lord.
        this.addAbility(new EntersBattlefieldAllTriggeredAbility(
                new AddCountersSourceEffect(CounterType.QUEST.createInstance()), goblinFilter, true
        ));

        // As long as Quest for the Goblin Lord has five or more quest counters on it, creatures you control get +2/+0.
        this.addAbility(new SimpleStaticAbility(new ConditionalContinuousEffect(
                new BoostControlledEffect(2, 0, Duration.WhileOnBattlefield), condition,
                "as long as {this} has five or more quest counters on it, creatures you control get +2/+0"
        )));
    }

    private QuestForTheGoblinLord(final QuestForTheGoblinLord card) {
        super(card);
    }

    @Override
    public QuestForTheGoblinLord copy() {
        return new QuestForTheGoblinLord(this);
    }
}
