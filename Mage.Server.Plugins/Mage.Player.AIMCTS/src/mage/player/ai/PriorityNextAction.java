package mage.player.ai;

import mage.abilities.Ability;
import mage.abilities.ActivatedAbility;
import mage.game.Game;

import java.util.ArrayList;
import java.util.List;

public class PriorityNextAction implements MCTSNodeNextAction{

    @Override
    public List<MCTSNode> performNextAction(MCTSNode node, MCTSPlayer player, Game game, String fullStateValue) {
        List<MCTSNode> children = new ArrayList<>();
        List<Ability> abilities;
        if (!MCTSNode.USE_ACTION_CACHE)
            abilities = player.getPlayableOptions(game);
        else
            abilities = MCTSNode.getPlayables(player, fullStateValue, game);
        for (Ability ability: abilities) {
            Game sim = game.copy();
            MCTSPlayer simPlayer = (MCTSPlayer) sim.getPlayer(player.getId());
            simPlayer.activateAbility((ActivatedAbility)ability, sim);
            sim.resume();
            children.add(new MCTSNode(node, sim, ability));
        }

        return children;
    }
}
