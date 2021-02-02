
package mage.cards.r;

import java.util.UUID;
import mage.abilities.effects.common.ReturnToHandTargetEffect;
import mage.cards.CardImpl;
import mage.cards.CardSetInfo;
import mage.constants.CardType;
import mage.target.common.TargetControlledPermanent;

/**
 *
 * @author LoneFox
 */
public final class Rescue extends CardImpl {

    public Rescue(UUID ownerId, CardSetInfo setInfo) {
        super(ownerId,setInfo,new CardType[]{CardType.INSTANT},"{U}");

        // Return target permanent you control to its owner's hand.
        this.getSpellAbility().addEffect(new ReturnToHandTargetEffect());
        this.getSpellAbility().addTarget(new TargetControlledPermanent());
    }

    private Rescue(final Rescue card) {
        super(card);
    }

    @Override
    public Rescue copy() {
        return new Rescue(this);
    }
}
