

package mage.cards.d;

import java.util.UUID;
import mage.abilities.effects.common.ReturnToHandTargetEffect;
import mage.cards.CardImpl;
import mage.cards.CardSetInfo;
import mage.constants.CardType;
import mage.target.common.TargetNonlandPermanent;

/**
 *
 * @author nantuko
 */
public final class Disperse extends CardImpl {

    public Disperse (UUID ownerId, CardSetInfo setInfo) {
        super(ownerId,setInfo,new CardType[]{CardType.INSTANT},"{1}{U}");

        // Return target nonland permanent to its owner's hand.
        this.getSpellAbility().addTarget(new TargetNonlandPermanent());
        this.getSpellAbility().addEffect(new ReturnToHandTargetEffect());
    }

    private Disperse(final Disperse card) {
        super(card);
    }

    @Override
    public Disperse copy() {
        return new Disperse(this);
    }

}
