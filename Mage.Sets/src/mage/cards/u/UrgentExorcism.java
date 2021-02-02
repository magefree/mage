
package mage.cards.u;

import java.util.UUID;
import mage.abilities.effects.common.DestroyTargetEffect;
import mage.cards.CardImpl;
import mage.cards.CardSetInfo;
import mage.constants.CardType;
import mage.constants.SubType;
import mage.filter.FilterPermanent;
import mage.filter.predicate.Predicates;
import mage.target.TargetPermanent;

/**
 *
 * @author nantuko
 */
public final class UrgentExorcism extends CardImpl {

    private static final FilterPermanent filter = new FilterPermanent("Spirit or enchantment");

    static {
        filter.add(Predicates.or(
                SubType.SPIRIT.getPredicate(),
                CardType.ENCHANTMENT.getPredicate()));
    }

    public UrgentExorcism(UUID ownerId, CardSetInfo setInfo) {
        super(ownerId,setInfo,new CardType[]{CardType.INSTANT},"{1}{W}");


        // Destroy target Spirit or enchantment.
        this.getSpellAbility().addEffect(new DestroyTargetEffect());
        this.getSpellAbility().addTarget(new TargetPermanent(filter));
    }

    private UrgentExorcism(final UrgentExorcism card) {
        super(card);
    }

    @Override
    public UrgentExorcism copy() {
        return new UrgentExorcism(this);
    }
}
