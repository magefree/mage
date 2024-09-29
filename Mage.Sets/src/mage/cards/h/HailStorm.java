
package mage.cards.h;

import java.util.UUID;
import mage.abilities.effects.common.DamageAllEffect;
import mage.abilities.effects.common.DamageControllerEffect;
import mage.cards.CardImpl;
import mage.cards.CardSetInfo;
import mage.constants.CardType;
import mage.filter.StaticFilters;
import mage.filter.common.FilterAttackingCreature;

/**
 *
 * @author L_J
 */
public final class HailStorm extends CardImpl {

    public HailStorm(UUID ownerId, CardSetInfo setInfo) {
        super(ownerId,setInfo,new CardType[]{CardType.INSTANT},"{1}{G}{G}");

        // Hail Storm deals 2 damage to each attacking creature and 1 damage to you and each creature you control.
        this.getSpellAbility().addEffect(new DamageAllEffect(2, new FilterAttackingCreature()));
        this.getSpellAbility().addEffect(new DamageControllerEffect(1).setText("and 1 damage to you"));
        this.getSpellAbility().addEffect(new DamageAllEffect(1, StaticFilters.FILTER_PERMANENT_CREATURE_CONTROLLED).setText("and each creature you control."));
    }

    private HailStorm(final HailStorm card) {
        super(card);
    }

    @Override
    public HailStorm copy() {
        return new HailStorm(this);
    }
}
