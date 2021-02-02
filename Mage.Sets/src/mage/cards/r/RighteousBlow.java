
package mage.cards.r;

import java.util.UUID;
import mage.abilities.effects.common.DamageTargetEffect;
import mage.cards.CardImpl;
import mage.cards.CardSetInfo;
import mage.constants.CardType;
import mage.target.common.TargetAttackingOrBlockingCreature;

/**
 * @author noxx
 */
public final class RighteousBlow extends CardImpl {

    public RighteousBlow(UUID ownerId, CardSetInfo setInfo) {
        super(ownerId,setInfo,new CardType[]{CardType.INSTANT},"{W}");


        // Righteous Blow deals 2 damage to target attacking or blocking creature.
        this.getSpellAbility().addTarget(new TargetAttackingOrBlockingCreature());
        this.getSpellAbility().addEffect(new DamageTargetEffect(2));
    }

    private RighteousBlow(final RighteousBlow card) {
        super(card);
    }

    @Override
    public RighteousBlow copy() {
        return new RighteousBlow(this);
    }
}
