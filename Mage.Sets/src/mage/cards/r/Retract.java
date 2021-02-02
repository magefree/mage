
package mage.cards.r;

import java.util.UUID;
import mage.abilities.effects.Effect;
import mage.abilities.effects.common.ReturnToHandFromBattlefieldAllEffect;
import mage.cards.CardImpl;
import mage.cards.CardSetInfo;
import mage.constants.CardType;
import mage.filter.common.FilterControlledArtifactPermanent;

/**
 *
 * @author LevelX2
 */
public final class Retract extends CardImpl {

    public Retract(UUID ownerId, CardSetInfo setInfo) {
        super(ownerId,setInfo,new CardType[]{CardType.INSTANT},"{U}");

        // Return all artifacts you control to their owner's hand.
        Effect effect = new ReturnToHandFromBattlefieldAllEffect(new FilterControlledArtifactPermanent());
        effect.setText("Return all artifacts you control to their owner's hand");
        this.getSpellAbility().addEffect(effect);        
        
    }

    private Retract(final Retract card) {
        super(card);
    }

    @Override
    public Retract copy() {
        return new Retract(this);
    }
}
