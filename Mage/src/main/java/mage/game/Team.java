

package mage.game;

import java.util.UUID;
import mage.MageItem;
import mage.players.Player;
import mage.players.Players;

/**
 *
 * @author BetaSteward_at_googlemail.com
 */
public class Team implements MageItem {

    private UUID teamId;
    private String name;
    private Players players = new Players();

    public Team (String name) {
        teamId = UUID.randomUUID();
        this.name = name;
    }

    public UUID getId() {
        return teamId;
    }

    public void addPlayer(Player player) {
        players.addPlayer(player);
    }

}
