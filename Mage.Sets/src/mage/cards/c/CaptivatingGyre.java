package mage.cards.c;

import mage.abilities.effects.common.ReturnToHandTargetEffect;
import mage.cards.CardImpl;
import mage.cards.CardSetInfo;
import mage.constants.CardType;
import mage.target.common.TargetCreaturePermanent;

import java.util.UUID;

/**
 * @author TheElk801
 */
public final class CaptivatingGyre extends CardImpl {

    public CaptivatingGyre(UUID ownerId, CardSetInfo setInfo) {
        super(ownerId, setInfo, new CardType[]{CardType.SORCERY}, "{4}{U}{U}");

        // Return up to three target creatures to their owners' hands.
        this.getSpellAbility().addEffect(new ReturnToHandTargetEffect());
        this.getSpellAbility().addTarget(new TargetCreaturePermanent(0, 3));
    }

    private CaptivatingGyre(final CaptivatingGyre card) {
        super(card);
    }

    @Override
    public CaptivatingGyre copy() {
        return new CaptivatingGyre(this);
    }
}
