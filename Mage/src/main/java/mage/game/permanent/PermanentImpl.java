package mage.game.permanent;

import mage.MageObject;
import mage.MageObjectReference;
import mage.ObjectColor;
import mage.abilities.Abilities;
import mage.abilities.Ability;
import mage.abilities.SpellAbility;
import mage.abilities.effects.ContinuousEffect;
import mage.abilities.effects.Effect;
import mage.abilities.effects.RestrictionEffect;
import mage.abilities.hint.Hint;
import mage.abilities.hint.HintUtils;
import mage.abilities.keyword.*;
import mage.abilities.text.TextPart;
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

import java.util.*;

/**
 * @author BetaSteward_at_googlemail.com
 */
public abstract class PermanentImpl extends CardImpl implements Permanent {

    private static final Logger logger = Logger.getLogger(PermanentImpl.class);

    static class MarkedDamageInfo {

        public MarkedDamageInfo(Counter counter, MageObject sourceObject) {
            this.counter = counter;
            this.sourceObject = sourceObject;
        }

        Counter counter;
        MageObject sourceObject;
    }

    private static final ThreadLocalStringBuilder threadLocalBuilder = new ThreadLocalStringBuilder(300);

    protected boolean tapped;
    protected boolean flipped;
    protected boolean transformed;
    protected boolean monstrous;
    protected boolean renowned;
    protected boolean manifested = false;
    protected boolean morphed = false;
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
    protected boolean removedFromCombat;
    protected boolean deathtouched;

    protected Map<String, List<UUID>> connectedCards = new HashMap<>();
    protected Set<MageObjectReference> dealtDamageByThisTurn;
    protected UUID attachedTo;
    protected int attachedToZoneChangeCounter;
    protected MageObjectReference pairedPermanent;
    protected List<UUID> bandedCards = new ArrayList<>();
    protected Counters counters;
    protected List<MarkedDamageInfo> markedDamage;
    protected int timesLoyaltyUsed = 0;
    protected Map<String, String> info;
    protected int createOrder;

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

