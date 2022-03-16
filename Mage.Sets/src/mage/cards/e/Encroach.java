
package mage.cards.e;

import java.util.UUID;
import mage.abilities.effects.common.discard.DiscardCardYouChooseTargetEffect;
import mage.cards.CardImpl;
import mage.cards.CardSetInfo;
import mage.constants.CardType;
import mage.constants.SuperType;
import mage.constants.TargetController;
import mage.filter.FilterCard;
import mage.filter.predicate.Predicates;
import mage.target.TargetPlayer;

/**
 *
 * @author LoneFox
 */
public final class Encroach extends CardImpl {

    private static final FilterCard filter = new FilterCard("a nonbasic land card");

    static {
        filter.add(Predicates.not(SuperType.BASIC.getPredicate()));
        filter.add(CardType.LAND.getPredicate());
    }

    public Encroach(UUID ownerId, CardSetInfo setInfo) {
        super(ownerId,setInfo,new CardType[]{CardType.SORCERY},"{B}");

        // Target player reveals their hand. You choose a nonbasic land card from it. That player discards that card.
        this.getSpellAbility().addEffect(new DiscardCardYouChooseTargetEffect(filter, TargetController.ANY));
        this.getSpellAbility().addTarget(new TargetPlayer());
   }

    private Encroach(final Encroach card) {
        super(card);
    }

    @Override
    public Encroach copy() {
        return new Encroach(this);
    }
}
