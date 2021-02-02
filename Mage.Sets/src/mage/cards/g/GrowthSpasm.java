
package mage.cards.g;

import mage.abilities.effects.common.CreateTokenEffect;
import mage.abilities.effects.common.search.SearchLibraryPutInPlayEffect;
import mage.cards.CardImpl;
import mage.cards.CardSetInfo;
import mage.constants.CardType;
import mage.filter.StaticFilters;
import mage.game.permanent.token.EldraziSpawnToken;
import mage.target.common.TargetCardInLibrary;

import java.util.UUID;

/**
 *
 * @author North
 */
public final class GrowthSpasm extends CardImpl {

    public GrowthSpasm(UUID ownerId, CardSetInfo setInfo) {
        super(ownerId,setInfo,new CardType[]{CardType.SORCERY},"{2}{G}");


        this.getSpellAbility().addEffect(new SearchLibraryPutInPlayEffect(new TargetCardInLibrary(StaticFilters.FILTER_CARD_BASIC_LAND), true));
        this.getSpellAbility().addEffect(new CreateTokenEffect(new EldraziSpawnToken()));
    }

    private GrowthSpasm(final GrowthSpasm card) {
        super(card);
    }

    @Override
    public GrowthSpasm copy() {
        return new GrowthSpasm(this);
    }
}
