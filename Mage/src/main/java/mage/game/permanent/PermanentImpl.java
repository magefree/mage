package mage.game.permanent;

import mage.ApprovingObject;
import mage.MageObject;
import mage.MageObjectReference;
import mage.ObjectColor;
import mage.abilities.Abilities;
import mage.abilities.Ability;
import mage.abilities.SpellAbility;
import mage.abilities.effects.ContinuousEffect;
import mage.abilities.effects.Effect;
import mage.abilities.effects.RequirementEffect;
import mage.abilities.effects.RestrictionEffect;
import mage.abilities.effects.common.RegenerateSourceEffect;
import mage.abilities.hint.Hint;
import mage.abilities.hint.HintUtils;
import mage.abilities.keyword.*;
import mage.cards.Card;
import mage.cards.CardImpl;
import mage.constants.*;
import mage.counters.Counter;
import mage.counters.CounterType;
import mage.counters.Counters;
import mage.game.Game;
import mage.game.GameState;
import mage.game.ZoneChangeInfo;
import mage.game.ZonesHandler;
import mage.game.combat.CombatGroup;
import mage.game.command.CommandObject;
import mage.game.events.*;
import mage.game.events.GameEvent.EventType;
import mage.game.permanent.token.SquirrelToken;
import mage.game.stack.Spell;
import mage.game.stack.StackObject;
import mage.players.Player;
import mage.target.TargetCard;
import mage.util.CardUtil;
import mage.util.GameLog;
import mage.util.ThreadLocalStringBuilder;
import org.apache.log4j.Logger;

import java.io.Serializable;
import java.util.*;

/**
 * @author BetaSteward_at_googlemail.com
 */
public abstract class PermanentImpl extends CardImpl implements Permanent {

    private static final Logger logger = Logger.getLogger(PermanentImpl.class);

    private static class MarkedDamageInfo implements Serializable {

        private final Counter counter;
        private final MageObject sourceObject;
        private final boolean addCounters;

        private MarkedDamageInfo(Counter counter, MageObject sourceObject, boolean addCounters) {
            this.counter = counter;
            this.sourceObject = sourceObject;
            this.addCounters = addCounters;
        }
    }

    private static final ThreadLocalStringBuilder threadLocalBuilder = new ThreadLocalStringBuilder(300);

    protected boolean tapped;
    protected boolean flipped;
    protected boolean transformed;
    protected boolean monstrous;
    protected boolean renowned;
    protected boolean manifested = false;
    protected boolean morphed = false;
    protected int classLevel = 1;
    protected final Set<UUID> goadingPlayers = new HashSet<>();
    protected UUID originalControllerId;
    protected UUID controllerId;
    protected UUID beforeResetControllerId;
    protected int damage;
    protected boolean controlledFromStartOfControllerTurn;
    protected int turnsOnBattlefield;
    protected boolean phasedIn = true;
    protected boolean indirectPhase = false;
    protected boolean faceDown;
    protected boolean attacking;
    protected int blocking;
    // number of creatures the permanent can block
    protected int maxBlocks = 1;
    // minimal number of creatures the creature can be blocked by
    protected int minBlockedBy = 1;
    // maximal number of creatures the creature can be blocked by  0 = no restriction
    protected int maxBlockedBy = 0;
    protected boolean deathtouched;

    protected Map<String, List<UUID>> connectedCards = new HashMap<>();
    protected Set<MageObjectReference> dealtDamageByThisTurn;
    protected UUID attachedTo;
    protected int attachedToZoneChangeCounter;
    protected MageObjectReference pairedPermanent;
    protected List<UUID> bandedCards = new ArrayList<>();
    protected Counters counters;
    protected List<MarkedDamageInfo> markedDamage;
    protected int markedLifelink;
    protected int timesLoyaltyUsed = 0;
    protected int loyaltyActivationsAvailable = 1;
    protected int transformCount = 0;
    protected Map<String, String> info;
    protected int createOrder;
    protected boolean legendRuleApplies = true;

    private static final List<UUID> emptyList = Collections.unmodifiableList(new ArrayList<UUID>());

    public PermanentImpl(UUID ownerId, UUID controllerId, String name) {
        super(ownerId, name);
        this.originalControllerId = controllerId;
        this.controllerId = controllerId;
        this.counters = new Counters();
    }

    public PermanentImpl(UUID id, UUID ownerId, UUID controllerId, String name) {
        super(id, ownerId, name);
        this.originalControllerId = controllerId;
        this.controllerId = controllerId;
        this.counters = new Counters();
    }

    public PermanentImpl(final PermanentImpl permanent) {
        super(permanent);
        this.tapped = permanent.tapped;
        this.flipped = permanent.flipped;
        this.originalControllerId = permanent.originalControllerId;
        this.controllerId = permanent.controllerId;
        this.damage = permanent.damage;
        this.controlledFromStartOfControllerTurn = permanent.controlledFromStartOfControllerTurn;
        this.turnsOnBattlefield = permanent.turnsOnBattlefield;
        this.phasedIn = permanent.phasedIn;
        this.indirectPhase = permanent.indirectPhase;
        this.faceDown = permanent.faceDown;
        this.attacking = permanent.attacking;
        this.blocking = permanent.blocking;
        this.maxBlocks = permanent.maxBlocks;
        this.deathtouched = permanent.deathtouched;
        this.markedLifelink = permanent.markedLifelink;

        for (Map.Entry<String, List<UUID>> entry : permanent.connectedCards.entrySet()) {
            this.connectedCards.put(entry.getKey(), entry.getValue());
        }
        if (permanent.dealtDamageByThisTurn != null) {
            dealtDamageByThisTurn = new HashSet<>(permanent.dealtDamageByThisTurn);
        }
        if (permanent.markedDamage != null) {
            markedDamage = new ArrayList<>();
            for (MarkedDamageInfo mdi : permanent.markedDamage) {
                markedDamage.add(new MarkedDamageInfo(mdi.counter.copy(), mdi.sourceObject, mdi.addCounters));
            }
        }
        if (permanent.info != null) {
            info = new HashMap<>();
            info.putAll(permanent.info);
        }
        this.counters = permanent.counters.copy();
        this.attachedTo = permanent.attachedTo;
        this.attachedToZoneChangeCounter = permanent.attachedToZoneChangeCounter;
        this.minBlockedBy = permanent.minBlockedBy;
        this.maxBlockedBy = permanent.maxBlockedBy;
        this.transformed = permanent.transformed;
        this.monstrous = permanent.monstrous;
        this.renowned = permanent.renowned;
        this.classLevel = permanent.classLevel;
        this.goadingPlayers.addAll(permanent.goadingPlayers);
        this.pairedPermanent = permanent.pairedPermanent;
        this.bandedCards.addAll(permanent.bandedCards);
        this.timesLoyaltyUsed = permanent.timesLoyaltyUsed;
        this.loyaltyActivationsAvailable = permanent.loyaltyActivationsAvailable;
        this.legendRuleApplies = permanent.legendRuleApplies;
        this.transformCount = permanent.transformCount;

        this.morphed = permanent.morphed;
        this.manifested = permanent.manifested;
        this.createOrder = permanent.createOrder;
    }

    @Override
    public String toString() {
        StringBuilder sb = threadLocalBuilder.get();
        sb.append(this.getName()).append('-').append(this.expansionSetCode);
        if (copy) {
            sb.append(" [Copy]");
        }
        return sb.toString();
    }

    @Override
    public void setControllerId(UUID controllerId) {
        this.controllerId = controllerId;
        abilities.setControllerId(controllerId);
    }

    @Override
    public void setOriginalControllerId(UUID originalControllerId) {
        this.originalControllerId = originalControllerId;
    }

