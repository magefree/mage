
package mage.cards.p;

import java.util.HashMap;
import java.util.Map;
import java.util.UUID;
import mage.abilities.Ability;
import mage.abilities.effects.OneShotEffect;
import mage.cards.Card;
import mage.cards.CardImpl;
import mage.cards.CardSetInfo;
import mage.constants.CardType;
import mage.constants.Outcome;
import mage.game.Game;
import mage.game.permanent.token.RatToken;
import mage.players.Player;
import mage.players.PlayerList;

/**
 *
 * @author jeffwadsworth
 */
public final class PlagueOfVermin extends CardImpl {

    public PlagueOfVermin(UUID ownerId, CardSetInfo setInfo) {
        super(ownerId,setInfo,new CardType[]{CardType.SORCERY},"{6}{B}");


        // Starting with you, each player may pay any amount of life. Repeat this process until no one pays life. Each player creates a 1/1 black Rat creature token for each 1 life he or she paid this way.
        this.getSpellAbility().addEffect(new PlagueOfVerminEffect());

    }

    public PlagueOfVermin(final PlagueOfVermin card) {
        super(card);
    }

    @Override
    public PlagueOfVermin copy() {
        return new PlagueOfVermin(this);
    }
}

class PlagueOfVerminEffect extends OneShotEffect {

    public PlagueOfVerminEffect() {
        super(Outcome.PutCardInPlay);
        this.staticText = "Starting with you, each player may pay any amount of life. Repeat this process until no one pays life. Each player creates a 1/1 black Rat creature token for each 1 life he or she paid this way.";
    }

    public PlagueOfVerminEffect(final PlagueOfVerminEffect effect) {
        super(effect);
    }

    @Override
    public PlagueOfVerminEffect copy() {
        return new PlagueOfVerminEffect(this);
    }

    @Override
    public boolean apply(Game game, Ability source) {
        Player controller = game.getPlayer(source.getControllerId());
        Card sourceCard = game.getCard(source.getSourceId());
        Map<UUID, Integer> payLife = new HashMap<>();
        int currentLifePaid;
        int totalPaidLife;
        if (controller != null) {
            PlayerList playerList = game.getState().getPlayerList().copy();
            while (!playerList.get().equals(source.getControllerId()) && controller.canRespond()) {
                playerList.getNext();
            }
            Player currentPlayer = game.getPlayer(playerList.get());
            UUID firstInactivePlayer = null;

            while (controller.canRespond()) {
                if (firstInactivePlayer == null) {
                    firstInactivePlayer = currentPlayer.getId();
                }
                if (currentPlayer != null && currentPlayer.canRespond() && game.getState().getPlayersInRange(controller.getId(), game).contains(currentPlayer.getId())) {
                    currentLifePaid = 0;
                    totalPaidLife = 0;
                    if (currentPlayer.chooseUse(Outcome.AIDontUseIt, "Pay life?", source, game)) {
                        totalPaidLife = currentPlayer.getAmount(0, controller.getLife(), "Pay how many life?", game);
                        if (totalPaidLife > 0) {
                            currentPlayer.loseLife(totalPaidLife, game, false);
                            if (payLife.get(currentPlayer.getId()) == null) {
                                payLife.put(currentPlayer.getId(), totalPaidLife);
                            } else {
                                currentLifePaid = payLife.get(currentPlayer.getId());
                                payLife.put(currentPlayer.getId(), currentLifePaid + totalPaidLife);
                            }
                        }
                        game.informPlayers(sourceCard.getName() + ": " + currentPlayer.getLogName() + " pays " + payLife.get(currentPlayer.getId()) + " life");
                        firstInactivePlayer = null;
                    }
                }
                
                // get next player
                playerList.getNext();
                currentPlayer = game.getPlayer(playerList.get());
                
                // if all player since this player didn't put permanent in play finish the process
                if (currentPlayer.getId().equals(firstInactivePlayer)) {
                    break;
                }
            }

            // create tokens according to life spent by each player
            RatToken token = new RatToken();
            for (Map.Entry<UUID, Integer> entry
                    : payLife.entrySet()) {
                Player player = game.getPlayer(entry.getKey());
                if (player != null) {
                    token.putOntoBattlefield(entry.getValue(), game, source.getSourceId(), player.getId());
                }
            }

        }
        return true;
    }
}
