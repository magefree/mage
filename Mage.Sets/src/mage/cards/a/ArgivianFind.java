
package mage.cards.a;

import java.util.UUID;
import mage.abilities.effects.common.ReturnToHandTargetEffect;
import mage.cards.CardImpl;
import mage.cards.CardSetInfo;
import mage.constants.CardType;
import mage.filter.FilterCard;
import mage.filter.predicate.Predicates;
import mage.filter.predicate.mageobject.CardTypePredicate;
import mage.target.common.TargetCardInYourGraveyard;

/**
 *
 * @author emerald000
 */
public final class ArgivianFind extends CardImpl {
    
    private static final FilterCard filter = new FilterCard("artifact or enchantment card");
    static {
        filter.add(Predicates.or(new CardTypePredicate(CardType.ARTIFACT), new CardTypePredicate(CardType.ENCHANTMENT)));
    }

    public ArgivianFind(UUID ownerId, CardSetInfo setInfo) {
        super(ownerId,setInfo,new CardType[]{CardType.INSTANT},"{W}");

        // Return target artifact or enchantment card from your graveyard to your hand.
        this.getSpellAbility().addTarget(new TargetCardInYourGraveyard(filter));
        this.getSpellAbility().addEffect(new ReturnToHandTargetEffect());
    }

    public ArgivianFind(final ArgivianFind card) {
        super(card);
    }

    @Override
    public ArgivianFind copy() {
        return new ArgivianFind(this);
    }
}