    /**
     * Called before each applyEffects or if after a permanent was copied for
     * the copied object
     *
     * @param game
     */
    @Override
    public void reset(Game game) {
        this.resetControl();
        this.maxBlocks = 1;
        this.minBlockedBy = 1;
        this.maxBlockedBy = 0;
        this.copy = false;
        this.goadingPlayers.clear();
        this.loyaltyActivationsAvailable = 1;
        this.legendRuleApplies = true;
    }

    @Override
    public String getName() {
        if (name.isEmpty()) {
            if (faceDown) {
                return EmptyNames.FACE_DOWN_CREATURE.toString();
            } else {
                return "";
            }
        } else {
            return name;
        }
    }

    @Override
    public String getValue(GameState state) {
        StringBuilder sb = threadLocalBuilder.get();
        sb.append(controllerId).append(getName()).append(tapped).append(damage);
        sb.append(subtype).append(supertype).append(power.getValue()).append(toughness.getValue());
        sb.append(abilities.getValue());
        for (Counter counter : getCounters(state).values()) {
            sb.append(counter.getName()).append(counter.getCount());
        }
        return sb.toString();
    }

    @Override
    public void addInfo(String key, String value, Game game) {
        if (info == null) {
            info = new HashMap<>();
        }
        if (value == null || value.isEmpty()) {
            info.remove(key);
        } else {
            info.put(key, value);
        }
    }

    /**
     * @param game can be null, e.g. for cards viewer
     * @return
     */
    @Override
    public List<String> getRules(Game game) {
        try {
            List<String> rules = super.getRules(game);

            // add additional data for GUI

            // info
            if (info != null) {
                rules.addAll(info.values());
            }

            // ability hints
            List<String> abilityHints = new ArrayList<>();
            if (HintUtils.ABILITY_HINTS_ENABLE) {
                for (Ability ability : getAbilities(game)) {
                    for (Hint hint : ability.getHints()) {
                        String s = hint.getText(game, ability);
                        if (s != null && !s.isEmpty()) {
                            abilityHints.add(s);
                        }
                    }
                }
            }

            // restrict hints
            List<String> restrictHints = new ArrayList<>();
            if (game != null && HintUtils.RESTRICT_HINTS_ENABLE) {
                // restrict
                for (Map.Entry<RestrictionEffect, Set<Ability>> entry : game.getContinuousEffects().getApplicableRestrictionEffects(this, game).entrySet()) {
                    for (Ability ability : entry.getValue()) {
                        if (!entry.getKey().canAttack(game, false) || !entry.getKey().canAttack(this, null, ability, game, false)) {
                            restrictHints.add(HintUtils.prepareText("Can't attack" + addSourceObjectName(game, ability), null, HintUtils.HINT_ICON_RESTRICT));
                        }
                        if (!entry.getKey().canBlock(null, this, ability, game, false)) {
                            restrictHints.add(HintUtils.prepareText("Can't block" + addSourceObjectName(game, ability), null, HintUtils.HINT_ICON_RESTRICT));
                        }
                        if (!entry.getKey().canBeUntapped(this, ability, game, false)) {
                            restrictHints.add(HintUtils.prepareText("Can't untapped" + addSourceObjectName(game, ability), null, HintUtils.HINT_ICON_RESTRICT));
                        }
                        if (!entry.getKey().canUseActivatedAbilities(this, ability, game, false)) {
                            restrictHints.add(HintUtils.prepareText("Can't use activated abilities" + addSourceObjectName(game, ability), null, HintUtils.HINT_ICON_RESTRICT));
                        }
                        if (!entry.getKey().canTransform(game, false)) {
                            restrictHints.add(HintUtils.prepareText("Can't transform" + addSourceObjectName(game, ability), null, HintUtils.HINT_ICON_RESTRICT));
                        }
                    }
                }

                // requirement
                for (Map.Entry<RequirementEffect, Set<Ability>> entry : game.getContinuousEffects().getApplicableRequirementEffects(this, false, game).entrySet()) {
                    for (Ability ability : entry.getValue()) {
                        if (entry.getKey().mustAttack(game)) {
                            restrictHints.add(HintUtils.prepareText("Must attack" + addSourceObjectName(game, ability), null, HintUtils.HINT_ICON_REQUIRE));
                        }
                        if (entry.getKey().mustBlock(game)) {
                            restrictHints.add(HintUtils.prepareText("Must block" + addSourceObjectName(game, ability), null, HintUtils.HINT_ICON_REQUIRE));
                        }
                        if (entry.getKey().mustBlockAny(game)) {
                            restrictHints.add(HintUtils.prepareText("Must block any" + addSourceObjectName(game, ability), null, HintUtils.HINT_ICON_REQUIRE));
                        }
                        if (entry.getKey().mustBlockAllAttackers(game)) {
                            restrictHints.add(HintUtils.prepareText("Must block all attackers" + addSourceObjectName(game, ability), null, HintUtils.HINT_ICON_REQUIRE));
                        }

                        MageObject object = game.getObject(entry.getKey().mustAttackDefender(ability, game));
                        if (object != null) {
                            restrictHints.add(HintUtils.prepareText("Must attack defender " + object.getLogName() + addSourceObjectName(game, ability), null, HintUtils.HINT_ICON_REQUIRE));
                        }
                        object = game.getObject(entry.getKey().mustBlockAttacker(ability, game));
                        if (object != null) {
                            restrictHints.add(HintUtils.prepareText("Must block attacker " + object.getLogName() + addSourceObjectName(game, ability), null, HintUtils.HINT_ICON_REQUIRE));
                        }
                        object = game.getObject(entry.getKey().mustBlockAttackerIfElseUnblocked(ability, game));
                        if (object != null) {
                            restrictHints.add(HintUtils.prepareText("Must block attacker if able " + object.getLogName() + addSourceObjectName(game, ability), null, HintUtils.HINT_ICON_REQUIRE));
                        }
                    }
                }

                restrictHints.sort(String::compareTo);
            }

            // total hints
            if (!abilityHints.isEmpty() || !restrictHints.isEmpty()) {
                rules.add(HintUtils.HINT_START_MARK);
                HintUtils.appendHints(rules, abilityHints);
                HintUtils.appendHints(rules, restrictHints);
            }

            return rules;
        } catch (Exception e) {
            return CardUtil.RULES_ERROR_INFO;
        }
    }

    private String addSourceObjectName(Game game, Ability ability) {
        if (ability != null) {
            MageObject object = game.getObject(ability.getSourceId());
            if (object != null) {
                return " (" + object.getIdName() + ")";
            }
        }
        return "";
    }

    @Override
    public Abilities<Ability> getAbilities() {
        return super.getAbilities();
    }

    @Override
    public Abilities<Ability> getAbilities(Game game) {
        return super.getAbilities(game);
    }

    @Override
    public void addAbility(Ability ability, UUID sourceId, Game game) {
        // singleton abilities -- only one instance
        // other abilities -- any amount of instances
        if (!abilities.containsKey(ability.getId())) {
            Ability copyAbility = ability.copy();
            copyAbility.newId(); // needed so that source can get an ability multiple times (e.g. Raging Ravine)
            copyAbility.setControllerId(controllerId);
            copyAbility.setSourceId(objectId);
            // triggered abilities must be added to the state().triggers
            // still as long as the prev. permanent is known to the LKI (e.g. Showstopper) so gained dies triggered ability will trigger
            if (game != null) {
                // game is null in cards viewer window (MageBook)
                game.getState().addAbility(copyAbility, sourceId, this);
            }
            abilities.add(copyAbility);
            abilities.addAll(ability.getSubAbilities());
        }
    }

    @Override
    public void removeAllAbilities(UUID sourceId, Game game) {
        // TODO: what about triggered abilities? See addAbility above -- triggers adds to GameState
        abilities.clear();
    }

