
package mage.cards.d;

import java.util.UUID;
import mage.abilities.effects.common.RegenerateTargetEffect;
import mage.cards.CardImpl;
import mage.cards.CardSetInfo;
import mage.constants.CardType;
import mage.target.common.TargetCreaturePermanent;

/**
 *
 * @author KholdFuzion

 */
public final class DeathWard extends CardImpl {

    public DeathWard(UUID ownerId, CardSetInfo setInfo) {
        super(ownerId,setInfo,new CardType[]{CardType.INSTANT},"{W}");


        // Regenerate target creature.
        this.getSpellAbility().addEffect(new RegenerateTargetEffect());
        this.getSpellAbility().addTarget(new TargetCreaturePermanent());
    }

    private DeathWard(final DeathWard card) {
        super(card);
    }

    @Override
    public DeathWard copy() {
        return new DeathWard(this);
    }
}
