
package mage.cards.b;

import java.util.UUID;
import mage.abilities.effects.Effect;
import mage.abilities.effects.common.DamageTargetEffect;
import mage.abilities.effects.common.continuous.BoostTargetEffect;
import mage.cards.CardImpl;
import mage.cards.CardSetInfo;
import mage.constants.CardType;
import mage.constants.Duration;
import mage.target.common.TargetCreaturePermanent;

/**
 *
 * @author fireshoes
 */
public final class BurningCloak extends CardImpl {

    public BurningCloak(UUID ownerId, CardSetInfo setInfo) {
        super(ownerId,setInfo,new CardType[]{CardType.SORCERY},"{R}");

        // Target creature gets +2/+0 until end of turn. Burning Cloak deals 2 damage to that creature.
        this.getSpellAbility().addEffect(new BoostTargetEffect(2, 0, Duration.EndOfTurn));
        Effect effect = new DamageTargetEffect(2);
        effect.setText("{this} deals 2 damage to that creature");
        this.getSpellAbility().addEffect(effect);
        this.getSpellAbility().addTarget(new TargetCreaturePermanent());
    }

    private BurningCloak(final BurningCloak card) {
        super(card);
    }

    @Override
    public BurningCloak copy() {
        return new BurningCloak(this);
    }
}
