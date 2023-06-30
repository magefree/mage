package mage.cards.c;

import mage.abilities.common.CastOnlyDuringPhaseStepSourceAbility;
import mage.abilities.condition.common.AfterBlockersAreDeclaredCondition;
import mage.abilities.effects.common.BecomeBlockedTargetEffect;
import mage.abilities.effects.common.DrawCardSourceControllerEffect;
import mage.cards.CardImpl;
import mage.cards.CardSetInfo;
import mage.constants.CardType;
import mage.constants.TurnPhase;
import mage.filter.common.FilterCreaturePermanent;
import mage.filter.predicate.Predicates;
import mage.filter.predicate.permanent.AttackingPredicate;
import mage.filter.predicate.permanent.BlockedPredicate;
import mage.target.common.TargetCreaturePermanent;

import java.util.UUID;

/**
 * @author LevelX2
 */
public final class CurtainOfLight extends CardImpl {

    private static final FilterCreaturePermanent filter
            = new FilterCreaturePermanent("unblocked attacking creature");

    static {
        filter.add(AttackingPredicate.instance);
        filter.add(Predicates.not(BlockedPredicate.instance));
    }

    public CurtainOfLight(UUID ownerId, CardSetInfo setInfo) {
        super(ownerId, setInfo, new CardType[]{CardType.INSTANT}, "{1}{W}");

        // Cast Curtain of Light only during combat after blockers are declared.
        this.addAbility(new CastOnlyDuringPhaseStepSourceAbility(
                TurnPhase.COMBAT, AfterBlockersAreDeclaredCondition.instance
        ));

        // Target unblocked attacking creature becomes blocked.
        this.getSpellAbility().addEffect(new BecomeBlockedTargetEffect());
        this.getSpellAbility().addTarget(new TargetCreaturePermanent(filter));

        // Draw a card.
        this.getSpellAbility().addEffect(new DrawCardSourceControllerEffect(1).concatBy("<br>"));
    }

    private CurtainOfLight(final CurtainOfLight card) {
        super(card);
    }

    @Override
    public CurtainOfLight copy() {
        return new CurtainOfLight(this);
    }
}
