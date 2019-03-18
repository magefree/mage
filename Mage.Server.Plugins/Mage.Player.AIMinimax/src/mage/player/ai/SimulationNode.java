

package mage.player.ai;

import mage.abilities.Ability;
import mage.game.Game;
import mage.game.combat.Combat;

import java.io.Serializable;
import java.util.ArrayList;
import java.util.List;
import java.util.UUID;

/**
 *
 * @author BetaSteward_at_googlemail.com
 */
public class SimulationNode implements Serializable {

    protected static int nodeCount;

    protected Game game;
    protected int gameValue;
    protected List<Ability> abilities;
    protected int depth;
    protected List<SimulationNode> children = new ArrayList<>();
    protected SimulationNode parent;
    protected List<UUID> targets = new ArrayList<>();
    protected List<String> choices = new ArrayList<>();
    protected UUID playerId;
    protected Combat combat;

    public SimulationNode(SimulationNode parent, Game game, UUID playerId) {
        this.parent = parent;
        this.game = game;
        if (parent == null)
            this.depth = 1;
        else
            this.depth = parent.getDepth() + 1;
        this.playerId = playerId;
        game.setCustomData(this);
        nodeCount++;
    }

    public SimulationNode(SimulationNode parent, Game game, List<Ability> abilities, UUID playerId) {
        this(parent, game, playerId);
        this.abilities = abilities;
    }

    public SimulationNode(SimulationNode parent, Game game, Ability ability, UUID playerId) {
        this(parent, game, playerId);
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

    public SimulationNode getParent() {
        return this.parent;
    }

    public List<SimulationNode> getChildren() {
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
}
