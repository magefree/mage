
package mage.cards.j;

import mage.abilities.Mode;
import mage.abilities.effects.common.continuous.PlayAdditionalLandsControllerEffect;
import mage.abilities.effects.common.search.SearchLibraryPutInHandEffect;
import mage.abilities.keyword.EntwineAbility;
import mage.cards.CardImpl;
import mage.cards.CardSetInfo;
import mage.constants.CardType;
import mage.constants.Duration;
import mage.filter.StaticFilters;
import mage.target.common.TargetCardInLibrary;

import java.util.UUID;

/**
 *
 * @author fireshoes
 */
public final class JourneyOfDiscovery extends CardImpl {

    public JourneyOfDiscovery(UUID ownerId, CardSetInfo setInfo) {
        super(ownerId,setInfo,new CardType[]{CardType.SORCERY},"{2}{G}");

        // Choose one - Search your library for up to two basic land cards, reveal them, put them into your hand, then shuffle your library; 
        this.getSpellAbility().addEffect(new SearchLibraryPutInHandEffect(new TargetCardInLibrary(0, 2, StaticFilters.FILTER_CARD_BASIC_LANDS), true));
        
        // or you may play up to two additional lands this turn.
        Mode mode = new Mode(new PlayAdditionalLandsControllerEffect(2, Duration.EndOfTurn));
        this.getSpellAbility().getModes().addMode(mode);
        
        // Entwine {2}{G}
        this.addAbility(new EntwineAbility("{2}{G}"));
    }

    private JourneyOfDiscovery(final JourneyOfDiscovery card) {
        super(card);
    }

    @Override
    public JourneyOfDiscovery copy() {
        return new JourneyOfDiscovery(this);
    }
}
