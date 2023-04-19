
package mage.cards.d;

import java.util.UUID;
import mage.abilities.costs.common.DiscardTargetCost;
import mage.abilities.effects.common.search.SearchLibraryPutInHandEffect;
import mage.abilities.keyword.BuybackAbility;
import mage.cards.CardImpl;
import mage.cards.CardSetInfo;
import mage.constants.CardType;
import mage.filter.FilterCard;
import mage.filter.StaticFilters;
import mage.target.common.TargetCardInHand;
import mage.target.common.TargetCardInLibrary;

/**
 *
 * @author LevelX2
 */
public final class DemonicCollusion extends CardImpl {

    public DemonicCollusion(UUID ownerId, CardSetInfo setInfo) {
        super(ownerId,setInfo,new CardType[]{CardType.SORCERY},"{3}{B}{B}");

        // Buyback—Discard two cards. (You may discard two cards in addition to any other costs as you cast this spell. If you do, put this card into your hand as it resolves.)
        this.addAbility(new BuybackAbility(new DiscardTargetCost(new TargetCardInHand(2, StaticFilters.FILTER_CARD_CARDS))));

        // Search your library for a card and put that card into your hand. Then shuffle your library.
        this.getSpellAbility().addEffect(new SearchLibraryPutInHandEffect(new TargetCardInLibrary(),false));
    }

    private DemonicCollusion(final DemonicCollusion card) {
        super(card);
    }

    @Override
    public DemonicCollusion copy() {
        return new DemonicCollusion(this);
    }
}
