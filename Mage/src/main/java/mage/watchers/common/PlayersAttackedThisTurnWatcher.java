package mage.watchers.common;

import mage.constants.WatcherScope;
import mage.game.Game;
import mage.game.events.GameEvent;
import mage.game.permanent.Permanent;
import mage.players.PlayerList;
import mage.watchers.Watcher;

import java.util.HashMap;
import java.util.Map;
import java.util.UUID;

/**
 * @author JayDi85
 */
public class PlayersAttackedThisTurnWatcher extends Watcher {

    // how many players or opponents each player attacked this turn
    private final Map<UUID, PlayerList> playersAttackedThisTurn = new HashMap<>();
    private final Map<UUID, PlayerList> opponentsAttackedThisTurn = new HashMap<>();
    private final Map<UUID, PlayerList> planeswalkerControllerAttackedThisTurn = new HashMap<>();

    public PlayersAttackedThisTurnWatcher() {
        super(WatcherScope.GAME);
    }

    @Override
    public void watch(GameEvent event, Game game) {
        /*
        Faramir, Prince of Ithilien:
            Attacking a planeswalker you control or battle you're protecting doesn't count as attacking you. (2023-06-16)
         */

        if (event.getType() == GameEvent.EventType.ATTACKER_DECLARED) {

            // players
            PlayerList playersAttacked = playersAttackedThisTurn.get(event.getPlayerId());
            if (playersAttacked == null) {
                playersAttacked = new PlayerList();
            }
            UUID playerDefender = event.getTargetId();
            if (playerDefender != null && game.getPlayer(playerDefender) != null
                    && !playersAttacked.contains(playerDefender)) {
                playersAttacked.add(playerDefender);
            }
            playersAttackedThisTurn.putIfAbsent(event.getPlayerId(), playersAttacked);

            // opponents
            PlayerList opponentsAttacked = opponentsAttackedThisTurn.get(event.getPlayerId());
            if (opponentsAttacked == null) {
                opponentsAttacked = new PlayerList();
            }
            UUID opponentDefender = event.getTargetId();
            if (opponentDefender != null
                    && game.getOpponents(event.getPlayerId()).contains(opponentDefender)
                    && !opponentsAttacked.contains(opponentDefender)) {
                opponentsAttacked.add(opponentDefender);
            }
            opponentsAttackedThisTurn.putIfAbsent(event.getPlayerId(), opponentsAttacked);

            //Planeswalker controllers
            PlayerList controllersAttacked = planeswalkerControllerAttackedThisTurn.get(event.getPlayerId());
            if (controllersAttacked == null) {
                controllersAttacked = new PlayerList();
            }
            Permanent permanent = game.getPermanent(event.getTargetId());
            UUID controllingDefender = game.getCombat().getDefendingPlayerId(event.getSourceId(), game);
            if (controllingDefender != null && permanent != null && permanent.isPlaneswalker(game)
                    && !controllersAttacked.contains(controllingDefender)) {
                controllersAttacked.add(controllingDefender);
            }
            planeswalkerControllerAttackedThisTurn.putIfAbsent(event.getPlayerId(), controllersAttacked);
        }
    }

    public boolean hasPlayerAttackedPlayer(UUID attacker, UUID defender){
        PlayerList defendersList = playersAttackedThisTurn.getOrDefault(attacker, null);
        return defendersList != null && defendersList.contains(defender);
    }
    public boolean hasPlayerAttackedPlayerOrControlledPlaneswalker(UUID attacker, UUID defender){
        PlayerList defendersList = playersAttackedThisTurn.getOrDefault(attacker, null);
        PlayerList planeswalkerControllersList = planeswalkerControllerAttackedThisTurn.getOrDefault(attacker, null);
        return (defendersList != null && defendersList.contains(defender)) ||
                (planeswalkerControllersList != null && planeswalkerControllersList.contains(defender));
    }

    public int getAttackedPlayersCount(UUID playerID) {
        PlayerList defendersList = playersAttackedThisTurn.getOrDefault(playerID, null);
        if (defendersList != null) {
            return defendersList.size();
        }
        return 0;
    }

    public int getAttackedOpponentsCount(UUID playerID) {
        PlayerList defendersList = opponentsAttackedThisTurn.getOrDefault(playerID, null);
        if (defendersList != null) {
            return defendersList.size();
        }
        return 0;
    }

    @Override
    public void reset() {
        super.reset();
        playersAttackedThisTurn.clear();
        opponentsAttackedThisTurn.clear();
        planeswalkerControllerAttackedThisTurn.clear();
    }
}
