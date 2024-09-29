package mage.cards.u;

import mage.abilities.effects.common.ReturnToHandTargetEffect;
import mage.abilities.effects.keyword.SurveilEffect;
import mage.cards.CardImpl;
import mage.cards.CardSetInfo;
import mage.constants.CardType;
import mage.target.common.TargetNonlandPermanent;

import java.util.UUID;

/**
 * @author TheElk801
 */
public final class UnauthorizedExit extends CardImpl {

    public UnauthorizedExit(UUID ownerId, CardSetInfo setInfo) {
        super(ownerId, setInfo, new CardType[]{CardType.INSTANT}, "{1}{U}");

        // Return target nonland permanent to its owner's hand. Surveil 1.
        this.getSpellAbility().addEffect(new ReturnToHandTargetEffect());
        this.getSpellAbility().addTarget(new TargetNonlandPermanent());
        this.getSpellAbility().addEffect(new SurveilEffect(1));
    }

    private UnauthorizedExit(final UnauthorizedExit card) {
        super(card);
    }

    @Override
    public UnauthorizedExit copy() {
        return new UnauthorizedExit(this);
    }
}
