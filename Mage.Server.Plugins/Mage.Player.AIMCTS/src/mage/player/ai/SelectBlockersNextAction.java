package mage.player.ai;

import mage.game.Game;
import mage.game.combat.CombatGroup;

import java.util.ArrayList;
import java.util.List;
import java.util.UUID;

import static mage.player.ai.MCTSNode.getBlocks;

public class SelectBlockersNextAction implements MCTSNodeNextAction{
    @Override
    public List<MCTSNode> performNextAction(MCTSNode node, MCTSPlayer player, Game game, String fullStateValue) {
        List<MCTSNode> children = new ArrayList<>();
        List<List<List<UUID>>> blocks;
        if (!MCTSNode.USE_ACTION_CACHE)
            blocks = player.getBlocks(game);
        else
            blocks = getBlocks(player, fullStateValue, game);
        for (List<List<UUID>> block : blocks) {
            Game sim = game.copy();
            MCTSPlayer simPlayer = (MCTSPlayer) sim.getPlayer(player.getId());
            List<CombatGroup> groups = sim.getCombat().getGroups();
            for (int i = 0; i < groups.size(); i++) {
                if (i < block.size()) {
                    for (UUID blockerId : block.get(i)) {
                        simPlayer.declareBlocker(simPlayer.getId(), blockerId, groups.get(i).getAttackers().get(0), sim);
                    }
                }
            }
            sim.resume();
            children.add(new MCTSNode(node, sim, sim.getCombat()));
        }

        return children;
    }
}
