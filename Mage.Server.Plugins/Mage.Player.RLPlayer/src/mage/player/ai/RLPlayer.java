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
import mage.game.stack.Spell;
import mage.player.ai.ComputerPlayer;
import mage.players.Player;
import mage.target.Target;
import mage.target.TargetAmount;
import mage.target.TargetCard;
import mage.util.RandomUtil;
import mage.MageObject;
import mage.constants.CardType;
import java.io.Serializable;
import java.nio.file.Files;
import java.util.*;
import org.apache.log4j.Logger;
import mage.player.ai.RLAgent.*;
import java.util.stream.Collectors;

import javax.management.RuntimeErrorException;

import mage.player.ai.RLAgent.*;

import static org.junit.Assert.assertTrue;

import java.io.*;
import java.nio.file.*;
import mage.abilities.*;
import mage.player.ai.RLAction;

/**
 * uses a reinforcement learning based AI
 *
 * @author Elchanan Haas
 */

public class RLPlayer extends ComputerPlayer{
    public DJLAgent learner;
    private static final Logger logger = Logger.getLogger(RLPlayer.class);
    List<RepresentedState> experiences=new ArrayList<RepresentedState>();
    private PassAbility pass = new PassAbility();
    public RLPlayer(String name) {
        this(name, RangeOfInfluence.ALL);
    }
    public RLPlayer(String name, RangeOfInfluence range){
        this(name,range,0);
    }
    public RLPlayer(String name , RangeOfInfluence range, int skill){
        super(name,RangeOfInfluence.ALL);
        logger.info("Constructing RLPlayer");
        try{
            InputStream configStream = RLPlayer.class.getClassLoader().getResourceAsStream("config.properties"); //no leading "/"!!!
            Properties config=new Properties();
            config.load(configStream);
            configStream.close();
            String path=config.getProperty("modelPath");
            FileInputStream fileIn =new FileInputStream(path+File.separator+"representer.bin");
            ObjectInputStream in = new ObjectInputStream(fileIn);
            learner=(DJLAgent) in.readObject();
            in.close();
            fileIn.close();
            learner.loadNets(path);
        }catch(Exception e){
            logger.error(e.getMessage());
            for(int i=0;i<e.getStackTrace().length;i++){
                logger.error(e.getStackTrace()[i]);
            }
            throw new RuntimeException(e);
        }
    }
    public RLPlayer(String name,DJLAgent inLearner) {  
        super(name,RangeOfInfluence.ALL);
        learner=inLearner;
    }
    public void addExperience(RepresentedState state){
        experiences.add(state);
    }
    public void sendExperiences(Game game){
        int reward;
        if(game.isADraw()){
            reward=0;
        }
        else if(hasWon()){
            reward=1;
        }
        else{
            reward=-1;
        }
        for(int i=0;i<experiences.size();i++){
            experiences.get(i).reward=((float) reward);
            experiences.get(i).rewardScale=(1.0f/experiences.size());
        }
        learner.addExperiences(experiences);
        experiences=new ArrayList<RepresentedState>();
    }
    public RLPlayer(final RLPlayer player) {
        super(player);   
    }

    /* 
    JSONObject actRepr=new JSONObject();
    if(action instanceof ActionAbility){
        String abilityName=action.getText();
        actRepr.put("action",abilityName);
    }*/
    String nameAbility(Ability ability,Game game){
        MageObject source=ability.getSourceObjectIfItStillExists(game);
        if(ability instanceof PassAbility){
            return "Ability:Pass";
        }
        else if(source==null){
            //logger.info("source is NULL!");
            return "Ability:NULL";
            //logger.info(ability.getRule());
        }
        else{
            if(source instanceof Permanent){
                return "Ability:"+"Perm:"+source.getName();
            }
            else if(source instanceof Card){
                return "Ability:"+"Card:"+source.getName();
            }
            else{
                return "Ability:Unrecognized";
            }
        }
    }

    @Override
    public boolean priority(Game game) {
        boolean didSomething = false;
        Ability ability = getAction(game);
        if (!(ability instanceof PassAbility)) {
            didSomething = true;
        }

        activateAbility((ActivatedAbility) ability, game);
        return didSomething;
    }

