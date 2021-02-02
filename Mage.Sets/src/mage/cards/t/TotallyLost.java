
package mage.cards.t;

import java.util.UUID;
import mage.abilities.effects.common.PutOnLibraryTargetEffect;
import mage.cards.CardImpl;
import mage.cards.CardSetInfo;
import mage.constants.CardType;
import mage.target.common.TargetNonlandPermanent;

/**
 *
 * @author Plopman
 */
public final class TotallyLost extends CardImpl {

    public TotallyLost(UUID ownerId, CardSetInfo setInfo) {
        super(ownerId,setInfo,new CardType[]{CardType.INSTANT},"{4}{U}");


        // Put target nonland permanent on top of its owner's library.
        this.getSpellAbility().addEffect(new PutOnLibraryTargetEffect(true));
        this.getSpellAbility().addTarget(new TargetNonlandPermanent());
    }

    private TotallyLost(final TotallyLost card) {
        super(card);
    }

    @Override
    public TotallyLost copy() {
        return new TotallyLost(this);
    }
}
