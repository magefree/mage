package mage.cards.v;

import mage.abilities.effects.common.search.SearchLibraryPutOntoBattlefieldTappedRestInHandEffect;
import mage.abilities.keyword.FreerunningAbility;
import mage.cards.CardImpl;
import mage.cards.CardSetInfo;
import mage.constants.CardType;
import mage.filter.StaticFilters;
import mage.target.common.TargetCardInLibrary;

import java.util.UUID;

public class ViewpointSynchronization extends CardImpl {
    public ViewpointSynchronization(UUID ownerId, CardSetInfo setInfo) {
        super(ownerId, setInfo, new CardType[]{CardType.SORCERY}, "{4}{G}");

        this.addAbility(new FreerunningAbility("{2}{G}"));

        // Search your library for up to three basic land cards and reveal them. Put two of them onto the battlefield tapped and the other in your hand, then shuffle.
        this.getSpellAbility().addEffect(new SearchLibraryPutOntoBattlefieldTappedRestInHandEffect(
                new TargetCardInLibrary(0, 3, StaticFilters.FILTER_CARD_BASIC_LANDS), 2));
    }

    public ViewpointSynchronization(ViewpointSynchronization card) {
        super(card);
    }

    @Override
    public ViewpointSynchronization copy() {
        return new ViewpointSynchronization(this);
    }
}
