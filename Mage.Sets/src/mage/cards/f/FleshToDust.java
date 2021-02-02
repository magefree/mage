
package mage.cards.f;

import java.util.UUID;
import mage.abilities.effects.common.DestroyTargetEffect;
import mage.cards.CardImpl;
import mage.cards.CardSetInfo;
import mage.constants.CardType;
import mage.target.common.TargetCreaturePermanent;

/**
 * @author noxx
 */
public final class FleshToDust extends CardImpl {

    public FleshToDust(UUID ownerId, CardSetInfo setInfo) {
        super(ownerId,setInfo,new CardType[]{CardType.INSTANT},"{3}{B}{B}");


        // Destroy target creature. It can't be regenerated.
        this.getSpellAbility().addTarget(new TargetCreaturePermanent());
        this.getSpellAbility().addEffect(new DestroyTargetEffect(true));
    }

    private FleshToDust(final FleshToDust card) {
        super(card);
    }

    @Override
    public FleshToDust copy() {
        return new FleshToDust(this);
    }
}
