
package mage.cards.d;

import java.util.UUID;
import mage.abilities.Ability;
import mage.abilities.effects.OneShotEffect;
import mage.cards.Card;
import mage.cards.CardImpl;
import mage.cards.CardSetInfo;
import mage.constants.CardType;
import mage.constants.Outcome;
import mage.constants.Zone;
import mage.game.Game;
import mage.players.Player;

/**
 *
 * @author Quercitron
 */
public final class DiminishingReturns extends CardImpl {

    public DiminishingReturns(UUID ownerId, CardSetInfo setInfo) {
        super(ownerId,setInfo,new CardType[]{CardType.SORCERY},"{2}{U}{U}");

        // Each player shuffles their hand and graveyard into their library. You exile the top ten cards of your library. Then each player draws up to seven cards.
        this.getSpellAbility().addEffect(new DiminishingReturnsEffect());
    }

    public DiminishingReturns(final DiminishingReturns card) {
        super(card);
    }

    @Override
    public DiminishingReturns copy() {
        return new DiminishingReturns(this);
    }
}

class DiminishingReturnsEffect extends OneShotEffect {

    public DiminishingReturnsEffect() {
        super(Outcome.Neutral);
        staticText = "Each player shuffles their hand and graveyard into their library. You exile the top ten cards of your library. Then each player draws up to seven cards.";
    }

    public DiminishingReturnsEffect(final DiminishingReturnsEffect effect) {
        super(effect);
    }

    @Override
    public boolean apply(Game game, Ability source) {
        Player controller = game.getPlayer(source.getControllerId());
        if (controller != null) {
            for (UUID playerId : game.getState().getPlayersInRange(controller.getId(), game)) {
                Player player = game.getPlayer(playerId);
                if (player != null) {
                    for (Card card: player.getHand().getCards(game)) {
                        card.moveToZone(Zone.LIBRARY, source.getSourceId(), game, true);
                    }                    
                    for (Card card: player.getGraveyard().getCards(game)) {
                        card.moveToZone(Zone.LIBRARY, source.getSourceId(), game, true);
                    }                    
                    player.shuffleLibrary(source, game);
                }
            }

            for (Card card: controller.getLibrary().getTopCards(game, 10)) {
                controller.moveCardToExileWithInfo(card, null, "", source.getSourceId(), game, Zone.LIBRARY, true);
            }

            for (UUID playerId : game.getState().getPlayersInRange(controller.getId(), game)) {
                Player player = game.getPlayer(playerId);
                if (player != null) {
                    int cardsToDrawCount = player.getAmount(0, 7, "How many cards to draw (up to 7)?", game);
                    player.drawCards(cardsToDrawCount, source.getSourceId(), game);
                }
            }
        }
        return true;
    }

    @Override
    public DiminishingReturnsEffect copy() {
        return new DiminishingReturnsEffect(this);
    }
}
