
package mage.cards.b;

import java.util.UUID;
import mage.abilities.Ability;
import mage.abilities.common.BeginningOfEndStepTriggeredAbility;
import mage.abilities.common.PutCardIntoGraveFromAnywhereAllTriggeredAbility;
import mage.abilities.condition.common.OpponentLostLifeCondition;
import mage.abilities.condition.common.SourceHasCounterCondition;
import mage.abilities.decorator.ConditionalInterveningIfTriggeredAbility;
import mage.abilities.effects.common.GainLifeEffect;
import mage.abilities.effects.common.LoseLifeTargetEffect;
import mage.abilities.effects.common.counter.AddCountersSourceEffect;
import mage.cards.CardImpl;
import mage.cards.CardSetInfo;
import mage.constants.CardType;
import mage.constants.ComparisonType;
import mage.constants.SetTargetPointer;
import mage.constants.TargetController;
import mage.constants.Zone;
import mage.counters.CounterType;
import mage.filter.FilterCard;

/**
 *
 * @author LevelX2
 */
public final class BloodchiefAscension extends CardImpl {

    public BloodchiefAscension(UUID ownerId, CardSetInfo setInfo) {
        super(ownerId, setInfo, new CardType[]{CardType.ENCHANTMENT}, "{B}");

        // At the beginning of each end step, if an opponent lost 2 or more life this turn, you may put a quest counter on Bloodchief Ascension. (Damage causes loss of life.)
        this.addAbility(new BeginningOfEndStepTriggeredAbility(Zone.BATTLEFIELD,
                new AddCountersSourceEffect(CounterType.QUEST.createInstance(1), false),
                TargetController.ANY,
                new OpponentLostLifeCondition(ComparisonType.MORE_THAN, 1),
                true));

        // Whenever a card is put into an opponent's graveyard from anywhere, if Bloodchief Ascension has three or more quest counters on it, you may have that player lose 2 life. If you do, you gain 2 life.
        Ability ability = new ConditionalInterveningIfTriggeredAbility(
                new PutCardIntoGraveFromAnywhereAllTriggeredAbility(
                        new LoseLifeTargetEffect(2), true, new FilterCard("a card"), TargetController.OPPONENT, SetTargetPointer.PLAYER),
                new SourceHasCounterCondition(CounterType.QUEST, 3, Integer.MAX_VALUE),
                "Whenever a card is put into an opponent's graveyard from anywhere, if {this} has three or more quest counters on it, you may have that player lose 2 life. If you do, you gain 2 life");
        ability.addEffect(new GainLifeEffect(2));
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
