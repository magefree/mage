
package mage.cards.h;

import java.util.UUID;
import mage.abilities.effects.common.DamageAllEffect;
import mage.abilities.effects.common.DamageControllerEffect;
import mage.cards.CardImpl;
import mage.cards.CardSetInfo;
import mage.constants.CardType;
import mage.constants.TargetController;
import mage.filter.common.FilterAttackingCreature;
import mage.filter.common.FilterCreaturePermanent;
import mage.filter.predicate.permanent.ControllerPredicate;

/**
 *
 * @author L_J
 */
public final class HailStorm extends CardImpl {

    private static final FilterCreaturePermanent filter = new FilterCreaturePermanent("creature you control");
    static {
        filter.add(new ControllerPredicate(TargetController.YOU));
    }
    
    public HailStorm(UUID ownerId, CardSetInfo setInfo) {
        super(ownerId,setInfo,new CardType[]{CardType.INSTANT},"{1}{G}{G}");

        // Hail Storm deals 2 damage to each attacking creature and 1 damage to you and each creature you control.
        this.getSpellAbility().addEffect(new DamageAllEffect(2, new FilterAttackingCreature()));
        this.getSpellAbility().addEffect(new DamageControllerEffect(1).setText("and 1 damage to you "));
        this.getSpellAbility().addEffect(new DamageAllEffect(1, filter).setText("and each creature you control."));
    }

    public HailStorm(final HailStorm card) {
        super(card);
    }

    @Override
    public HailStorm copy() {
        return new HailStorm(this);
    }
}
