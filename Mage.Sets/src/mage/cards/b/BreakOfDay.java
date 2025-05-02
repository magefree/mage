package mage.cards.b;

import mage.abilities.condition.LockedInCondition;
import mage.abilities.condition.common.FatefulHourCondition;
import mage.abilities.decorator.ConditionalContinuousEffect;
import mage.abilities.effects.common.continuous.BoostControlledEffect;
import mage.abilities.effects.common.continuous.GainAbilityControlledEffect;
import mage.abilities.keyword.IndestructibleAbility;
import mage.cards.CardImpl;
import mage.cards.CardSetInfo;
import mage.constants.CardType;
import mage.constants.Duration;
import mage.filter.StaticFilters;

import java.util.UUID;

/**
 * @author Loki
 */
public final class BreakOfDay extends CardImpl {

    public BreakOfDay(UUID ownerId, CardSetInfo setInfo) {
        super(ownerId, setInfo, new CardType[]{CardType.INSTANT}, "{1}{W}");

        // Creatures you control get +1/+1 until end of turn.
        this.getSpellAbility().addEffect(new BoostControlledEffect(1, 1, Duration.EndOfTurn));

        // Fateful hour - If you have 5 or less life, those creatures also are indestructible this turn.
        this.getSpellAbility().addEffect(new ConditionalContinuousEffect(
                new GainAbilityControlledEffect(
                        IndestructibleAbility.getInstance(), Duration.EndOfTurn,
                        StaticFilters.FILTER_PERMANENT_CREATURES, false
                ), new LockedInCondition(FatefulHourCondition.instance),
                "<br><i>Fateful hour</i> &mdash; If you have 5 or less life, " +
                        "those creatures gain indestructible until end of turn"
        ));
    }

    private BreakOfDay(final BreakOfDay card) {
        super(card);
    }

    @Override
    public BreakOfDay copy() {
        return new BreakOfDay(this);
    }
}
