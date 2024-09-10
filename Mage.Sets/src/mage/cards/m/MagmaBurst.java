package mage.cards.m;

import mage.abilities.condition.common.KickedCondition;
import mage.abilities.costs.common.SacrificeTargetCost;
import mage.abilities.effects.Effect;
import mage.abilities.effects.common.DamageTargetEffect;
import mage.abilities.keyword.KickerAbility;
import mage.cards.CardImpl;
import mage.cards.CardSetInfo;
import mage.constants.CardType;
import mage.filter.StaticFilters;
import mage.target.common.TargetAnyTarget;
import mage.target.targetadjustment.ConditionalTargetAdjuster;

import java.util.UUID;

/**
 * @author LoneFox
 */
public final class MagmaBurst extends CardImpl {

    public MagmaBurst(UUID ownerId, CardSetInfo setInfo) {
        super(ownerId, setInfo, new CardType[]{CardType.INSTANT}, "{3}{R}");

        // Kicker-Sacrifice two lands.
        this.addAbility(new KickerAbility(new SacrificeTargetCost(2, StaticFilters.FILTER_LANDS)));

        // Magma Burst deals 3 damage to any target. If Magma Burst was kicked, it deals 3 damage to another any target.
        Effect effect = new DamageTargetEffect(3);
        effect.setText("{this} deals 3 damage to any target. If this spell was kicked, it deals 3 damage to another target.");
        this.getSpellAbility().addEffect(effect);
        this.getSpellAbility().addTarget(new TargetAnyTarget());
        this.getSpellAbility().setTargetAdjuster(new ConditionalTargetAdjuster(KickedCondition.ONCE,
                new TargetAnyTarget(2)));
    }

    private MagmaBurst(final MagmaBurst card) {
        super(card);
    }

    @Override
    public MagmaBurst copy() {
        return new MagmaBurst(this);
    }
}
