package org.mage.test.player.RLagent;
import mage.game.Game;
import java.util.*;

/**
 * @author Elchanan Haas
 */

public class Experience{
    Game game;
    List<RLAction> actions;
    int chosen;
    public Experience(Game inGame, List<RLAction> inActions, int inChoice){
        game=inGame;
        actions=inActions;
        chosen=inChoice;
    }
}