package mage.player.ai.encoder;

import mage.ConditionalMana;
import mage.MageObject;
import mage.Mana;
import mage.abilities.*;
import mage.abilities.costs.Cost;
import mage.abilities.costs.Costs;
import mage.abilities.costs.mana.ManaCost;
import mage.abilities.costs.mana.ManaCosts;
import mage.abilities.effects.ContinuousEffect;
import mage.abilities.effects.ContinuousEffectsList;
import mage.abilities.effects.Effect;
import mage.abilities.keyword.KickerAbility;
import mage.cards.Card;
import mage.cards.Cards;
import mage.constants.CardType;
import mage.constants.SubType;
import mage.constants.Zone;
import mage.counters.Counters;
import mage.game.Exile;
import mage.game.ExileZone;
import mage.game.Game;
import mage.game.Graveyard;
import mage.game.command.CommandObject;
import mage.game.command.Commander;
import mage.game.command.Emblem;
import mage.game.permanent.Battlefield;
import mage.game.permanent.Permanent;
import mage.game.permanent.PermanentCard;
import mage.game.stack.SpellStack;
import mage.game.stack.StackObject;
import mage.players.ManaPool;
import mage.players.Player;
import mage.players.PlayerImpl;
import mage.target.Target;
import mage.target.Targets;
import mage.util.CardUtil;
import mage.watchers.Watcher;
import mage.watchers.common.CastSpellLastTurnWatcher;
import mage.watchers.common.CreatedTokenWatcher;
import mage.watchers.common.PlayerGainedLifeWatcher;
import mage.watchers.common.PlayerLostLifeWatcher;
import org.apache.log4j.Logger;

import java.util.*;
import java.util.stream.Collectors;

/**
 * Global sparse state hasher for deep learning and MCTS validation
 * @author WillWroble
 *
 */
public class StateEncoder {
    public FeatureMap featureMap = new FeatureMap();
    protected static Logger logger = Logger.getLogger(StateEncoder.class);
    public boolean perfectInfo = true;
    private final Features features;
    public Set<Integer> featureVector = new HashSet<>();
    private UUID opponentId;
    private UUID myPlayerId;

    public List<LabeledState>  labeledStates = new ArrayList<>();


    public StateEncoder() {
        features = new Features();
        features.setEncoder(this);
    }
    public void setAgent(UUID me) {
        myPlayerId = me;
    }
    public void setOpponent(UUID op) {
        opponentId = op;
    }
    public synchronized UUID getMyPlayerId() {return myPlayerId;}


