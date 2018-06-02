
package mage.cards.r;

import java.util.UUID;
import mage.abilities.effects.common.ReturnToHandTargetEffect;
import mage.cards.CardImpl;
import mage.cards.CardSetInfo;
import mage.constants.CardType;
import mage.target.common.TargetCardInYourGraveyard;

/**
 *
 * @author KholdFuzion

 */
public final class Regrowth extends CardImpl {

    public Regrowth(UUID ownerId, CardSetInfo setInfo) {
        super(ownerId,setInfo,new CardType[]{CardType.SORCERY},"{1}{G}");


        // Return target card from your graveyard to your hand.
        this.getSpellAbility().addEffect(new ReturnToHandTargetEffect());
        this.getSpellAbility().addTarget(new TargetCardInYourGraveyard());
    }

    public Regrowth(final Regrowth card) {
        super(card);
    }

    @Override
    public Regrowth copy() {
        return new Regrowth(this);
    }
}
