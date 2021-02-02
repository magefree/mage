
package mage.cards.r;

import java.util.UUID;
import mage.abilities.effects.common.ExileTargetEffect;
import mage.cards.CardImpl;
import mage.cards.CardSetInfo;
import mage.constants.CardType;
import mage.filter.FilterPermanent;
import mage.filter.predicate.Predicates;
import mage.filter.predicate.mageobject.MulticoloredPredicate;
import mage.target.TargetPermanent;

/**
 *
 * @author jgreene40
 */
public final class RadiantPurge extends CardImpl {
    
    private static final FilterPermanent filter = new FilterPermanent("multicolored creature or multicolored enchantment");
    static {
        filter.add(Predicates.or(
                Predicates.and(CardType.CREATURE.getPredicate(), MulticoloredPredicate.instance),
                Predicates.and(CardType.ENCHANTMENT.getPredicate(), MulticoloredPredicate.instance)));
    }

    public RadiantPurge(UUID ownerId, CardSetInfo setInfo) {
        super(ownerId,setInfo,new CardType[]{CardType.INSTANT},"{1}{W}");

        //Exile target multicolored creature or multicolored enchantment.
        this.getSpellAbility().addEffect(new ExileTargetEffect());
        this.getSpellAbility().addTarget(new TargetPermanent(filter));
    }

    private RadiantPurge(final RadiantPurge card) {
        super(card);
    }

    @Override
    public RadiantPurge copy() {
        return new RadiantPurge(this);
    }
}
