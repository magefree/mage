
package mage.cards.b;

import java.util.UUID;
import mage.abilities.effects.common.DamageTargetEffect;
import mage.abilities.effects.keyword.ScryEffect;
import mage.cards.CardImpl;
import mage.cards.CardSetInfo;
import mage.constants.CardType;
import mage.target.common.TargetAnyTarget;

/**
 *
 * @author LevelX2
 */
public final class BoltOfKeranos extends CardImpl {

    public BoltOfKeranos(UUID ownerId, CardSetInfo setInfo) {
        super(ownerId,setInfo,new CardType[]{CardType.SORCERY},"{1}{R}{R}");


        // Bolt of Keranos deals 3 damage to target creature and/or player. Scry 1.
        this.getSpellAbility().addEffect(new DamageTargetEffect(3));
        this.getSpellAbility().addTarget(new TargetAnyTarget());
        this.getSpellAbility().addEffect(new ScryEffect(1));
    }

    private BoltOfKeranos(final BoltOfKeranos card) {
        super(card);
    }

    @Override
    public BoltOfKeranos copy() {
        return new BoltOfKeranos(this);
    }
}