    @Override
    public void removeAbility(Ability abilityToRemove, UUID sourceId, Game game) {
        if (abilityToRemove == null) {
            return;
        }

        // 112.10b  Effects that remove an ability remove all instances of it.
        List<Ability> toRemove = new ArrayList<>();
        abilities.forEach(a -> {
            if (a.isSameInstance(abilityToRemove)) {
                toRemove.add(a);
            }
        });

        // TODO: what about triggered abilities? See addAbility above -- triggers adds to GameState
        toRemove.forEach(r -> abilities.remove(r));
    }

    @Override
    public void removeAbilities(List<Ability> abilitiesToRemove, UUID sourceId, Game game) {
        if (abilitiesToRemove == null) {
            return;
        }

        abilitiesToRemove.forEach(a -> removeAbility(a, sourceId, game));
    }

    @Override
    public Counters getCounters(Game game) {
        return counters;
    }

    @Override
    public Counters getCounters(GameState state) {
        return counters;
    }

    @Override
    protected UUID getControllerOrOwner() {
        return controllerId;
    }

    @Override
    public int getTurnsOnBattlefield() {
        return turnsOnBattlefield;
    }

    @Override
    public void beginningOfTurn(Game game) {
        if (game.isActivePlayer(this.controllerId)) {
            this.controlledFromStartOfControllerTurn = true;
        }
    }

    @Override
    public void endOfTurn(Game game) {
        this.damage = 0;
        this.timesLoyaltyUsed = 0;
        this.turnsOnBattlefield++;
        this.deathtouched = false;
        dealtDamageByThisTurn = null;
        for (Ability ability : this.abilities) {
            ability.reset(game);
        }
    }

    @Override
    public void incrementLoyaltyActivationsAvailable() {
        this.incrementLoyaltyActivationsAvailable(Integer.MAX_VALUE);
    }

    @Override
    public void incrementLoyaltyActivationsAvailable(int max) {
        if (this.loyaltyActivationsAvailable < max) {
            this.loyaltyActivationsAvailable++;
        }
    }

    @Override
    public void setLoyaltyActivationsAvailable(int setActivations) {
        if(this.loyaltyActivationsAvailable < setActivations) {
            this.loyaltyActivationsAvailable = setActivations;
        }
    }

    @Override
    public void addLoyaltyUsed() {
        this.timesLoyaltyUsed++;
    }

    @Override
    public boolean canLoyaltyBeUsed(Game game) {
        Player controller = game.getPlayer(controllerId);
        if (controller != null) {
            return loyaltyActivationsAvailable > timesLoyaltyUsed;
        }
        return false;
    }

    @Override
    public void setLegendRuleApplies(boolean legendRuleApplies) {
        this.legendRuleApplies = legendRuleApplies;
    }

    @Override
    public boolean legendRuleApplies() {
        return this.legendRuleApplies;
    }

    @Override
    public boolean isTapped() {
        return tapped;
    }

    @Override
    public void setTapped(boolean tapped) {
        this.tapped = tapped;
    }

    @Override
    public boolean canTap(Game game) {
        return !isCreature(game) || !hasSummoningSickness();
    }

    @Override
    public boolean untap(Game game) {
        //20091005 - 701.15b
        if (tapped) {
            if (!replaceEvent(EventType.UNTAP, game)) {
                this.tapped = false;
                fireEvent(EventType.UNTAPPED, game);
                return true;
            }
        }
        return false;
    }

    @Override
    public boolean tap(Ability source, Game game) {
        return tap(false, source, game);
    }

    @Override
    public boolean tap(boolean forCombat, Ability source, Game game) {
        //20091005 - 701.15a
        if (!tapped) {
            if (!replaceEvent(EventType.TAP, game)) {
                this.tapped = true;
                game.fireEvent(new GameEvent(GameEvent.EventType.TAPPED, objectId, source, controllerId, 0, forCombat));
                return true;
            }
        }
        return false;
    }

    @Override
    public void setFaceDown(boolean value, Game game) {
        this.faceDown = value;
    }

    @Override
    public boolean isFaceDown(Game game) {
        return faceDown;
    }

    @Override
    public boolean isFlipped() {
        return flipped;
    }

    @Override
    public boolean flip(Game game) {
        if (!flipped) {
            if (!replaceEvent(EventType.FLIP, game)) {
                this.flipped = true;
                fireEvent(EventType.FLIPPED, game);
                return true;
            }
        }
        return false;
    }

    @Override
    public boolean transform(Ability source, Game game) {
        return this.transform(source, game, false);
    }

    private boolean checkDayNightBound() {
        return this.getAbilities().containsClass(DayboundAbility.class)
                || this.getAbilities().containsClass(NightboundAbility.class);
    }

    private boolean checkTransformRestrictionEffects(Game game) {
        for (Map.Entry<RestrictionEffect, Set<Ability>> entry : game.getContinuousEffects().getApplicableRestrictionEffects(this, game).entrySet()) {
            if (!entry.getKey().canTransform(game, true)) {
                return false;
            }
        }
        return true;
    }

    private Card getOtherFace() {
        return transformed ? this.getMainCard() : this.getMainCard().getSecondCardFace();
    }

    @Override
    public boolean transform(Ability source, Game game, boolean ignoreDayNight) {
        if (!this.isTransformable()
                || (!ignoreDayNight && this.checkDayNightBound())
                || this.getOtherFace().isInstantOrSorcery()
                || !this.checkTransformRestrictionEffects(game)
                || (source != null && !source.checkTransformCount(this, game))) {
            return false;
        }
        if (this.transformed) {
            Card orgCard = this.getMainCard();
            this.getPower().setModifiedBaseValue(orgCard.getPower().getValue());
            this.getToughness().setModifiedBaseValue(orgCard.getToughness().getValue());
        }
        game.informPlayers(this.getLogName() + " transforms into " + this.getOtherFace().getLogName()
                + CardUtil.getSourceLogName(game, source, this.getId()));
        this.setTransformed(!this.transformed);
        this.transformCount++;
        game.applyEffects();
        this.replaceEvent(EventType.TRANSFORMING, game);
        game.addSimultaneousEvent(GameEvent.getEvent(EventType.TRANSFORMED, this.getId(), this.getControllerId()));
        return true;
    }

    @Override
    public int getTransformCount() {
        return transformCount;
    }

    @Override
    public boolean isPhasedIn() {
        return phasedIn;
    }

    @Override
    public boolean isPhasedOutIndirectly() {
        return !phasedIn && indirectPhase;
    }

    @Override
    public boolean phaseIn(Game game) {
        return phaseIn(game, true);
    }

    @Override
    public boolean phaseIn(Game game, boolean onlyDirect) {
        if (!phasedIn) {
            if (!replaceEvent(EventType.PHASE_IN, game)
                    && (!onlyDirect || !indirectPhase)) {
                this.phasedIn = true;
                this.indirectPhase = false;
                if (!game.isSimulation()) {
                    game.informPlayers(getLogName() + " phased in");
                }
                for (UUID attachedId : this.getAttachments()) {
                    Permanent attachedPerm = game.getPermanent(attachedId);
                    if (attachedPerm != null) {
                        attachedPerm.phaseIn(game, false);
                    }
                }
                fireEvent(EventType.PHASED_IN, game);
                return true;
            }
        }
        return false;
    }

    @Override
    public boolean phaseOut(Game game) {
        return phaseOut(game, false);
    }

