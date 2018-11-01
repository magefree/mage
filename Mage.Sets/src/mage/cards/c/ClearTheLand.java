package mage.cards.c;

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
import mage.players.Library;
import mage.players.Player;

/**
 *
 * @author davidmfritz
 */
public final class ClearTheLand extends CardImpl {

    public ClearTheLand(UUID ownerId, CardSetInfo setInfo) {
        super(ownerId, setInfo, new CardType[]{CardType.SORCERY}, "{2}{G}");
        

        // Each player reveals the top five cards of his or her library, puts all land cards revealed this way onto the battlefield tapped, and exiles the rest.
        getSpellAbility().addEffect(new ClearTheLandEffect());
    }

    public ClearTheLand(final ClearTheLand card) {
        super(card);
    }

    @Override
    public ClearTheLand copy() {
        return new ClearTheLand(this);
    }
}

class ClearTheLandEffect extends OneShotEffect {
    
    public ClearTheLandEffect() {
        super(Outcome.Benefit);
        this.staticText = "Each player reveals the top five cards of his or her library, puts all land cards revealed this way onto the battlefield tapped, and exiles the rest.";
    }
    
    public ClearTheLandEffect(final ClearTheLandEffect effect) {
        super(effect);
    }
    
    @Override
    public ClearTheLandEffect copy() {
        return new ClearTheLandEffect(this);
    }
    
    @Override
    public boolean apply(Game game, Ability source) {
        
        boolean tapped = true;
        
        Player controller = game.getPlayer(source.getControllerId());
        
        if (controller != null) {
            for (UUID playerId : game.getState().getPlayersInRange(controller.getId(), game)) {
                Player player = game.getPlayer(playerId);
                if (player != null) {
                    Library library = player.getLibrary();
                    Cards cardsToReveal = new CardsImpl();
                    cardsToReveal.addAll(library.getTopCards(game, 5));
                    if (!cardsToReveal.isEmpty()) {
                        player.revealCards(source, "Revealed cards for " + player.getName(), cardsToReveal, game);
                        for (Card card : cardsToReveal.getCards(game)) {
                            if (card.isLand()) {
                                player.moveCards(card, Zone.BATTLEFIELD, source, game, tapped, false, true, null);
                            } else {
                                player.moveCards(card, Zone.EXILED, source, game);
                            }
                        }
                    }
                }
            }
            return true;
        }
        return false;
    }
}
