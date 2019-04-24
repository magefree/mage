
package mage.cards.d;

import java.util.LinkedHashMap;
import java.util.Map;
import java.util.UUID;
import mage.abilities.Ability;
import mage.abilities.effects.OneShotEffect;
import mage.cards.CardImpl;
import mage.cards.CardSetInfo;
import mage.constants.CardType;
import mage.constants.Outcome;
import mage.game.Game;
import mage.players.Player;

/**
 *
 * @author LevelX2
 */
public final class DarkDeal extends CardImpl {

    public DarkDeal(UUID ownerId, CardSetInfo setInfo) {
        super(ownerId,setInfo,new CardType[]{CardType.SORCERY},"{2}{B}");

        // Each player discards all the cards in their hand, then draws that many cards minus one.
        this.getSpellAbility().addEffect(new DarkDealEffect());
    }

    public DarkDeal(final DarkDeal card) {
        super(card);
    }

    @Override
    public DarkDeal copy() {
        return new DarkDeal(this);
    }
}

class DarkDealEffect extends OneShotEffect {

    DarkDealEffect() {
        super(Outcome.Detriment);
        this.staticText = "Each player discards all the cards in their hand, then draws that many cards minus one";
    }

    DarkDealEffect(final DarkDealEffect effect) {
        super(effect);
    }

    @Override
    public DarkDealEffect copy() {
        return new DarkDealEffect(this);
    }

    @Override
    public boolean apply(Game game, Ability source) {
        Player controller = game.getPlayer(source.getControllerId());
        if (controller != null) {
            Map<UUID, Integer> cardsToDraw = new LinkedHashMap<>();
            for (UUID playerId : game.getState().getPlayersInRange(controller.getId(), game)) {
                Player player = game.getPlayer(playerId);
                if (player != null) {
                    int cardsInHand = player.getHand().size();
                    player.discard(cardsInHand, false, source, game);
                    if (cardsInHand > 1) {
                        cardsToDraw.put(playerId, cardsInHand - 1);
                    }
                }
            }
            for (UUID playerId : cardsToDraw.keySet()) {
                Player player = game.getPlayer(playerId);
                if (player != null) {
                    player.drawCards(cardsToDraw.get(playerId), game);
                }
            }
            return true;
        }
        return false;
    }
}
