
package mage.cards.p;

import java.util.UUID;
import mage.abilities.effects.common.DamageTargetEffect;
import mage.cards.CardImpl;
import mage.cards.CardSetInfo;
import mage.constants.CardType;
import mage.target.common.TargetCreaturePermanent;

/**
 *
 * @author LoneFox
 */
public final class PinpointAvalanche extends CardImpl {

    public PinpointAvalanche(UUID ownerId, CardSetInfo setInfo) {
        super(ownerId,setInfo,new CardType[]{CardType.INSTANT},"{3}{R}{R}");

        // Pinpoint Avalanche deals 4 damage to target creature. The damage can't be prevented.
        this.getSpellAbility().addEffect(new DamageTargetEffect(4, false));
        this.getSpellAbility().addTarget(new TargetCreaturePermanent());
    }

    private PinpointAvalanche(final PinpointAvalanche card) {
        super(card);
    }

    @Override
    public PinpointAvalanche copy() {
        return new PinpointAvalanche(this);
    }
}
