
package mage.cards.b;

import java.util.UUID;
import mage.abilities.effects.common.ReturnToHandTargetEffect;
import mage.cards.CardImpl;
import mage.cards.CardSetInfo;
import mage.constants.CardType;
import mage.target.TargetPermanent;

/**
 *
 * @author Loki
 */
public final class Boomerang extends CardImpl {

    public Boomerang(UUID ownerId, CardSetInfo setInfo) {
        super(ownerId,setInfo,new CardType[]{CardType.INSTANT},"{U}{U}");

        
        // Return target permanent to its owner's hand. 
        this.getSpellAbility().addEffect(new ReturnToHandTargetEffect());
        this.getSpellAbility().addTarget(new TargetPermanent());
    }

    public Boomerang(final Boomerang card) {
        super(card);
    }

    @Override
    public Boomerang copy() {
        return new Boomerang(this);
    }
}
