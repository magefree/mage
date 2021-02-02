
package mage.cards.f;

import java.util.UUID;
import mage.abilities.effects.common.DamageTargetEffect;
import mage.abilities.effects.keyword.ScryEffect;
import mage.cards.CardImpl;
import mage.cards.CardSetInfo;
import mage.constants.CardType;
import mage.target.common.TargetAnyTarget;

/**
 *
 * @author Styxo
 */
public final class ForceSpark extends CardImpl {

    public ForceSpark(UUID ownerId, CardSetInfo setInfo) {
        super(ownerId,setInfo,new CardType[]{CardType.INSTANT},"{R}");

        // Force Spark deals 1 damage to any target.
        this.getSpellAbility().addEffect(new DamageTargetEffect(1));
        this.getSpellAbility().addTarget(new TargetAnyTarget());

        // Scry 1.
        this.getSpellAbility().addEffect(new ScryEffect(1));
    }

    private ForceSpark(final ForceSpark card) {
        super(card);
    }

    @Override
    public ForceSpark copy() {
        return new ForceSpark(this);
    }
}
