
package mage.cards.s;

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
public final class Sandblast extends CardImpl {

    public Sandblast(UUID ownerId, CardSetInfo setInfo) {
        super(ownerId,setInfo,new CardType[]{CardType.INSTANT},"{2}{W}");

        // Sandblast deals 5 damage to target attacking or blocking creature.
        getSpellAbility().addEffect(new DamageTargetEffect(5));
        getSpellAbility().addTarget(new TargetAttackingOrBlockingCreature());
    }

    private Sandblast(final Sandblast card) {
        super(card);
    }

    @Override
    public Sandblast copy() {
        return new Sandblast(this);
    }
}
