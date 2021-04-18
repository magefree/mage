
package mage.cards.a;

import mage.abilities.effects.Effect;
import mage.abilities.effects.common.counter.GetEnergyCountersControllerEffect;
import mage.abilities.effects.common.search.SearchLibraryPutInHandEffect;
import mage.cards.CardImpl;
import mage.cards.CardSetInfo;
import mage.constants.CardType;
import mage.filter.StaticFilters;
import mage.target.common.TargetCardInLibrary;

import java.util.UUID;

/**
 *
 * @author LevelX2
 */
public final class AttuneWithAether extends CardImpl {

    public AttuneWithAether(UUID ownerId, CardSetInfo setInfo) {
        super(ownerId,setInfo,new CardType[]{CardType.SORCERY},"{G}");

        // Search you library for a basic land card, reveal it, put it into your hand, then shuffle your library. You get {E}{E}.
        Effect effect = new SearchLibraryPutInHandEffect(new TargetCardInLibrary(1, 1, StaticFilters.FILTER_CARD_BASIC_LAND), true);
        effect.setText("Search your library for a basic land card, reveal it, put it into your hand, then shuffle");
        this.getSpellAbility().addEffect(effect);
        this.getSpellAbility().addEffect(new GetEnergyCountersControllerEffect(2));
    }

    private AttuneWithAether(final AttuneWithAether card) {
        super(card);
    }

    @Override
    public AttuneWithAether copy() {
        return new AttuneWithAether(this);
    }
}
