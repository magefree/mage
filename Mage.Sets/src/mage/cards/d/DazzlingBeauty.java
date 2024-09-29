package mage.cards.d;

import mage.abilities.common.CastOnlyDuringPhaseStepSourceAbility;
import mage.abilities.common.delayed.AtTheBeginOfNextUpkeepDelayedTriggeredAbility;
import mage.abilities.effects.common.BecomeBlockedTargetEffect;
import mage.abilities.effects.common.CreateDelayedTriggeredAbilityEffect;
import mage.abilities.effects.common.DrawCardSourceControllerEffect;
import mage.cards.CardImpl;
import mage.cards.CardSetInfo;
import mage.constants.CardType;
import mage.constants.PhaseStep;
import mage.filter.common.FilterCreaturePermanent;
import mage.filter.predicate.Predicates;
import mage.filter.predicate.permanent.AttackingPredicate;
import mage.filter.predicate.permanent.BlockedPredicate;
import mage.target.common.TargetCreaturePermanent;

import java.util.UUID;

/**
 * @author LevelX2 & L_J
 */
public final class DazzlingBeauty extends CardImpl {

    private static final FilterCreaturePermanent filter = new FilterCreaturePermanent("unblocked attacking creature");

    static {
        filter.add(AttackingPredicate.instance);
        filter.add(Predicates.not(BlockedPredicate.instance));
    }

    public DazzlingBeauty(UUID ownerId, CardSetInfo setInfo) {
        super(ownerId, setInfo, new CardType[]{CardType.INSTANT}, "{2}{W}");

        // Cast Dazzling Beauty only during the declare blockers step.
        this.addAbility(new CastOnlyDuringPhaseStepSourceAbility(
                null, PhaseStep.DECLARE_BLOCKERS, null,
                "Cast this spell only during the declare blockers step"
        ));

        // Target unblocked attacking creature becomes blocked.
        this.getSpellAbility().addEffect(new BecomeBlockedTargetEffect());
        this.getSpellAbility().addTarget(new TargetCreaturePermanent(filter));

        // Draw a card at the beginning of the next turn's upkeep.
        this.getSpellAbility().addEffect(new CreateDelayedTriggeredAbilityEffect(new AtTheBeginOfNextUpkeepDelayedTriggeredAbility(
                new DrawCardSourceControllerEffect(1)), false).concatBy("<br>"));
    }

    private DazzlingBeauty(final DazzlingBeauty card) {
        super(card);
    }

    @Override
    public DazzlingBeauty copy() {
        return new DazzlingBeauty(this);
    }
}