        for (Map.Entry<String, List<UUID>> entry : permanent.connectedCards.entrySet()) {
            this.connectedCards.put(entry.getKey(), entry.getValue());
        }
        if (permanent.dealtDamageByThisTurn != null) {
            dealtDamageByThisTurn = new HashSet<>(permanent.dealtDamageByThisTurn);
        }
        if (permanent.markedDamage != null) {
            markedDamage = new ArrayList<>();
            for (MarkedDamageInfo mdi : permanent.markedDamage) {
                markedDamage.add(new MarkedDamageInfo(mdi.counter.copy(), mdi.sourceObject));
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
        this.pairedPermanent = permanent.pairedPermanent;
        this.bandedCards.addAll(permanent.bandedCards);
        this.timesLoyaltyUsed = permanent.timesLoyaltyUsed;

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
        for (TextPart textPart : textParts) {
            textPart.reset();
        }
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
            List<String> rules = getRules();

            // info
            if (info != null) {
                for (String data : info.values()) {
                    rules.add(data);
                }
            }

            // ability hints
            List<String> abilityHints = new ArrayList<>();
            if (HintUtils.ABILITY_HINTS_ENABLE) {
                for (Ability ability : abilities) {
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
                for (Map.Entry<RestrictionEffect, Set<Ability>> entry : game.getContinuousEffects().getApplicableRestrictionEffects(this, game).entrySet()) {
                    for (Ability ability : entry.getValue()) {
                        if (!entry.getKey().applies(this, ability, game)) {
                            continue;
                        }

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

                        if (!entry.getKey().canTransform(this, ability, game, false)) {
                            restrictHints.add(HintUtils.prepareText("Can't transform" + addSourceObjectName(game, ability), null, HintUtils.HINT_ICON_RESTRICT));
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
            return rulesError;
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
        return abilities;
    }

    @Override
    public Abilities<Ability> getAbilities(Game game) {
        return abilities;
    }

    /**
     * @param ability
     * @param game
     */
    @Override
    public void addAbility(Ability ability, Game game) {
        if (!abilities.containsKey(ability.getId())) {
            Ability copyAbility = ability.copy();
            copyAbility.setControllerId(controllerId);
            copyAbility.setSourceId(objectId);
            if (game != null) {
                game.getState().addAbility(copyAbility, this);
            }
            abilities.add(copyAbility);
        }
    }

    @Override
    public void addAbility(Ability ability, UUID sourceId, Game game) {
        addAbility(ability, sourceId, game, true);
    }

    @Override
    public void addAbility(Ability ability, UUID sourceId, Game game, boolean createNewId) {
        if (!abilities.containsKey(ability.getId())) {
            Ability copyAbility = ability.copy();
            if (createNewId) {
                copyAbility.newId(); // needed so that source can get an ability multiple times (e.g. Raging Ravine)
            }
            copyAbility.setControllerId(controllerId);
            copyAbility.setSourceId(objectId);
            game.getState().addAbility(copyAbility, sourceId, this);
            abilities.add(copyAbility);
        } else if (!createNewId) {
            // triggered abilities must be added to the state().triggerdAbilities
            // still as long as the prev. permanent is known to the LKI (e.g. Showstopper) so gained dies triggered ability will trigger
            if (!game.getBattlefield().containsPermanent(this.getId())) {
                Ability copyAbility = ability.copy();
                copyAbility.setControllerId(controllerId);
                copyAbility.setSourceId(objectId);
                game.getState().addAbility(copyAbility, sourceId, this);
            }
        }
    }

    @Override
    public void removeAllAbilities(UUID sourceId, Game game) {
        getAbilities().clear();
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
    public void addLoyaltyUsed() {
        this.timesLoyaltyUsed++;
    }

    @Override
    public boolean canLoyaltyBeUsed(Game game) {
        Player controller = game.getPlayer(controllerId);
        if (controller != null) {
            return controller.getLoyaltyUsePerTurn() > timesLoyaltyUsed;
        }
        return false;
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
    public boolean canTap() {
        return !isCreature() || !hasSummoningSickness();
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
    public boolean tap(Game game) {
        return tap(false, game);
    }

    @Override
    public boolean tap(boolean forCombat, Game game) {
        //20091005 - 701.15a
        if (!tapped) {
            if (!replaceEvent(EventType.TAP, game)) {
                this.tapped = true;
                game.fireEvent(new GameEvent(EventType.TAPPED, objectId, ownerId, controllerId, 0, forCombat));
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
    public boolean unflip(Game game) {
        if (flipped) {
            if (!replaceEvent(EventType.UNFLIP, game)) {
                this.flipped = false;
                fireEvent(EventType.UNFLIPPED, game);
                return true;
            }
        }
        return false;
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
    public boolean transform(Game game) {
        if (transformable) {
            if (!replaceEvent(EventType.TRANSFORM, game)) {
                setTransformed(!transformed);
                game.applyEffects();
                game.addSimultaneousEvent(GameEvent.getEvent(EventType.TRANSFORMED, getId(), getControllerId()));
                return true;
            }
        }
        return false;
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
    public boolean isRemovedFromCombat() {
        return removedFromCombat;
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
    public boolean changeControllerId(UUID controllerId, Game game) {
        Player newController = game.getPlayer(controllerId);

        GameEvent loseControlEvent = GameEvent.getEvent(GameEvent.EventType.LOSE_CONTROL, this.getId(), null, controllerId);

        if (game.replaceEvent(loseControlEvent)) {
            return false;
        }

        if (newController != null && (!newController.hasLeft() || !newController.hasLost())) {
            this.controllerId = controllerId;
            return true;
        }
        return false;
    }

    @Override
    public boolean checkControlChanged(Game game) {
        if (!controllerId.equals(beforeResetControllerId)) {
            this.removeFromCombat(game);
            this.controlledFromStartOfControllerTurn = false;

            this.getAbilities(game).setControllerId(controllerId);
            game.getContinuousEffects().setController(objectId, controllerId);
            // the controller of triggered abilites is always set/checked before the abilities triggers so not needed here
            game.fireEvent(new GameEvent(EventType.LOST_CONTROL, objectId, objectId, beforeResetControllerId));
            game.fireEvent(new GameEvent(EventType.GAINED_CONTROL, objectId, objectId, controllerId));

            return true;
        } else if (isCopy()) {// Because the previous copied abilities can be from another controller chnage controller in any case for abilities
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
        this.addInfo("attachedToCard", null, game);
    }

    @Override
    public void attachTo(UUID attachToObjectId, Game game) {
        if (this.attachedTo != null && !Objects.equals(this.attachedTo, attachToObjectId)) {
            Permanent attachedToUntilNowObject = game.getPermanent(this.attachedTo);
            if (attachedToUntilNowObject != null) {
                attachedToUntilNowObject.removeAttachment(this.objectId, game);
            } else {
                Card attachedToUntilNowCard = game.getCard(this.attachedTo);
                if (attachedToUntilNowCard != null) {
                    attachedToUntilNowCard.removeAttachment(this.objectId, game);
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
        if (getSpellAbility() == null) {
            // Can happen e.g. for Token Equipments like Stoneforged Blade
            return;
        }
        if (!getSpellAbility().getTargets().isEmpty() && (getSpellAbility().getTargets().get(0) instanceof TargetCard)) {
            Card attachedToCard = game.getCard(this.getAttachedTo());
            if (attachedToCard != null) {
                // Because cards are not on the battlefield, the relation has to be shown in the card tooltip (e.g. the enchanted card in graveyard)
                this.addInfo("attachedToCard", CardUtil.addToolTipMarkTags("Enchanted card: " + attachedToCard.getIdName()), game);
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
    public int damage(int damage, UUID sourceId, Game game) {
        return damage(damage, sourceId, game, true, false, false, null);
    }

    @Override
    public int damage(int damage, UUID sourceId, Game game, boolean combat, boolean preventable) {
        return damage(damage, sourceId, game, preventable, combat, false, null);
    }

    @Override
    public int damage(int damage, UUID sourceId, Game game, boolean combat, boolean preventable, List<UUID> appliedEffects) {
        return damage(damage, sourceId, game, preventable, combat, false, appliedEffects);
    }

    /**
     * @param damageAmount
     * @param sourceId
     * @param game
     * @param preventable
     * @param combat
     * @param markDamage   If true, damage will be dealt later in applyDamage
     *                     method
     * @return
     */
    private int damage(int damageAmount, UUID sourceId, Game game, boolean preventable, boolean combat, boolean markDamage, List<UUID> appliedEffects) {
        int damageDone = 0;
        if (damageAmount > 0 && canDamage(game.getObject(sourceId), game)) {
            if (this.isPlaneswalker()) {
                damageDone = damagePlaneswalker(damageAmount, sourceId, game, preventable, combat, markDamage, appliedEffects);
            } else {
                damageDone = damageCreature(damageAmount, sourceId, game, preventable, combat, markDamage, appliedEffects);
            }
            if (damageDone > 0) {
                UUID sourceControllerId = null;
                Abilities sourceAbilities = null;
                MageObject source = game.getPermanentOrLKIBattlefield(sourceId);
                if (source == null) {
                    StackObject stackObject = game.getStack().getStackObject(sourceId);
                    if (stackObject != null) {
                        source = stackObject.getStackAbility().getSourceObject(game);
                    } else {
                        source = game.getObject(sourceId);
                    }
                    if (source instanceof Spell) {
                        sourceAbilities = ((Spell) source).getAbilities(game);
                        sourceControllerId = ((Spell) source).getControllerId();
                    } else if (source instanceof Card) {
                        sourceAbilities = ((Card) source).getAbilities(game);
                        sourceControllerId = ((Card) source).getOwnerId();
                    } else if (source instanceof CommandObject) {
                        sourceControllerId = ((CommandObject) source).getControllerId();
                        sourceAbilities = source.getAbilities();
                    } else {
                        source = null;
                    }
                } else {
                    sourceAbilities = ((Permanent) source).getAbilities(game);
                    sourceControllerId = ((Permanent) source).getControllerId();
                }
                if (source != null && sourceAbilities != null) {
                    if (sourceAbilities.containsKey(LifelinkAbility.getInstance().getId())) {
                        Player player = game.getPlayer(sourceControllerId);
                        player.gainLife(damageDone, game, sourceId);
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
                        new SquirrelToken().putOntoBattlefield(damageDone, game, sourceId, player.getId());
                    }
                    dealtDamageByThisTurn.add(new MageObjectReference(source, game));
                }
                if (source == null) {
                    game.informPlayers(getLogName() + " gets " + damageDone + " damage");
                } else {
                    game.informPlayers(source.getLogName() + " deals " + damageDone + " damage to " + getLogName());
                }
            }
        }
        return damageDone;
    }

    @Override
    public int markDamage(int damageAmount, UUID sourceId, Game game, boolean preventable, boolean combat) {
        return damage(damageAmount, sourceId, game, preventable, combat, true, null);
    }

    @Override
    public int applyDamage(Game game) {
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
            addCounters(mdi.counter, source, game);
        }
        markedDamage.clear();
        return 0;
    }

    @Override
    public void removeAllDamage(Game game) {
        damage = 0;
        deathtouched = false;
    }

    protected int damagePlaneswalker(int damage, UUID sourceId, Game game, boolean preventable, boolean combat, boolean markDamage, List<UUID> appliedEffects) {
        GameEvent event = new DamagePlaneswalkerEvent(objectId, sourceId, controllerId, damage, preventable, combat);
        event.setAppliedEffects(appliedEffects);
        if (!game.replaceEvent(event)) {
            int actualDamage = event.getAmount();
            if (actualDamage > 0) {
                int countersToRemove = actualDamage;
                if (countersToRemove > getCounters(game).getCount(CounterType.LOYALTY)) {
                    countersToRemove = getCounters(game).getCount(CounterType.LOYALTY);
                }
                removeCounters(CounterType.LOYALTY.getName(), countersToRemove, game);
                game.fireEvent(new DamagedPlaneswalkerEvent(objectId, sourceId, controllerId, actualDamage, combat));
                return actualDamage;
            }
        }
        return 0;
    }

    protected int damageCreature(int damage, UUID sourceId, Game game, boolean preventable, boolean combat, boolean markDamage, List<UUID> appliedEffects) {
        GameEvent event = new DamageCreatureEvent(objectId, sourceId, controllerId, damage, preventable, combat);
        event.setAppliedEffects(appliedEffects);
        if (!game.replaceEvent(event)) {
            int actualDamage = checkProtectionAbilities(event, sourceId, game);
            if (actualDamage > 0) {
                //Permanent source = game.getPermanent(sourceId);
                MageObject source = game.getObject(sourceId);
                if (source != null && (source.getAbilities().containsKey(InfectAbility.getInstance().getId())
                        || source.getAbilities().containsKey(WitherAbility.getInstance().getId()))) {
                    if (markDamage) {
                        // mark damage only
                        markDamage(CounterType.M1M1.createInstance(actualDamage), source);
                    } else {
                        Ability damageSourceAbility = null;
                        if (source instanceof Permanent) {
                            damageSourceAbility = ((Permanent) source).getSpellAbility();
                        }
                        // deal damage immediately
                        addCounters(CounterType.M1M1.createInstance(actualDamage), damageSourceAbility, game);
                    }
                } else {
                    // this.damage += actualDamage;
                    this.damage = CardUtil.addWithOverflowCheck(this.damage, actualDamage);
                }
                game.fireEvent(new DamagedCreatureEvent(objectId, sourceId, controllerId, actualDamage, combat));
                return actualDamage;
            }
        }
        return 0;
    }

    private int checkProtectionAbilities(GameEvent event, UUID sourceId, Game game) {
        MageObject source = game.getObject(sourceId);
        if (source != null && hasProtectionFrom(source, game)) {
            GameEvent preventEvent = new GameEvent(GameEvent.EventType.PREVENT_DAMAGE, this.objectId, sourceId, this.controllerId, event.getAmount(), false);
            if (!game.replaceEvent(preventEvent)) {
                int preventedDamage = event.getAmount();
                event.setAmount(0);
                game.fireEvent(GameEvent.getEvent(GameEvent.EventType.PREVENTED_DAMAGE, this.objectId, sourceId, this.controllerId, preventedDamage));
                return 0;
            }
        }
        return event.getAmount();
    }

    private void markDamage(Counter counter, MageObject source) {
        if (markedDamage == null) {
            markedDamage = new ArrayList<>();
        }
        markedDamage.add(new MarkedDamageInfo(counter, source));
    }

    @Override
    public boolean entersBattlefield(UUID sourceId, Game game, Zone fromZone, boolean fireEvent) {
        controlledFromStartOfControllerTurn = false;
        if (this.isFaceDown(game)) {
            // remove some attributes here, because first apply effects comes later otherwise abilities (e.g. color related) will unintended trigger
            MorphAbility.setPermanentToFaceDownCreature(this);
        }

        EntersTheBattlefieldEvent event = new EntersTheBattlefieldEvent(this, sourceId, getControllerId(), fromZone, EnterEventType.SELF);
        if (game.replaceEvent(event)) {
            return false;
        }
        event = new EntersTheBattlefieldEvent(this, sourceId, getControllerId(), fromZone);
        if (!game.replaceEvent(event)) {
            if (fireEvent) {
                game.addSimultaneousEvent(event);
                return true;
            }

        }
        return false;
    }

    @Override
    public boolean canBeTargetedBy(MageObject source, UUID sourceControllerId, Game game) {
        if (source != null) {
            if (abilities.containsKey(ShroudAbility.getInstance().getId())) {
                if (null == game.getContinuousEffects().asThough(this.getId(), AsThoughEffectType.SHROUD, null, sourceControllerId, game)) {
                    return false;
                }
            }
            if (abilities.containsKey(HexproofAbility.getInstance().getId())) {
                if (game.getPlayer(this.getControllerId()).hasOpponent(sourceControllerId, game)
                        && null == game.getContinuousEffects().asThough(this.getId(), AsThoughEffectType.HEXPROOF, null, sourceControllerId, game)) {
                    return false;
                }
            }

            if (abilities.containsKey(HexproofFromWhiteAbility.getInstance().getId())) {
                if (game.getPlayer(this.getControllerId()).hasOpponent(sourceControllerId, game)
                        && null == game.getContinuousEffects().asThough(this.getId(), AsThoughEffectType.HEXPROOF, null, sourceControllerId, game)
                        && source.getColor(game).isWhite()) {
                    return false;
                }
            }

            if (abilities.containsKey(HexproofFromBlueAbility.getInstance().getId())) {
                if (game.getPlayer(this.getControllerId()).hasOpponent(sourceControllerId, game)
                        && null == game.getContinuousEffects().asThough(this.getId(), AsThoughEffectType.HEXPROOF, null, sourceControllerId, game)
                        && source.getColor(game).isBlue()) {
                    return false;
                }
            }

            if (abilities.containsKey(HexproofFromBlackAbility.getInstance().getId())) {
                if (game.getPlayer(this.getControllerId()).hasOpponent(sourceControllerId, game)
                        && null == game.getContinuousEffects().asThough(this.getId(), AsThoughEffectType.HEXPROOF, null, sourceControllerId, game)
                        && source.getColor(game).isBlack()) {
                    return false;
                }
            }

            if (abilities.containsKey(HexproofFromMonocoloredAbility.getInstance().getId())) {
                if (game.getPlayer(this.getControllerId()).hasOpponent(sourceControllerId, game)
                        && null == game.getContinuousEffects().asThough(this.getId(), AsThoughEffectType.HEXPROOF, null, sourceControllerId, game)
                        && !source.getColor(game).isColorless() && !source.getColor(game).isMulticolored()) {
                    return false;
                }
            }

            if (hasProtectionFrom(source, game)) {
                return false;
            }
            // needed to get the correct possible targets if target rule modification effects are active
            // e.g. Fiendslayer Paladin tried to target with Ultimate Price
            return !game.getContinuousEffects().preventedByRuleModification(GameEvent.getEvent(EventType.TARGET, this.getId(), source.getId(), sourceControllerId), null, game, true);
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
    public boolean cantBeAttachedBy(MageObject source, Game game) {
        for (ProtectionAbility ability : this.getAbilities(game).getProtectionAbilities()) {
            if (!(source.hasSubtype(SubType.AURA, game)
                    && !ability.removesAuras())
                    && !source.getId().equals(ability.getAuraIdNotToBeRemoved())
                    && !ability.canTarget(source, game)) {
                return true;
            }
        }
        return game.getContinuousEffects().preventedByRuleModification(GameEvent.getEvent(EventType.STAY_ATTACHED, objectId, source.getId(), null), null, game, false);
    }

    protected boolean canDamage(MageObject source, Game game) {
        //noxx: having protection doesn't prevents from dealing damage
        // instead it adds damage prevention
        //return (!hasProtectionFrom(source, game));
        return true;
    }

    @Override
    public boolean destroy(UUID sourceId, Game game, boolean noRegen) {
        //20091005 - 701.6
        if (abilities.containsKey(IndestructibleAbility.getInstance().getId())) {
            return false;
        }

        if (!game.replaceEvent(GameEvent.getEvent(EventType.DESTROY_PERMANENT, objectId, sourceId, controllerId, noRegen ? 1 : 0))) {
            // this means destroy was successful, if object movement to graveyard will be replaced (e.g. commander to command zone) its still
            // is handled as successful destroying (but not as sucessful "dies this way" for destroying).
            if (moveToZone(Zone.GRAVEYARD, sourceId, game, false)) {
                if (!game.isSimulation()) {
                    String logName;
                    Card card = game.getCard(this.getId());
                    if (card != null) {
                        logName = card.getLogName();
                    } else {
                        logName = this.getLogName();
                    }
                    if (this.isCreature()) {
                        game.informPlayers(logName + " died");
                    } else {
                        game.informPlayers(logName + " was destroyed");
                    }
                }
                game.fireEvent(GameEvent.getEvent(EventType.DESTROYED_PERMANENT, objectId, sourceId, controllerId));
            }
            return true;
        }
        return false;
    }

    @Override
    public boolean sacrifice(UUID sourceId, Game game) {
        //20091005 - 701.13
        if (isPhasedIn() && !game.replaceEvent(GameEvent.getEvent(EventType.SACRIFICE_PERMANENT, objectId, sourceId, controllerId))) {
            // Commander replacement effect or Rest in Peace (exile instead of graveyard) in play does not prevent successful sacrifice
            // so the return value of the moveToZone is not taken into account here
            moveToZone(Zone.GRAVEYARD, sourceId, game, false);
            Player player = game.getPlayer(getControllerId());
            if (player != null && !game.isSimulation()) {
                game.informPlayers(player.getLogName() + " sacrificed " + this.getLogName());
            }
            game.fireEvent(GameEvent.getEvent(EventType.SACRIFICED_PERMANENT, objectId, sourceId, controllerId));
            return true;
        }
        return false;
    }

    @Override
    public boolean regenerate(UUID sourceId, Game game) {
        //20110930 - 701.12
        if (!game.replaceEvent(GameEvent.getEvent(EventType.REGENERATE, objectId, sourceId, controllerId))) {
            this.tap(game);
            this.removeFromCombat(game);
            this.removeAllDamage(game);
            game.fireEvent(GameEvent.getEvent(EventType.REGENERATED, objectId, sourceId, controllerId));
            return true;
        }
        return false;
    }

    @Override
    public void addPower(int power) {
        this.power.boostValue(power);
    }

    @Override
    public void addToughness(int toughness) {
        this.toughness.boostValue(toughness);
    }

    protected void fireEvent(EventType eventType, Game game) {
        game.fireEvent(GameEvent.getEvent(eventType, this.objectId, ownerId)); // controllerId seems to me more logical (LevelX2)
    }

    protected boolean replaceEvent(EventType eventType, Game game) {
        return game.replaceEvent(GameEvent.getEvent(eventType, this.objectId, ownerId));// controllerId seems to me more logical (LevelX2)
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
        if (hasSummoningSickness()
                && null == game.getContinuousEffects().asThough(this.objectId, AsThoughEffectType.ATTACK_AS_HASTE, null, this.getControllerId(), game)) {
            return false;
        }
        //20101001 - 508.1c
        if (defenderId == null) {
            boolean oneCanBeAttacked = false;
            for (UUID defenderToCheckId : game.getCombat().getDefenders()) {
                if (canAttackCheckRestrictionEffects(defenderToCheckId, game)) {
                    oneCanBeAttacked = true;
                    break;
                }
            }
            if (!oneCanBeAttacked) {
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
    public boolean canTransform(Ability source, Game game) {
        if (transformable) {
            for (Map.Entry<RestrictionEffect, Set<Ability>> entry : game.getContinuousEffects().getApplicableRestrictionEffects(this, game).entrySet()) {
                RestrictionEffect effect = entry.getKey();
                for (Ability ability : entry.getValue()) {
                    if (!effect.canTransform(this, ability, game, true)) {
                        return false;
                    }
                }
            }
        }
        return transformable;
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
    public boolean removeFromCombat(Game game, boolean withInfo) {
        if (this.isAttacking() || this.blocking > 0) {
            return game.getCombat().removeFromCombat(objectId, game, withInfo);
        } else if (this.isPlaneswalker()) {
            if (game.getCombat().getDefenders().contains(getId())) {
                game.getCombat().removePlaneswalkerFromCombat(objectId, game, withInfo);
            }
        }
        return false;
    }

    @Override
    public void setRemovedFromCombat(boolean removedFromCombat) {
        this.removedFromCombat = removedFromCombat;
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
        game.fireEvent(GameEvent.getEvent(GameEvent.EventType.FIGHTED_PERMANENT, fightTarget.getId(), getId(), source.getControllerId()));
        game.fireEvent(GameEvent.getEvent(GameEvent.EventType.FIGHTED_PERMANENT, getId(), fightTarget.getId(), source.getControllerId()));
        damage(fightTarget.getPower().getValue(), fightTarget.getId(), game, false, true);
        fightTarget.damage(getPower().getValue(), getId(), game, false, true);
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
                if (attachment.hasSubtype(SubType.AURA, game) && attachmentCard.isCreature()) {
                    BestowAbility.becomeCreature(attachment, game);
                }
            }
        }
    }

    @Override
    public boolean moveToZone(Zone toZone, UUID sourceId, Game game, boolean flag, List<UUID> appliedEffects) {
        Zone fromZone = game.getState().getZone(objectId);
        Player controller = game.getPlayer(controllerId);
        if (controller != null) {
            ZoneChangeEvent event = new ZoneChangeEvent(this, sourceId, controllerId, fromZone, toZone, appliedEffects);
            ZoneChangeInfo zoneChangeInfo;
            if (toZone == Zone.LIBRARY) {
                zoneChangeInfo = new ZoneChangeInfo.Library(event, flag /* put on top */);
            } else {
                zoneChangeInfo = new ZoneChangeInfo(event);
            }
            boolean successfullyMoved = ZonesHandler.moveCard(zoneChangeInfo, game);
            //20180810 - 701.3d
            detachAllAttachments(game);
            return successfullyMoved;
        }
        return false;
    }

    @Override
    public boolean moveToExile(UUID exileId, String name, UUID sourceId, Game game, List<UUID> appliedEffects) {
        Zone fromZone = game.getState().getZone(objectId);
        ZoneChangeEvent event = new ZoneChangeEvent(this, sourceId, ownerId, fromZone, Zone.EXILED, appliedEffects);
        ZoneChangeInfo.Exile info = new ZoneChangeInfo.Exile(event, exileId, name);

        boolean successfullyMoved = ZonesHandler.moveCard(info, game);
        //20180810 - 701.3d
        detachAllAttachments(game);
        return successfullyMoved;
    }

}