    @Override
    public boolean phaseOut(Game game, boolean indirectPhase) {
        if (phasedIn) {
            if (!replaceEvent(EventType.PHASE_OUT, game)) {
                for (UUID attachedId : this.getAttachments()) {
                    Permanent attachedPerm = game.getPermanent(attachedId);
                    if (attachedPerm != null) {
                        attachedPerm.phaseOut(game, true);
                    }
                }
                this.removeFromCombat(game);
                this.phasedIn = false;
                this.indirectPhase = indirectPhase;
                if (!game.isSimulation()) {
                    game.informPlayers(getLogName() + " phased out");
                }
                fireEvent(EventType.PHASED_OUT, game);
                return true;
            }
        }
        return false;
    }

    public void removeSummoningSickness() {
        this.controlledFromStartOfControllerTurn = true;
    }

    @Override
    public boolean wasControlledFromStartOfControllerTurn() {
        return this.controlledFromStartOfControllerTurn;
    }

    @Override
    public boolean hasSummoningSickness() {
        return !(this.controlledFromStartOfControllerTurn || this.abilities.containsKey(HasteAbility.getInstance().getId()));
    }

    @Override
    public boolean isAttacking() {
        return attacking;
    }

    @Override
    public boolean isBlocked(Game game) {
        for (CombatGroup combatGroup : game.getCombat().getGroups()) {
            if (combatGroup.getBlocked() && combatGroup.getAttackers().contains(this.getId())) {
                return true;
            }
        }
        return false;
    }

    @Override
    public int getBlocking() {
        return blocking;
    }

    @Override
    public int getMaxBlocks() {
        return maxBlocks;
    }

    @Override
    public int getMinBlockedBy() {
        return minBlockedBy;
    }

    @Override
    public int getMaxBlockedBy() {
        return maxBlockedBy;
    }

    @Override
    public UUID getControllerId() {
        return this.controllerId;
    }

    @Override
    public void resetControl() {
        this.beforeResetControllerId = this.controllerId;
        this.controllerId = this.originalControllerId;
    }

    @Override
    public boolean changeControllerId(UUID newControllerId, Game game, Ability source) {
        Player newController = game.getPlayer(newControllerId);
        if (newController == null || !newController.isInGame()) {
            return false;
        }

        // For each control change compared to last controller send a GAIN_CONTROL replace event to be able to prevent the gain control (e.g. Guardian Beast)
        if (beforeResetControllerId != newControllerId) {
            GameEvent gainControlEvent = GameEvent.getEvent(GameEvent.EventType.GAIN_CONTROL, this.getId(), null, newControllerId);
            if (game.replaceEvent(gainControlEvent)) {
                return false;
            }
        }

        GameEvent loseControlEvent = GameEvent.getEvent(GameEvent.EventType.LOSE_CONTROL, this.getId(), null, newControllerId);
        if (game.replaceEvent(loseControlEvent)) {
            return false;
        }

        // must change abilities controller too
        this.controllerId = newControllerId;
        this.getAbilities().setControllerId(newControllerId);
        return true;
    }

    @Override
    public boolean checkControlChanged(Game game) {
        if (!controllerId.equals(beforeResetControllerId)) {
            this.removeFromCombat(game);
            this.controlledFromStartOfControllerTurn = false;

            this.getAbilities(game).setControllerId(controllerId);
            game.getContinuousEffects().setController(objectId, controllerId);
            // the controller of triggered abilites is always set/checked before the abilities triggers so not needed here
            game.fireEvent(new GameEvent(GameEvent.EventType.LOST_CONTROL, objectId, null, beforeResetControllerId));
            game.fireEvent(new GameEvent(GameEvent.EventType.GAINED_CONTROL, objectId, null, controllerId));

            return true;
        } else if (isCopy()) {// Because the previous copied abilities can be from another controller - change controller in any case for abilities
            this.getAbilities(game).setControllerId(controllerId);
            game.getContinuousEffects().setController(objectId, controllerId);
        }
        return false;
    }

    @Override
    public UUID getAttachedTo() {
        return attachedTo;
    }

    @Override
    public int getAttachedToZoneChangeCounter() {
        return attachedToZoneChangeCounter;
    }

    @Override
    public void addConnectedCard(String key, UUID connectedCard) {
        if (this.connectedCards.containsKey(key)) {
            this.connectedCards.get(key).add(connectedCard);
        } else {
            List<UUID> list = new ArrayList<>();
            list.add(connectedCard);
            this.connectedCards.put(key, list);
        }
    }

    @Override
    public List<UUID> getConnectedCards(String key) {
        return this.connectedCards.getOrDefault(key, emptyList);
    }

    @Override
    public void clearConnectedCards(String key) {
        if (this.connectedCards.containsKey(key)) {
            this.connectedCards.get(key).clear();
        }
    }

    @Override
    public void unattach(Game game) {
        this.attachedTo = null;
        this.addInfo("attachedTo", null, game);
    }

    @Override
    public void attachTo(UUID attachToObjectId, Ability source, Game game) {
        // 701.3a - Permanents can be attached to an object or player
        if (this.attachedTo != null && !Objects.equals(this.attachedTo, attachToObjectId)) {
            Permanent attachedToUntilNowObject = game.getPermanent(this.attachedTo);
            if (attachedToUntilNowObject != null) {
                attachedToUntilNowObject.removeAttachment(this.objectId, source, game);
            } else {
                Card attachedToUntilNowCard = game.getCard(this.attachedTo);
                if (attachedToUntilNowCard != null) {
                    attachedToUntilNowCard.removeAttachment(this.objectId, source, game);
                } else {
                    Player attachedToUntilNowPlayer = game.getPlayer(this.attachedTo);
                    if (attachedToUntilNowPlayer != null) {
                        attachedToUntilNowPlayer.removeAttachment(this, source, game);
                    }
                }
            }

        }
        this.attachedTo = attachToObjectId;
        this.attachedToZoneChangeCounter = game.getState().getZoneChangeCounter(attachToObjectId);
        for (Ability ability : this.getAbilities()) {
            for (Iterator<Effect> ite = ability.getEffects(game, EffectType.CONTINUOUS).iterator(); ite.hasNext(); ) {
                ContinuousEffect effect = (ContinuousEffect) ite.next();
                game.getContinuousEffects().setOrder(effect);
                // It's important to update the timestamp of the copied effect in ContinuousEffects because it does the action
                for (ContinuousEffect conEffect : game.getContinuousEffects().getLayeredEffects(game)) {
                    if (conEffect.getId().equals(effect.getId())) {
                        game.getContinuousEffects().setOrder(conEffect);
                    }
                }
            }
        }

        // Reset "attached to" tooltip before potentially adding it
        this.addInfo("attachedTo", null, game);
        if (this.attachedTo != null) {
            Permanent attachedToPerm = game.getPermanent(this.getAttachedTo());
            // If what this permanent is attached to isn't also a permenent, such as a
            // player or card in graveyard, it is important to mention what it is attached
            // to in the tooltip. The rules let you attach a permanent to any kind of object
            // or player, although currently the only objects it's possible to attach a
            // permanent to are another permanent or a card. But to help future-proof this,
            // we'll generalise to objects.
            if (attachedToPerm == null) {
                MageObject attachedToObject = game.getObject(this.getAttachedTo());
                if (attachedToObject != null) {
                    this.addInfo("attachedTo",
                            CardUtil.addToolTipMarkTags("Attached to: " + attachedToObject.getIdName()), game);
                } else {
                    Player attachedToPlayer = game.getPlayer(this.getAttachedTo());
                    if (attachedToPlayer != null) {
                        this.addInfo("attachedTo",
                                CardUtil.addToolTipMarkTags("Attached to: " + attachedToPlayer.getName()), game);
                    }
                }
            }
        }
    }

    @Override
    public boolean isDeathtouched() {
        return deathtouched;
    }

    @Override
    public int getDamage() {
        return this.damage;
    }

    @Override
    public int damage(int damage, Ability source, Game game) {
        return damage(damage, source.getSourceId(), source, game);
    }

