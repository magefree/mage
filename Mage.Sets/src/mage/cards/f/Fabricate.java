
package mage.cards.f;

import java.util.UUID;
import mage.abilities.effects.common.search.SearchLibraryPutInHandEffect;
import mage.cards.CardImpl;
import mage.cards.CardSetInfo;
import mage.constants.CardType;
import mage.filter.FilterCard;
import mage.filter.predicate.mageobject.CardTypePredicate;
import mage.target.common.TargetCardInLibrary;

/**
 *
 * @author Loki
 */
public final class Fabricate extends CardImpl {

    private static final FilterCard filter = new FilterCard("artifact");

    static {
        filter.add(new CardTypePredicate(CardType.ARTIFACT));
    }

    public Fabricate(UUID ownerId, CardSetInfo setInfo) {
        super(ownerId,setInfo,new CardType[]{CardType.SORCERY},"{2}{U}");


        // Search your library for an artifact card, reveal it, and put it into your hand. Then shuffle your library.
        this.getSpellAbility().addEffect(new SearchLibraryPutInHandEffect(new TargetCardInLibrary(1, filter), true));
    }

    public Fabricate(final Fabricate card) {
        super(card);
    }

    @Override
    public Fabricate copy() {
        return new Fabricate(this);
    }
}
