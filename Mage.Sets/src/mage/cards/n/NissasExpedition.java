
package mage.cards.n;

import java.util.UUID;
import mage.abilities.effects.common.search.SearchLibraryPutInPlayEffect;
import mage.abilities.keyword.ConvokeAbility;
import mage.cards.CardImpl;
import mage.cards.CardSetInfo;
import mage.constants.CardType;
import mage.filter.StaticFilters;
import mage.target.common.TargetCardInLibrary;

/**
 *
 * @author LevelX2
 */
public final class NissasExpedition extends CardImpl {

    public NissasExpedition(UUID ownerId, CardSetInfo setInfo) {
        super(ownerId,setInfo,new CardType[]{CardType.SORCERY},"{4}{G}");


        // Convoke
        this.addAbility(new ConvokeAbility());
        // Search your library for up to two basic land cards, put them onto the battlefield tapped, then shuffle your library.
        this.getSpellAbility().addEffect(new SearchLibraryPutInPlayEffect(new TargetCardInLibrary(0,2, StaticFilters.FILTER_CARD_BASIC_LANDS), true));
    }

    private NissasExpedition(final NissasExpedition card) {
        super(card);
    }

    @Override
    public NissasExpedition copy() {
        return new NissasExpedition(this);
    }
}
