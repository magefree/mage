package mage.cards.c;

import java.util.UUID;
import mage.abilities.effects.common.search.SearchLibraryWithLessCMCPutInPlayEffect;
import mage.abilities.keyword.ConvokeAbility;
import mage.cards.CardImpl;
import mage.cards.CardSetInfo;
import mage.constants.CardType;
import mage.filter.StaticFilters;

/**
 *
 * @author jonubuu
 */
public final class ChordOfCalling extends CardImpl {

    public ChordOfCalling(UUID ownerId, CardSetInfo setInfo) {
        super(ownerId, setInfo, new CardType[]{CardType.INSTANT}, "{X}{G}{G}{G}");

        // Convoke (Your creatures can help cast this spell. Each creature you tap while casting this spell pays for {1} or one mana of that creature's color.)
        this.addAbility(new ConvokeAbility());

        // Search your library for a creature card with converted mana cost X or less and put it onto the battlefield. Then shuffle your library.
        this.getSpellAbility().addEffect(new SearchLibraryWithLessCMCPutInPlayEffect(StaticFilters.FILTER_CARD_CREATURE));
    }

    private ChordOfCalling(final ChordOfCalling card) {
        super(card);
    }

    @Override
    public ChordOfCalling copy() {
        return new ChordOfCalling(this);
    }
}
