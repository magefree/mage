
package mage.cards.n;

import java.util.UUID;
import mage.abilities.effects.common.DestroyTargetEffect;
import mage.cards.CardImpl;
import mage.cards.CardSetInfo;
import mage.constants.CardType;
import mage.target.common.TargetAttackingOrBlockingCreature;

/**
 *
 * @author Loki
 */
public final class NeckSnap extends CardImpl {

    public NeckSnap(UUID ownerId, CardSetInfo setInfo) {
        super(ownerId,setInfo,new CardType[]{CardType.INSTANT},"{3}{W}");

        // Destroy target attacking or blocking creature.
        this.getSpellAbility().addEffect(new DestroyTargetEffect());
        this.getSpellAbility().addTarget(new TargetAttackingOrBlockingCreature());
    }

    private NeckSnap(final NeckSnap card) {
        super(card);
    }

    @Override
    public NeckSnap copy() {
        return new NeckSnap(this);
    }
}
