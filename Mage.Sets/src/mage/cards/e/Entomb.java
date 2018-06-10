
package mage.cards.e;

import java.util.UUID;
import mage.abilities.Ability;
import mage.abilities.effects.SearchEffect;
import mage.cards.CardImpl;
import mage.cards.CardSetInfo;
import mage.constants.CardType;
import mage.constants.Outcome;
import mage.constants.Zone;
import mage.filter.FilterCard;
import mage.game.Game;
import mage.players.Player;
import mage.target.common.TargetCardInLibrary;

/**
 *
 * @author Plopman
 */
public final class Entomb extends CardImpl {

    public Entomb(UUID ownerId, CardSetInfo setInfo) {
        super(ownerId,setInfo,new CardType[]{CardType.INSTANT},"{B}");

        // Search your library for a card and put that card into your graveyard. Then shuffle your library.
        this.getSpellAbility().addEffect(new SearchLibraryPutInGraveyard());
    }

    public Entomb(final Entomb card) {
        super(card);
    }

    @Override
    public Entomb copy() {
        return new Entomb(this);
    }
}


class SearchLibraryPutInGraveyard extends SearchEffect {

  public SearchLibraryPutInGraveyard() {
        super(new TargetCardInLibrary(new FilterCard()), Outcome.Neutral);
        staticText = "Search your library for a card and put that card into your graveyard. Then shuffle your library";
    }

    public SearchLibraryPutInGraveyard(final SearchLibraryPutInGraveyard effect) {
        super(effect);
    }

    @Override
    public SearchLibraryPutInGraveyard copy() {
        return new SearchLibraryPutInGraveyard(this);
    }

    @Override
    public boolean apply(Game game, Ability source) {
        Player controller = game.getPlayer(source.getControllerId());
        if (controller == null) {
            return false;
        }
        if (controller.searchLibrary(target, game)) {
            controller.moveCards(game.getCard(target.getFirstTarget()), Zone.GRAVEYARD, source, game);
        }
        controller.shuffleLibrary(source, game);
        return true;
    }
    
}
