package mage.player.ai.RLAgent;
import mage.game.Game;
import mage.players.Player;
import java.util.*;
import org.apache.log4j.Logger;
import mage.player.ai.RLAction;
import mage.player.ai.RLPlayer;

public class DJLAgent{
    public Representer representer;
    PyConnection conn;
    private static final Logger logger = Logger.getLogger(DJLAgent.class);
    List<RepresentedState> experience;
    public DJLAgent(){
        representer=new Representer();
        experience=new ArrayList<RepresentedState>();
    }
    public int choose(Game game, RLPlayer player, List<RLAction> actions){
        RepresentedState state=representer.represent(game, player, actions);
        ___ netInput=prepare(state)//Pack into a list
        int choice=run(net,netInput);
        state.chosenAction=choice;
        player.addExperience(state);
        return choice;
    }
}

