

package mage.server.game;

import mage.constants.RangeOfInfluence;
import mage.players.Player;
import mage.players.PlayerType;
import org.apache.log4j.Logger;

import java.lang.reflect.Constructor;
import java.util.EnumMap;
import java.util.Optional;
import java.util.Set;

/**
 * @author BetaSteward_at_googlemail.com
 */
public enum PlayerFactory {

    instance;
    private static final Logger logger = Logger.getLogger(PlayerFactory.class);

    private final EnumMap<PlayerType, Class> playerTypes = new EnumMap<>(PlayerType.class);


    public Optional<Player> createPlayer(PlayerType playerType, String name, RangeOfInfluence range, int skill) {
        try {
            Class playerTypeClass = playerTypes.get(playerType);
            if (playerTypeClass != null) {
                Constructor<?> con = playerTypeClass.getConstructor(String.class, RangeOfInfluence.class, int.class);
                Player player = (Player) con.newInstance(name, range, skill);
                logger.trace("Player created: " + name + " - " + player.getId());
                return Optional.of(player);
            } else {
                logger.fatal("Unknown player type: " + playerType);
            }
        } catch (Exception ex) {
            logger.fatal("PlayerFactory error ", ex);
        }
        return Optional.empty();
    }

    public Set<PlayerType> getPlayerTypes() {
        return playerTypes.keySet();
    }

    public void addPlayerType(String name, Class playerType) {
        PlayerType type = PlayerType.getByDescription(name);
        if (type != null) {
            if (playerType != null) {
                this.playerTypes.put(type, playerType);
            }
        }
    }

}
