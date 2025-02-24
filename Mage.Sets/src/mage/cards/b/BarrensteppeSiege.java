package mage.cards.b;

import mage.abilities.common.EntersBattlefieldAbility;
import mage.abilities.condition.Condition;
import mage.abilities.condition.common.CreatureDiedControlledCondition;
import mage.abilities.condition.common.ModeChoiceSourceCondition;
import mage.abilities.decorator.ConditionalTriggeredAbility;
import mage.abilities.effects.common.ChooseModeEffect;
import mage.abilities.effects.common.SacrificeOpponentsEffect;
import mage.abilities.effects.common.counter.AddCountersAllEffect;
import mage.abilities.triggers.BeginningOfEndStepTriggeredAbility;
import mage.cards.CardImpl;
import mage.cards.CardSetInfo;
import mage.constants.CardType;
import mage.counters.CounterType;
import mage.filter.StaticFilters;

import java.util.UUID;

/**
 * @author TheElk801
 */
public final class BarrensteppeSiege extends CardImpl {

    private static final Condition condition1 = new ModeChoiceSourceCondition("Abzan");
    private static final String rule1 = "&bull Abzan &mdash; At the beginning of your end step, " +
            "put a +1/+1 counter on each creature you control.";
    private static final Condition condition2 = new ModeChoiceSourceCondition("Mardu");
    private static final String rule2 = "&bull Mardu &mdash; At the beginning of your end step, " +
            "if a creature died under your control this turn, each opponent sacrifices a creature of their choice.";

    public BarrensteppeSiege(UUID ownerId, CardSetInfo setInfo) {
        super(ownerId, setInfo, new CardType[]{CardType.ENCHANTMENT}, "{2}{W}{B}");

        // As this enchantment enters, choose Abzan or Mardu.
        this.addAbility(new EntersBattlefieldAbility(
                new ChooseModeEffect("Abzan or Mardu?", "Abzan", "Mardu"),
                null, "As {this} enters, choose Abzan or Mardu.", ""
        ));

        // * Abzan -- At the beginning of your end step, put a +1/+1 counter on each creature you control.
        this.addAbility(new ConditionalTriggeredAbility(
                new BeginningOfEndStepTriggeredAbility(new AddCountersAllEffect(
                        CounterType.P1P1.createInstance(), StaticFilters.FILTER_CONTROLLED_CREATURE
                )), condition1, rule1
        ));

        // * Mardu -- At the beginning of your end step, if a creature died under your control this turn, each opponent sacrifices a creature of their choice.
        this.addAbility(new ConditionalTriggeredAbility(
                new BeginningOfEndStepTriggeredAbility(
                        new SacrificeOpponentsEffect(StaticFilters.FILTER_PERMANENT_CREATURE)
                ).withInterveningIf(CreatureDiedControlledCondition.instance), condition2, rule2
        ));
    }

    private BarrensteppeSiege(final BarrensteppeSiege card) {
        super(card);
    }

    @Override
    public BarrensteppeSiege copy() {
        return new BarrensteppeSiege(this);
    }
}
