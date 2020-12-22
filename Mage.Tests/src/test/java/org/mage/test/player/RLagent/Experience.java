package org.mage.test.player.RLagent;
import mage.game.Game;
import java.util.*;

/**
 * @author Elchanan Haas
 */

public class Experience{
    RepresentedGame repr;
    int chosen;
    public Experience(RepresentedGame repr,int chosen){
        this.repr=repr;
        this.chosen=chosen;
    }
}