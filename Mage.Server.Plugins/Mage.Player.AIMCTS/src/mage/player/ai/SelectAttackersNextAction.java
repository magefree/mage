package mage.player.ai;

import mage.game.Game;

import java.util.ArrayList;
import java.util.List;
import java.util.UUID;

import static mage.player.ai.MCTSNode.getAttacks;

public class SelectAttackersNextAction implements MCTSNodeNextAction{
    @Override
    public List<MCTSNode> performNextAction(MCTSNode node, MCTSPlayer player, Game game, String fullStateValue) {
        List<MCTSNode> children = new ArrayList<>();
        List<List<UUID>> attacks;
        if (!MCTSNode.USE_ACTION_CACHE)
            attacks = player.getAttacks(game);
        else
            attacks = getAttacks(player, fullStateValue, game);
        UUID defenderId = game.getOpponents(player.getId()).iterator().next();
        for (List<UUID> attack: attacks) {
            Game sim = game.copy();
            MCTSPlayer simPlayer = (MCTSPlayer) sim.getPlayer(player.getId());
            for (UUID attackerId: attack) {
                simPlayer.declareAttacker(attackerId, defenderId, sim, false);
            }
            sim.resume();
            children.add(new MCTSNode(node, sim, sim.getCombat()));
        }

        return children;
    }
}
