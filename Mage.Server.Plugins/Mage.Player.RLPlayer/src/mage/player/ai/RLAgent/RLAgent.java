package mage.player.ai.RLAgent;
import mage.game.Game;
import mage.players.Player;
import java.util.*;
import mage.player.ai.RLAction;

public abstract class RLAgent {
    public RLAgent(){
    }
    public abstract int choose(Game game, Player player, List<RLAction> actions);

}
