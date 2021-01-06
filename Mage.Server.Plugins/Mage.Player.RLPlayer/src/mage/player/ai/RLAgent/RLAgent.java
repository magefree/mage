package mage.player.ai.RLAgent;
import mage.game.Game;
import mage.players.Player;
import java.util.*;

public abstract class RLAgent {
    Representer representer;
    public RLAgent(){
        representer=new Representer();
    }
    public abstract int choose(Game game, Player player,List<RLAction> actions);

}
