package mage.cards.e;

import mage.abilities.effects.common.search.SearchLibraryPutInGraveyardEffect;
import mage.cards.CardImpl;
import mage.cards.CardSetInfo;
import mage.constants.CardType;

import java.util.UUID;

/**
 * @author Plopman
 */
public final class Entomb extends CardImpl {

    public Entomb(UUID ownerId, CardSetInfo setInfo) {
        super(ownerId, setInfo, new CardType[]{CardType.INSTANT}, "{B}");

        // Search your library for a card and put that card into your graveyard. Then shuffle your library.
        this.getSpellAbility().addEffect(new SearchLibraryPutInGraveyardEffect());
    }

    private Entomb(final Entomb card) {
        super(card);
    }

    @Override
    public Entomb copy() {
        return new Entomb(this);
    }
}
