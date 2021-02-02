
package mage.cards.b;

import java.util.UUID;
import mage.abilities.effects.common.DamageTargetEffect;
import mage.cards.CardImpl;
import mage.cards.CardSetInfo;
import mage.constants.CardType;
import mage.target.common.TargetCreaturePermanent;

/**
 *
 * @author L_J
 */
public final class Bombard extends CardImpl {

    public Bombard(UUID ownerId, CardSetInfo setInfo) {
        super(ownerId,setInfo,new CardType[]{CardType.INSTANT},"{2}{R}");

        // Bombard deals 4 damage to target creature.
        this.getSpellAbility().addTarget(new TargetCreaturePermanent());
        this.getSpellAbility().addEffect(new DamageTargetEffect(4));
    }

    private Bombard(final Bombard card) {
        super(card);
    }

    @Override
    public Bombard copy() {
        return new Bombard(this);
    }

}
