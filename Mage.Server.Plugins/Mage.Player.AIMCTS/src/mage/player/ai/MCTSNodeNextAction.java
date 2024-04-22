package mage.player.ai;

import mage.game.Game;

import java.util.List;

public interface MCTSNodeNextAction {
    List<MCTSNode> performNextAction(MCTSNode node, MCTSPlayer player, Game game, String fullStateValue);
}
