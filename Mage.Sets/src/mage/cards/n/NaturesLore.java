
package mage.cards.n;

import mage.abilities.effects.common.search.SearchLibraryPutInPlayEffect;
import mage.cards.CardImpl;
import mage.cards.CardSetInfo;
import mage.constants.CardType;
import mage.constants.SubType;
import mage.filter.FilterCard;
import mage.target.common.TargetCardInLibrary;

import java.util.UUID;

/**
 *
 * @author LevelX2
 */
public final class NaturesLore extends CardImpl {

    private static final FilterCard filter = new FilterCard("Forest card");

    static {
        filter.add(SubType.FOREST.getPredicate());
    }

    public NaturesLore(UUID ownerId, CardSetInfo setInfo) {
        super(ownerId,setInfo,new CardType[]{CardType.SORCERY},"{1}{G}");


        // Search your library for a Forest card and put that card onto the battlefield. Then shuffle your library.
        this.getSpellAbility().addEffect(new SearchLibraryPutInPlayEffect(new TargetCardInLibrary(filter), false, true));
    }

    private NaturesLore(final NaturesLore card) {
        super(card);
    }

    @Override
    public NaturesLore copy() {
        return new NaturesLore(this);
    }
}
