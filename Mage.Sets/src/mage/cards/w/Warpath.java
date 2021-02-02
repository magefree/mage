
package mage.cards.w;

import java.util.UUID;
import mage.abilities.effects.common.DamageAllEffect;
import mage.cards.CardImpl;
import mage.cards.CardSetInfo;
import mage.constants.CardType;
import mage.filter.common.FilterCreaturePermanent;
import mage.filter.predicate.Predicates;
import mage.filter.predicate.permanent.BlockedPredicate;
import mage.filter.predicate.permanent.BlockingPredicate;

/**
 *
 * @author L_J
 */
public final class Warpath extends CardImpl {

    private static final FilterCreaturePermanent filter = new FilterCreaturePermanent("blocking creature and each blocked creature");

    static {
        filter.add(Predicates.or(
                BlockingPredicate.instance,
                BlockedPredicate.instance));
    }

    public Warpath(UUID ownerId, CardSetInfo setInfo) {
        super(ownerId,setInfo,new CardType[]{CardType.INSTANT},"{3}{R}");

        // Warpath deals 3 damage to each blocking creature and each blocked creature.
        this.getSpellAbility().addEffect(new DamageAllEffect(3, filter));
    }

    private Warpath(final Warpath card) {
        super(card);
    }

    @Override
    public Warpath copy() {
        return new Warpath(this);
    }
}
