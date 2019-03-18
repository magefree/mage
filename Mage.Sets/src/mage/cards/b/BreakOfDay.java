
package mage.cards.b;

import java.util.UUID;
import mage.abilities.condition.LockedInCondition;
import mage.abilities.condition.common.FatefulHourCondition;
import mage.abilities.decorator.ConditionalContinuousEffect;
import mage.abilities.effects.common.continuous.BoostControlledEffect;
import mage.abilities.effects.common.continuous.GainAbilityAllEffect;
import mage.abilities.keyword.IndestructibleAbility;
import mage.cards.CardImpl;
import mage.cards.CardSetInfo;
import mage.constants.CardType;
import mage.constants.Duration;
import mage.filter.common.FilterControlledCreaturePermanent;

/**
 *
 * @author Loki

 */
public final class BreakOfDay extends CardImpl {

    public BreakOfDay(UUID ownerId, CardSetInfo setInfo) {
        super(ownerId,setInfo,new CardType[]{CardType.INSTANT},"{1}{W}");


        // Creatures you control get +1/+1 until end of turn.
        this.getSpellAbility().addEffect(new BoostControlledEffect(1, 1, Duration.EndOfTurn));
        // Fateful hour - If you have 5 or less life, those creatures also are indestructible this turn.
        this.getSpellAbility().addEffect(new ConditionalContinuousEffect(
                new GainAbilityAllEffect(IndestructibleAbility.getInstance(), Duration.EndOfTurn, new FilterControlledCreaturePermanent("creatures you control"), false),
                new LockedInCondition(FatefulHourCondition.instance),
                "If you have 5 or less life, those creatures also are indestructible this turn"));
    }

    public BreakOfDay(final BreakOfDay card) {
        super(card);
    }

    @Override
    public BreakOfDay copy() {
        return new BreakOfDay(this);
    }
}
