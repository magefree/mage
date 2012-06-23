/*
 *  Copyright 2010 BetaSteward_at_googlemail.com. All rights reserved.
 *
 *  Redistribution and use in source and binary forms, with or without modification, are
 *  permitted provided that the following conditions are met:
 *
 *     1. Redistributions of source code must retain the above copyright notice, this list of
 *        conditions and the following disclaimer.
 *
 *     2. Redistributions in binary form must reproduce the above copyright notice, this list
 *        of conditions and the following disclaimer in the documentation and/or other materials
 *        provided with the distribution.
 *
 *  THIS SOFTWARE IS PROVIDED BY BetaSteward_at_googlemail.com ``AS IS'' AND ANY EXPRESS OR IMPLIED
 *  WARRANTIES, INCLUDING, BUT NOT LIMITED TO, THE IMPLIED WARRANTIES OF MERCHANTABILITY AND
 *  FITNESS FOR A PARTICULAR PURPOSE ARE DISCLAIMED. IN NO EVENT SHALL BetaSteward_at_googlemail.com OR
 *  CONTRIBUTORS BE LIABLE FOR ANY DIRECT, INDIRECT, INCIDENTAL, SPECIAL, EXEMPLARY, OR
 *  CONSEQUENTIAL DAMAGES (INCLUDING, BUT NOT LIMITED TO, PROCUREMENT OF SUBSTITUTE GOODS OR
 *  SERVICES; LOSS OF USE, DATA, OR PROFITS; OR BUSINESS INTERRUPTION) HOWEVER CAUSED AND ON
 *  ANY THEORY OF LIABILITY, WHETHER IN CONTRACT, STRICT LIABILITY, OR TORT (INCLUDING
 *  NEGLIGENCE OR OTHERWISE) ARISING IN ANY WAY OUT OF THE USE OF THIS SOFTWARE, EVEN IF
 *  ADVISED OF THE POSSIBILITY OF SUCH DAMAGE.
 *
 *  The views and conclusions contained in the software and documentation are those of the
 *  authors and should not be interpreted as representing official policies, either expressed
 *  or implied, of BetaSteward_at_googlemail.com.
 */

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
public class SimulationNode implements Serializable {

    protected static int nodeCount;

    protected Game game;
    protected int gameValue;
    protected List<Ability> abilities;
    protected int depth;
    protected List<SimulationNode> children = new ArrayList<SimulationNode>();
    protected SimulationNode parent;
    protected List<UUID> targets = new ArrayList<UUID>();
    protected List<String> choices = new ArrayList<String>();
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
        this.abilities = new ArrayList<Ability>();
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
