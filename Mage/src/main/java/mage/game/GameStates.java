
package mage.game;

import java.io.Serializable;
import java.util.ArrayList;
import java.util.List;
import org.apache.log4j.Logger;

/**
 *
 * @author BetaSteward_at_googlemail.com
 */
public class GameStates implements Serializable {

    private static final Logger logger = Logger.getLogger(GameStates.class);
    private final List<GameState> states;

    public GameStates() {
        this.states = new ArrayList<>();
    }

    public void save(GameState gameState) {
        states.add(gameState.copy());
        //logger.warn("states size: " + states.size());
    }

    public int getSize() {
        return states.size();
    }

    public GameState rollback(int index) {
        if (!states.isEmpty() && index < states.size()) {
            while (states.size() > index + 1) {
                states.remove(states.size() - 1);
            }
            logger.trace("Rolling back state: " + index);
            return states.get(index);
        }
        return null;
    }

    public int remove(int index) {
        if (!states.isEmpty() && index < states.size()) {
            while (states.size() > index && !states.isEmpty()) {
                states.remove(states.size() - 1);
            }
        }
        return states.size();
    }

    public GameState get(int index) {
        if (index < states.size()) {
            return states.get(index);
        }
        return null;
    }

    public void clear() {
        states.clear();
    }
}
