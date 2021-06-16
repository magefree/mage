package mage.player.ai.RLAgent;
import mage.game.Game;
import mage.players.Player;
import java.util.*;
import org.json.simple.JSONArray;

public abstract class RLAgent {
    public RLAgent(){
    }
    public abstract int choose(Game game, Player player, JSONArray actions);

}
