
package mage.player.ai;

import java.io.Serializable;
import java.util.ArrayList;
import java.util.List;
import java.util.UUID;
import mage.abilities.Ability;
import mage.game.Game;
import mage.game.combat.Combat;

/**
 *
 * @author BetaSteward_at_googlemail.com
 */
public class SimulationNode2 implements Serializable {

    protected static int nodeCount;

    protected Game game;
    protected int gameValue;
    protected int score;
    protected List<Ability> abilities;
    protected int depth;
    protected List<SimulationNode2> children = new ArrayList<>();
    protected SimulationNode2 parent;
    protected List<UUID> targets = new ArrayList<>();
    protected List<String> choices = new ArrayList<>();
    protected UUID playerId;
    protected Combat combat;

    public SimulationNode2(SimulationNode2 parent, Game game, int depth, UUID playerId) {
        this.parent = parent;
        this.game = game;
        this.depth = depth;
        this.playerId = playerId;
        game.setCustomData(this);
        nodeCount++;
    }

    public SimulationNode2(SimulationNode2 parent, Game game, List<Ability> abilities, int depth, UUID playerId) {
        this(parent, game, depth, playerId);
        this.abilities = abilities;
    }

    public SimulationNode2(SimulationNode2 parent, Game game, Ability ability, int depth, UUID playerId) {
        this(parent, game, depth, playerId);
        this.abilities = new ArrayList<>();
        abilities.add(ability);
    }

    public static void resetCount() {
        nodeCount = 0;
    }

    public static int getCount() {
        return nodeCount;
    }

    public Game getGame() {
        return this.game;
    }

    public int getGameValue() {
        return this.gameValue;
    }

    public void setGameValue(int value) {
        this.gameValue = value;
    }

    public List<Ability> getAbilities() {
        return this.abilities;
    }

    public SimulationNode2 getParent() {
        return this.parent;
    }

    public List<SimulationNode2> getChildren() {
        return this.children;
    }

    public int getDepth() {
        return this.depth;
    }

    public UUID getPlayerId() {
        return this.playerId;
    }

    public Combat getCombat() {
        return this.combat;
    }

    public void setCombat(Combat combat) {
        this.combat = combat;
    }

    public List<UUID> getTargets() {
        return this.targets;
    }

    public List<String> getChoices() {
        return this.choices;
    }

    public int getScore() {
        return score;
    }

    public void setScore(int score) {
        this.score = score;
    }
}
