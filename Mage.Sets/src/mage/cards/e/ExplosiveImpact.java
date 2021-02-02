

package mage.cards.e;

import java.util.UUID;
import mage.abilities.effects.common.DamageTargetEffect;
import mage.cards.CardImpl;
import mage.cards.CardSetInfo;
import mage.constants.CardType;
import mage.target.common.TargetAnyTarget;

/**
 *
 * @author LevelX2
 */
public final class ExplosiveImpact extends CardImpl {

    public ExplosiveImpact(UUID ownerId, CardSetInfo setInfo) {
        super(ownerId,setInfo,new CardType[]{CardType.INSTANT},"{5}{R}");


        // Explosive Impact deals 5 damage to any target.
        this.getSpellAbility().addTarget(new TargetAnyTarget());
        this.getSpellAbility().addEffect(new DamageTargetEffect(5));
    }

    private ExplosiveImpact(final ExplosiveImpact card) {
        super(card);
    }

    @Override
    public ExplosiveImpact copy() {
        return new ExplosiveImpact(this);
    }

}
