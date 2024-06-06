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
import mage.abilities.effects.common.continuous.BecomesFaceDownCreatureEffect;
import mage.abilities.hint.HintUtils;
import mage.abilities.keyword.*;
import mage.cards.Card;
import mage.cards.CardImpl;
import mage.constants.*;
import mage.counters.Counter;
import mage.counters.CounterType;
import mage.counters.Counters;
import mage.filter.FilterOpponent;
import mage.game.Game;
import mage.game.GameState;
import mage.game.ZoneChangeInfo;
import mage.game.ZonesHandler;
import mage.game.combat.CombatGroup;
import mage.game.command.CommandObject;
import mage.game.events.*;
import mage.game.events.GameEvent.EventType;
import mage.game.permanent.token.SquirrelToken;
import mage.game.permanent.token.Token;
import mage.game.stack.Spell;
import mage.game.stack.StackObject;
import mage.players.Player;
import mage.target.TargetPlayer;
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
    protected boolean suspected;
    protected boolean manifested = false;
    protected boolean cloaked = false;
    protected boolean morphed = false;
    protected boolean disguised = false;
    protected boolean ringBearerFlag = false;
    protected boolean canBeSacrificed = true;
    protected int classLevel = 1;
    protected final Set<UUID> goadingPlayers = new HashSet<>();
    protected UUID originalControllerId;
    protected UUID controllerId;
    protected UUID protectorId = null;
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
    protected boolean solved = false;

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
    protected Map<String, String> info = new LinkedHashMap<>(); // additional info for permanent's rules
    protected int createOrder;
    protected boolean legendRuleApplies = true;
    protected boolean prototyped;

    private static final List<UUID> emptyList = Collections.unmodifiableList(new ArrayList<>());

    protected PermanentImpl(UUID ownerId, UUID controllerId, String name) {
        super(ownerId, name);

        // runtime check: need controller (if you catch it in non-game then use random uuid)
        if (controllerId == null) {
            throw new IllegalArgumentException("Wrong code usage: controllerId can't be null - " + name, new Throwable());
        }

        this.originalControllerId = controllerId;
        this.controllerId = controllerId;
        this.counters = new Counters();
    }

    protected PermanentImpl(UUID id, UUID ownerId, UUID controllerId, String name) {
        super(id, ownerId, name);
        this.originalControllerId = controllerId;
        this.controllerId = controllerId;
        this.counters = new Counters();
    }

    protected PermanentImpl(final PermanentImpl permanent) {
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
        this.solved = permanent.solved;
        this.markedLifelink = permanent.markedLifelink;
        this.connectedCards = CardUtil.deepCopyObject(permanent.connectedCards);
        this.dealtDamageByThisTurn = CardUtil.deepCopyObject(permanent.dealtDamageByThisTurn);
        if (permanent.markedDamage != null) {
            markedDamage = new ArrayList<>();
            for (MarkedDamageInfo mdi : permanent.markedDamage) {
                markedDamage.add(new MarkedDamageInfo(mdi.counter.copy(), mdi.sourceObject, mdi.addCounters));
            }
        }
        this.info.putAll(permanent.info);
        this.counters = permanent.counters.copy();
        this.attachedTo = permanent.attachedTo;
        this.attachedToZoneChangeCounter = permanent.attachedToZoneChangeCounter;
        this.minBlockedBy = permanent.minBlockedBy;
        this.maxBlockedBy = permanent.maxBlockedBy;
        this.transformed = permanent.transformed;
        this.monstrous = permanent.monstrous;
        this.renowned = permanent.renowned;
        this.suspected = permanent.suspected;
        this.ringBearerFlag = permanent.ringBearerFlag;
        this.classLevel = permanent.classLevel;
        this.goadingPlayers.addAll(permanent.goadingPlayers);
        this.pairedPermanent = permanent.pairedPermanent;
        this.bandedCards.addAll(permanent.bandedCards);
        this.timesLoyaltyUsed = permanent.timesLoyaltyUsed;
        this.loyaltyActivationsAvailable = permanent.loyaltyActivationsAvailable;
        this.legendRuleApplies = permanent.legendRuleApplies;
        this.transformCount = permanent.transformCount;
        this.protectorId = permanent.protectorId;

        this.morphed = permanent.morphed;
        this.disguised = permanent.disguised;
        this.manifested = permanent.manifested;
        this.cloaked = permanent.cloaked;
        this.createOrder = permanent.createOrder;
        this.prototyped = permanent.prototyped;
        this.canBeSacrificed = permanent.canBeSacrificed;
    }

    @Override
    public String toString() {
        String name = getName().isEmpty()
                ? "face down" + " [" + getId().toString().substring(0, 3) + "]"
                : getIdName();
        String imageInfo = getExpansionSetCode()
                + ":" + getCardNumber()
                + ":" + getImageFileName()
                + ":" + getImageNumber();
        return name
                + ", " + (getBasicMageObject() instanceof Token ? "T" : "C")
                + ", " + getBasicMageObject().getClass().getSimpleName()
                + ", " + imageInfo
                + ", " + this.getPower() + "/" + this.getToughness()
                + (this.isCopy() ? ", copy" : "")
                + (this.isTapped() ? ", tapped" : "");
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
        this.canBeSacrificed = true;
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
    final public List<String> getRules(Game game) {
        try {
            List<String> rules = super.getRules(game);

            // add additional data for GUI

            // info
            if (info != null) {
                rules.addAll(info.values());
            }

            if (game == null || game.getPhase() == null) {
                // dynamic hints for started game only
                return rules;
            }

            // ability hints already collected in super call

            // warning, if you add new icon type for restriction or requirement then don't forget
            // to add it for card icon too (search CardIconType.OTHER_HAS_RESTRICTIONS)

            // restrict hints
            List<String> restrictHints = new ArrayList<>();
            if (HintUtils.RESTRICT_HINTS_ENABLE) {
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

                // requirement hints
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
                        Player player = game.getPlayer(entry.getKey().mustAttackDefender(ability, game));
                        if (player != null) {
                            restrictHints.add(HintUtils.prepareText(
                                    "Must attack defender " + player.getLogName() + addSourceObjectName(game, ability),
                                    null, HintUtils.HINT_ICON_REQUIRE));
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

                // goaded hints
                for (UUID playerId : getGoadingPlayers()) {
                    Player player = game.getPlayer(playerId);
                    if (player != null) {
                        restrictHints.add(HintUtils.prepareText("Goaded by " + player.getLogName() + " (must attack)", null, HintUtils.HINT_ICON_REQUIRE));
                    }
                }

                restrictHints.sort(String::compareTo);
            }

            // total hints
            if (!restrictHints.isEmpty()) {
                if (rules.stream().noneMatch(s -> s.contains(HintUtils.HINT_START_MARK))) {
                    rules.add(HintUtils.HINT_START_MARK);
                }
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

    /**
     * Add an ability to the permanent. When copying from an existing source
     * you should use the fromExistingObject variant of this function to prevent double-copying subabilities
     *
     * @param ability  The ability to be added
     * @param sourceId id of the source doing the added (for the effect created to add it)
     * @param game
     * @return The newly added ability copy
     */
    @Override
    public Ability addAbility(Ability ability, UUID sourceId, Game game) {
        return addAbility(ability, sourceId, game, false);
    }

    /**
     * @param ability            The ability to be added
     * @param sourceId           id of the source doing the added (for the effect created to add it)
     * @param game
     * @param fromExistingObject if copying abilities from an existing source then must ignore sub-abilities because they're already on the source object
     *                           Otherwise sub-abilities will be added twice to the resulting object
     * @return The newly added ability copy
     */
    @Override
    public Ability addAbility(Ability ability, UUID sourceId, Game game, boolean fromExistingObject) {
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
            if (!fromExistingObject) {
                abilities.addAll(copyAbility.getSubAbilities());
            }
            return copyAbility;
        }
        return null;
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
    public UUID getControllerOrOwnerId() {
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
        if (this.loyaltyActivationsAvailable < setActivations) {
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
        if (tapped && !replaceEvent(EventType.UNTAP, game)) {
            this.tapped = false;
            UntappedEvent event = new UntappedEvent(
                    objectId, this.controllerId,
                    // Since triggers are not checked until the next step,
                    // we use the event flag to know if untapping was done during the untap step
                    game.getTurnStepType() == PhaseStep.UNTAP
            );
            game.fireEvent(event);
            game.getState().addSimultaneousUntappedToBatch(event, game);
            return true;
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
        if (!tapped && !replaceEvent(EventType.TAP, game)) {
            this.tapped = true;
            TappedEvent event = new TappedEvent(objectId, source, source == null ? null : source.getControllerId(), forCombat);
            game.fireEvent(event);
            game.getState().addSimultaneousTappedToBatch(event, game);
            return true;
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
        if (!flipped && !replaceEvent(EventType.FLIP, game)) {
            this.flipped = true;
            fireEvent(EventType.FLIPPED, game);
            return true;
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

    public MageObject getOtherFace() {
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
        if (!phasedIn && !replaceEvent(EventType.PHASE_IN, game) && (!onlyDirect || !indirectPhase)) {
            this.phasedIn = true;
            this.indirectPhase = false;
            game.informPlayers(getLogName() + " phased in");
            for (UUID attachedId : this.getAttachments()) {
                Permanent attachedPerm = game.getPermanent(attachedId);
                if (attachedPerm != null) {
                    attachedPerm.phaseIn(game, false);
                }
            }
            game.addSimultaneousEvent(GameEvent.getEvent(EventType.PHASED_IN, this.objectId, null, this.controllerId));
            return true;
        }
        return false;
    }

    @Override
    public boolean phaseOut(Game game) {
        return phaseOut(game, false);
    }

    @Override
    public boolean phaseOut(Game game, boolean indirectPhase) {
        if (phasedIn && !replaceEvent(EventType.PHASE_OUT, game)) {
            for (UUID attachedId : this.getAttachments()) {
                Permanent attachedPerm = game.getPermanent(attachedId);
                if (attachedPerm != null) {
                    attachedPerm.phaseOut(game, true);
                }
            }
            this.removeFromCombat(game);
            this.phasedIn = false;
            this.indirectPhase = indirectPhase;
            game.informPlayers(getLogName() + " phased out");
            fireEvent(EventType.PHASED_OUT, game);
            return true;
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

    // Losing control of a ring bearer clear its status.
    public void removeUncontrolledRingBearer(Game game) {
        if (isRingBearer()) {
            Player controller = beforeResetControllerId == null ? null : game.getPlayer(beforeResetControllerId);
            String controllerName = controller == null ? "" : controller.getLogName();
            game.informPlayers(controllerName + " has lost control of " + getLogName() + ". It is no longer a Ring-bearer.");
            this.setRingBearer(game, false);
        }
    }

    @Override
    public boolean checkControlChanged(Game game) {
        if (!controllerId.equals(beforeResetControllerId)) {
            this.removeFromCombat(game);
            this.controlledFromStartOfControllerTurn = false;
            this.removeUncontrolledRingBearer(game);

            this.getAbilities(game).setControllerId(controllerId);
            game.getContinuousEffects().setController(objectId, controllerId);
            // the controller of triggered abilities is always set/checked before the abilities triggers so not needed here
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
            for (Effect value : ability.getEffects(game, EffectType.CONTINUOUS)) {
                ContinuousEffect effect = (ContinuousEffect) value;
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
            // If what this permanent is attached to isn't also a permanent, such as a
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
        if (damageAmount < 1) {
            return 0;
        }
        DamageEvent event = new DamagePermanentEvent(objectId, attackerId, controllerId, damageAmount, preventable, combat);
        event.setAppliedEffects(appliedEffects);
        // Even if no damage was dealt, some watchers would need a reset next time actions are processed.
        // For instance PhantomPreventionWatcher used by the [[Phantom Wurm]] type of replacement effect.
        game.getState().addBatchDamageCouldHaveBeenFired(combat, game);
        if (game.replaceEvent(event)) {
            return 0;
        }
        int actualDamageDone = checkProtectionAbilities(event, attackerId, source, game);
        if (actualDamageDone < 1) {
            return 0;
        }
        int lethal = getLethalDamage(attackerId, game);
        MageObject attacker = game.getObject(attackerId);
        if (this.isCreature(game)) {
            if (checkWither(event, attacker, game)) {
                if (markDamage) {
                    // mark damage only
                    markDamage(CounterType.M1M1.createInstance(actualDamageDone), attacker, true);
                } else {
                    Ability damageSourceAbility = null;
                    if (attacker instanceof Permanent) {
                        damageSourceAbility = ((Permanent) attacker).getSpellAbility();
                    }
                    // deal damage immediately
                    addCounters(CounterType.M1M1.createInstance(actualDamageDone), game.getControllerId(attackerId), damageSourceAbility, game);
                }
            } else {
                this.damage = CardUtil.overflowInc(this.damage, actualDamageDone);
            }
        }
        if (this.isPlaneswalker(game)) {
            int loyalty = getCounters(game).getCount(CounterType.LOYALTY);
            int countersToRemove = Math.min(actualDamageDone, loyalty);
            if (attacker != null && markDamage) {
                markDamage(CounterType.LOYALTY.createInstance(countersToRemove), attacker, false);
            } else {
                removeCounters(CounterType.LOYALTY.getName(), countersToRemove, source, game, true);
            }
        }
        if (this.isBattle(game)) {
            int defense = getCounters(game).getCount(CounterType.DEFENSE);
            int countersToRemove = Math.min(actualDamageDone, defense);
            if (attacker != null && markDamage) {
                markDamage(CounterType.DEFENSE.createInstance(countersToRemove), attacker, false);
            } else {
                removeCounters(CounterType.DEFENSE.getName(), countersToRemove, source, game, true);
            }
        }
        DamagedEvent damagedEvent = new DamagedPermanentEvent(this.getId(), attackerId, this.getControllerId(), actualDamageDone, combat);
        damagedEvent.setExcess(actualDamageDone - lethal);
        game.fireEvent(damagedEvent);
        game.getState().addSimultaneousDamage(damagedEvent, game);
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
                    game.getPermanent(attackerId).markLifelink(actualDamageDone);
                } else {
                    Player player = game.getPlayer(sourceControllerId);
                    player.gainLife(actualDamageDone, game, source);
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
                new SquirrelToken().putOntoBattlefield(actualDamageDone, game, source, player.getId());
            }
            dealtDamageByThisTurn.add(new MageObjectReference(attacker, game));
        }
        if (attacker == null) {
            game.informPlayers(getLogName() + " gets " + actualDamageDone + " damage");
        } else {
            game.informPlayers(attacker.getLogName() + " deals " + actualDamageDone + " damage to " + getLogName());
        }
        return actualDamageDone;
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
                /* Tokens don't have a spellAbility. We must make a phony one as the source so the events in addCounters
                 * can trace the source back to an object/controller.
                 */
                PermanentToken sourceToken = (PermanentToken) mdi.sourceObject;
                source = new SpellAbility(null, sourceToken.name);
                source.setSourceId(sourceToken.objectId);
                source.setControllerId(sourceToken.controllerId);
            } else if (mdi.sourceObject instanceof Permanent) {
                source = ((Permanent) mdi.sourceObject).getSpellAbility();
            }
            if (mdi.addCounters) {
                addCounters(mdi.counter, game.getControllerId(mdi.sourceObject.getId()), source, game);
            } else {
                removeCounters(mdi.counter, source, game, true);
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
                lethal = power.getValue();
            } else {
                lethal = toughness.getValue();
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
        if (this.isBattle(game)) {
            lethal = Math.min(lethal, this.getCounters(game).getCount(CounterType.DEFENSE));
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

        BecomesFaceDownCreatureEffect.FaceDownType faceDownType = BecomesFaceDownCreatureEffect.findFaceDownType(game, this);
        if (faceDownType != null) {
            // remove some attributes here, because first apply effects comes later otherwise abilities (e.g. color related) will unintended trigger
            BecomesFaceDownCreatureEffect.makeFaceDownObject(game, null, this, faceDownType, null);
        }

        // own etb event
        if (game.replaceEvent(new EntersTheBattlefieldEvent(this, source, getControllerId(), fromZone, EnterEventType.SELF))) {
            return false;
        }

        // normal etb event
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
        if (this.isBattle(game)) {
            int defense;
            if (this.getStartingDefense() == -2) {
                defense = source.getManaCostsToPay().getX();
            } else {
                defense = this.getStartingDefense();
            }
            if (defense > 0) {
                this.addCounters(CounterType.DEFENSE.createInstance(defense), source, game);
            }
            this.chooseProtector(game, source);
        }
        if (!fireEvent) {
            return false;
        }
        game.addSimultaneousEvent(event);
        return true;
    }

    @Override
    public boolean canBeTargetedBy(MageObject sourceObject, UUID sourceControllerId, Ability source, Game game) {
        if (sourceObject != null) {
            if (abilities.containsKey(ShroudAbility.getInstance().getId())) {
                if (game.getContinuousEffects().asThough(this.getId(), AsThoughEffectType.SHROUD, null, sourceControllerId, game).isEmpty()) {
                    return false;
                }
            }

            if (game.getPlayer(this.getControllerId()).hasOpponent(sourceControllerId, game)
                    && game.getContinuousEffects().asThough(this.getId(), AsThoughEffectType.HEXPROOF, null, sourceControllerId, game).isEmpty()
                    && abilities.stream()
                    .filter(HexproofBaseAbility.class::isInstance)
                    .map(HexproofBaseAbility.class::cast)
                    .anyMatch(ability -> ability.checkObject(sourceObject, source, game))) {
                return false;
            }

            if (hasProtectionFrom(sourceObject, game)) {
                return false;
            }

            // example: Fiendslayer Paladin tried to target with Ultimate Price
            return !game.getContinuousEffects().preventedByRuleModification(
                    new TargetEvent(this, sourceObject.getId(), sourceControllerId),
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

    @Override
    public boolean destroy(Ability source, Game game) {
        return destroy(source, game, false);
    }

    @Override
    public boolean destroy(Ability source, Game game, boolean noRegen) {
        // Only permanents on the battlefield can be destroyed
        if (!game.getState().getZone(getId()).equals(Zone.BATTLEFIELD)) {
            return false;
        }
        //20091005 - 701.6
        if (abilities.containsKey(IndestructibleAbility.getInstance().getId())) {
            return false;
        }

        if (!game.replaceEvent(GameEvent.getEvent(GameEvent.EventType.DESTROY_PERMANENT, objectId, source, controllerId, noRegen ? 1 : 0))) {
            // this means destroy was successful, if object movement to graveyard will be replaced (e.g. commander to command zone) it's still
            // handled as successful destroying (but not as successful "dies this way" for destroying).
            if (moveToZone(Zone.GRAVEYARD, source, game, false)) {
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
                game.fireEvent(GameEvent.getEvent(GameEvent.EventType.DESTROYED_PERMANENT, objectId, source, controllerId));
            }
            return true;
        }
        return false;
    }

    @Override
    public boolean sacrifice(Ability source, Game game) {
        //20091005 - 701.13
        if (isPhasedIn() && canBeSacrificed && !game.replaceEvent(GameEvent.getEvent(GameEvent.EventType.SACRIFICE_PERMANENT, objectId, source, controllerId))) {
            // Commander replacement effect or Rest in Peace (exile instead of graveyard) in play does not prevent successful sacrifice
            // so the return value of the moveToZone is not taken into account here
            moveToZone(Zone.GRAVEYARD, source, game, false);
            Player player = game.getPlayer(getControllerId());
            if (player != null) {
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
    public boolean canBeAttacked(UUID attackerId, UUID defendingPlayerId, Game game) {
        if (isPlaneswalker(game)) {
            return isControlledBy(defendingPlayerId);
        }
        if (isBattle(game)) {
            return isProtectedBy(defendingPlayerId);
        }
        return false;
    }

    @Override
    public boolean canAttackInPrinciple(UUID defenderId, Game game) {
        if (isBattle(game)) {
            // battles can never attack
            return false;
        }
        Set<ApprovingObject> approvingObjects = game.getContinuousEffects().asThough(
                this.objectId, AsThoughEffectType.ATTACK_AS_HASTE, null, defenderId, game
        );
        if (hasSummoningSickness() && approvingObjects.isEmpty()) {
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
                || !game.getContinuousEffects().asThough(this.objectId, AsThoughEffectType.ATTACK, null, this.getControllerId(), game).isEmpty();
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
        if (tapped && game.getState().getContinuousEffects().asThough(this.getId(), AsThoughEffectType.BLOCK_TAPPED, null, this.getControllerId(), game).isEmpty() || isBattle(game) || isSuspected()) {
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
        if (tapped && game.getState().getContinuousEffects().asThough(this.getId(), AsThoughEffectType.BLOCK_TAPPED, null, this.getControllerId(), game).isEmpty()) {
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
        } else if (this.isPlaneswalker(game) && game.getCombat().getDefenders().contains(getId())) {
            game.getCombat().removeDefendingPermanentFromCombat(objectId, game);
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
        return this.connectedCards.getOrDefault("imprint", emptyList);
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
    public boolean isPrototyped() {
        return this.prototyped;
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
    public boolean isSuspected() {
        return suspected;
    }

    private static final String suspectedInfoKey = "IS_SUSPECTED";

    @Override
    public void setSuspected(boolean value, Game game, Ability source) {
        if (!value || !game.replaceEvent(GameEvent.getEvent(
                EventType.BECOME_SUSPECTED, getId(),
                source, source.getControllerId()
        ))) {
            this.suspected = value;
        }
        if (this.suspected) {
            addInfo(suspectedInfoKey, CardUtil.addToolTipMarkTags("Suspected (has menace and can't block)"), game);
        } else {
            addInfo(suspectedInfoKey, null, game);
        }
    }

    // Used as key for the ring bearer info.
    private static final String ringbearerInfoKey = "IS_RINGBEARER";

    @Override
    public void setRingBearer(Game game, boolean value) {
        if (value == this.ringBearerFlag) {
            return;
        }

        if (value) {
            // The player may have another Ringbearer. We need to clear it if so.
            //
            // 701.52a Certain spells and abilities have the text “the Ring tempts you.”
            // Each time the Ring tempts you, choose a creature you control.
            // That creature becomes your Ring-bearer until another creature
            // becomes your Ring-bearer or another player gains control of it.
            Player player = game.getPlayer(getControllerId());
            String playername = "";
            if (player != null) {
                playername = player.getLogName();
                Permanent existingRingbearer = player.getRingBearer(game);
                if (existingRingbearer != null && existingRingbearer.getId() != this.getId()) {
                    existingRingbearer.setRingBearer(game, false);
                }
            }

            addInfo(ringbearerInfoKey, CardUtil.addToolTipMarkTags("Is " + playername + "'s Ring-bearer"), game);
        } else {
            addInfo(ringbearerInfoKey, null, game);
        }

        this.ringBearerFlag = value;
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
    public void chooseProtector(Game game, Ability source) {
        Set<UUID> opponents = game.getOpponents(this.getControllerId());
        Player controller = game.getPlayer(this.getControllerId());
        if (controller == null) {
            return;
        }

        Player newProtector;
        if (opponents.size() > 1) {
            TargetPlayer target = new TargetPlayer(new FilterOpponent("protector for " + getName()));
            target.withNotTarget(true);
            target.setRequired(true);
            controller.choose(Outcome.Neutral, target, source, game);
            newProtector = game.getPlayer(target.getFirstTarget());
        } else if (opponents.size() == 1) {
            newProtector = game.getPlayer(opponents.iterator().next());
        } else {
            newProtector = null;
        }

        if (newProtector != null) {
            String protectorName = newProtector.getLogName();
            game.informPlayers(protectorName + " has been chosen to protect " + this.getLogName());
            this.addInfo("protector", "Protected by " + protectorName, game);
            this.setProtectorId(newProtector.getId());
        } else {
            game.informPlayers(controller.getLogName() + " remains in protect of " + this.getLogName() + " as there are no opponents for new protector");
        }
    }

    @Override
    public void setProtectorId(UUID protectorId) {
        this.protectorId = protectorId;
    }

    @Override
    public UUID getProtectorId() {
        return protectorId;
    }

    @Override
    public boolean isProtectedBy(UUID playerId) {
        return protectorId != null && protectorId.equals(playerId);
    }

    @Override
    public void setCanBeSacrificed(boolean canBeSacrificed) {
        this.canBeSacrificed = canBeSacrificed;
    }

    @Override
    public boolean canBeSacrificed() {
        return canBeSacrificed;
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
    public boolean isCloaked() {
        return cloaked;
    }

    @Override
    public void setCloaked(boolean value) {
        cloaked = value;
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
    public boolean isDisguised() {
        return disguised;
    }

    @Override
    public void setDisguised(boolean value) {
        disguised = value;
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

    public void setPrototyped(boolean prototyped) {
        this.prototyped = prototyped;
    }

    @Override
    public boolean isRingBearer() {
        return ringBearerFlag;
    }

    @Override
    public boolean isSolved() {
        return solved;
    }

    @Override
    public boolean solve(Game game, Ability source) {
        if (this.solved) {
            return false;
        }
        GameEvent event = new GameEvent(GameEvent.EventType.SOLVE_CASE, getId(),
                source, source.getControllerId());
        if (game.replaceEvent(event)) {
            return false;
        }
        Player controller = game.getPlayer(source.getControllerId());
        if (controller != null) {
            game.informPlayers(controller.getLogName() + " solved " + this.getLogName() +
                    CardUtil.getSourceLogName(game, source));
        }

        this.solved = true;
        game.fireEvent(new GameEvent(EventType.CASE_SOLVED, getId(), source,
                source.getControllerId()));
        return true;
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
            if (successfullyMoved) {
                detachAllAttachments(game);
            }
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
        if (successfullyMoved) {
            detachAllAttachments(game);
        }
        return successfullyMoved;
    }
}