    @Override
    public int damage(int damage, UUID attackerId, Ability source, Game game) {
        return doDamage(damage, attackerId, source, game, true, false, false, null);
    }

    @Override
    public int damage(int damage, UUID attackerId, Ability source, Game game, boolean combat, boolean preventable) {
        return doDamage(damage, attackerId, source, game, preventable, combat, false, null);
    }

    @Override
    public int damage(int damage, UUID attackerId, Ability source, Game game, boolean combat, boolean preventable, List<UUID> appliedEffects) {
        return doDamage(damage, attackerId, source, game, preventable, combat, false, appliedEffects);
    }

    /**
     * @param damageAmount
     * @param attackerId   id of the permanent or player who make damage (source.getSourceId() in most cases)
     * @param source
     * @param game
     * @param preventable
     * @param combat
     * @param markDamage   If true, damage will be dealt later in applyDamage
     *                     method, uses only in inner markDamage.
     * @return
     */
    private int doDamage(int damageAmount, UUID attackerId, Ability source, Game game, boolean preventable, boolean combat, boolean markDamage, List<UUID> appliedEffects) {
        int damageDone = 0;
        if (damageAmount < 1 || !canDamage(game.getObject(attackerId), game)) {
            return 0;
        }
        DamageEvent event = new DamagePermanentEvent(objectId, attackerId, controllerId, damageAmount, preventable, combat);
        event.setAppliedEffects(appliedEffects);
        if (game.replaceEvent(event)) {
            return 0;
        }
        int actualDamage = checkProtectionAbilities(event, attackerId, source, game);
        if (actualDamage < 1) {
            return 0;
        }
        int lethal = getLethalDamage(attackerId, game);
        MageObject attacker = game.getObject(attackerId);
        if (this.isCreature(game)) {
            if (checkWither(event, attacker, game)) {
                if (markDamage) {
                    // mark damage only
                    markDamage(CounterType.M1M1.createInstance(actualDamage), attacker, true);
                } else {
                    Ability damageSourceAbility = null;
                    if (attacker instanceof Permanent) {
                        damageSourceAbility = ((Permanent) attacker).getSpellAbility();
                    }
                    // deal damage immediately
                    addCounters(CounterType.M1M1.createInstance(actualDamage), game.getControllerId(attackerId), damageSourceAbility, game);
                }
            } else {
                this.damage = CardUtil.overflowInc(this.damage, actualDamage);
            }
        }
        if (this.isPlaneswalker(game)) {
            int loyalty = getCounters(game).getCount(CounterType.LOYALTY);
            int countersToRemove = Math.min(actualDamage, loyalty);
            if (attacker != null && markDamage) {
                markDamage(CounterType.LOYALTY.createInstance(countersToRemove), attacker, false);
            } else {
                removeCounters(CounterType.LOYALTY.getName(), countersToRemove, source, game);
            }
        }
        DamagedEvent damagedEvent = new DamagedPermanentEvent(this.getId(), attackerId, this.getControllerId(), actualDamage, combat);
        damagedEvent.setExcess(actualDamage - lethal);
        game.fireEvent(damagedEvent);
        game.getState().addSimultaneousDamage(damagedEvent, game);
        damageDone = actualDamage;
        if (damageDone < 1) {
            return 0;
        }
        UUID sourceControllerId = null;
        Abilities sourceAbilities = null;
        attacker = game.getPermanentOrLKIBattlefield(attackerId);
        if (attacker == null) {
            StackObject stackObject = game.getStack().getStackObject(attackerId);
            if (stackObject != null) {
                attacker = stackObject.getStackAbility().getSourceObject(game);
            } else {
                attacker = game.getObject(attackerId);
            }
            if (attacker instanceof Spell) {
                sourceAbilities = ((Spell) attacker).getAbilities(game);
                sourceControllerId = ((Spell) attacker).getControllerId();
            } else if (attacker instanceof Card) {
                sourceAbilities = ((Card) attacker).getAbilities(game);
                sourceControllerId = ((Card) attacker).getOwnerId();
            } else if (attacker instanceof CommandObject) {
                sourceControllerId = ((CommandObject) attacker).getControllerId();
                sourceAbilities = attacker.getAbilities();
            } else {
                attacker = null;
            }
        } else {
            sourceAbilities = ((Permanent) attacker).getAbilities(game);
            sourceControllerId = ((Permanent) attacker).getControllerId();
        }
        if (attacker != null && sourceAbilities != null) {
            if (sourceAbilities.containsKey(LifelinkAbility.getInstance().getId())) {
                if (markDamage) {
                    game.getPermanent(attackerId).markLifelink(damageDone);
                } else {
                    Player player = game.getPlayer(sourceControllerId);
                    player.gainLife(damageDone, game, source);
                }
            }
            if (sourceAbilities.containsKey(DeathtouchAbility.getInstance().getId())) {
                deathtouched = true;
            }
            if (dealtDamageByThisTurn == null) {
                dealtDamageByThisTurn = new HashSet<>();
            }
            // Unstable ability - Earl of Squirrel
            if (sourceAbilities.containsKey(SquirrellinkAbility.getInstance().getId())) {
                Player player = game.getPlayer(sourceControllerId);
                new SquirrelToken().putOntoBattlefield(damageDone, game, source, player.getId());
            }
            dealtDamageByThisTurn.add(new MageObjectReference(attacker, game));
        }
        if (attacker == null) {
            game.informPlayers(getLogName() + " gets " + damageDone + " damage");
        } else {
            game.informPlayers(attacker.getLogName() + " deals " + damageDone + " damage to " + getLogName());
        }
        return damageDone;
    }

    private static boolean checkWither(DamageEvent event, MageObject attacker, Game game) {
        if (event.isAsThoughWither() || event.isAsThoughInfect()) {
            return true;
        }
        if (attacker == null) {
            return false;
        }
        Abilities abilities;
        if (attacker instanceof Card) {
            abilities = ((Card) attacker).getAbilities(game);
        } else {
            abilities = attacker.getAbilities();
        }
        return abilities.containsKey(InfectAbility.getInstance().getId())
                || abilities.containsKey(WitherAbility.getInstance().getId());
    }

    @Override
    public void markLifelink(int damage) {
        markedLifelink += damage;
    }

    @Override
    public int markDamage(int damageAmount, UUID attackerId, Ability source, Game game, boolean preventable, boolean combat) {
        return doDamage(damageAmount, attackerId, source, game, preventable, combat, true, null);
    }

    @Override
    public int applyDamage(Game game) {
        if (markedLifelink > 0) {
            Player player = game.getPlayer(this.getControllerId());
            player.gainLife(markedLifelink, game, null);
            markedLifelink = 0;
        }
        if (markedDamage == null) {
            return 0;
        }
        for (MarkedDamageInfo mdi : markedDamage) {
            Ability source = null;
            if (mdi.sourceObject instanceof PermanentToken) {
                /* Tokens dont have a spellAbility. We must make a phony one as the source so the events in addCounters
                 * can trace the source back to an object/controller.
                 */
                source = new SpellAbility(null, ((PermanentToken) mdi.sourceObject).name);
                source.setSourceId(((PermanentToken) mdi.sourceObject).objectId);
            } else if (mdi.sourceObject instanceof Permanent) {
                source = ((Permanent) mdi.sourceObject).getSpellAbility();
            }
            if (mdi.addCounters) {
                addCounters(mdi.counter, game.getControllerId(mdi.sourceObject.getId()), source, game);
            } else {
                removeCounters(mdi.counter, source, game);
            }
        }
        markedDamage.clear();
        return 0;
    }

