package mage.player.ai;

import mage.abilities.*;
import mage.abilities.common.PassAbility;
import mage.abilities.costs.mana.GenericManaCost;
import mage.cards.Card;
import mage.cards.Cards;
import mage.cards.t.TrueLovesKiss;
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
import mage.player.ai.RLAgent.*;
import java.io.*;
import mage.abilities.*;
import org.json.simple.JSONObject;
import org.json.simple.JSONValue;
import org.json.simple.JSONArray;

/**
 * uses a reinforcement learning based AI
 *
 * @author Elchanan Haas
 */

public class RLPlayer extends RandomNonTappingPlayer{
    public RLAgent learner;
    private static final Logger logger = Logger.getLogger(RLPlayer.class);
    public RLPlayer(String name , RangeOfInfluence range, int skill){
        super(name);
        System.out.println("Completed super!");
        logger.warn("completed super!");
        PyConnection conn=new PyConnection(5009);
        learner=new RLPyAgent(conn);
        if(Objects.isNull(learner)){
            logger.warn("learner is null in RLPlayer creation!");
        }
        System.out.println("Created RL Player!");
    }
    public RLPlayer(String name,RLAgent inLearner) {  
        super(name);
        learner=inLearner;
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
    private Ability chooseAbility(Game game, List<Ability> options){
        Ability ability=pass;
        List<String> names=new ArrayList<String>();
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
                }  
            }
            int choice;
            if (names.size() == 1) { //Don't call ML model for single element
                choice=0;
                //logger.info("Turn"+game.getTurnNum()+" handSize "+getHand().size());
            } else {
                //logger.info("Calling model"+game.getTurnNum());
                JSONArray list = new JSONArray();
                for(int i=0;i<names.size();i++){
                    JSONObject actRepr=new JSONObject();
                    actRepr.put("action",names.get(i));
                    list.add(actRepr);
                }
                choice=learner.choose(game,this,list);

            }
            ability = options.get(choice);
        }
        return ability;
    }

    @Override
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
    public void selectAttackers(Game game, UUID attackingPlayerId) { //Recorded by AI now!
        //logger.info("life total of " + getName() +" is "+getLife());
        UUID defenderId = game.getOpponents(playerId).iterator().next();
        List<Permanent> attackersList = super.getAvailableAttackers(defenderId, game);
        for(int i=0;i<attackersList.size();i++){
            Permanent attacker=attackersList.get(i);
            JSONObject toAttack=new JSONObject();
            JSONObject noAttack=new JSONObject();
            toAttack.put("attack","Attack:"+attacker.getName());
            noAttack.put("attack","NoAttack");
            JSONArray list = new JSONArray();
            list.add(noAttack);
            list.add(toAttack);
            int index=learner.choose(game,this,list);
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
        JSONObject yesMull=new JSONObject();
        JSONObject noMull=new JSONObject();
        yesMull.put("action","yesMulligan");
        noMull.put("action","noMulligan");
        JSONArray list = new JSONArray();
        list.add(noMull);
        list.add(yesMull);
        int index=learner.choose(game,this,list);
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
            JSONArray list = new JSONArray();
            List<CombatGroup> groups=game.getCombat().getGroups();
            for(int i=0;i<numGroups;i++){
                List<UUID> groupIDs=groups.get(i).getAttackers();
                if(groupIDs.size()>0){
                    UUID attacker=groupIDs.get(0);
                    JSONObject actRepr=new JSONObject();
                    actRepr.put("attacker","BlockAttacker:"+game.getPermanent(attacker).getName());
                    actRepr.put("blocker","Blocker:"+blocker.getName()); 
                    list.add(actRepr);
                }
            }
            JSONObject actRepr=new JSONObject();
            actRepr.put("attacker","NoAttackerToBlock");
            actRepr.put("blocker","Blocker:"+blocker.getName()); 
            list.add(actRepr);
            int choice=learner.choose(game,this,list);

            if (choice<list.size()-1) {
                CombatGroup group = groups.get(choice);
                if (!group.getAttackers().isEmpty()) {
                    this.declareBlocker(this.getId(), blocker.getId(), group.getAttackers().get(0), game);
                }
            }
        }
        actionCount++;
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
        List<UUID> targets=new ArrayList<UUID>(targetSet);
        if (target.isRequired(source) && targets.size() == 1) {
            target.addTarget(targets.get(0), source, game); // todo: addtryaddtarget or return type (see computerPlayer)
            return true;
        }
        JSONArray list = new JSONArray();
        for(int i=0;i<targets.size();i++){
            JSONObject actRepr=new JSONObject();
            UUID thingId=targets.get(i);
            String name="Target:"+nameUUID(thingId, game);
            String causeName;
            if(source!=null){
                MageObject cause=source.getSourceObject(game);
                causeName="TargetAbility:"+cause.getName();
            }
            else{
                causeName="TargetAbility:None";
            }
            actRepr.put("target",name);
            actRepr.put("ability",causeName);
            list.add(actRepr); 
        }
        if (!target.isRequired(source)) {
            JSONObject actRepr=new JSONObject();
            actRepr.put("target","Target:None");
            actRepr.put("ability","TargetAbility:None");
            list.add(actRepr);
        }
        int choice=learner.choose(game,this,list);
        if(choice==targets.size()){
            return false;
        }
        target.addTarget(targets.get(choice), source, game);
        return true;
    }


    @Override
    public boolean choose(Outcome outcome, Target target, UUID sourceId, Game game) {
        //System.out.println("Chose target with source");
        Set<UUID> targetSet = target.possibleTargets(playerId, game);
        if (targetSet.isEmpty()) {
            return false;
        }
        List<UUID> targets=new ArrayList<UUID>(targetSet);
        if (targets.size() == 1) {
            target.add(targets.get(0), game); // todo: addtryaddtarget or return type (see computerPlayer)
            return true;
        }
        JSONArray list = new JSONArray();
        for(int i=0;i<targets.size();i++){
            JSONObject actRepr=new JSONObject();
            UUID thingId=targets.get(i);
            String name="Target:"+nameUUID(thingId, game);
            String causeName;
            causeName="TargetSource:"+nameUUID(sourceId, game);
            actRepr.put("target",name);
            actRepr.put("ability",causeName);
            list.add(actRepr); 
        }
        int choice=learner.choose(game,this,list);
        target.add(targets.get(choice), game);
        return true;
    }
}