    private Ability chooseAbility(Game game, List<Ability> options){
        Ability ability=pass;
        List<String> names=new ArrayList<String>();
        List<Ability> uniqueOptions=new ArrayList<Ability>();
        if (!options.isEmpty()) {
            for(int i=0;i<options.size();i++){
                Ability abil=options.get(i);
                String name=nameAbility(abil, game);
                boolean toAdd=true;
                for(int j=0;j<names.size();j++){
                    if(name.equals(names.get(j))){
                        toAdd=false;
                    }
                }
                if(toAdd){
                    names.add(name);
                    uniqueOptions.add(options.get(i));
                }  
            }
            int choice;
            if (names.size() == 1) { //Don't call ML model for single element
                choice=0;
                //logger.info("Turn"+game.getTurnNum()+" handSize "+getHand().size());
            } else {
                List<RLAction> actions=new ArrayList<RLAction>();
                for(int i=0;i<names.size();i++){
                    actions.add(new RLAction(names.get(i)));
                }
                choice=learner.choose(game,this,actions);

            }
            ability = uniqueOptions.get(choice);
        }
        return ability;
    }
    protected List<ActivatedAbility> getPlayableAbilities(Game game) {
        List<ActivatedAbility> playables = getPlayable(game, true);
        playables.add(pass);
        return playables;
    }
    protected List<ActivatedAbility> getFilteredPlayableAbilities(Game game){
        List<ActivatedAbility> playables=getPlayableAbilities(game);
        List<ActivatedAbility> filtered=new ArrayList<ActivatedAbility>();
        for(int i=0;i<playables.size();i++){
            MageObject source=playables.get(i).getSourceObjectIfItStillExists(game);
            if(source!=null && source instanceof Permanent && source.isLand()){
                //Don't allow just tapping a land to be an action
                //May break lands with activated abilities
                continue;
            }
            filtered.add(playables.get(i));
        }
        return filtered;
    }

