

package mage.players;

import java.util.LinkedHashMap;
import java.util.Map.Entry;
import java.util.UUID;

/**
 * @author BetaSteward_at_googlemail.com
 */
public class Players extends LinkedHashMap<UUID, Player> {

    public Players() {
    }

    protected Players(final Players players) {
        for (Entry<UUID, Player> entry : players.entrySet()) {
            this.put(entry.getKey(), entry.getValue().copy());
        }
    }

    public void addPlayer(Player player) {
        this.put(player.getId(), player);
    }

    public void resetPassed() {
        for (Player player : this.values()) {
            player.resetPassed();
        }
    }

    public Players copy() {
        return new Players(this);
    }

}
