
package mage.cards.r;

import java.util.UUID;
import mage.abilities.effects.common.DamageAllEffect;
import mage.cards.CardImpl;
import mage.cards.CardSetInfo;
import mage.constants.CardType;
import mage.filter.common.FilterAttackingCreature;

/**
 *
 * @author North
 */
public final class RainOfBlades extends CardImpl {

    public RainOfBlades(UUID ownerId, CardSetInfo setInfo) {
        super(ownerId,setInfo,new CardType[]{CardType.INSTANT},"{W}");


        // Rain of Blades deals 1 damage to each attacking creature.
        this.getSpellAbility().addEffect(new DamageAllEffect(1, new FilterAttackingCreature()));
    }

    private RainOfBlades(final RainOfBlades card) {
        super(card);
    }

    @Override
    public RainOfBlades copy() {
        return new RainOfBlades(this);
    }
}
