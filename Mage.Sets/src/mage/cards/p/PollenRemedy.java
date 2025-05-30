package mage.cards.p;

import mage.abilities.condition.common.KickedCondition;
import mage.abilities.costs.common.SacrificeTargetCost;
import mage.abilities.decorator.ConditionalReplacementEffect;
import mage.abilities.effects.Effect;
import mage.abilities.effects.common.PreventDamageToTargetMultiAmountEffect;
import mage.abilities.keyword.KickerAbility;
import mage.cards.CardImpl;
import mage.cards.CardSetInfo;
import mage.constants.CardType;
import mage.constants.Duration;
import mage.filter.StaticFilters;
import mage.target.common.TargetAnyTargetAmount;
import mage.target.targetadjustment.ConditionalTargetAdjuster;

import java.util.UUID;

/**
 * @author LoneFox
 */
public final class PollenRemedy extends CardImpl {

    public PollenRemedy(UUID ownerId, CardSetInfo setInfo) {
        super(ownerId, setInfo, new CardType[]{CardType.INSTANT}, "{W}");

        // Kicker-Sacrifice a land.
        this.addAbility(new KickerAbility(new SacrificeTargetCost(StaticFilters.FILTER_LAND)));

        // Prevent the next 3 damage that would be dealt this turn to any number of targets, divided as you choose.
        // If Pollen Remedy was kicked, prevent the next 6 damage this way instead.
        Effect effect = new ConditionalReplacementEffect(new PreventDamageToTargetMultiAmountEffect(Duration.EndOfTurn, 6),
                KickedCondition.ONCE, new PreventDamageToTargetMultiAmountEffect(Duration.EndOfTurn, 3));
        effect.setText("Prevent the next 3 damage that would be dealt this turn to any number of targets, divided as you choose. If this spell was kicked, prevent the next 6 damage this way instead.");
        this.getSpellAbility().addEffect(effect);
        this.getSpellAbility().addTarget(new TargetAnyTargetAmount(3, 0, 3));
        this.getSpellAbility().setTargetAdjuster(new ConditionalTargetAdjuster(KickedCondition.ONCE,
                new TargetAnyTargetAmount(6)));
    }

    private PollenRemedy(final PollenRemedy card) {
        super(card);
    }

    @Override
    public PollenRemedy copy() {
        return new PollenRemedy(this);
    }
}