    private void processManaCosts(ManaCosts<ManaCost> manaCost, Game game, Features f, String suffix) {
        f.addNumericFeature("ManaValue" + suffix, manaCost.manaValue());
        for(ManaCost mc : manaCost) {
            f.addFeature(mc.getText() + suffix);
        }
    }
    private void processCosts(Costs<Cost> costs, ManaCosts<ManaCost> manaCosts, Game game, Features f) {

        if(manaCosts != null && !manaCosts.isEmpty()) processManaCosts(manaCosts, game, f, "_dynamic");
        if(costs == null || costs.isEmpty()) return;
        for(Cost cc : costs) {
            f.addFeature(cc.getText());
        }
    }
    private void processAbility(Ability a, Game game, Features f) {

        Costs<Cost> c = a.getCosts();
        ManaCosts<ManaCost> mcs = a.getManaCostsToPay();
        if(!c.isEmpty() || !mcs.isEmpty()) {
            processCosts(c, mcs, game, f);
        }
        for(Mode m : a.getModes().getAvailableModes(a, game)) {
            for(Effect e : m.getEffects()) {
                f.parent.addFeature(cleanString(e.getText(m)));//only add feature for abstraction (isn't dynamic)
            }
        }
        //process watchers
        for (Watcher w : a.getWatchers()) {
            if(w.conditionMet()) f.addFeature(w.getKey(), false);
        }
    }
    private void processActivatedAbility(ActivatedAbility aa, Game game, Features f) {

        processAbility(aa, game, f);
        if(aa.isManaAbility()) f.addFeature("ManaAbility");
        try {
            UUID controllerId = aa.getControllerId();
            if (controllerId != null && aa.copy().canActivate(controllerId, game).canActivate()) {
                f.addFeature("CanActivate");
            }
        } catch (Exception e) {
            logger.warn("failed activation check in encoder: " + aa);
        }
    }
    private void processTriggeredAbility(TriggeredAbility ta, Game game, Features f) {

        processAbility(ta, game, f);

        if(!ta.checkTriggeredLimit(game)) f.addFeature("ReachedTriggerLimit");
        if(ta.checkUsedAlready(game)) f.addFeature("UsedAlready");//use ta.checkUsedAlready(game)
        if(ta.getTriggerEvent() != null) f.addFeature(ta.getTriggerEvent().getType().name());

    }
    //encodes only static features of card, see permanents for dynamic feature encoding
    //since all features here are static, they are only encoded for abstraction so just add features directly to parent
    private void processCard(Card c, Game game, Features f) {


        //process counters (suspend is the only non-permanent dynamic feature I can think of)
        Counters counters = c.getCounters(game);
        for (String counterName : counters.keySet()) {
            f.addNumericFeature(counterName, counters.get(counterName).getCount());
        }

        if(!f.passToParent) return;

        f = f.parent;


        f.addFeature("Card");//raw universal type of card added for counting purposes

        if(c.isPermanent()) {
            f.addFeature("Permanent");
        }
        //add types
        for (CardType ct : c.getCardType()) {
            f.addFeature(ct.name());
        }
        //add color
        if(c.getColor().isRed()) f.addFeature("RedCard");
        if(c.getColor().isWhite()) f.addFeature("WhiteCard");
        if(c.getColor().isBlack()) f.addFeature("BlackCard");
        if(c.getColor().isGreen()) f.addFeature("GreenCard");
        if(c.getColor().isBlue()) f.addFeature("BlueCard");
        if(c.getColor().isColorless()) f.addFeature("ColorlessCard");
        if(c.getColor().isMulticolored()) f.addFeature("MultiColored");

        //add subtypes
        for (SubType st : c.getSubtype()) {
            if(!st.name().isEmpty()) f.addFeature(st.name());
        }
        ManaCosts<ManaCost> mc = c.getManaCost();
        processManaCosts(mc, game, f, "");



    }

