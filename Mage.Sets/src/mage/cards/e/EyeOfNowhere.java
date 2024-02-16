

package mage.cards.e;

import mage.abilities.effects.common.ReturnToHandTargetEffect;
import mage.cards.CardImpl;
import mage.cards.CardSetInfo;
import mage.constants.CardType;
import mage.constants.SubType;
import mage.target.TargetPermanent;

import java.util.UUID;

/**
 *
 * @author Loki
 */
public final class EyeOfNowhere extends CardImpl {

    public EyeOfNowhere (UUID ownerId, CardSetInfo setInfo) {
        super(ownerId,setInfo,new CardType[]{CardType.SORCERY},"{U}{U}");
        this.subtype.add(SubType.ARCANE);
        
        // Return target permanent to its owner's hand.        
        this.getSpellAbility().addEffect(new ReturnToHandTargetEffect());
        this.getSpellAbility().addTarget(new TargetPermanent());

    }

    private EyeOfNowhere(final EyeOfNowhere card) {
        super(card);
    }

    @Override
    public EyeOfNowhere copy() {
        return new EyeOfNowhere(this);
    }

}
