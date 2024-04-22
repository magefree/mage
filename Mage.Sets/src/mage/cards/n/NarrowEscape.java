

package mage.cards.n;

import java.util.UUID;
import mage.abilities.effects.common.GainLifeEffect;
import mage.abilities.effects.common.ReturnToHandTargetEffect;
import mage.cards.CardImpl;
import mage.cards.CardSetInfo;
import mage.constants.CardType;
import mage.target.common.TargetControlledPermanent;

/**
 *
 * @author Loki
 */
public final class NarrowEscape extends CardImpl {

    public NarrowEscape (UUID ownerId, CardSetInfo setInfo) {
        super(ownerId,setInfo,new CardType[]{CardType.INSTANT},"{2}{W}");
        
        // Return target permanent you control to its owner's hand. You gain 4 life.
        this.getSpellAbility().addTarget(new TargetControlledPermanent());
        this.getSpellAbility().addEffect(new ReturnToHandTargetEffect());
        this.getSpellAbility().addEffect(new GainLifeEffect(4));
    }

    private NarrowEscape(final NarrowEscape card) {
        super(card);
    }

    @Override
    public NarrowEscape copy() {
        return new NarrowEscape(this);
    }
}
