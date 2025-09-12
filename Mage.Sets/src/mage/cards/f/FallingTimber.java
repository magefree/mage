package mage.cards.f;

import mage.abilities.costs.common.SacrificeTargetCost;
import mage.abilities.effects.Effect;
import mage.abilities.effects.common.PreventDamageByTargetEffect;
import mage.abilities.keyword.KickerAbility;
import mage.cards.CardImpl;
import mage.cards.CardSetInfo;
import mage.constants.CardType;
import mage.constants.Duration;
import mage.filter.StaticFilters;
import mage.target.common.TargetCreaturePermanent;
import mage.target.targetadjustment.ConditionalTargetAdjuster;

import java.util.UUID;

import static mage.abilities.condition.common.KickedCondition.ONCE;

/**
 * @author LoneFox
 */
public final class FallingTimber extends CardImpl {

    public FallingTimber(UUID ownerId, CardSetInfo setInfo) {
        super(ownerId, setInfo, new CardType[]{CardType.INSTANT}, "{2}{G}");

        // Kicker-Sacrifice a land.
        this.addAbility(new KickerAbility(new SacrificeTargetCost(StaticFilters.FILTER_LAND)));

        // Prevent all combat damage target creature would deal this turn. If Falling Timber was kicked,
        // prevent all combat damage another target creature would deal this turn.
        Effect effect = new PreventDamageByTargetEffect(Duration.EndOfTurn, true);
        effect.setText("Prevent all combat damage target creature would deal this turn. If this spell was kicked, " +
                "prevent all combat damage another target creature would deal this turn.");
        this.getSpellAbility().addEffect(effect);
        this.getSpellAbility().addTarget(new TargetCreaturePermanent());
        this.getSpellAbility().setTargetAdjuster(new ConditionalTargetAdjuster(ONCE, new TargetCreaturePermanent(2)));
    }

    private FallingTimber(final FallingTimber card) {
        super(card);
    }

    @Override
    public FallingTimber copy() {
        return new FallingTimber(this);
    }
}
