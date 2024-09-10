package mage.cards.c;

import mage.abilities.Ability;
import mage.abilities.common.BeginningOfUpkeepTriggeredAbility;
import mage.abilities.common.EntersBattlefieldTriggeredAbility;
import mage.abilities.condition.common.MonarchIsSourceControllerCondition;
import mage.abilities.decorator.ConditionalOneShotEffect;
import mage.abilities.effects.common.BecomesMonarchSourceEffect;
import mage.abilities.effects.common.counter.DistributeCountersEffect;
import mage.abilities.effects.common.counter.DoubleCounterOnEachPermanentEffect;
import mage.abilities.hint.common.MonarchHint;
import mage.cards.CardImpl;
import mage.cards.CardSetInfo;
import mage.constants.CardType;
import mage.constants.TargetController;
import mage.counters.CounterType;
import mage.filter.StaticFilters;
import mage.target.common.TargetCreaturePermanentAmount;

import java.util.UUID;

/**
 * @author Susucr
 */
public final class CourtOfGarenbrig extends CardImpl {

    public CourtOfGarenbrig(UUID ownerId, CardSetInfo setInfo) {
        super(ownerId, setInfo, new CardType[]{CardType.ENCHANTMENT}, "{1}{G}{G}");


        // When Court of Garenbrig enters the battlefield, you become the monarch.
        this.addAbility(new EntersBattlefieldTriggeredAbility(new BecomesMonarchSourceEffect()).addHint(MonarchHint.instance));

        // At the beginning of your upkeep, distribute two +1/+1 counters among up to two target creatures. Then if you're the monarch, double the number of +1/+1 counters on each creature you control.
        Ability ability = new BeginningOfUpkeepTriggeredAbility(
                new DistributeCountersEffect(
                        CounterType.P1P1, 2, false, "up to two target creatures"
                ), TargetController.YOU, false
        );
        TargetCreaturePermanentAmount target = new TargetCreaturePermanentAmount(2);
        target.setMinNumberOfTargets(0);
        target.setMaxNumberOfTargets(2);
        ability.addTarget(target);
        ability.addEffect(new ConditionalOneShotEffect(
                new DoubleCounterOnEachPermanentEffect(CounterType.P1P1, StaticFilters.FILTER_CONTROLLED_CREATURE),
                MonarchIsSourceControllerCondition.instance
        ).concatBy("Then"));
        this.addAbility(ability);
    }

    private CourtOfGarenbrig(final CourtOfGarenbrig card) {
        super(card);
    }

    @Override
    public CourtOfGarenbrig copy() {
        return new CourtOfGarenbrig(this);
    }
}
