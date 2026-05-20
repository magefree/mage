
package mage.cards.g;

import mage.abilities.effects.common.MillCardsControllerEffect;
import mage.abilities.effects.common.ReturnCardChosenFromGraveyardEffect;
import mage.cards.CardImpl;
import mage.cards.CardSetInfo;
import mage.constants.CardType;
import mage.constants.PutCards;
import mage.filter.FilterCard;
import mage.filter.predicate.Predicates;

import java.util.UUID;

/**
 *
 * @author fireshoes
 */
public final class GrappleWithThePast extends CardImpl {

    private static final FilterCard filter = new FilterCard("creature or land card from your graveyard");

    static {
        filter.add(Predicates.or(
                CardType.CREATURE.getPredicate(),
                CardType.LAND.getPredicate()));
    }

    public GrappleWithThePast(UUID ownerId, CardSetInfo setInfo) {
        super(ownerId,setInfo,new CardType[]{CardType.INSTANT},"{1}{G}");

        // Put the top three cards of your library into your graveyard, then you may return a creature or land card from your graveyard to your hand.
        this.getSpellAbility().addEffect(new MillCardsControllerEffect(3));
        this.getSpellAbility().addEffect(new ReturnCardChosenFromGraveyardEffect(true,
                filter, PutCards.HAND).concatBy(", then"));
    }

    private GrappleWithThePast(final GrappleWithThePast card) {
        super(card);
    }

    @Override
    public GrappleWithThePast copy() {
        return new GrappleWithThePast(this);
    }
}
