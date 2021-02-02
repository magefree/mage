
package mage.cards.c;

import java.util.UUID;
import mage.abilities.effects.common.ReturnToHandTargetEffect;
import mage.abilities.keyword.BuybackAbility;
import mage.cards.CardImpl;
import mage.cards.CardSetInfo;
import mage.constants.CardType;
import mage.target.TargetPermanent;

/**
 *
 * @author jeffwadsworth
 */
public final class Capsize extends CardImpl {

    public Capsize(UUID ownerId, CardSetInfo setInfo) {
        super(ownerId,setInfo,new CardType[]{CardType.INSTANT},"{1}{U}{U}");


        // Buyback {3}
        this.addAbility(new BuybackAbility("{3}"));
        
        // Return target permanent to its owner's hand.
        this.getSpellAbility().addEffect(new ReturnToHandTargetEffect());
        this.getSpellAbility().addTarget(new TargetPermanent());
    }

    private Capsize(final Capsize card) {
        super(card);
    }

    @Override
    public Capsize copy() {
        return new Capsize(this);
    }
}
