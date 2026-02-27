package mage.cards.h;

import java.util.UUID;

import mage.abilities.common.SimpleStaticAbility;
import mage.abilities.condition.common.ControlsCreatureGreatestPowerCondition;
import mage.abilities.decorator.ConditionalOneShotEffect;
import mage.abilities.effects.common.DrawCardSourceControllerEffect;
import mage.abilities.effects.common.replacement.ModifyCountersAddedEffect;
import mage.abilities.triggers.BeginningOfEndStepTriggeredAbility;
import mage.cards.CardImpl;
import mage.cards.CardSetInfo;
import mage.constants.CardType;
import mage.counters.CounterType;
import mage.filter.StaticFilters;

/**
 *
 * @author muz
 */
public final class HighScore extends CardImpl {

    public HighScore(UUID ownerId, CardSetInfo setInfo) {
        super(ownerId, setInfo, new CardType[]{CardType.ENCHANTMENT}, "{2}{G}");

        // If one or more +1/+1 counters would be put on a creature you control, that many plus one +1/+1 counters are put on it instead.
        this.addAbility(new SimpleStaticAbility(new ModifyCountersAddedEffect(StaticFilters.FILTER_CONTROLLED_CREATURE, CounterType.P1P1)));

        // At the beginning of your end step, draw a card if you control a creature with the greatest power among creatures on the battlefield.
        this.addAbility(new BeginningOfEndStepTriggeredAbility(
            new ConditionalOneShotEffect(
                new DrawCardSourceControllerEffect(1),
                ControlsCreatureGreatestPowerCondition.instance,
                "draw a card if you control the creature with the greatest power among creatures on the battlefield"
            )
        ));
    }

    private HighScore(final HighScore card) {
        super(card);
    }

    @Override
    public HighScore copy() {
        return new HighScore(this);
    }
}
