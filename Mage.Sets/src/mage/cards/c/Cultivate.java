package mage.cards.c;

import mage.abilities.effects.common.search.SearchLibraryPutOneOntoBattlefieldTappedRestInHandEffect;
import mage.cards.*;
import mage.constants.CardType;
import mage.filter.StaticFilters;
import mage.target.common.TargetCardInLibrary;

import java.util.UUID;

/**
 * @author BetaSteward_at_googlemail.com
 */
public final class Cultivate extends CardImpl {

    public Cultivate(UUID ownerId, CardSetInfo setInfo) {
        super(ownerId, setInfo, new CardType[]{CardType.SORCERY}, "{2}{G}");

        // Search your library for up to two basic land cards, reveal those cards, put one onto the battlefield tapped and the other into your hand, then shuffle.
        this.getSpellAbility().addEffect(new SearchLibraryPutOneOntoBattlefieldTappedRestInHandEffect(
                new TargetCardInLibrary(0, 2, StaticFilters.FILTER_CARD_BASIC_LANDS)));

    }

    private Cultivate(final Cultivate card) {
        super(card);
    }

    @Override
    public Cultivate copy() {
        return new Cultivate(this);
    }

}
