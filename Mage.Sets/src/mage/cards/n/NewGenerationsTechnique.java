package mage.cards.n;

import java.util.UUID;

import mage.abilities.effects.common.search.SearchLibraryPutInPlayEffect;
import mage.abilities.keyword.SneakAbility;
import mage.cards.CardImpl;
import mage.cards.CardSetInfo;
import mage.constants.CardType;
import mage.filter.StaticFilters;
import mage.target.common.TargetCardInLibrary;

/**
 *
 * @author muz
 */
public final class NewGenerationsTechnique extends CardImpl {

    public NewGenerationsTechnique(UUID ownerId, CardSetInfo setInfo) {
        super(ownerId, setInfo, new CardType[]{CardType.SORCERY}, "{3}{G}");

        // Sneak {2}{G}
        this.addAbility(new SneakAbility(this, "{2}{G}"));

        // Search your library for up to two basic land cards, put them onto the battlefield tapped, then shuffle.
        this.getSpellAbility().addEffect(new SearchLibraryPutInPlayEffect(
            new TargetCardInLibrary(0, 2, StaticFilters.FILTER_CARD_BASIC_LANDS), true
        ));
    }

    private NewGenerationsTechnique(final NewGenerationsTechnique card) {
        super(card);
    }

    @Override
    public NewGenerationsTechnique copy() {
        return new NewGenerationsTechnique(this);
    }
}
