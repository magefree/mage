
package mage.cards.d;

import java.util.UUID;
import mage.abilities.effects.Effect;
import mage.abilities.effects.common.DamageTargetEffect;
import mage.cards.CardImpl;
import mage.cards.CardSetInfo;
import mage.constants.CardType;
import mage.target.common.TargetCreaturePermanent;

/**
 *
 * @author LevelX2
 */
public final class DualShot extends CardImpl {

    public DualShot(UUID ownerId, CardSetInfo setInfo) {
        super(ownerId,setInfo,new CardType[]{CardType.INSTANT},"{R}");

        // Dual Shot deals 1 damage to each of up to two target creatures.
        Effect effect = new DamageTargetEffect(1);
        effect.setText("{this} deals 1 damage to each of up to two target creatures");
        this.getSpellAbility().addEffect(effect);
        this.getSpellAbility().addTarget(new TargetCreaturePermanent(0, 2));
    }

    private DualShot(final DualShot card) {
        super(card);
    }

    @Override
    public DualShot copy() {
        return new DualShot(this);
    }
}
