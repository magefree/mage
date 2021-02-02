
package mage.cards.s;

import java.util.UUID;
import mage.abilities.effects.common.DamageAllEffect;
import mage.cards.CardImpl;
import mage.cards.CardSetInfo;
import mage.constants.CardType;
import mage.filter.common.FilterAttackingCreature;

/**
 *
 * @author michael.napoleon@gmail.com
 */
public final class Sandstorm extends CardImpl {
  
    public Sandstorm(UUID ownerId, CardSetInfo setInfo) {
        super(ownerId,setInfo,new CardType[]{CardType.INSTANT},"{G}");

        // Sandstorm deals 1 damage to each attacking creature.
        this.getSpellAbility().addEffect(new DamageAllEffect(1, new FilterAttackingCreature()));
    }

    private Sandstorm(final Sandstorm card) {
        super(card);
    }

    @Override
    public Sandstorm copy() {
        return new Sandstorm(this);
    }
}
