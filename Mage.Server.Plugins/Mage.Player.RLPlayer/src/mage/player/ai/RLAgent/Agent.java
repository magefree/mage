package mage.player.ai.RLAgent;

import mage.player.ai.ComputerPlayer;
import mage.game.Game;
import mage.game.GameState;
import mage.players.Player;
import java.util.*;
import org.apache.log4j.Logger;
import mage.player.ai.RLAction;
import mage.player.ai.RLPlayer;
import ai.djl.*;
import ai.djl.nn.*;
import ai.djl.nn.core.*;
import ai.djl.ndarray.*;
import ai.djl.ndarray.types.*;
import ai.djl.ndarray.index.*;
import java.util.concurrent.ThreadLocalRandom;   
import java.nio.file.*;
import java.io.*;

//Plays randomly, usable as an opponent
public class Agent{
    public transient boolean runMode;
    public Agent(){

    }
    public void loadNets(String path){

    }
    public void addExperiences(List<RepresentedState> exp){

    }

    public int choose(Game game, RLPlayer player, List<RLAction> actions){
        return ThreadLocalRandom.current().nextInt(actions.size());
    }
    public void save(int iter) throws IOException{
    }
    public void trainIfReady(){}
}
