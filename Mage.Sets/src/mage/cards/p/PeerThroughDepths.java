package mage.cards.p;

import mage.abilities.effects.common.LookLibraryAndPickControllerEffect;
import mage.cards.CardImpl;
import mage.cards.CardSetInfo;
import mage.constants.CardType;
import mage.constants.PutCards;
import mage.constants.SubType;
import mage.filter.FilterCard;
import mage.filter.predicate.Predicates;

import java.util.UUID;

/**
 *
 * @author LevelX
 */
public final class PeerThroughDepths extends CardImpl {

    private static final FilterCard filter = new FilterCard("an instant or sorcery card");
    static {
        filter.add(Predicates.or(
                CardType.SORCERY.getPredicate(),
                CardType.INSTANT.getPredicate()));
    }


    public PeerThroughDepths (UUID ownerId, CardSetInfo setInfo) {
        super(ownerId,setInfo,new CardType[]{CardType.INSTANT},"{1}{U}");
        this.subtype.add(SubType.ARCANE);

        // Look at the top five cards of your library. You may reveal an instant or sorcery card from among them and put it into your hand. 
        // Put the rest on the bottom of your library in any order.
        this.getSpellAbility().addEffect(new LookLibraryAndPickControllerEffect(5, 1, filter, PutCards.HAND, PutCards.BOTTOM_ANY));
    }

    public PeerThroughDepths (final PeerThroughDepths card) {
        super(card);
    }

    @Override
    public PeerThroughDepths copy() {
        return new PeerThroughDepths(this);
    }
}
