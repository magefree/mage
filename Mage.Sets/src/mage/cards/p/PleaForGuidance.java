package mage.cards.p;

import mage.abilities.effects.common.search.SearchLibraryPutInHandEffect;
import mage.cards.CardImpl;
import mage.cards.CardSetInfo;
import mage.constants.CardType;
import mage.filter.StaticFilters;
import mage.target.common.TargetCardInLibrary;

import java.util.UUID;

/**
 * @author LevelX2
 */
public final class PleaForGuidance extends CardImpl {

    public PleaForGuidance(UUID ownerId, CardSetInfo setInfo) {
        super(ownerId, setInfo, new CardType[]{CardType.SORCERY}, "{5}{W}");

        // Search your library for up to two enchantment cards, reveal them, and put them into your hand. Then shuffle your library.
        this.getSpellAbility().addEffect(new SearchLibraryPutInHandEffect(new TargetCardInLibrary(0, 2, StaticFilters.FILTER_CARD_ENCHANTMENTS), true));
    }

    private PleaForGuidance(final PleaForGuidance card) {
        super(card);
    }

    @Override
    public PleaForGuidance copy() {
        return new PleaForGuidance(this);
    }
}
