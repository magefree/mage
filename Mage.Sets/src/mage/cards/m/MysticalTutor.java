
package mage.cards.m;

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
public final class MysticalTutor extends CardImpl {

    private static final FilterCard filter = new FilterCard("instant or sorcery card");
    static{
        filter.add(Predicates.or(
                CardType.INSTANT.getPredicate(),
                CardType.SORCERY.getPredicate()));
    }
    
    public MysticalTutor(UUID ownerId, CardSetInfo setInfo) {
        super(ownerId,setInfo,new CardType[]{CardType.INSTANT},"{U}");


        // Search your library for an instant or sorcery card and reveal that card. Shuffle your library, then put the card on top of it.
        this.getSpellAbility().addEffect(new SearchLibraryPutOnLibraryEffect(new TargetCardInLibrary(filter), true));
    }

    private MysticalTutor(final MysticalTutor card) {
        super(card);
    }

    @Override
    public MysticalTutor copy() {
        return new MysticalTutor(this);
    }
}
