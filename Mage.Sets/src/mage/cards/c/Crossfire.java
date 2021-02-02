
package mage.cards.c;

import java.util.UUID;
import mage.abilities.effects.Effect;
import mage.abilities.effects.common.DamageTargetControllerEffect;
import mage.abilities.effects.common.DamageTargetEffect;
import mage.cards.CardImpl;
import mage.cards.CardSetInfo;
import mage.constants.CardType;
import mage.target.common.TargetCreaturePermanent;

/**
 *
 * @author Styxo
 */
public final class Crossfire extends CardImpl {

    public Crossfire(UUID ownerId, CardSetInfo setInfo) {
        super(ownerId,setInfo,new CardType[]{CardType.INSTANT},"{2}{R}{R}");

        // Crossfire deals 4 damge to target creature and 2 damage to that creature's controller.
        this.getSpellAbility().addEffect(new DamageTargetEffect(4));
        Effect effect = new DamageTargetControllerEffect(2);
        effect.setText("and 2 damage to that creature's controller");
        this.getSpellAbility().addEffect(effect);
        this.getSpellAbility().addTarget(new TargetCreaturePermanent());
    }

    private Crossfire(final Crossfire card) {
        super(card);
    }

    @Override
    public Crossfire copy() {
        return new Crossfire(this);
    }
}
