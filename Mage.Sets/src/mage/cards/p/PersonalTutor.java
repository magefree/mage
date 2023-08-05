
package mage.cards.p;

import java.util.UUID;
import mage.abilities.effects.common.search.SearchLibraryPutOnLibraryEffect;
import mage.cards.CardImpl;
import mage.cards.CardSetInfo;
import mage.constants.CardType;
import mage.filter.FilterCard;
import mage.target.common.TargetCardInLibrary;

/**
 *
 * @author Plopman
 */
public final class PersonalTutor extends CardImpl {
    private static final FilterCard filter = new FilterCard("sorcery card");
    static{
        filter.add(CardType.SORCERY.getPredicate());
    }
   
    public PersonalTutor(UUID ownerId, CardSetInfo setInfo) {
        super(ownerId,setInfo,new CardType[]{CardType.SORCERY},"{U}");


        // Search your library for a sorcery card and reveal that card. Shuffle your library, then put the card on top of it.
        this.getSpellAbility().addEffect(new SearchLibraryPutOnLibraryEffect(new TargetCardInLibrary(filter), true));
    }

    private PersonalTutor(final PersonalTutor card) {
        super(card);
    }

    @Override
    public PersonalTutor copy() {
        return new PersonalTutor(this);
    }
}
