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
import mage.player.ai.RLAgent.*;
import java.util.stream.Collectors;
import mage.player.ai.RLAgent.*;
import java.io.*;

public class RLPyAgent extends RLAgent {
    Representer representer;
    PyConnection conn;
    private static final Logger logger = Logger.getLogger(RLPyAgent.class);
    public RLPyAgent(PyConnection conn){
        representer=new Representer();
        this.conn=conn;
    }
    public int choose(Game game, Player player,List<RLAction> actions){
        RepresentedGame repr=representer.represent(game, player, actions);
        conn.write_string("not done");
        conn.write(repr);
        //logger.info("wrote data");
        int action=conn.read();
        assert 0<= action && action <actions.size();
        return action%actions.size();
    }

}

