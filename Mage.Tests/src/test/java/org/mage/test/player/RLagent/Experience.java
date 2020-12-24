package org.mage.test.player.RLagent;
import mage.game.Game;

import java.io.Serializable;
import java.util.*;
import java.io.*;
/**
 * @author Elchanan Haas
 */

public class Experience implements Serializable{
    RepresentedGame repr;
    int chosen;
    public Experience(RepresentedGame repr,int chosen){
        this.repr=repr;
        this.chosen=chosen;
    }
}