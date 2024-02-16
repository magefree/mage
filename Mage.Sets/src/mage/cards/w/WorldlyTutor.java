package mage.cards.w;

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
public final class WorldlyTutor extends CardImpl {

    public WorldlyTutor(UUID ownerId, CardSetInfo setInfo) {
        super(ownerId, setInfo, new CardType[]{CardType.INSTANT}, "{G}");

        // Search your library for a creature card and reveal that card. Shuffle your library, then put the card on top of it.
        this.getSpellAbility().addEffect(new SearchLibraryPutOnLibraryEffect(new TargetCardInLibrary(StaticFilters.FILTER_CARD_CREATURE), true));
    }

    private WorldlyTutor(final WorldlyTutor card) {
        super(card);
    }

    @Override
    public WorldlyTutor copy() {
        return new WorldlyTutor(this);
    }
}
