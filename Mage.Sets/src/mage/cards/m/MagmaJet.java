
package mage.cards.m;

import java.util.UUID;
import mage.abilities.effects.common.DamageTargetEffect;
import mage.abilities.effects.keyword.ScryEffect;
import mage.cards.CardImpl;
import mage.cards.CardSetInfo;
import mage.constants.CardType;
import mage.target.common.TargetAnyTarget;

/**
 *
 * @author jonubuu
 */
public final class MagmaJet extends CardImpl {

    public MagmaJet(UUID ownerId, CardSetInfo setInfo) {
        super(ownerId,setInfo,new CardType[]{CardType.INSTANT},"{1}{R}");


        // Magma Jet deals 2 damage to any target.
        this.getSpellAbility().addEffect(new DamageTargetEffect(2));
        this.getSpellAbility().addTarget(new TargetAnyTarget());
        // Scry 2.
        this.getSpellAbility().addEffect(new ScryEffect(2));
    }

    private MagmaJet(final MagmaJet card) {
        super(card);
    }

    @Override
    public MagmaJet copy() {
        return new MagmaJet(this);
    }
}
