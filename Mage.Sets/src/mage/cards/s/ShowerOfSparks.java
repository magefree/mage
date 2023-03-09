
package mage.cards.s;

import java.util.UUID;
import mage.abilities.effects.Effect;
import mage.abilities.effects.common.DamageTargetEffect;
import mage.cards.CardImpl;
import mage.cards.CardSetInfo;
import mage.constants.CardType;
import mage.target.common.TargetCreaturePermanent;
import mage.target.common.TargetPlayerOrPlaneswalker;
import mage.target.targetpointer.SecondTargetPointer;

/**
 *
 * @author LevelX2
 */
public final class ShowerOfSparks extends CardImpl {

    public ShowerOfSparks(UUID ownerId, CardSetInfo setInfo) {
        super(ownerId, setInfo, new CardType[]{CardType.INSTANT}, "{R}");
        
        // Shower of sparks deals 1 damage to target creature and 1 damage to target player.
        this.getSpellAbility().addEffect(new DamageTargetEffect(1, true, "to target creature and 1 damage to target player or planeswalker")); //code modified from punish the enemy
        target = new TargetCreaturePermanent();
        this.getSpellAbility().addTarget(target);
        Target target = new TargetPlayerOrPlaneswalker();
        this.getSpellAbility().addTarget(target);
    }

    private ShowerOfSparks(final ShowerOfSparks card) {
        super(card);
    }

    @Override
    public ShowerOfSparks copy() {
        return new ShowerOfSparks(this);
    }
}
