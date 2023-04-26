
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

    private DarkDeal(final DarkDeal card) {
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
                    if(cardsInHand > 0) {
                        player.discard(cardsInHand, false, false, source, game);
                        cardsToDraw.put(playerId, cardsInHand - 1);
                    }
                }
            }
            for (Map.Entry<UUID, Integer> toDrawByPlayer : cardsToDraw.entrySet()) {
                Player player = game.getPlayer(toDrawByPlayer.getKey());
                if (player != null && toDrawByPlayer.getValue() > 0) {
                    player.drawCards(toDrawByPlayer.getValue(), source, game);
                }
            }
            return true;
        }
        return false;
    }
}
