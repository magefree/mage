
package mage.cards.g;

import java.util.UUID;
import mage.abilities.effects.common.DamageTargetEffect;
import mage.cards.CardImpl;
import mage.cards.CardSetInfo;
import mage.constants.CardType;
import mage.target.common.TargetAttackingOrBlockingCreature;

/**
 *
 * @author fireshoes
 */
public final class GideonsReproach extends CardImpl {

    public GideonsReproach(UUID ownerId, CardSetInfo setInfo) {
        super(ownerId,setInfo,new CardType[]{CardType.INSTANT},"{1}{W}");

        // Gideon's Reproach deals 4 damage to target attacking or blocking creature.
        getSpellAbility().addEffect(new DamageTargetEffect(4));
        getSpellAbility().addTarget(new TargetAttackingOrBlockingCreature());
    }

    private GideonsReproach(final GideonsReproach card) {
        super(card);
    }

    @Override
    public GideonsReproach copy() {
        return new GideonsReproach(this);
    }
}
