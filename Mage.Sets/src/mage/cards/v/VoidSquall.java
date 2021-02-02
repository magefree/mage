
package mage.cards.v;

import java.util.UUID;
import mage.abilities.effects.common.ReturnToHandTargetEffect;
import mage.abilities.keyword.ReboundAbility;
import mage.cards.CardImpl;
import mage.cards.CardSetInfo;
import mage.constants.CardType;
import mage.target.common.TargetNonlandPermanent;

/**
 *
 * @author LevelX2
 */
public final class VoidSquall extends CardImpl {

    public VoidSquall(UUID ownerId, CardSetInfo setInfo) {
        super(ownerId,setInfo,new CardType[]{CardType.SORCERY},"{4}{U}");

        // Return target nonland permanent to its owner's hand.
        this.getSpellAbility().addTarget(new TargetNonlandPermanent());
        this.getSpellAbility().addEffect(new ReturnToHandTargetEffect());        
        
        // Rebound
        this.addAbility(new ReboundAbility());
    }

    private VoidSquall(final VoidSquall card) {
        super(card);
    }

    @Override
    public VoidSquall copy() {
        return new VoidSquall(this);
    }
}
