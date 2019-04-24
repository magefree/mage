
package mage.watchers.common;

import java.util.HashMap;
import java.util.Map;
import java.util.UUID;
import mage.MageObject;
import mage.cards.Card;
import mage.constants.WatcherScope;
import mage.game.Game;
import mage.game.events.DamagedPlayerEvent;
import mage.game.events.GameEvent;
import mage.game.events.GameEvent.EventType;
import mage.game.permanent.Permanent;
import mage.players.Player;
import mage.watchers.Watcher;

/* 20130711
 *903.14a A player that's been dealt 21 or more combat damage by the same commander
 * over the course of the game loses the game. (This is a state-based action. See rule 704.)
 *
 *
 * @author Plopman
 */
public class CommanderInfoWatcher extends Watcher {

    public final Map<UUID, Integer> damageToPlayer = new HashMap<>();
    public final boolean checkCommanderDamage;

    public CommanderInfoWatcher(UUID commander, boolean checkCommanderDamage) {
        super(CommanderInfoWatcher.class.getSimpleName(), WatcherScope.CARD);
        this.sourceId = commander;
        this.checkCommanderDamage = checkCommanderDamage;
    }

    public CommanderInfoWatcher(final CommanderInfoWatcher watcher) {
        super(watcher);
        this.damageToPlayer.putAll(watcher.damageToPlayer);
        this.checkCommanderDamage = watcher.checkCommanderDamage;
    }

    @Override
    public CommanderInfoWatcher copy() {
        return new CommanderInfoWatcher(this);
    }

    @Override
    public void watch(GameEvent event, Game game) {
        if (checkCommanderDamage && event.getType() == EventType.DAMAGED_PLAYER && event instanceof DamagedPlayerEvent) {
            if (sourceId.equals(event.getSourceId())) {
                DamagedPlayerEvent damageEvent = (DamagedPlayerEvent) event;
                if (damageEvent.isCombatDamage()) {
                    UUID playerUUID = event.getTargetId();
                    Integer damage = damageToPlayer.getOrDefault(playerUUID, 0);
                    damage += damageEvent.getAmount();
                    damageToPlayer.put(playerUUID, damage);
                    Player player = game.getPlayer(playerUUID);
                    MageObject commander = game.getObject(sourceId);
                    if (player != null && commander != null) {
                        if (!game.isSimulation()) {
                            game.informPlayers(commander.getLogName() + " did " + damage + " combat damage to " + player.getLogName() + " during the game.");
                        }
                        this.addCardInfoToCommander(game);
                    }
                }
            }
        }
        // Add card info to the commander
        if (event.getType() == GameEvent.EventType.ZONE_CHANGE && event.getTargetId().equals(sourceId)) {
            this.addCardInfoToCommander(game);
        }
    }

    public void addCardInfoToCommander(Game game) {
        MageObject object = game.getPermanent(sourceId);
        if (object == null) {
            object = game.getCard(sourceId);
        }
        if (object != null) {
            StringBuilder sb = new StringBuilder();
            sb.append("<b>Commander</b>");
            Integer castCount = (Integer) game.getState().getValue(sourceId + "_castCount");
            if (castCount != null) {
                sb.append(' ').append(castCount).append(castCount == 1 ? " time" : " times").append(" casted from the command zone.");
            }
            this.addInfo(object, "Commander", sb.toString(), game);
            if (checkCommanderDamage) {
                for (Map.Entry<UUID, Integer> entry : damageToPlayer.entrySet()) {
                    Player damagedPlayer = game.getPlayer(entry.getKey());
                    sb.append("<b>Commander</b> did ").append(entry.getValue()).append(" combat damage to player ").append(damagedPlayer.getLogName()).append('.');
                    this.addInfo(object, "Commander" + entry.getKey(),
                            "<b>Commander</b> did " + entry.getValue() + " combat damage to player " + damagedPlayer.getLogName() + '.', game);
                }
            }
        }
    }

    private void addInfo(MageObject object, String key, String value, Game game) {
        ((Card) object).addInfo(key, value, game);
        if (object instanceof Permanent) {
            ((Permanent) object).addInfo(key, value, game);
        }
    }

    public Map<UUID, Integer> getDamageToPlayer() {
        return damageToPlayer;
    }

}
