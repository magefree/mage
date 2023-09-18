package mage.cards.u;

import java.util.UUID;
import mage.abilities.effects.common.ReturnToHandTargetEffect;
import mage.abilities.effects.keyword.SurveilEffect;
import mage.cards.CardImpl;
import mage.cards.CardSetInfo;
import mage.constants.CardType;
import mage.target.common.TargetCreaturePermanent;

/**
 *
 * @author TheElk801
 */
public final class UnexplainedDisappearance extends CardImpl {

    public UnexplainedDisappearance(UUID ownerId, CardSetInfo setInfo) {
        super(ownerId, setInfo, new CardType[]{CardType.INSTANT}, "{1}{U}");

        // Return target creature to its owner's hand.
        this.getSpellAbility().addEffect(new ReturnToHandTargetEffect());
        this.getSpellAbility().addTarget(new TargetCreaturePermanent());

        // Surveil 1.
        this.getSpellAbility().addEffect(new SurveilEffect(1).concatBy("<br>"));
    }

    private UnexplainedDisappearance(final UnexplainedDisappearance card) {
        super(card);
    }

    @Override
    public UnexplainedDisappearance copy() {
        return new UnexplainedDisappearance(this);
    }
}
