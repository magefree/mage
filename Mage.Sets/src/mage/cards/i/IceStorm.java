
package mage.cards.i;

import java.util.UUID;
import mage.abilities.effects.common.DestroyTargetEffect;
import mage.cards.CardImpl;
import mage.cards.CardSetInfo;
import mage.constants.CardType;
import mage.target.common.TargetLandPermanent;

/**
 *
 * @author fireshoes
 */
public final class IceStorm extends CardImpl {

    public IceStorm(UUID ownerId, CardSetInfo setInfo) {
        super(ownerId,setInfo,new CardType[]{CardType.SORCERY},"{2}{G}");

        // Destroy target land.
        this.getSpellAbility().addTarget(new TargetLandPermanent());
        this.getSpellAbility().addEffect(new DestroyTargetEffect());
        
    }

    private IceStorm(final IceStorm card) {
        super(card);
    }

    @Override
    public IceStorm copy() {
        return new IceStorm(this);
    }
}
