
package mage.cards.m;

import mage.abilities.common.EntersBattlefieldOrDiesSourceTriggeredAbility;
import mage.abilities.effects.common.search.SearchLibraryPutInHandEffect;
import mage.cards.CardImpl;
import mage.cards.CardSetInfo;
import mage.constants.CardType;
import mage.filter.StaticFilters;
import mage.target.common.TargetCardInLibrary;

import java.util.UUID;

/**
 * @author North
 */
public final class MycosynthWellspring extends CardImpl {

    public MycosynthWellspring(UUID ownerId, CardSetInfo setInfo) {
        super(ownerId, setInfo, new CardType[]{CardType.ARTIFACT}, "{2}");

        // When Mycosynth Wellspring enters the battlefield or is put into a graveyard from the battlefield,
        // you may search your library for a basic land card, reveal it, put it into your hand, then shuffle your library.
        this.addAbility(new EntersBattlefieldOrDiesSourceTriggeredAbility(new SearchLibraryPutInHandEffect(
                new TargetCardInLibrary(StaticFilters.FILTER_CARD_BASIC_LAND), true
        ), true, false));
    }

    private MycosynthWellspring(final MycosynthWellspring card) {
        super(card);
    }

    @Override
    public MycosynthWellspring copy() {
        return new MycosynthWellspring(this);
    }
}