    @Override
    public int getLethalDamage(UUID attackerId, Game game) {
        int lethal = Integer.MAX_VALUE;
        if (this.isCreature(game)) {
            if (game.getState().getActivePowerInsteadOfToughnessForDamageLethalityFilters().stream().anyMatch(f -> f.match(this, game))) {
                lethal = Math.min(lethal, power.getValue());
            } else {
                lethal = Math.min(lethal, toughness.getValue());
            }
            lethal = Math.max(lethal - this.damage, 0);
            Card attacker = game.getPermanent(attackerId);
            if (attacker == null) {
                attacker = game.getCard(attackerId);
            }
            if (attacker != null && attacker.getAbilities(game).containsKey(DeathtouchAbility.getInstance().getId())) {
                lethal = Math.min(1, lethal);
            }
        }
        if (this.isPlaneswalker(game)) {
            lethal = Math.min(lethal, this.getCounters(game).getCount(CounterType.LOYALTY));
        }
        return lethal;
    }

    @Override
    public void removeAllDamage(Game game) {
        damage = 0;
        deathtouched = false;
    }

    private int checkProtectionAbilities(GameEvent event, UUID attackerId, Ability source, Game game) {
        MageObject attacker = game.getObject(attackerId);
        if (attacker != null && hasProtectionFrom(attacker, game)) {
            GameEvent preventEvent = new PreventDamageEvent(this.getId(), attackerId, source, this.getControllerId(), event.getAmount(), ((DamageEvent) event).isCombatDamage());
            if (!game.replaceEvent(preventEvent)) {
                int preventedDamage = event.getAmount();
                event.setAmount(0);
                game.fireEvent(new PreventedDamageEvent(this.getId(), attackerId, source, this.getControllerId(), preventedDamage));
                return 0;
            }
        }
        return event.getAmount();
    }

    private void markDamage(Counter counter, MageObject source, boolean addCounters) {
        if (markedDamage == null) {
            markedDamage = new ArrayList<>();
        }
        markedDamage.add(new MarkedDamageInfo(counter, source, addCounters));
    }

    @Override
    public boolean entersBattlefield(Ability source, Game game, Zone fromZone, boolean fireEvent) {
        controlledFromStartOfControllerTurn = false;
        if (this.isFaceDown(game)) {
            // remove some attributes here, because first apply effects comes later otherwise abilities (e.g. color related) will unintended trigger
            MorphAbility.setPermanentToFaceDownCreature(this, game);
        }

        if (game.replaceEvent(new EntersTheBattlefieldEvent(this, source, getControllerId(), fromZone, EnterEventType.SELF))) {
            return false;
        }
        EntersTheBattlefieldEvent event = new EntersTheBattlefieldEvent(this, source, getControllerId(), fromZone);
        if (game.replaceEvent(event)) {
            return false;
        }
        if (this.isPlaneswalker(game)) {
            int loyalty;
            if (this.getStartingLoyalty() == -2) {
                loyalty = source.getManaCostsToPay().getX();
            } else {
                loyalty = this.getStartingLoyalty();
            }
            int countersToAdd;
            if (this.hasAbility(CompleatedAbility.getInstance(), game)) {
                countersToAdd = loyalty - 2 * source.getManaCostsToPay().getPhyrexianPaid();
            } else {
                countersToAdd = loyalty;
            }
            if (countersToAdd > 0) {
                this.addCounters(CounterType.LOYALTY.createInstance(countersToAdd), source, game);
            }
        }
        if (!fireEvent) {
            return false;
        }
        game.addSimultaneousEvent(event);
        return true;
    }

    @Override
    public boolean canBeTargetedBy(MageObject source, UUID sourceControllerId, Game game) {
        if (source != null) {
            if (abilities.containsKey(ShroudAbility.getInstance().getId())) {
                if (null == game.getContinuousEffects().asThough(this.getId(), AsThoughEffectType.SHROUD, null, sourceControllerId, game)) {
                    return false;
                }
            }

            if (game.getPlayer(this.getControllerId()).hasOpponent(sourceControllerId, game)
                    && null == game.getContinuousEffects().asThough(this.getId(), AsThoughEffectType.HEXPROOF, null, sourceControllerId, game)
                    && abilities.stream()
                    .filter(HexproofBaseAbility.class::isInstance)
                    .map(HexproofBaseAbility.class::cast)
                    .anyMatch(ability -> ability.checkObject(source, game))) {
                return false;
            }

            if (hasProtectionFrom(source, game)) {
                return false;
            }

            // example: Fiendslayer Paladin tried to target with Ultimate Price
            return !game.getContinuousEffects().preventedByRuleModification(
                    new TargetEvent(this, source.getId(), sourceControllerId),
                    null,
                    game,
                    true
            );
        }

        return true;
    }

    @Override
    public boolean hasProtectionFrom(MageObject source, Game game) {
        for (ProtectionAbility ability : this.getAbilities(game).getProtectionAbilities()) {
            if (!ability.canTarget(source, game)) {
                return true;
            }
        }
        return false;
    }

    @Override
    public boolean cantBeAttachedBy(MageObject attachment, Ability source, Game game, boolean silentMode) {
        for (ProtectionAbility ability : this.getAbilities(game).getProtectionAbilities()) {
            if ((!attachment.hasSubtype(SubType.AURA, game) || ability.removesAuras())
                    && (!attachment.hasSubtype(SubType.EQUIPMENT, game) || ability.removesEquipment())
                    && !attachment.getId().equals(ability.getAuraIdNotToBeRemoved())
                    && !ability.canTarget(attachment, game)) {
                return !ability.getDoesntRemoveControlled() || isControlledBy(game.getControllerId(attachment.getId()));
            }
        }
        return game.getContinuousEffects().preventedByRuleModification(new StayAttachedEvent(this.getId(), attachment.getId(), source), null, game, silentMode);
    }

    protected boolean canDamage(MageObject source, Game game) {
        //noxx: having protection doesn't prevents from dealing damage
        // instead it adds damage prevention
        //return (!hasProtectionFrom(source, game));
        return true;
    }

    @Override
    public boolean destroy(Ability source, Game game) {
        return destroy(source, game, false);
    }

    @Override
    public boolean destroy(Ability source, Game game, boolean noRegen) {
        // Only permanets on the battlefield can be destroyed
        if (!game.getState().getZone(getId()).equals(Zone.BATTLEFIELD)) {
            return false;
        }
        //20091005 - 701.6
        if (abilities.containsKey(IndestructibleAbility.getInstance().getId())) {
            return false;
        }

        if (!game.replaceEvent(GameEvent.getEvent(GameEvent.EventType.DESTROY_PERMANENT, objectId, source, controllerId, noRegen ? 1 : 0))) {
            // this means destroy was successful, if object movement to graveyard will be replaced (e.g. commander to command zone) it's still
            // handled as successful destroying (but not as sucessful "dies this way" for destroying).
            if (moveToZone(Zone.GRAVEYARD, source, game, false)) {
                if (!game.isSimulation()) {
                    String logName;
                    Card card = game.getCard(this.getId());
                    if (card != null) {
                        logName = card.getLogName();
                    } else {
                        logName = this.getLogName();
                    }
                    if (this.isCreature(game)) {
                        game.informPlayers(logName + " died" + CardUtil.getSourceLogName(game, " by ", source, "", ""));
                    } else {
                        game.informPlayers(logName + " was destroyed" + CardUtil.getSourceLogName(game, " by ", source, "", ""));
                    }
                }
                game.fireEvent(GameEvent.getEvent(GameEvent.EventType.DESTROYED_PERMANENT, objectId, source, controllerId));
            }
            return true;
        }
        return false;
    }

