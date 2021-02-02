
package mage.cards.w;

import java.util.UUID;
import mage.abilities.effects.common.ReturnToHandTargetEffect;
import mage.abilities.keyword.SplitSecondAbility;
import mage.cards.CardImpl;
import mage.cards.CardSetInfo;
import mage.constants.CardType;
import mage.target.TargetPermanent;

/**
 *
 * @author Plopman
 */
public final class WipeAway extends CardImpl {

    public WipeAway(UUID ownerId, CardSetInfo setInfo) {
        super(ownerId,setInfo,new CardType[]{CardType.INSTANT},"{1}{U}{U}");


        // Split second
        this.addAbility(new SplitSecondAbility());
        // Return target permanent to its owner's hand.
        this.getSpellAbility().addTarget(new TargetPermanent());
        this.getSpellAbility().addEffect(new ReturnToHandTargetEffect());
    }

    private WipeAway(final WipeAway card) {
        super(card);
    }

    @Override
    public WipeAway copy() {
        return new WipeAway(this);
    }
}
