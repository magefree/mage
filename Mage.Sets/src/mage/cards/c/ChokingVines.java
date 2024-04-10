package mage.cards.c;

import mage.abilities.common.CastOnlyDuringPhaseStepSourceAbility;
import mage.abilities.effects.common.BecomeBlockedTargetEffect;
import mage.abilities.effects.common.DamageTargetEffect;
import mage.cards.CardImpl;
import mage.cards.CardSetInfo;
import mage.constants.CardType;
import mage.constants.PhaseStep;
import mage.filter.StaticFilters;
import mage.target.common.TargetCreaturePermanent;
import mage.target.targetadjustment.XTargetsCountAdjuster;

import java.util.UUID;

/**
 * @author arcox
 */
public final class ChokingVines extends CardImpl {

    public ChokingVines(UUID ownerId, CardSetInfo setInfo) {
        super(ownerId, setInfo, new CardType[]{CardType.INSTANT}, "{X}{G}");

        // Cast only during the declare blockers step.
        this.addAbility(new CastOnlyDuringPhaseStepSourceAbility(null,
                PhaseStep.DECLARE_BLOCKERS, null, "Cast this spell only during the declare blockers step"));

        // X target attacking creatures become blocked. Choking Vines deals 1 damage to each of those creatures.
        this.getSpellAbility().addEffect(new BecomeBlockedTargetEffect()
                .setText("X target attacking creatures become blocked."));
        this.getSpellAbility().addEffect(new DamageTargetEffect(1)
                .setText("{this} deals 1 damage to each of those creatures"));
        this.getSpellAbility().setTargetAdjuster(new XTargetsCountAdjuster());
        this.getSpellAbility().addTarget(new TargetCreaturePermanent(StaticFilters.FILTER_ATTACKING_CREATURES));
    }

    private ChokingVines(final ChokingVines card) {
        super(card);
    }

    @Override
    public ChokingVines copy() {
        return new ChokingVines(this);
    }
}
