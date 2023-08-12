package mage.cards.s;

import java.util.UUID;
import mage.abilities.effects.common.search.SearchLibraryPutOnLibraryEffect;
import mage.cards.CardImpl;
import mage.cards.CardSetInfo;
import mage.constants.CardType;
import mage.filter.StaticFilters;
import mage.target.common.TargetCardInLibrary;

/**
 *
 * @author Plopman
 */
public final class SylvanTutor extends CardImpl {

    public SylvanTutor(UUID ownerId, CardSetInfo setInfo) {
        super(ownerId, setInfo, new CardType[]{CardType.SORCERY}, "{G}");

        // Search your library for a creature card and reveal that card. Shuffle your library, then put the card on top of it.
        this.getSpellAbility().addEffect(new SearchLibraryPutOnLibraryEffect(new TargetCardInLibrary(StaticFilters.FILTER_CARD_CREATURE), true));
    }

    private SylvanTutor(final SylvanTutor card) {
        super(card);
    }

    @Override
    public SylvanTutor copy() {
        return new SylvanTutor(this);
    }
}
