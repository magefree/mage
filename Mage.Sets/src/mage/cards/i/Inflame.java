
package mage.cards.i;

import java.util.UUID;
import mage.abilities.effects.common.DamageAllEffect;
import mage.cards.CardImpl;
import mage.cards.CardSetInfo;
import mage.constants.CardType;
import mage.filter.common.FilterCreaturePermanent;
import mage.filter.predicate.permanent.WasDealtDamageThisTurnPredicate;

/**
 *
 * @author Quercitron
 */
public final class Inflame extends CardImpl {

    private static final FilterCreaturePermanent FILTER = new FilterCreaturePermanent("creature dealt damage this turn");

    static {
        FILTER.add(WasDealtDamageThisTurnPredicate.instance);
    }

    public Inflame(UUID ownerId, CardSetInfo setInfo) {
        super(ownerId,setInfo,new CardType[]{CardType.INSTANT},"{R}");

        // Inflame deals 2 damage to each creature dealt damage this turn.
        this.getSpellAbility().addEffect(new DamageAllEffect(2, FILTER));
    }

    private Inflame(final Inflame card) {
        super(card);
    }

    @Override
    public Inflame copy() {
        return new Inflame(this);
    }
}
