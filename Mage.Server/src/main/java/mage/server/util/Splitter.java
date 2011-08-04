package mage.server.util;

import mage.game.Game;
import mage.players.Player;

import java.util.ArrayList;
import java.util.List;
import java.util.UUID;

/**
 * @author nantuko
 */
public class Splitter {

	public static List<UUID> split(Game game, UUID playerId) {
		List<UUID> players = new ArrayList<UUID>();
		players.add(playerId); // add original player
		Player player = game.getPlayer(playerId);
		if (player != null && player.getTurnControlledBy() != null) {
			players.add(player.getTurnControlledBy());
		}
		return players;
	}
}
