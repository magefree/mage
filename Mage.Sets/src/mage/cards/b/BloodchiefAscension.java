package mage.cards.b;

import mage.abilities.Ability;
import mage.abilities.common.PutCardIntoGraveFromAnywhereAllTriggeredAbility;
import mage.abilities.condition.Condition;
import mage.abilities.condition.common.OpponentLostLifeCondition;
import mage.abilities.condition.common.SourceHasCounterCondition;
import mage.abilities.effects.common.GainLifeEffect;
import mage.abilities.effects.common.LoseLifeTargetEffect;
import mage.abilities.effects.common.counter.AddCountersSourceEffect;
import mage.abilities.triggers.BeginningOfEndStepTriggeredAbility;
import mage.cards.CardImpl;
import mage.cards.CardSetInfo;
import mage.constants.CardType;
import mage.constants.ComparisonType;
import mage.constants.SetTargetPointer;
import mage.constants.TargetController;
import mage.counters.CounterType;
import mage.filter.StaticFilters;

import java.util.UUID;

/**
 * @author LevelX2
 */
public final class BloodchiefAscension extends CardImpl {

    private static final Condition condition = new SourceHasCounterCondition(CounterType.QUEST, 3);

    public BloodchiefAscension(UUID ownerId, CardSetInfo setInfo) {
        super(ownerId, setInfo, new CardType[]{CardType.ENCHANTMENT}, "{B}");

        // At the beginning of each end step, if an opponent lost 2 or more life this turn, you may put a quest counter on Bloodchief Ascension. (Damage causes loss of life.)
        this.addAbility(new BeginningOfEndStepTriggeredAbility(
                TargetController.ANY, new AddCountersSourceEffect(CounterType.QUEST.createInstance(1), false),
                true, new OpponentLostLifeCondition(ComparisonType.MORE_THAN, 1)
        ));

        // Whenever a card is put into an opponent's graveyard from anywhere, if Bloodchief Ascension has three or more quest counters on it, you may have that player lose 2 life. If you do, you gain 2 life.
        Ability ability = new PutCardIntoGraveFromAnywhereAllTriggeredAbility(
                new LoseLifeTargetEffect(2).setText("have that player lose 2 life"), true, StaticFilters.FILTER_CARD_A,
                TargetController.OPPONENT, SetTargetPointer.PLAYER
        ).withInterveningIf(condition);
        ability.addEffect(new GainLifeEffect(2).concatBy("If you do,"));
        this.addAbility(ability);
    }

    private BloodchiefAscension(final BloodchiefAscension card) {
        super(card);
    }

    @Override
    public BloodchiefAscension copy() {
        return new BloodchiefAscension(this);
    }
}
