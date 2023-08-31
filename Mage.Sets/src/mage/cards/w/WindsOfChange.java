
package mage.cards.w;

import java.util.HashMap;
import java.util.Map;
import java.util.UUID;
import mage.abilities.Ability;
import mage.abilities.effects.OneShotEffect;
import mage.cards.CardImpl;
import mage.cards.CardSetInfo;
import mage.constants.CardType;
import mage.constants.Outcome;
import mage.constants.Zone;
import mage.game.Game;
import mage.players.Player;

/**
 *
 * @author LevelX2
 */
public final class WindsOfChange extends CardImpl {

    public WindsOfChange(UUID ownerId, CardSetInfo setInfo) {
        super(ownerId,setInfo,new CardType[]{CardType.SORCERY},"{R}");

        // Each player shuffles the cards from their hand into their library, then draws that many cards.
        this.getSpellAbility().addEffect(new WindsOfChangeEffect());
    }

    private WindsOfChange(final WindsOfChange card) {
        super(card);
    }

    @Override
    public WindsOfChange copy() {
        return new WindsOfChange(this);
    }
}

class WindsOfChangeEffect extends OneShotEffect {

    public WindsOfChangeEffect() {
        super(Outcome.Benefit);
        this.staticText = "Each player shuffles the cards from their hand into their library, then draws that many cards";
    }

    private WindsOfChangeEffect(final WindsOfChangeEffect effect) {
        super(effect);
    }

    @Override
    public WindsOfChangeEffect copy() {
        return new WindsOfChangeEffect(this);
    }

    @Override
    public boolean apply(Game game, Ability source) {
        Player controller = game.getPlayer(source.getControllerId());
        if (controller != null) {
            Map<UUID, Integer> permanentsCount = new HashMap<>();
            for (UUID playerId : game.getState().getPlayersInRange(source.getControllerId(), game)) {
                Player player = game.getPlayer(playerId);
                if (player != null) {
                    permanentsCount.put(playerId, player.getHand().size());
                    player.moveCards(player.getHand(), Zone.LIBRARY, source, game);
                    player.shuffleLibrary(source, game);
                }
            }
            for (UUID playerId : game.getState().getPlayersInRange(source.getControllerId(), game)) {
                Player player = game.getPlayer(playerId);
                if (player != null && permanentsCount.containsKey(playerId)) {
                    player.drawCards(permanentsCount.get(playerId), source, game);
                }
            }
            return true;
        }
        return false;
    }
}
