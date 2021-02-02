
package mage.cards.a;

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
public final class ArrowsOfJustice extends CardImpl {

    public ArrowsOfJustice(UUID ownerId, CardSetInfo setInfo) {
        super(ownerId,setInfo,new CardType[]{CardType.INSTANT},"{2}{R/W}");


        // Arrows of Justice deals 4 damage to target attacking or blocking creature.
        getSpellAbility().addEffect(new DamageTargetEffect(4));
        getSpellAbility().addTarget(new TargetAttackingOrBlockingCreature());
    }

    private ArrowsOfJustice(final ArrowsOfJustice card) {
        super(card);
    }

    @Override
    public ArrowsOfJustice copy() {
        return new ArrowsOfJustice(this);
    }
}