    @Override
    public boolean sacrifice(Ability source, Game game) {
        //20091005 - 701.13
        if (isPhasedIn() && !game.replaceEvent(GameEvent.getEvent(GameEvent.EventType.SACRIFICE_PERMANENT, objectId, source, controllerId))) {
            // Commander replacement effect or Rest in Peace (exile instead of graveyard) in play does not prevent successful sacrifice
            // so the return value of the moveToZone is not taken into account here
            moveToZone(Zone.GRAVEYARD, source, game, false);
            Player player = game.getPlayer(getControllerId());
            if (player != null && !game.isSimulation()) {
                game.informPlayers(player.getLogName() + " sacrificed " + this.getLogName() + CardUtil.getSourceLogName(game, source));
            }
            game.fireEvent(GameEvent.getEvent(GameEvent.EventType.SACRIFICED_PERMANENT, objectId, source, controllerId));
            return true;
        }
        return false;
    }

    @Override
    public boolean regenerate(Ability source, Game game) {
        //20110930 - 701.12
        if (!game.replaceEvent(GameEvent.getEvent(GameEvent.EventType.REGENERATE, objectId, source, controllerId))) {
            this.tap(source, game);
            this.removeFromCombat(game);
            this.removeAllDamage(game);

            // remove one regen shield
            RegenerateSourceEffect.decRegenerationShieldsAmount(game, this.getId());

            game.fireEvent(GameEvent.getEvent(GameEvent.EventType.REGENERATED, objectId, source, controllerId));
            return true;
        }
        return false;
    }

    @Override
    public void addPower(int power) {
        this.power.increaseBoostedValue(power);
    }

    @Override
    public void addToughness(int toughness) {
        this.toughness.increaseBoostedValue(toughness);
    }

    /**
     * Simple event without source
     *
     * @param eventType
     * @param game
     */
    protected void fireEvent(EventType eventType, Game game) {
        game.fireEvent(GameEvent.getEvent(eventType, this.objectId, null, this.controllerId));
    }

    /**
     * Simple event without source
     *
     * @param eventType
     * @param game
     * @return
     */
    protected boolean replaceEvent(EventType eventType, Game game) {
        return game.replaceEvent(GameEvent.getEvent(eventType, this.objectId, null, this.controllerId));
    }

    @Override
    public boolean canAttack(UUID defenderId, Game game) {
        if (tapped) {
            return false;
        }
        return canAttackInPrinciple(defenderId, game);
    }

    @Override
    public boolean canAttackInPrinciple(UUID defenderId, Game game) {
        ApprovingObject approvingObject = game.getContinuousEffects().asThough(
                this.objectId, AsThoughEffectType.ATTACK_AS_HASTE, null, defenderId, game
        );
        if (hasSummoningSickness() && approvingObject == null) {
            return false;
        }
        //20101001 - 508.1c
        if (defenderId == null) {
            if (game.getCombat()
                    .getDefenders()
                    .stream()
                    .noneMatch(defenderToCheckId -> canAttackCheckRestrictionEffects(defenderToCheckId, game))) {
                return false;
            }
        } else if (!canAttackCheckRestrictionEffects(defenderId, game)) {
            return false;
        }

        return !abilities.containsKey(DefenderAbility.getInstance().getId())
                || null != game.getContinuousEffects().asThough(this.objectId, AsThoughEffectType.ATTACK, null, this.getControllerId(), game);
    }

    private boolean canAttackCheckRestrictionEffects(UUID defenderId, Game game) {
        //20101001 - 508.1c
        for (Map.Entry<RestrictionEffect, Set<Ability>> effectEntry : game.getContinuousEffects().getApplicableRestrictionEffects(this, game).entrySet()) {
            if (!effectEntry.getKey().canAttack(game, true)) {
                return false;
            }
            for (Ability ability : effectEntry.getValue()) {
                if (!effectEntry.getKey().canAttack(this, defenderId, ability, game, true)) {
                    return false;
                }
            }
        }
        return true;
    }

    @Override
    public boolean canBlock(UUID attackerId, Game game) {
        if (tapped && null == game.getState().getContinuousEffects().asThough(this.getId(), AsThoughEffectType.BLOCK_TAPPED, null, this.getControllerId(), game)) {
            return false;
        }
        Permanent attacker = game.getPermanent(attackerId);
        if (attacker == null) {
            return false;
        }
        // controller of attacking permanent must be an opponent
        if (!game.getPlayer(this.getControllerId()).hasOpponent(attacker.getControllerId(), game)) {
            return false;
        }
        //20101001 - 509.1b
        // check blocker restrictions
        for (Map.Entry<RestrictionEffect, Set<Ability>> entry : game.getContinuousEffects().getApplicableRestrictionEffects(this, game).entrySet()) {
            for (Ability ability : entry.getValue()) {
                if (!entry.getKey().canBlock(attacker, this, ability, game, true)) {
                    return false;
                }
            }
        }
        // check also attacker's restriction effects
        for (Map.Entry<RestrictionEffect, Set<Ability>> restrictionEntry : game.getContinuousEffects().getApplicableRestrictionEffects(attacker, game).entrySet()) {
            for (Ability ability : restrictionEntry.getValue()) {
                if (!restrictionEntry.getKey().canBeBlocked(attacker, this, ability, game, true)) {
                    return false;
                }
            }
        }
        return !attacker.hasProtectionFrom(this, game);
    }

    @Override
    public boolean canBlockAny(Game game) {
        if (tapped && null == game.getState().getContinuousEffects().asThough(this.getId(), AsThoughEffectType.BLOCK_TAPPED, null, this.getControllerId(), game)) {
            return false;
        }

        //20101001 - 509.1b
        for (Map.Entry<RestrictionEffect, Set<Ability>> entry : game.getContinuousEffects().getApplicableRestrictionEffects(this, game).entrySet()) {
            RestrictionEffect effect = entry.getKey();
            for (Ability ability : entry.getValue()) {
                if (!effect.canBlock(null, this, ability, game, true)) {
                    return false;
                }
            }
        }

        return true;
    }

    /**
     * Checks by restriction effects if the permanent can use activated
     * abilities
     *
     * @param game
     * @return true - permanent can use activated abilities
     */
    @Override
    public boolean canUseActivatedAbilities(Game game) {
        for (Map.Entry<RestrictionEffect, Set<Ability>> entry : game.getContinuousEffects().getApplicableRestrictionEffects(this, game).entrySet()) {
            RestrictionEffect effect = entry.getKey();
            for (Ability ability : entry.getValue()) {
                if (!effect.canUseActivatedAbilities(this, ability, game, true)) {
                    return false;
                }
            }
        }
        return true;
    }

    @Override
    public void setAttacking(boolean attacking) {
        this.attacking = attacking;
    }

    @Override
    public void setBlocking(int blocking) {
        this.blocking = blocking;
    }

    @Override
    public void setMaxBlocks(int maxBlocks) {
        this.maxBlocks = maxBlocks;
    }

    @Override
    public void setMinBlockedBy(int minBlockedBy) {
        this.minBlockedBy = minBlockedBy;
    }

    @Override
    public void setMaxBlockedBy(int maxBlockedBy) {
        this.maxBlockedBy = maxBlockedBy;
    }

    @Override
    public boolean removeFromCombat(Game game) {
        return removeFromCombat(game, true);
    }

    @Override
    public boolean removeFromCombat(Game game, boolean withEvent) {
        if (this.isAttacking() || this.blocking > 0) {
            return game.getCombat().removeFromCombat(objectId, game, withEvent);
        } else if (this.isPlaneswalker(game)) {
            if (game.getCombat().getDefenders().contains(getId())) {
                game.getCombat().removePlaneswalkerFromCombat(objectId, game);
            }
        }
        return false;
    }

    @Override
    public boolean imprint(UUID imprintedCard, Game game) {
        if (!game.getExile().containsId(imprintedCard, game)) {
            return false;
        }
        if (connectedCards.containsKey("imprint")) {
            if (!this.connectedCards.get("imprint").contains(imprintedCard)) {
                this.connectedCards.get("imprint").add(imprintedCard);
            }
        } else {
            List<UUID> imprinted = new ArrayList<>();
            imprinted.add(imprintedCard);
            this.connectedCards.put("imprint", imprinted);
        }
        return true;
    }

