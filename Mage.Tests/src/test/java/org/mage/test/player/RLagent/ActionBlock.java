package org.mage.test.player.RLagent;

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
import java.util.*;
import org.apache.log4j.Logger;

public class ActionBlock extends RLAction{
    public Permanent attacker;
    public Permanent blocker;
    public Boolean isBlock;
    public ActionBlock(Permanent attack,Permanent block, Boolean IsBlock){
        attacker=attack;
        blocker=block;
        isBlock=IsBlock;
    }

}