    private void processPermBattlefield(Permanent p, Game game, UUID playerId, Features f) {

        if(p instanceof PermanentCard) processCardInZone(((PermanentCard) p).getCard(), Zone.BATTLEFIELD, game, f);
        //is tapped?
        if(p.isTapped()) f.addFeature("Tapped");

        //dynamic effects
        for (CardType ct : p.getCardType(game)) {
            f.addFeature(ct.name()+"_dynamic");
        }
        for (SubType st : p.getSubtype(game)) {
            f.addFeature(st.name()+"_dynamic");
        }
        if(p.getColor(game).isRed()) f.addFeature("RedCard_dynamic");
        if(p.getColor(game).isWhite()) f.addFeature("WhiteCard_dynamic");
        if(p.getColor(game).isBlack()) f.addFeature("BlackCard_dynamic");
        if(p.getColor(game).isGreen()) f.addFeature("GreenCard_dynamic");
        if(p.getColor(game).isBlue()) f.addFeature("BlueCard_dynamic");
        if(p.getColor(game).isColorless()) f.addFeature("ColorlessCard_dynamic");
        if(p.getColor(game).isMulticolored()) f.addFeature("MultiColored_dynamic");

        //dynamic abilities
        List<Ability> abilities = p.getAbilities(game);
        if(!abilities.isEmpty()) {
            Features permAbilities = f.getSubFeatures("DynamicPermAbilities", false);
            for(Ability a : abilities) {
                Features permAbility =  permAbilities.getSubFeatures(a.getRule());
                processAbility(a, game, permAbility);
            }
        }

        //process attachments
        List<UUID> attachments = p.getAttachments();
        if(attachments != null && !attachments.isEmpty()) {
            Features attachedFeatures = f.getSubFeatures("attached", false);
            for (UUID id : attachments) {
                Permanent attachment = game.getPermanent(id);
                if(attachment == null) continue;
                //don't pass pooled attachment features up, or they will be counted twice
                Features attachmentFeatures = attachedFeatures.getSubFeatures(attachment.getName());
                processPermBattlefield(attachment, game, playerId, attachmentFeatures);

            }
        }
        //process imprinted
        List<UUID> imprinted = p.getImprinted();
        if(imprinted != null && !imprinted.isEmpty()) {
            Features imprintedFeatures = f.getSubFeatures("imprinted", false);
            for (UUID id : imprinted) {
                Card imprintedCard = game.getCard(id);
                if(imprintedCard == null) continue;
                Features imprintedCardFeatures = imprintedFeatures.getSubFeatures(imprintedCard.getName());
                processCard(imprintedCard, game, imprintedCardFeatures);

            }
        }
        //paired
        Card pairedCard = (Card) p.getPairedCard();
        if(pairedCard != null) {
            Features pairedFeatures = f.getSubFeatures("paired", false);
            processCard(pairedCard, game, pairedFeatures);
        }
        //process special exile zone (oblivion ring effect)
        UUID exileId = CardUtil.getExileZoneId(game, p.getId(), p.getZoneChangeCounter(game));
        ExileZone exileZone = game.getExile().getExileZone(exileId);

        if (exileZone != null) {
            Features exileZoneFeatures = f.getSubFeatures(cleanString(exileZone.getName()), false);
            processExileZone(exileZone, game, exileZoneFeatures);
        }
        // stack objects targeting this
        List<Integer> stackIndices = new ArrayList<>();
        List<StackObject> targetingObjects = getSpellsTargetingPermanent(p, game, stackIndices);
        if(!targetingObjects.isEmpty()) {
            Features targetingFeatures = f.getSubFeatures("TargetedBy", false);
            for (StackObject so : targetingObjects) {
                targetingFeatures.addFeature(cleanString(so.toString()));
                //Features targetingObjectFeatures = targetingFeatures.getSubFeatures(cleanString(so.toString()));
                //processStackObject(so, game, playerId, targetingObjectFeatures);
            }
        }



        //TODO soulbond, banding


        //unique flags
        if(p.isFlipped()) f.addFeature("flipped");
        if(p.isHarnessed()) f.addFeature("harnessed");
        if(p.isSolved()) f.addFeature("solved");
        if(p.isSuspected()) f.addFeature("suspected");
        if(p.isRingBearer()) f.addFeature("RingBearer");
        if(p.isRenowned()) f.addFeature("Renowned");
        if(p.isMonstrous()) f.addFeature("Monstrous");
        if(p.isCloaked()) f.addFeature("Cloaked");
        if(p.isDisguised()) f.addFeature("disguised");
        if(p.isMorphed()) f.addFeature("morphed");
        if(p.isLeftDoorUnlocked()) f.addFeature("Room-LeftDoor");
        if(p.isRightDoorUnlocked()) f.addFeature("Room-RightDoor");


        if(p.isCreature(game)) {
            if(p.hasSummoningSickness()) f.addFeature("SummoningSick");
            if(p.canAttack(game.getOpponent(playerId).getId(), game)) f.addFeature("CanAttack"); //use p.canAttack()
            if(p.canBlockAny(game)) f.addFeature("CanBlock");
            //if(p.hasSummoningSickness()) f.addFeature("SummoningSickness");
            if(p.isAttacking()) {
                f.addFeature("Attacking");
                for(UUID blockerId : game.getCombat().findGroup(p.getId()).getBlockers()) {
                    Permanent blocker  = game.getPermanent(blockerId);
                    f.addFeature(blocker.getName() + " Blocking");
                }
            }
            f.addNumericFeature("Damage", p.getDamage());
            f.addNumericFeature("Power", p.getPower().getValue());
            f.addNumericFeature("Toughness", p.getToughness().getValue());
        }
    }
    private void processCardInZone(Card c, Zone z, Game game, Features f) {

        //process as card (static features)
        processCard(c, game, f);


        Abilities<Ability> allAbilities = c.getAbilities(game);
        //static abilities
        for (StaticAbility sa : allAbilities.getStaticAbilities(z)) {
            Features saFeatures = f.getSubFeatures(sa.getRule());
            processAbility(sa, game, saFeatures);
        }
        //activated abilities
        for(ActivatedAbility aa : allAbilities.getActivatedAbilities(z)) {
            Features aaFeatures = f.getSubFeatures(aa.getRule());
            processActivatedAbility(aa, game, aaFeatures);
        }
        //triggered abilities
        for(TriggeredAbility ta : allAbilities.getTriggeredAbilities(z)) {
            Features taFeatures = f.getSubFeatures(ta.getRule());
            processTriggeredAbility(ta, game, taFeatures);

        }
    }
    private void processBattlefield(Battlefield bf, Game game, Features f, UUID playerId) {
        //sort for deterministic traversal
        TreeMap<String, Permanent> sortedPerms = new TreeMap<>();
        for(Permanent p : bf.getAllActivePermanents(playerId)) {
            sortedPerms.put(p.getValue(game, playerId), p);
        }
        for (Permanent p : sortedPerms.values()) {
            Features permFeatures = f.getSubFeatures(p.getName());
            processPermBattlefield(p, game, playerId, permFeatures);
        }
    }
    private void processGraveyard(Graveyard gy, Game game, Features f) {
        for (Card c : gy.getCardsSorted(game)) {
            Features graveCardFeatures = f.getSubFeatures(c.getName(), true, c.getId());
            processCardInZone(c, Zone.GRAVEYARD, game, graveCardFeatures);
        }
    }
    private void processHand(Cards hand, Game game, Features f) {
        for (Card c : hand.getCardsSorted(game)) {
            Features handCardFeatures = f.getSubFeatures(c.getName(), true, c.getId());
            processCardInZone(c, Zone.HAND, game, handCardFeatures);
        }
    }
    private void processStackObject(StackObject so, Game game, UUID playerId, Features f) {

        if(so.getControllerId().equals(playerId)) f.addFeature("isController");
        Ability sa = so.getStackAbility();
        //abstract only since non-dynamic
        f.parent.addFeature(sa.getRule());

        Targets myTargets = sa.getTargets();
        if(!myTargets.isEmpty()) {
            Features targetsFeatures = f.getSubFeatures("targets", false);
            for (Target target : myTargets) {
                for (UUID id : target.getTargets()) {
                    Features targetFeatures = targetsFeatures.getSubFeatures(game.getEntityName(id, playerId));
                    //Features targetFeatures = targetsFeatures.getSubFeatures(features.getNameFromUUID(id));
                    Card c = game.getCard(id);
                    if (c != null) {
                        processCard(c, game, targetFeatures);
                    }
                }
            }
        }
        //kicker
        int totalKicks = KickerAbility.getKickedCounter(game, sa);
        f.addNumericFeature("Kicks", totalKicks, false);
        //cost tags
        Map<String, Object> tags = CardUtil.getSourceCostsTagsMap(game, sa);
        if (tags != null && !tags.isEmpty()) {
            for(String tag : tags.keySet()) {
                Object v = tags.get(tag);
                f.addNumericFeature(tag + "_CostTag", (v instanceof Integer) ? (Integer) v : 1);
            }
        }
        //modes
        List<UUID> selectedModes = sa.getModes().getSelectedModes();
        if (selectedModes != null && !selectedModes.isEmpty() && sa.getModes().size()>1) {
            Features modesFeatures = f.getSubFeatures("modes", false);
            //selected modes
            for(UUID id : selectedModes) {
                Mode m = sa.getModes().get(id);
                for(Effect e : m.getEffects()) {
                    modesFeatures.addFeature(cleanString(e.getText(m)));
                }
            }
        }
        //variable cost
        int xValue = CardUtil.getSourceCostsTag(game, sa, "X", 0);
        f.addNumericFeature("XValue", xValue, false);

        if(sa instanceof TriggeredAbility) {
            processTriggeredAbility((TriggeredAbility) sa, game, f);
        } else {
            processActivatedAbility((ActivatedAbility)sa, game, f);
            if(sa instanceof SpellAbility) {
                MageObject source = game.getObject(so.getSourceId());
                for (Ability a : source.getAbilities().getStaticAbilities(Zone.STACK)) {
                    f.addFeature(a.toString());
                }
            }
        }
    }
    private void processStack(SpellStack stack, Game game, UUID playerId, Features f) {
        Iterator<StackObject> itr = stack.iterator();
        StackObject so;
        int depth=0;
        while(itr.hasNext()) {
            depth++;
            so = itr.next();
            Features soFeatures = f.getSubFeatures(cleanString(so.toString()));
            soFeatures.addNumericFeature("Depth", depth, false);
            processStackObject(so, game, playerId, soFeatures);
        }
    }
    private void processExileZone(ExileZone exileZone, Game game, Features f) {
        for (Card c : exileZone.getCardsSorted(game)) {
            Features exileCardFeatures = f.getSubFeatures(c.getName());
            processCardInZone(c, Zone.EXILED, game, exileCardFeatures);
        }
    }
    private void processExile(Exile exile, Game game, Features f) {

        for (ExileZone ez : exile.getExileZones()) {
            Features exileZoneFeatures = f.getSubFeatures(cleanString(ez.getName()));
            processExileZone(ez, game, exileZoneFeatures);
        }
    }
    private void processMana(Mana mana, Game game, Features f) {
        f.addNumericFeature("GreenMana", mana.getGreen());
        f.addNumericFeature("RedMana", mana.getRed());
        f.addNumericFeature("BlueMana", mana.getBlue());
        f.addNumericFeature("WhiteMana", mana.getWhite());
        f.addNumericFeature("BlackMana", mana.getBlack());
        f.addNumericFeature("ColorlessMana", mana.getColorless());
    }
    private void processManaPool(ManaPool mp, Game game,  Features f) {
        processMana(mp.getMana(), game, f);

        List<ConditionalMana> conditionalMana = mp.getConditionalMana();
        if(conditionalMana != null && !conditionalMana.isEmpty()) {
            Features conditionalManaFeatures = f.getSubFeatures("ConditionalMana", false);
            for(ConditionalMana condMana : conditionalMana) {
                Features cmFeatures = conditionalManaFeatures.getSubFeatures(condMana.getConditionString());
                processMana(condMana, game, cmFeatures);
            }
        }
    }
    private void processCommandZone(Game game, UUID playerId, Features f) {
        // Command zone
        for (CommandObject co : game.getState().getCommand()) {
            if (co instanceof Emblem) {
                Emblem emblem = (Emblem) co;
                if (playerId.equals(emblem.getControllerId())) {
                    Features emblemFeatures = f.getSubFeatures(emblem.getName());
                    emblemFeatures.addFeature("Emblem");
                    // Emblems mainly have continuous/static abilities
                    for (Ability a : emblem.getAbilities()) {
                        Features aFeatures = emblemFeatures.getSubFeatures(a.getRule());
                        processAbility(a, game, aFeatures);
                    }
                }
            }
            if(co instanceof Commander) {
                Commander commander = (Commander) co;
                Features commanderFeatures = f.getSubFeatures("Commander");
                commanderFeatures.addFeature(commander.getName());
                processCard(commander.getSourceObject(), game, commanderFeatures);
                for (Ability a : commander.getAbilities()) {
                    Features aFeatures = commanderFeatures.getSubFeatures(a.getRule());
                    processAbility(a, game, aFeatures);
                }
            }
        }

        // Helper emblems (some emblems can be mirrored here)
        for (Emblem emblem : game.getState().getHelperEmblems()) {
            if (playerId.equals(emblem.getControllerId())) {
                Features eFeatures = f.getSubFeatures(emblem.getName());
                eFeatures.addFeature("Emblem");
                for (Ability a : emblem.getAbilities()) {
                    Features aFeatures = eFeatures.getSubFeatures(a.getRule());
                    processAbility(a, game, aFeatures);
                }
            }
        }
        //TODO: companions
    }
    private void processWatchers(Game game, UUID playerId, Features f) {
        // Storm / spells cast counts
        CastSpellLastTurnWatcher stormW = game.getState().getWatcher(CastSpellLastTurnWatcher.class);
        if (stormW != null) {
            f.addNumericFeature("SpellsCastThisTurn", stormW.getAmountOfSpellsPlayerCastOnCurrentTurn(playerId));
        }
        // Life gained this turn
        PlayerGainedLifeWatcher lifeW = game.getState().getWatcher(PlayerGainedLifeWatcher.class);
        if (lifeW != null) {
            f.addNumericFeature("LifeGainedThisTurn", lifeW.getLifeGained(playerId));
        }
        // Life lost this turn
        PlayerLostLifeWatcher lossW = game.getState().getWatcher(PlayerLostLifeWatcher.class);
        if (lossW != null) {
            f.addNumericFeature("LifeLostThisTurn", lossW.getLifeLost(playerId));
        }
        // Tokens created this turn
        CreatedTokenWatcher tokenW = game.getState().getWatcher(CreatedTokenWatcher.class);
        if (tokenW != null) {
            f.addNumericFeature("TokensCreatedThisTurn", CreatedTokenWatcher.getPlayerCount(playerId, game));
        }
    }
    private void processMicroDecisions(Game game, UUID playerId, Features f) {
        Player myPlayer = game.getPlayer(playerId);
        //current targets selected for when it's in the middle selecting multiple targets
        Features chosenTargetsFeatures = f.getSubFeatures("ChosenTargets", false);
        for(UUID targetID : myPlayer.getPlayerHistory().targetSequence) {
            chosenTargetsFeatures = chosenTargetsFeatures.getSubFeatures(game.getEntityName(targetID, playerId));
        }
        //current choices selected for when it's in the middle selecting multiple choices
        Features choiceFeatures = f.getSubFeatures("ChosenChoices", false);
        for(String choice : myPlayer.getPlayerHistory().choiceSequence) {
            choiceFeatures = choiceFeatures.getSubFeatures(choice);
        }
        //current choices selected for when it's in the middle selecting multiple choices
        Features useFeatures = f.getSubFeatures("UseChoices", false);
        for(Boolean use : myPlayer.getPlayerHistory().useSequence) {
            useFeatures = useFeatures.getSubFeatures(use.toString());
        }
        //current choices selected for when it's in the middle selecting multiple choices
        Features amountFeatures = f.getSubFeatures("AmountChoices", false);
        for(Integer num : myPlayer.getPlayerHistory().numSequence) {
            amountFeatures = amountFeatures.getSubFeatures(num.toString());
        }
    }
    private void processPlayer(Game game, UUID playerId, UUID decisionPlayerId, Features f) {
        Player myPlayer = game.getPlayer(playerId);

        if(myPlayer.isInPayManaMode()) f.addFeature("InPayManaMode", false);
        if(((PlayerImpl)myPlayer).isActivating) f.addFeature("Activating", false);

        //micro decision state
        processMicroDecisions(game, playerId, f);




        if(game.isActivePlayer(playerId)) f.addFeature("IsActivePlayer");
        if(decisionPlayerId.equals(playerId)) f.addFeature("IsDecisionPlayer");
        f.addNumericFeature("LifeTotal", myPlayer.getLife());
        if(myPlayer.canPlayLand()) f.addFeature("CanPlayLand");
        if(game.hasDayNight()) {
            if(game.checkDayNight(true)) {
                f.addFeature("DayTime");
            }
            if(game.checkDayNight(false)) {
                f.addFeature("NightTime");
            }
        }

        //library
        f.addNumericFeature("LibraryCount", myPlayer.getLibrary().size());
        //TODO: revealed cards

        //attachments
        List<UUID> attachments = myPlayer.getAttachments();
        if(attachments != null && !attachments.isEmpty()) {
            Features attachmentsFeatures = f.getSubFeatures("Attachments", false);
            for(UUID id : attachments) {
                processPermBattlefield(game.getPermanent(id), game, playerId, attachmentsFeatures);
            }
        }
        //counters
        Counters counters = myPlayer.getCountersAsCopy();
        for (String counterName : counters.keySet()) {
            f.addNumericFeature(counterName, counters.get(counterName).getCount());
        }

        //mana pool
        Features mpFeatures = f.getSubFeatures("ManaPool", false);
        processManaPool(myPlayer.getManaPool(), game, mpFeatures);

        //battlefield
        Battlefield bf = game.getBattlefield();
        Features bfFeatures = f.getSubFeatures("Battlefield");
        processBattlefield(bf, game, bfFeatures, playerId);

        //graveyard
        Graveyard gy = myPlayer.getGraveyard();
        Features gyFeatures = f.getSubFeatures("Graveyard");
        processGraveyard(gy, game, gyFeatures);

        //hand
        if(playerId==decisionPlayerId || perfectInfo) { //keep perspective
            Cards hand = myPlayer.getHand();
            Features handFeatures = f.getSubFeatures("Hand");
            processHand(hand, game, handFeatures);
        } else {
            Cards hand = myPlayer.getHand();
            f.addNumericFeature("CardsInHand", hand.size());
        }
        //command zone
        Features commandZoneFeatures = f.getSubFeatures("CommandZone", false);
        processCommandZone(game, playerId, commandZoneFeatures);

        //global watchers
        Features globalWatcherFeatures = f.getSubFeatures("GlobalWatchers", false);
        processWatchers(game, playerId, globalWatcherFeatures);


        //TODO dungeons
    }
    /**
     * vectorizes (hashes) the entire game state in a neural network-learnable way. These vectors are massive and sparse -
     * they are designed to have redundant features masked before training and used with a massive embedding bag in pytorch
     * @param game S
     * @param decisionPlayerId the player who is making the decision at this state
     * @param decisionType type of decision being made at this state (choose_target, choose_use, choose etc.)
     * @param decisionsText informative context about the micro decision being made to be hashed as its own feature for the network
     * @return set of active indices in the sparse binary vector
     */
    public synchronized Set<Integer> processState(Game game, UUID decisionPlayerId, ActionEncoder.ActionType decisionType, String decisionsText) {
        features.stateRefresh();
        featureVector.clear();

        //globals
        if(game.getPhase() != null) {
            features.addFeature(game.getTurnStepType().toString()); //phases
        }

        //decision type
        features.addFeature(decisionType.toString());
        //decision state
        features.addFeature(cleanString(decisionsText));



        //stack
        Features stackFeatures = features.getSubFeatures("Stack", false);
        processStack(game.getStack(), game, myPlayerId, stackFeatures);

        //exiled
        Features exileFeatures = features.getSubFeatures("Exile");
        processExile(game.getExile(), game, exileFeatures);

        //each player

        //PlayerA
        Features playerFeatures = features.getSubFeatures("Player");
        processPlayer(game, myPlayerId, decisionPlayerId, playerFeatures);
        //PlayerB
        Features opponentFeatures = features.getSubFeatures("Opponent");
        processPlayer(game, opponentId, decisionPlayerId, opponentFeatures);


        return new HashSet<>(featureVector);

    }