    @Override
    public boolean clearImprinted(Game game) {
        this.connectedCards.put("imprint", new ArrayList<>());
        return true;
    }

    @Override
    public List<UUID> getImprinted() {
        if (this.connectedCards.containsKey("imprint")) {
            return this.connectedCards.get("imprint");
        } else {
            return emptyList;
        }
    }

    @Override
    public Set<MageObjectReference> getDealtDamageByThisTurn() {
        if (dealtDamageByThisTurn == null) {
            return new HashSet<>();
        }
        return dealtDamageByThisTurn;
    }

    @Override
    public boolean isTransformed() {
        return this.transformed;
    }

    @Override
    public void setTransformed(boolean value) {
        this.transformed = value;
    }

    @Override
    public boolean isMonstrous() {
        return this.monstrous;
    }

    @Override
    public void setMonstrous(boolean value) {
        this.monstrous = value;
    }

    @Override
    public boolean isRenowned() {
        return this.renowned;
    }

    @Override
    public void setRenowned(boolean value) {
        this.renowned = value;
    }

    @Override
    public int getClassLevel() {
        return classLevel;
    }

    @Override
    public boolean setClassLevel(int classLevel) {
        // can level up to next (+1) level only
        if (this.classLevel == classLevel - 1) {
            this.classLevel = classLevel;
            return true;
        }
        return false;
    }

    @Override
    public void addGoadingPlayer(UUID playerId) {
        this.goadingPlayers.add(playerId);
    }

    @Override
    public Set<UUID> getGoadingPlayers() {
        return goadingPlayers;
    }

    @Override
    public void setPairedCard(MageObjectReference pairedCard) {
        this.pairedPermanent = pairedCard;
        if (pairedCard == null) {
            // remove existing soulbond info text
            this.addInfo("soulbond", null, null);
        }
    }

    @Override
    public MageObjectReference getPairedCard() {
        return pairedPermanent;
    }

    @Override
    public void clearPairedCard() {
        this.pairedPermanent = null;
    }

    @Override
    public void addBandedCard(UUID bandedCard) {
        if (!this.bandedCards.contains(bandedCard)) {
            this.bandedCards.add(bandedCard);
        }
    }

    @Override
    public void removeBandedCard(UUID bandedCard) {
        this.bandedCards.remove(bandedCard);
    }

    @Override
    public List<UUID> getBandedCards() {
        return bandedCards;
    }

    @Override
    public void clearBandedCards() {
        this.bandedCards.clear();
    }

    @Override
    public String getLogName() {
        if (name.isEmpty()) {
            if (faceDown) {
                return GameLog.getNeutralColoredText("face down creature");
            } else {
                return GameLog.getNeutralColoredText("a creature without name");
            }
        }
        return GameLog.getColoredObjectIdName(this);
    }

    @Override
    public boolean isManifested() {
        return manifested;
    }

    @Override
    public void setManifested(boolean value) {
        manifested = value;
    }

    @Override
    public boolean isMorphed() {
        return morphed;
    }

    @Override
    public void setMorphed(boolean value) {
        morphed = value;
    }

    @Override
    public void setCardNumber(String cid) {
        this.cardNumber = cid;
    }

    @Override
    public void setExpansionSetCode(String expansionSetCode) {
        this.expansionSetCode = expansionSetCode;
    }

    @Override
    public void setRarity(Rarity rarity) {
        this.rarity = rarity;
    }

    @Override
    public void setFlipCard(boolean flipCard) {
        this.flipCard = flipCard;
    }

    @Override
    public void setFlipCardName(String flipCardName) {
        this.flipCardName = flipCardName;
    }

    @Override
    public void setSecondCardFace(Card card) {
        this.secondSideCard = card;
    }

    @Override
    public boolean fight(Permanent fightTarget, Ability source, Game game) {
        return this.fight(fightTarget, source, game, true);
    }

    @Override
    public boolean fight(Permanent fightTarget, Ability source, Game game, boolean batchTrigger) {
        // double fight events for each creature
        game.fireEvent(GameEvent.getEvent(GameEvent.EventType.FIGHTED_PERMANENT, fightTarget.getId(), source, source.getControllerId()));
        game.fireEvent(GameEvent.getEvent(GameEvent.EventType.FIGHTED_PERMANENT, getId(), source, source.getControllerId()));
        damage(fightTarget.getPower().getValue(), fightTarget.getId(), source, game);
        fightTarget.damage(getPower().getValue(), getId(), source, game);

        if (batchTrigger) {
            Set<MageObjectReference> morSet = new HashSet<>();
            morSet.add(new MageObjectReference(this, game));
            morSet.add(new MageObjectReference(fightTarget, game));
            String data = UUID.randomUUID().toString();
            game.getState().setValue("batchFight_" + data, morSet);
            game.fireEvent(GameEvent.getEvent(GameEvent.EventType.BATCH_FIGHT, getId(), source, source.getControllerId(), data, 0));
        }

        return true;
    }

    @Override
    public int getCreateOrder() {
        return createOrder;
    }

    @Override
    public void setCreateOrder(int createOrder) {
        this.createOrder = createOrder;
    }

    @Override
    public ObjectColor getColor() {
        return color;
    }

    @Override
    public ObjectColor getColor(Game game) {
        return color;
    }

    //20180810 - 701.3d
    //If an object leaves the zone it's in, all attached permanents become unattached
    //note that this code doesn't actually detach anything, and is a bit of a bandaid
    public void detachAllAttachments(Game game) {
        for (UUID attachmentId : getAttachments()) {
            Permanent attachment = game.getPermanent(attachmentId);
            Card attachmentCard = game.getCard(attachmentId);
            if (attachment != null && attachmentCard != null) {
                //make bestow cards and licids into creatures
                //aura test to stop bludgeon brawl shenanigans from using this code
                //consider adding code to handle that case?
                if (attachment.hasSubtype(SubType.AURA, game) && attachmentCard.isCreature(game)) {
                    BestowAbility.becomeCreature(attachment, game);
                }
            }
        }
    }

    @Override
    public boolean moveToZone(Zone toZone, Ability source, Game game, boolean flag, List<UUID> appliedEffects) {
        Zone fromZone = game.getState().getZone(objectId);
        Player controller = game.getPlayer(controllerId);
        if (controller != null) {
            ZoneChangeEvent event = new ZoneChangeEvent(this, source, controllerId, fromZone, toZone, appliedEffects);
            ZoneChangeInfo zoneChangeInfo;
            if (toZone == Zone.LIBRARY) {
                zoneChangeInfo = new ZoneChangeInfo.Library(event, flag /* put on top */);
            } else {
                zoneChangeInfo = new ZoneChangeInfo(event);
            }
            boolean successfullyMoved = ZonesHandler.moveCard(zoneChangeInfo, game, source);
            //20180810 - 701.3d
            detachAllAttachments(game);
            return successfullyMoved;
        }
        return false;
    }

    @Override
    public boolean moveToExile(UUID exileId, String name, Ability source, Game game, List<UUID> appliedEffects) {
        Zone fromZone = game.getState().getZone(objectId);
        ZoneChangeEvent event = new ZoneChangeEvent(this, source, ownerId, fromZone, Zone.EXILED, appliedEffects);
        ZoneChangeInfo.Exile zcInfo = new ZoneChangeInfo.Exile(event, exileId, name);

        boolean successfullyMoved = ZonesHandler.moveCard(zcInfo, game, source);
        //20180810 - 701.3d
        detachAllAttachments(game);
        return successfullyMoved;
    }
}
