package mage.player.ai;

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
import mage.MageObject;

import java.io.Serializable;
import java.nio.file.Files;
import java.util.*;
import org.apache.log4j.Logger;
import mage.player.ai.RLAgent.*;
import java.util.stream.Collectors;
import mage.player.ai.RLAgent.*;
import java.io.*;
import mage.abilities.*;

/**
 * uses a reinforcement learning based AI
 *
 * @author Elchanan Haas
 */

public class RLPlayer extends RandomPlayer{
    public RLAgent learner;
    private static final Logger logger = Logger.getLogger(RLPlayer.class);
    public RLPlayer(String name , RangeOfInfluence range, int skill){
        super(name);
        learner=loadPyLearner("pydefault");
        if(Objects.isNull(learner)){
            logger.warn("learner is null in RLPlayer creation!");
        }
    }
    public RLPlayer(String name,RLAgent inLearner) {  
        super(name);
        learner=inLearner;
    }
    public RLPlayer(final RLPlayer player) {
        super(player);   
    }
    private static String getPath(String fileName,String tail){
        String home = System.getProperty("user.home");
        java.nio.file.Path path = java.nio.file.Paths.get(home, "xmage-models",fileName+tail);
        try{
            Files.createDirectories(path.getParent());
        }
        catch(IOException e){
            System.err.println("Failed to create directory!" + e.getMessage());
        }
        
        logger.info(path.toString());
        return path.toString();
    }
    public static String getDataLoc(String fileName){
        return getPath(fileName,"-data.ser");
    }
    public static String getClassLoc(String fileName){
        return getPath(fileName,"-class.ser");
    }
    public static String getModelLoc(String fileName){
        return getPath(fileName,"-model.zip");
    }
    public static void savePyLearner(RLAgent agent, String loc){
        try {
        FileOutputStream fileClassOut =new FileOutputStream(getClassLoc(loc));
        ObjectOutputStream classOut = new ObjectOutputStream(fileClassOut);
        classOut.writeObject(agent.representer);
        classOut.close();
        }catch (IOException i) {
            i.printStackTrace();
         }
    }
    public static RLAgent loadPyLearner(String loc){
        try {
            FileInputStream fileClassIn = new FileInputStream(getClassLoc(loc));
            ObjectInputStream classIn = new ObjectInputStream(fileClassIn);
            PyConnection conn=new PyConnection(5000);
            RLAgent agent= new RLPyAgent(conn);
            agent.representer= (Representer) classIn.readObject();
            return agent;
        }catch (IOException i) {
            logger.warn("IO exception");
            System.err.println("IO exception!");
            i.printStackTrace();
            return null;
         }
         catch (ClassNotFoundException c) {
            logger.warn("Class Not Found exception");
            System.out.println("RLAgent class not found");
            c.printStackTrace();
            return null;
         }
    }
    private Ability chooseAbility(Game game, List<Ability> options){
        Ability ability=pass;
        if (!options.isEmpty()) {
            if (options.size() == 1) { //Don't call ML model for single element
                ability = options.get(0);
            } else {
                List<RLAction> toUse=new ArrayList<RLAction>();
                for(int i=0;i<options.size();i++){
                    Ability abil=options.get(i);
                    toUse.add((RLAction) new ActionAbility(game,abil));
                }
                int choice=learner.choose(game,this,toUse);
                ActionAbility chosenAction=(ActionAbility) toUse.get(choice);
                ability = chosenAction.ability;
            }
        }
        return ability;
    }

    protected List<ActivatedAbility> getPlayableNonLandTapAbilities(Game game){
        List<ActivatedAbility> playables=getPlayableAbilities(game);
        List<ActivatedAbility> filtered=new ArrayList<ActivatedAbility>();
        for(int i=0;i<playables.size();i++){
            MageObject source=playables.get(i).getSourceObjectIfItStillExists(game);
            if(source!=null && source instanceof Permanent && source.isLand()){
                //Don't allow just tapping a land to be an action
                //System.out.println("skipped a land");
                continue;
            }
            filtered.add(playables.get(i));
        }
        return filtered;
    }
    @Override
    protected Ability getAction(Game game) {
        //logger.info("Getting action");
        //List<ActivatedAbility> playables = getPlayableAbilities(game); //already contains pass
        List<ActivatedAbility> playables =getPlayableNonLandTapAbilities(game);
        List<Ability> castPlayables=playables.stream().map(element->(Ability) element).collect(Collectors.toList());
        Ability ability;
        ability=chooseAbility(game, castPlayables);
        List<Ability> options = getPlayableOptions(ability, game);
        if (!options.isEmpty()) {
            ability=chooseAbility(game, options);
        }
        if (!ability.getManaCosts().getVariableCosts().isEmpty()) {//leave random for now-variable spells, AI can wait
            int amount = getAvailableManaProducers(game).size() - ability.getManaCosts().convertedManaCost();
            if (amount > 0) {
                ability = ability.copy();
                ability.getManaCostsToPay().add(new GenericManaCost(RandomUtil.nextInt(amount)));
            }
        }
        return ability;
    }

    @Override
    public void selectAttackers(Game game, UUID attackingPlayerId) { //Recorded by AI now!
        //logger.info("life total of " + getName() +" is "+getLife());
        UUID defenderId = game.getOpponents(playerId).iterator().next();
        List<Permanent> attackersList = super.getAvailableAttackers(defenderId, game);
        for(int i=0;i<attackersList.size();i++){
            Permanent attacker=attackersList.get(i);

            List<RLAction> toattack= Arrays.asList((RLAction) new ActionAttack(attacker,false),(RLAction) new ActionAttack(attacker,true));
            int index=learner.choose(game,this,toattack);
            if(index==1){//chose to attack
                setStoredBookmark(game.bookmarkState()); // makes it possible to UNDO a declared attacker with costs from e.g. Propaganda
                if (!game.getCombat().declareAttacker(attacker.getId(), defenderId, playerId, game)) {
                    game.undo(playerId);
                }
            }
        }
        actionCount++;
    }

    @Override
    public boolean chooseMulligan(Game game) {
        List<RLAction> toMull= Arrays.asList((RLAction) new ActionMulligan(false),(RLAction) new ActionMulligan(true));
        int index=learner.choose(game,this,toMull);
        return index==1;
    }

    @Override
    public void selectBlockers(Ability source,Game game, UUID defendingPlayerId) {
        //logger.info("selcting blockers");
        int numGroups = game.getCombat().getGroups().size();
        if (numGroups == 0) {
            return;
        }

        List<Permanent> blockers = getAvailableBlockers(game);
        for (Permanent blocker : blockers) {
            List<RLAction> toblock=new ArrayList<RLAction>();
            List<CombatGroup> groups=game.getCombat().getGroups();
            for(int i=0;i<numGroups;i++){
                List<UUID> groupIDs=groups.get(i).getAttackers();
                if(groupIDs.size()>0){
                    UUID attacker=groupIDs.get(0);
                    toblock.add((RLAction) new ActionBlock(game.getPermanent(attacker),blocker,true));
           
                }
            }
            toblock.add((RLAction) new ActionBlock(null,null,false)); // Don't block anything
            int choice=learner.choose(game,this,toblock);
            ActionBlock chosenAction=(ActionBlock) toblock.get(choice);

            if (chosenAction.isBlock) {
                CombatGroup group = groups.get(choice);
                if (!group.getAttackers().isEmpty()) {
                    this.declareBlocker(this.getId(), blocker.getId(), group.getAttackers().get(0), game);
                }
            }
        }
        actionCount++;
    }
}