    protected Ability getAction(Game game) {
        //logger.info("Getting action");
        List<ActivatedAbility> playables =getFilteredPlayableAbilities(game);//already contains pass
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
    public void selectAttackers(Game game, UUID attackingPlayerId) { 
        //logger.info("life total of " + getName() +" is "+getLife());
        UUID defenderId = game.getOpponents(playerId).iterator().next();
        List<Permanent> attackersList = super.getAvailableAttackers(defenderId, game);
        for(int i=0;i<attackersList.size();i++){
            Permanent attacker=attackersList.get(i);
            List<RLAction> attackOptions=new ArrayList<RLAction>();
            attackOptions.add(new RLAction("noAttack"));
            attackOptions.add(new RLAction("Attack"+attacker.getName())); //Perhaps add power/toughness features?
            int index=learner.choose(game,this,attackOptions);
            if(index==1){//chose to attack
                setStoredBookmark(game.bookmarkState()); // makes it possible to UNDO a declared attacker with costs from e.g. Propaganda
                if (!game.getCombat().declareAttacker(attacker.getId(), defenderId, playerId, game)) {
                    game.undo(playerId);
                }
            }
        }
    }

    @Override
    public boolean chooseMulligan(Game game) {
        List<RLAction> actions=new ArrayList<RLAction>();
        actions.add(new RLAction("noMulligan"));
        actions.add(new RLAction("yesMulligan"));
        int index=learner.choose(game,this,actions);
        return index==1;
    }

    @Override
    public void selectBlockers(Ability source,Game game, UUID defendingPlayerId) {
        int numGroups = game.getCombat().getGroups().size();
        if (numGroups == 0) {
            return;
        }
        List<Permanent> blockers = getAvailableBlockers(game);
        for (Permanent blocker : blockers) {
            List<RLAction> actions=new ArrayList<RLAction>();
            List<CombatGroup> rawGroups=game.getCombat().getGroups();
            List<CombatGroup> nonZeroGroups=new ArrayList<CombatGroup>();
            for(int i=0;i<numGroups;i++){
                List<UUID> groupIDs=rawGroups.get(i).getAttackers();
                if(groupIDs.size()>0){
                    UUID attacker=groupIDs.get(0);
                    String attackerName="BlockAttacker:"+game.getPermanent(attacker).getName();
                    String blockerName="Blocker:"+blocker.getName();
                    actions.add(new RLAction(blockerName, attackerName));
                    nonZeroGroups.add(rawGroups.get(i));
                }
            } 
            actions.add(new RLAction("Blocker:"+blocker.getName(), "NoAttackerToBlock"));
            int choice=learner.choose(game,this,actions);
            assertTrue(actions.size()==nonZeroGroups.size()+1);
            if (choice<nonZeroGroups.size()) { //Not the last one where no block occurs
                CombatGroup group = nonZeroGroups.get(choice);
                if (!group.getAttackers().isEmpty()) {
                    this.declareBlocker(this.getId(), blocker.getId(), group.getAttackers().get(0), game);
                }
            }
        }
    }
    private String namePlayer(UUID playerId){
        if(playerId==getId()){
            return "RLPlayer";
        }
        else{
            return "Opponent";
        }
    }

    String nameUUID(UUID id,Game game){
        String IDname;
        Permanent perm=game.getPermanent(id);
        if(id == null){
            IDname="NullID";
            return IDname;
        }
        if(perm!=null){
            IDname="Permanent:"+perm.getName();
            return IDname;
        }
        Player targetPlayer=game.getPlayer(id);
        if(targetPlayer!=null){
            IDname="Player:"+namePlayer(id);
            return IDname;
        }
        Spell spell=game.getSpell(id);
        if(spell!=null){
            UUID controller=spell.getControllerId();
            IDname="Spell:"+namePlayer(controller)+":"+spell.getName();
            return IDname;
        }
        Card card=game.getCard(id);
        if(card!=null){
            IDname="Card:"+card.getName();
            return IDname;
        }
        MageObject object=game.getObject(id);
        if(object!=null){
            ArrayList<CardType> types=object.getCardType();
            String allTypes="";
            for(int i=0;i<types.size();i++){
                allTypes+=types.get(i).toString();
            }
            IDname="Object:"+allTypes+":"+object.getName();
            return IDname;
        }
        IDname="Unknown";
        System.out.println("Unknown ID");
        return IDname;
    }

    @Override
    public boolean chooseTarget(Outcome outcome, Target target, Ability source, Game game) {
        //System.out.println("Chose target with ability");
        Set<UUID> targetSet = target.possibleTargets(source == null ? null : source.getSourceId(), playerId, game);
        if (targetSet.isEmpty()) {
            return false;
        }
        boolean required = target.isRequired(source);
        if (target.getTargets().size() >= target.getNumberOfTargets()) {
            required = false;
        }
        List<UUID> targets=new ArrayList<UUID>(targetSet);
        if (required && targets.size() == 1) {
            target.addTarget(targets.get(0), source, game); // todo: addtryaddtarget or return type (see computerPlayer)
            return true;
        }
        List<RLAction> actions=new ArrayList<RLAction>();
        String causeName;
        if(source!=null){
            MageObject cause=source.getSourceObject(game);
            causeName="TargetAbility:"+cause.getName();
        }
        else{
            causeName="TargetAbility:None";
        }
        for(int i=0;i<targets.size();i++){
            UUID thingId=targets.get(i);
            String name="Target:"+nameUUID(thingId, game);
            actions.add(new RLAction(name,causeName));
        }
        if (!required) {
            actions.add(new RLAction("Target:None",causeName));
        }
        int choice=learner.choose(game,this,actions);
        if(choice==targets.size()){
            return false;
        }
        target.addTarget(targets.get(choice), source, game);
        return true;
    }

    
    @Override
    public boolean choose(Outcome outcome, Target target, UUID sourceId, Game game) {
        //System.out.println("Chose target with source"+outcome+" "+target);
        Set<UUID> targetSet = target.possibleTargets(sourceId,playerId, game);
        if (targetSet.isEmpty()) {
            return false;
        }
        boolean required = target.isRequired(sourceId, game);
        if (target.getTargets().size() >= target.getNumberOfTargets()) {
            required = false;
        }

        List<UUID> targets=new ArrayList<UUID>(targetSet);
        if (required && targets.size() == 1) {
            target.add(targets.get(0), game); // todo: addtryaddtarget or return type (see computerPlayer)
            return true;
        }
        List<RLAction> actions=new ArrayList<RLAction>();
        String causeName="TargetSource:"+nameUUID(sourceId, game);
        for(int i=0;i<targets.size();i++){
            UUID thingId=targets.get(i);
            String name="Target:"+nameUUID(thingId, game);
            actions.add(new RLAction(name,causeName));
        }
        if (!required) {
            actions.add(new RLAction("Target:None",causeName));
        }
        boolean added=false;
        while(target.getTargets().size()<target.getMinNumberOfTargets() || target.getNumberOfTargets()==0){
            int choice=learner.choose(game,this,actions);
            if(choice==targets.size() ){
               return added; 
            }
            target.add(targets.get(choice), game);
            actions.remove(choice);
            targets.remove(choice);
            added=true;
            if(target.getTargets().size()==target.getMaxNumberOfTargets()) break;
        }
        return true;
    }
}
