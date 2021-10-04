
package mage.cards.d;

import mage.abilities.costs.mana.ManaCostsImpl;
import mage.abilities.effects.common.search.SearchLibraryPutInPlayEffect;
import mage.abilities.keyword.FlashbackAbility;
import mage.cards.CardImpl;
import mage.cards.CardSetInfo;
import mage.constants.CardType;
import mage.constants.TimingRule;
import mage.filter.StaticFilters;
import mage.target.common.TargetCardInLibrary;

import java.util.UUID;

/**
 *
 * @author LevelX2
 */
public final class DeepReconnaissance extends CardImpl {

    public DeepReconnaissance(UUID ownerId, CardSetInfo setInfo) {
        super(ownerId,setInfo,new CardType[]{CardType.SORCERY},"{2}{G}");


        // Search your library for a basic land card and put that card onto the battlefield tapped. Then shuffle your library.
        this.getSpellAbility().addEffect(new SearchLibraryPutInPlayEffect(new TargetCardInLibrary(StaticFilters.FILTER_CARD_BASIC_LAND), true, true));
        // Flashback {4}{G}
        this.addAbility(new FlashbackAbility(this, new ManaCostsImpl("{4}{G}")));
    }

    private DeepReconnaissance(final DeepReconnaissance card) {
        super(card);
    }

    @Override
    public DeepReconnaissance copy() {
        return new DeepReconnaissance(this);
    }
}
