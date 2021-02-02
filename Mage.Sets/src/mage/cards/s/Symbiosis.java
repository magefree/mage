
package mage.cards.s;

import java.util.UUID;
import mage.abilities.effects.common.continuous.BoostTargetEffect;
import mage.cards.CardImpl;
import mage.cards.CardSetInfo;
import mage.constants.CardType;
import mage.constants.Duration;
import mage.target.common.TargetCreaturePermanent;

/**
 *
 * @author LoneFox

 */
public final class Symbiosis extends CardImpl {

    public Symbiosis(UUID ownerId, CardSetInfo setInfo) {
        super(ownerId,setInfo,new CardType[]{CardType.INSTANT},"{1}{G}");

        // Two target creatures each get +2/+2 until end of turn.
        this.getSpellAbility().addTarget(new TargetCreaturePermanent(2));
        this.getSpellAbility().addEffect(new BoostTargetEffect(2, 2, Duration.EndOfTurn));
    }

    private Symbiosis(final Symbiosis card) {
        super(card);
    }

    @Override
    public Symbiosis copy() {
        return new Symbiosis(this);
    }
}
