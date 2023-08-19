
package mage.cards.e;

import java.util.UUID;
import mage.abilities.effects.common.search.SearchLibraryPutOnLibraryEffect;
import mage.cards.CardImpl;
import mage.cards.CardSetInfo;
import mage.constants.CardType;
import mage.filter.FilterCard;
import mage.filter.predicate.Predicates;
import mage.target.common.TargetCardInLibrary;

/**
 *
 * @author Plopman
 */
public final class EnlightenedTutor extends CardImpl {

    private static final FilterCard filter = new FilterCard("artifact or enchantment card");
    static{
        filter.add(Predicates.or(
                CardType.ARTIFACT.getPredicate(),
                CardType.ENCHANTMENT.getPredicate()));
    }
    
    public EnlightenedTutor(UUID ownerId, CardSetInfo setInfo) {
        super(ownerId,setInfo,new CardType[]{CardType.INSTANT},"{W}");


        // Search your library for an artifact or enchantment card and reveal that card. Shuffle your library, then put the card on top of it.
        this.getSpellAbility().addEffect(new SearchLibraryPutOnLibraryEffect(new TargetCardInLibrary(filter), true));
    }

    private EnlightenedTutor(final EnlightenedTutor card) {
        super(card);
    }

    @Override
    public EnlightenedTutor copy() {
        return new EnlightenedTutor(this);
    }
}
