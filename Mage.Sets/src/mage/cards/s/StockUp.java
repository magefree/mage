package mage.cards.s;

import mage.abilities.effects.common.LookLibraryAndPickControllerEffect;
import mage.cards.CardImpl;
import mage.cards.CardSetInfo;
import mage.constants.CardType;
import mage.constants.PutCards;

import java.util.UUID;

/**
 * @author TheElk801
 */
public final class StockUp extends CardImpl {

    public StockUp(UUID ownerId, CardSetInfo setInfo) {
        super(ownerId, setInfo, new CardType[]{CardType.SORCERY}, "{2}{U}");

        // Look at the top five cards of your library. Put two of them into your hand and the rest on the bottom of your library in any order.
        this.getSpellAbility().addEffect(new LookLibraryAndPickControllerEffect(5, 2, PutCards.HAND, PutCards.BOTTOM_ANY));
    }

    private StockUp(final StockUp card) {
        super(card);
    }

    @Override
    public StockUp copy() {
        return new StockUp(this);
    }
}