    public synchronized Set<Integer> processState(Game game, UUID actingPlayerID) {
        return processState(game, actingPlayerID, ActionEncoder.ActionType.PRIORITY,"priority");
    }

    public void addLabeledState(Set<Integer> stateVector, int[] actionVector, double score, ActionEncoder.ActionType actionType, boolean isPlayer) {
        LabeledState newState = new LabeledState(stateVector, actionVector, score, actionType, isPlayer);
        labeledStates.add(newState);
    }


    /**
     * removes all instances of UUIDs for consistent hashing
     * @param input nondeterministic string with UUIDs
     * @return deterministic cleaned strings
     */
    public static String cleanString (String input) {
        if (input == null || input.isEmpty()) {
            return input;
        }
        String cleaned = input.replaceAll(" \\[[0-9a-f]+]", "");
        cleaned = cleaned.replaceAll("<[^>]*>", "");
        return cleaned;
    }

    /**
     * returns stack objects targeting given permanent. and populates a list of stackIndices that correspond to
     * those object's stack positions
     * @param permanent
     * @param game
     * @param stackIndices is populated
     * @return
     */
    public static List<StackObject> getSpellsTargetingPermanent(Permanent permanent, Game game, List<Integer> stackIndices) {
        List<StackObject> result = new ArrayList<>();
        int index = 1;
        if (permanent == null) return result;

        for (Iterator<StackObject> it = game.getStack().descendingIterator(); it.hasNext(); ) {
            StackObject so = it.next();
            Ability sa = so.getStackAbility();
            if (sa == null) continue;

            Targets targets = sa.getTargets();
            if (targets == null || targets.isEmpty()) continue;

            boolean targetsThisPerm = targets
                    .stream()
                    .anyMatch(t -> t.getTargets().contains(permanent.getId()));

            if (targetsThisPerm) {
                result.add(so);
                stackIndices.add(index);
            }
            index++;
        }
        return result;
    }
}
