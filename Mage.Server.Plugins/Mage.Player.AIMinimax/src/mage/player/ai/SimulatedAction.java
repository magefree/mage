

package mage.player.ai;

import java.util.List;
import mage.abilities.Ability;
import mage.game.Game;

/**
 *
 * @author BetaSteward_at_googlemail.com
 */
public class SimulatedAction {

    private Game game;
    private List<Ability> abilities;

    public SimulatedAction(Game game, List<Ability> abilities) {
        this.game = game;
        this.abilities = abilities;
    }

    public Game getGame() {
        return this.game;
    }

    public List<Ability> getAbilities() {
        return this.abilities;
    }

    @Override
    public String toString() {
        return this.abilities.toString();
    }

    public boolean usesStack() {
        if (abilities != null && !abilities.isEmpty()) {
            return abilities.get(abilities.size() -1).isUsesStack();
        }
        return true;
    }
}
