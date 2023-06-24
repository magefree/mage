package mage.cards.p;

import java.util.UUID;

import mage.abilities.effects.common.WillOfThePlaneswalkersEffect;
import mage.abilities.effects.common.search.SearchLibraryPutInPlayEffect;
import mage.cards.CardImpl;
import mage.cards.CardSetInfo;
import mage.constants.CardType;
import mage.filter.StaticFilters;
import mage.filter.common.FilterBasicLandCard;
import mage.target.common.TargetCardInLibrary;

/**
 *
 * @author TheElk801
 */
public final class PathOfTheAnimist extends CardImpl {

    public PathOfTheAnimist(UUID ownerId, CardSetInfo setInfo) {
        super(ownerId, setInfo, new CardType[]{CardType.SORCERY}, "{3}{G}");

        // Search your library for up to two basic land cards, put them onto the battlefield tapped, then shuffle.
        this.getSpellAbility().addEffect(new SearchLibraryPutInPlayEffect(new TargetCardInLibrary(0, 2, StaticFilters.FILTER_CARD_BASIC_LANDS), true));

        // Will of the Planeswalkers -- Starting with you, each player votes for planeswalk or chaos. If planeswalk gets more votes, planeswalk. If chaos gets more votes or the vote is tied, chaos ensues.
        this.getSpellAbility().addEffect(new WillOfThePlaneswalkersEffect());
    }

    private PathOfTheAnimist(final PathOfTheAnimist card) {
        super(card);
    }

    @Override
    public PathOfTheAnimist copy() {
        return new PathOfTheAnimist(this);
    }
}
