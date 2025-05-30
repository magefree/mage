package mage.cards.r;

import mage.abilities.effects.common.search.SearchLibraryPutInPlayEffect;
import mage.abilities.keyword.HarmonizeAbility;
import mage.cards.CardImpl;
import mage.cards.CardSetInfo;
import mage.constants.CardType;
import mage.filter.StaticFilters;
import mage.target.common.TargetCardInLibrary;

import java.util.UUID;

/**
 * @author TheElk801
 */
public final class RoamersRoutine extends CardImpl {

    public RoamersRoutine(UUID ownerId, CardSetInfo setInfo) {
        super(ownerId, setInfo, new CardType[]{CardType.SORCERY}, "{2}{G}");

        // Search your library for a basic land card, put it onto the battlefield tapped, then shuffle.
        this.getSpellAbility().addEffect(new SearchLibraryPutInPlayEffect(new TargetCardInLibrary(StaticFilters.FILTER_CARD_BASIC_LAND), true));

        // Harmonize {4}{G}
        this.addAbility(new HarmonizeAbility(this, "{4}{G}"));
    }

    private RoamersRoutine(final RoamersRoutine card) {
        super(card);
    }

    @Override
    public RoamersRoutine copy() {
        return new RoamersRoutine(this);
    }
}
