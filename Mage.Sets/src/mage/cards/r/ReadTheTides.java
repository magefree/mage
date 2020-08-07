package mage.cards.r;

import mage.abilities.Mode;
import mage.abilities.effects.common.DrawCardSourceControllerEffect;
import mage.abilities.effects.common.ReturnToHandTargetEffect;
import mage.cards.CardImpl;
import mage.cards.CardSetInfo;
import mage.constants.CardType;
import mage.target.common.TargetCreaturePermanent;

import java.util.UUID;

/**
 * @author TheElk801
 */
public final class ReadTheTides extends CardImpl {

    public ReadTheTides(UUID ownerId, CardSetInfo setInfo) {
        super(ownerId, setInfo, new CardType[]{CardType.SORCERY}, "{5}{U}");

        // Choose one —
        // • Draw three cards.
        this.getSpellAbility().addEffect(new DrawCardSourceControllerEffect(3));

        // • Return up to two target creatures to their owners' hands.
        Mode mode = new Mode(new ReturnToHandTargetEffect());
        mode.addTarget(new TargetCreaturePermanent(0, 2));
        this.getSpellAbility().addMode(mode);
    }

    private ReadTheTides(final ReadTheTides card) {
        super(card);
    }

    @Override
    public ReadTheTides copy() {
        return new ReadTheTides(this);
    }
}
