package mage.cards.w;

import mage.abilities.common.SimpleStaticAbility;
import mage.abilities.condition.Condition;
import mage.abilities.condition.common.PermanentsOnTheBattlefieldCondition;
import mage.abilities.effects.common.DrawCardSourceControllerEffect;
import mage.abilities.effects.common.continuous.GainAbilityControlledEffect;
import mage.abilities.keyword.TrainingAbility;
import mage.abilities.triggers.BeginningOfEndStepTriggeredAbility;
import mage.cards.CardImpl;
import mage.cards.CardSetInfo;
import mage.constants.CardType;
import mage.constants.Duration;
import mage.counters.CounterType;
import mage.filter.FilterPermanent;
import mage.filter.StaticFilters;
import mage.filter.common.FilterControlledCreaturePermanent;
import mage.filter.predicate.permanent.AttackedThisTurnPredicate;

import java.util.UUID;

/**
 * @author TheElk801
 */
public final class WarriorsResolve extends CardImpl {

    private static final FilterPermanent filter = new FilterControlledCreaturePermanent(
            "you control a creature with a +1/+1 counter on it that attacked this turn"
    );

    static {
        filter.add(CounterType.P1P1.getPredicate());
        filter.add(AttackedThisTurnPredicate.instance);
    }

    private static final Condition condition = new PermanentsOnTheBattlefieldCondition(filter);

    public WarriorsResolve(UUID ownerId, CardSetInfo setInfo) {
        super(ownerId, setInfo, new CardType[]{CardType.ENCHANTMENT}, "{2}{W}");

        // Creatures you control have training.
        this.addAbility(new SimpleStaticAbility(new GainAbilityControlledEffect(
                new TrainingAbility(), Duration.WhileControlled, StaticFilters.FILTER_PERMANENT_CREATURES
        )));

        // At the beginning of your end step, if you control a creature with a +1/+1 counter on it that attacked this turn, draw a card.
        this.addAbility(new BeginningOfEndStepTriggeredAbility(new DrawCardSourceControllerEffect(1)).withInterveningIf(condition));
    }

    private WarriorsResolve(final WarriorsResolve card) {
        super(card);
    }

    @Override
    public WarriorsResolve copy() {
        return new WarriorsResolve(this);
    }
}
