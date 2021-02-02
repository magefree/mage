
package mage.cards.b;

import java.util.UUID;
import mage.abilities.effects.common.UntapTargetEffect;
import mage.cards.CardImpl;
import mage.cards.CardSetInfo;
import mage.constants.CardType;
import mage.target.TargetPermanent;

/**
 *
 * @author Plopman
 */
public final class BurstOfEnergy extends CardImpl {

    public BurstOfEnergy(UUID ownerId, CardSetInfo setInfo) {
        super(ownerId,setInfo,new CardType[]{CardType.INSTANT},"{W}");


        // Untap target permanent.
        this.getSpellAbility().addEffect(new UntapTargetEffect());
        this.getSpellAbility().addTarget(new TargetPermanent());
    }

    private BurstOfEnergy(final BurstOfEnergy card) {
        super(card);
    }

    @Override
    public BurstOfEnergy copy() {
        return new BurstOfEnergy(this);
    }
}
