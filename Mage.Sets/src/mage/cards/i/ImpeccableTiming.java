
package mage.cards.i;

import java.util.UUID;
import mage.abilities.effects.common.DamageTargetEffect;
import mage.cards.CardImpl;
import mage.cards.CardSetInfo;
import mage.constants.CardType;
import mage.target.common.TargetAttackingOrBlockingCreature;

/**
 *
 * @author LevelX2
 */
public final class ImpeccableTiming extends CardImpl {

    public ImpeccableTiming(UUID ownerId, CardSetInfo setInfo) {
        super(ownerId,setInfo,new CardType[]{CardType.INSTANT},"{1}{W}");

        // Impeccable Timing deals 3 damage to target attacking or blocking creature.
        this.getSpellAbility().addTarget(new TargetAttackingOrBlockingCreature());
        this.getSpellAbility().addEffect(new DamageTargetEffect(3));
    }

    private ImpeccableTiming(final ImpeccableTiming card) {
        super(card);
    }

    @Override
    public ImpeccableTiming copy() {
        return new ImpeccableTiming(this);
    }
}
