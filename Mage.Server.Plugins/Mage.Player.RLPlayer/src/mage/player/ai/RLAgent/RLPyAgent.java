package mage.player.ai.RLAgent;

import mage.abilities.*;
import mage.abilities.common.PassAbility;
import mage.abilities.costs.mana.GenericManaCost;
import mage.cards.Card;
import mage.cards.Cards;
import mage.choices.Choice;
import mage.constants.Outcome;
import mage.constants.RangeOfInfluence;
import mage.game.Game;
import mage.game.combat.CombatGroup;
import mage.game.events.GameEvent;
import mage.game.permanent.Permanent;
import mage.game.stack.StackAbility;
import mage.player.ai.ComputerPlayer;
import mage.players.Player;
import mage.target.Target;
import mage.target.TargetAmount;
import mage.target.TargetCard;
import mage.util.RandomUtil;

import java.io.Serializable;
import java.nio.file.Files;
import java.util.*;
import org.apache.log4j.Logger;
import org.json.simple.JSONObject;

import mage.player.ai.RLAgent.*;
import java.util.stream.Collectors;
import mage.player.ai.RLAgent.*;
import java.io.*;

public class RLPyAgent extends RLAgent {
    public RepresenterJSON representer;
    PyConnection conn;
    public transient boolean done=false;
    private static final Logger logger = Logger.getLogger(RLPyAgent.class);
    public RLPyAgent(PyConnection conn){
        representer=new RepresenterJSON();
        this.conn=conn;
        //conn.write_hparams();
    }
    
    public int choose(Game game, Player player,List<RLAction> actions){
        if(done){
            return 0;
        }
        sendGame(game, player,actions);
        //logger.info("wrote data");
        int action=conn.read();
        if(action==-2){
            done=true;
            return 0;
        }
        assert 0<= action && action <actions.size();
        //int chosenact=action%actions.size();
        int chosenact=action;
        //logger.info(actions.get(chosenact).getText());
        return chosenact;
    }
    public void sendGame(Game game,Player player,List<RLAction> actions){
        JSONObject repr=representer.represent(game, player, actions);
        conn.write(repr);
    }
}

