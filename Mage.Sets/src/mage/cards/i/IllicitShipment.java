package mage.cards.i;

import mage.abilities.effects.common.search.SearchLibraryPutInHandEffect;
import mage.abilities.keyword.CasualtyAbility;
import mage.cards.CardImpl;
import mage.cards.CardSetInfo;
import mage.constants.CardType;
import mage.target.common.TargetCardInLibrary;

import java.util.UUID;

/**
 * @author TheElk801
 */
public final class IllicitShipment extends CardImpl {

    public IllicitShipment(UUID ownerId, CardSetInfo setInfo) {
        super(ownerId, setInfo, new CardType[]{CardType.SORCERY}, "{3}{B}{B}");

        // Casualty 3
        this.addAbility(new CasualtyAbility(this, 3));

        // Search your library for a card, put that card into your hand, then shuffle.
        this.getSpellAbility().addEffect(new SearchLibraryPutInHandEffect(new TargetCardInLibrary()));
    }

    private IllicitShipment(final IllicitShipment card) {
        super(card);
    }

    @Override
    public IllicitShipment copy() {
        return new IllicitShipment(this);
    }
}
