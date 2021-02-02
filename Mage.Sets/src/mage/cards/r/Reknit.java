
package mage.cards.r;

import java.util.UUID;
import mage.abilities.effects.common.RegenerateTargetEffect;
import mage.cards.CardImpl;
import mage.cards.CardSetInfo;
import mage.constants.CardType;
import mage.target.TargetPermanent;

/**
 *
 * @author North
 */
public final class Reknit extends CardImpl {

    public Reknit(UUID ownerId, CardSetInfo setInfo) {
        super(ownerId,setInfo,new CardType[]{CardType.INSTANT},"{1}{G/W}");


        // Regenerate target permanent.
        this.getSpellAbility().addEffect(new RegenerateTargetEffect());
        this.getSpellAbility().addTarget(new TargetPermanent());
    }

    private Reknit(final Reknit card) {
        super(card);
    }

    @Override
    public Reknit copy() {
        return new Reknit(this);
    }
}
