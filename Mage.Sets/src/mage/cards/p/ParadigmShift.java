
package mage.cards.p;

import java.util.UUID;
import mage.abilities.Ability;
import mage.abilities.effects.OneShotEffect;
import mage.cards.Card;
import mage.cards.CardImpl;
import mage.cards.CardSetInfo;
import mage.cards.Cards;
import mage.cards.CardsImpl;
import mage.constants.CardType;
import mage.constants.Outcome;
import mage.constants.Zone;
import mage.game.Game;
import mage.players.Player;

/**
 *
 * @author fireshoes
 */
public final class ParadigmShift extends CardImpl {

    public ParadigmShift(UUID ownerId, CardSetInfo setInfo) {
        super(ownerId,setInfo,new CardType[]{CardType.SORCERY},"{1}{U}");

        // Exile all cards from your library. Then shuffle your graveyard into your library.
        this.getSpellAbility().addEffect(new ExileLibraryEffect());
    }

    public ParadigmShift(final ParadigmShift card) {
        super(card);
    }

    @Override
    public ParadigmShift copy() {
        return new ParadigmShift(this);
    }
}

class ExileLibraryEffect extends OneShotEffect {

    public ExileLibraryEffect() {
        super(Outcome.Exile);
        staticText = "Exile all cards from your library. Then shuffle your graveyard into your library";
    }

    @Override
    public ExileLibraryEffect copy() {
        return new ExileLibraryEffect();
    }

    @Override
    public boolean apply(Game game, Ability source) {
        Player controller = game.getPlayer(source.getControllerId());
        if (controller != null) {
            int count = controller.getLibrary().size();
            Cards cards = new CardsImpl();
            cards.addAll(controller.getLibrary().getTopCards(game, count));
            controller.moveCards(cards, Zone.EXILED, source, game);
            
            for (Card card: controller.getGraveyard().getCards(game)) {
                controller.moveCardToLibraryWithInfo(card, source.getSourceId(), game, Zone.GRAVEYARD, true, true);
            }            
            controller.shuffleLibrary(source, game);
            return true;
        }
        return false;
    }
}
