
package mage.cards.d;

import java.util.UUID;
import mage.abilities.effects.common.search.SearchLibraryPutInPlayEffect;
import mage.abilities.keyword.StormAbility;
import mage.cards.CardImpl;
import mage.cards.CardSetInfo;
import mage.constants.CardType;
import mage.constants.SubType;
import mage.filter.common.FilterPermanentCard;
import mage.target.common.TargetCardInLibrary;

/**
 *
 * @author Plopman
 */
public final class Dragonstorm extends CardImpl {

    private static final FilterPermanentCard filter = new FilterPermanentCard("Dragon permanent card");

    static {
        filter.add(SubType.DRAGON.getPredicate());
    }

    public Dragonstorm(UUID ownerId, CardSetInfo setInfo) {
        super(ownerId,setInfo,new CardType[]{CardType.SORCERY},"{8}{R}");

        // Search your library for a Dragon permanent card and put it onto the battlefield. Then shuffle your library.
        this.getSpellAbility().addEffect(new SearchLibraryPutInPlayEffect(new TargetCardInLibrary(filter), false));
        // Storm
        this.addAbility(new StormAbility());
    }

    private Dragonstorm(final Dragonstorm card) {
        super(card);
    }

    @Override
    public Dragonstorm copy() {
        return new Dragonstorm(this);
    }
}
