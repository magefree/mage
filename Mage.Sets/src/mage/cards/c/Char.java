
package mage.cards.c;

import java.util.UUID;
import mage.abilities.effects.Effect;
import mage.abilities.effects.common.DamageControllerEffect;
import mage.abilities.effects.common.DamageTargetEffect;
import mage.cards.CardImpl;
import mage.cards.CardSetInfo;
import mage.constants.CardType;
import mage.target.common.TargetAnyTarget;

/**
 *
 * @author LevelX2
 */
public final class Char extends CardImpl {

    public Char(UUID ownerId, CardSetInfo setInfo) {
        super(ownerId,setInfo,new CardType[]{CardType.INSTANT},"{2}{R}");


        // Char deals 4 damage to any target and 2 damage to you.
        this.getSpellAbility().addEffect(new DamageTargetEffect(4));
        this.getSpellAbility().addTarget(new TargetAnyTarget());
        Effect effect = new DamageControllerEffect(2);
        effect.setText("and 2 damage to you");
        this.getSpellAbility().addEffect(effect);
    }

    private Char(final Char card) {
        super(card);
    }

    @Override
    public Char copy() {
        return new Char(this);
    }
}
