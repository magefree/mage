
package mage.cards.p;

import java.util.UUID;
import mage.abilities.effects.common.DamageTargetEffect;
import mage.abilities.keyword.ReplicateAbility;
import mage.cards.CardImpl;
import mage.cards.CardSetInfo;
import mage.constants.CardType;
import mage.target.common.TargetAnyTarget;

/**
 *
 * @author LevelX2
 */
public final class Pyromatics extends CardImpl {

    public Pyromatics(UUID ownerId, CardSetInfo setInfo) {
        super(ownerId,setInfo,new CardType[]{CardType.INSTANT},"{1}{R}");


        // Replicate {1}{R}
        this.addAbility(new ReplicateAbility("{1}{R}"));
        // Pyromatics deals 1 damage to any target.
        this.getSpellAbility().addEffect(new DamageTargetEffect(1));
        this.getSpellAbility().addTarget(new TargetAnyTarget());

    }

    private Pyromatics(final Pyromatics card) {
        super(card);
    }

    @Override
    public Pyromatics copy() {
        return new Pyromatics(this);
    }
}
