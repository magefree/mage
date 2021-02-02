
package mage.cards.v;

import java.util.UUID;
import mage.abilities.effects.common.ReturnToHandTargetEffect;
import mage.cards.CardImpl;
import mage.cards.CardSetInfo;
import mage.constants.CardType;
import mage.target.common.TargetNonlandPermanent;

/**
 *
 * @author LevelX2
 */
public final class VoidSnare extends CardImpl {

    public VoidSnare(UUID ownerId, CardSetInfo setInfo) {
        super(ownerId,setInfo,new CardType[]{CardType.SORCERY},"{U}");


        // Return target nonland permanent to its owner's hand.
        this.getSpellAbility().addTarget(new TargetNonlandPermanent());
        this.getSpellAbility().addEffect(new ReturnToHandTargetEffect());
    }

    private VoidSnare(final VoidSnare card) {
        super(card);
    }

    @Override
    public VoidSnare copy() {
        return new VoidSnare(this);
    }
}
