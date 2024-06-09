

package mage.cards.e;

import mage.abilities.effects.common.search.SearchLibraryPutInHandEffect;
import mage.cards.CardImpl;
import mage.cards.CardSetInfo;
import mage.constants.CardType;
import mage.constants.SubType;
import mage.filter.FilterCard;
import mage.target.common.TargetCardInLibrary;

import java.util.UUID;

/**
 * @author Loki
 */
public final class EerieProcession extends CardImpl {

    private static final FilterCard filter = new FilterCard("Arcane card");

    static {
        filter.add(SubType.ARCANE.getPredicate());
    }

    public EerieProcession(UUID ownerId, CardSetInfo setInfo) {
        super(ownerId,setInfo,new CardType[]{CardType.SORCERY},"{2}{U}");
        this.subtype.add(SubType.ARCANE);


        // Search your library for an Arcane card, reveal that card, and put it into your hand. Then shuffle your library.
        this.getSpellAbility().addEffect(new SearchLibraryPutInHandEffect(new TargetCardInLibrary(filter), true, true));
    }

    private EerieProcession(final EerieProcession card) {
        super(card);
    }

    @Override
    public EerieProcession copy() {
        return new EerieProcession(this);
    }

}
