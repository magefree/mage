package mage.game.events;

import mage.ApprovingObject;
import mage.MageIdentifier;
import mage.abilities.Ability;
import mage.constants.Zone;

import java.io.Serializable;
import java.util.ArrayList;
import java.util.List;
import java.util.UUID;

/**
 * @author BetaSteward_at_googlemail.com
 */
public class GameEvent implements Serializable {

    protected EventType type;
    protected UUID id;
    protected UUID targetId;
    protected UUID sourceId; // TODO: check sourceId usage in all events, it must gets sourceId from source ability only, not other values
    protected UUID playerId;
    protected int amount;
    // flags:
    // for counters: event is result of effect (+1 from planeswalkers is cost, not effect)
    // for combat damage: event is preventable damage
    // for discard: event is result of effect (1) or result of cost (0)
    // for prevent damage: try to prevent combat damage (1) or other damage (0)
    // for tapped: is it tapped for combat (1) or for another reason (0)
    protected boolean flag;
    protected String data;
    protected Zone zone;
    protected List<UUID> appliedEffects = new ArrayList<>();
    protected ApprovingObject approvingObject; // e.g. the approving object for casting a spell from non hand zone
    protected UUID customEventType = null;

    public enum EventType {

        //Game events
        BEGINNING,
        PREVENT_DAMAGE, PREVENTED_DAMAGE,
        //Turn-based events
        PLAY_TURN, EXTRA_TURN,
        CHANGE_PHASE, PHASE_CHANGED,
        CHANGE_STEP, STEP_CHANGED,
        BEGINNING_PHASE, BEGINNING_PHASE_PRE, BEGINNING_PHASE_POST,
        UNTAP_STEP_PRE, UNTAP_STEP, UNTAP_STEP_POST,
        UPKEEP_STEP_PRE, UPKEEP_STEP, UPKEEP_STEP_POST,
        DRAW_STEP_PRE, DRAW_STEP, DRAW_STEP_POST,
        PRECOMBAT_MAIN_PHASE, PRECOMBAT_MAIN_PHASE_PRE, PRECOMBAT_MAIN_PHASE_POST,
        PRECOMBAT_MAIN_STEP_PRE, PRECOMBAT_MAIN_STEP, PRECOMBAT_MAIN_STEP_POST,
        COMBAT_PHASE, COMBAT_PHASE_PRE, COMBAT_PHASE_POST,
        BEGIN_COMBAT_STEP_PRE, BEGIN_COMBAT_STEP, BEGIN_COMBAT_STEP_POST,
        DECLARE_ATTACKERS_STEP_PRE, DECLARE_ATTACKERS_STEP, DECLARE_ATTACKERS_STEP_POST,
        DECLARE_BLOCKERS_STEP_PRE, DECLARE_BLOCKERS_STEP, DECLARE_BLOCKERS_STEP_POST,
        COMBAT_DAMAGE_STEP, COMBAT_DAMAGE_STEP_PRE, COMBAT_DAMAGE_STEP_PRIORITY, COMBAT_DAMAGE_STEP_POST,
        END_COMBAT_STEP_PRE, END_COMBAT_STEP, END_COMBAT_STEP_POST,
        POSTCOMBAT_MAIN_PHASE, POSTCOMBAT_MAIN_PHASE_PRE, POSTCOMBAT_MAIN_PHASE_POST,
        POSTCOMBAT_MAIN_STEP_PRE, POSTCOMBAT_MAIN_STEP, POSTCOMBAT_MAIN_STEP_POST,
        END_PHASE, END_PHASE_PRE, END_PHASE_POST,
        END_TURN_STEP_PRE, END_TURN_STEP, END_TURN_STEP_POST,
        CLEANUP_STEP_PRE, CLEANUP_STEP, CLEANUP_STEP_POST,
        EMPTY_MANA_POOL,
        AT_END_OF_TURN,
        //player events
        /* ZONE_CHANGE
         targetId    id of the zone changing object
         sourceId    sourceId of the ability with the object moving effect (WARNING, can be null if it move of fizzled spells)
         playerId    controller of the moved object
         amount      not used for this event
         flag        not used for this event
         */
        ZONE_CHANGE,
        ZONE_CHANGE_GROUP,
        DRAW_CARDS, // event calls for multi draws only (if player draws 2+ cards at once)
        DRAW_CARD, DREW_CARD,
        EXPLORED,
        ECHO_PAID,
        MIRACLE_CARD_REVEALED,
        /* MADNESS_CARD_EXILED,
         targetId    id of the card with madness
         sourceId    original id of the madness ability
         playerId    controller of the card
         */
        MADNESS_CARD_EXILED,
        INVESTIGATED,
        KICKED,
        /* CONVOKED
         targetId    id of the creature that was taped to convoke the sourceId
         sourceId    sourceId of the convoked spell
         playerId    controller of the convoked spell
         */
        CONVOKED,
        /* DISCARD_CARD
         flag        event is result of effect (1) or result of cost (0)
         */
        DISCARD_CARD,
        DISCARDED_CARD,
        DISCARDED_CARDS,
        CYCLE_CARD, CYCLED_CARD, CYCLE_DRAW,
        CLASH, CLASHED,
        DAMAGE_PLAYER,
        MILL_CARDS,
        MILLED_CARD,
        /* DAMAGED_PLAYER
         targetId    the id of the damaged player
         sourceId    sourceId of the ability which caused the damage
         playerId    the id of the damged player
         amount      amount of damage
         flag        true = comabat damage - other damage = false
         */
        DAMAGED_PLAYER,
        DAMAGED_PLAYER_BATCH,
        /* DAMAGE_CAUSES_LIFE_LOSS,
         targetId    the id of the damaged player
         sourceId    sourceId of the ability which caused the damage, can be null for default events like combat
         playerId    the id of the damged player
         amount      amount of damage
         flag        is it combat damage
         */
        DAMAGE_CAUSES_LIFE_LOSS,
        PLAYER_LIFE_CHANGE,
        GAIN_LIFE, GAINED_LIFE,
        LOSE_LIFE, LOST_LIFE,
        /* LOSE_LIFE + LOST_LIFE
         targetId    the id of the player loosing life
         sourceId    sourceId of the ability which caused the lose
         playerId    the id of the player loosing life
         amount      amount of life loss
         flag        true = from comabat damage - other from non combat damage
         */
        PLAY_LAND, LAND_PLAYED,
        CREATURE_CHAMPIONED,
        /* CREATURE_CHAMPIONED
         targetId    the id of the creature that was championed
         sourceId    sourceId of the creature using the champion ability
         playerId    the id of the controlling player
         */
        CREW_VEHICLE,
        /* CREW_VEHICLE
         targetId    the id of the creature that crewed a vehicle
         sourceId    sourceId of the vehicle
         playerId    the id of the controlling player
         */
        CREWED_VEHICLE,
        /* CREWED_VEHICLE
         targetId    the id of the creature that crewed a vehicle
         sourceId    sourceId of the vehicle
         playerId    the id of the controlling player
         */
        VEHICLE_CREWED,
        /* VEHICLE_CREWED
         targetId    the id of the vehicle
         sourceId    sourceId of the vehicle
         playerId    the id of the controlling player
         */
        X_MANA_ANNOUNCE,
        /* X_MANA_ANNOUNCE
         mana x-costs announced by players (X value can be changed by replace events like Unbound Flourishing)
         targetId    id of the spell that's cast
         playerId    player that casts the spell or ability
         amount      X multiplier to change X value, default 1
         */
        CAST_SPELL,
        CAST_SPELL_LATE,
        /* SPELL_CAST, CAST_SPELL_LATE
         targetId    id of the spell that's try to cast
         sourceId    sourceId of the spell that's try to cast
         playerId    player that try to cast the spell
         amount      not used for this event
         flag        not used for this event
         zone        zone the spell is cast from (main card)
         */
        SPELL_CAST,
        /* SPELL_CAST
         targetId    id of the spell that's cast
         sourceId    sourceId of the spell that's cast
         playerId    player that casts the spell
         amount      not used for this event
         flag        not used for this event
         zone        zone the spell is cast from
         */
        ACTIVATE_ABILITY, ACTIVATED_ABILITY,
        /* ACTIVATE_ABILITY, ACTIVATED_ABILITY,
         targetId    id of the ability to activate / use
         sourceId    sourceId of the object with that ability
         playerId    player that tries to use this ability
         */
        TAKE_SPECIAL_ACTION, TAKEN_SPECIAL_ACTION,
        /* TAKE_SPECIAL_ACTION, TAKEN_SPECIAL_ACTION,
         targetId    id of the ability to activate / use
         sourceId    sourceId of the object with that ability
         playerId    player that tries to use this ability
         */
        TAKE_SPECIAL_MANA_PAYMENT, TAKEN_SPECIAL_MANA_PAYMENT,
        /* TAKE_SPECIAL_MANA_PAYMENT, TAKEN_SPECIAL_MANA_PAYMENT
         targetId    id of the ability to activate / use
         sourceId    sourceId of the object with that ability
         playerId    player that tries to use this ability
         */
        TRIGGERED_ABILITY,
        ABILITY_TRIGGERED,
        RESOLVING_ABILITY,
        /* COPY_STACKOBJECT
         targetId    id of the spell/ability to copy
         sourceId    id of the object with copy ability
         playerId    id of the player who will be control new copied spell/ability
         amount      number on copies
         */
        COPY_STACKOBJECT,
        /* COPIED_STACKOBJECT, TODO: make same logic in params for COPY_STACKOBJECT and COPIED_STACKOBJECT
         targetId    id of the new copied spell/ability
         sourceId    id of the spell/ability to copy
         playerId    id of the player who will be control new copied spell/ability
         */
        COPIED_STACKOBJECT,
        /* ADD_MANA
         targetId    id of the ability that added the mana
         sourceId    sourceId of the ability that added the mana
         playerId    player the mana is added to the mana pool for
         mana        the mana added
         */
        ADD_MANA,
        /* MANA_ADDED
         targetId    id of the ability that added the mana
         sourceId    sourceId of the ability that added the mana
         playerId    player the mana is added to the mana pool for
         mana        the mana added
         */
        MANA_ADDED,
        /* MANA_PAID
         targetId    id if the ability the mana was paid for (not the sourceId)
         sourceId    sourceId of the mana source
         playerId    controller of the ability the mana was paid for
         amount      not used for this event
         flag        indicates a special condition of mana
         data        originalId of the mana producing ability as string (converted from UUID)
         */
        MANA_PAID,
        LOSES, LOST, WINS, DRAW_PLAYER,
        /* TARGET
         targetId    id of the targeting card
         sourceId    id of the ability's object that try to targeting
         playerId    player who try to targeting (can be different from source ability's controller)
                     TODO: BUT there is isLegal(Ability source, Game game) code and it uses only source ability's controller,
                       so some abilities can be fizzled on resolve cause no legal targets?
         amount      not used for this event
         */
        TARGET, TARGETED,
        /* TARGETS_VALID
         targetId    id of the spell or id of stack ability the targets were set to
         sourceId    = targetId
         playerId    controller of the spell or stack ability
         amount      not used for this event
         */
        TARGETS_VALID,
        /* COUNTER
         targetId    id of the spell or id of stack ability
         sourceId    sourceId of the ability countering the spell or stack ability
         playerId    controller of the countered spell or stack ability
         amount      not used for this event
         flag        not used for this event
         */
        COUNTER,
        COUNTERED,
        DECLARING_ATTACKERS, DECLARED_ATTACKERS,
        /* DECLARE_ATTACKER
         REPLACE EVENT - can be used to replace attack declaration
         targetId    id of the defending player or planeswalker attacked
         sourceId    id of the attacking creature
         playerId    player defining the attacking creatures
         */
        DECLARE_ATTACKER,
        /* ATTACKER_DECLARED
         targetId    id of the defending player or planeswalker attacked
         sourceId    id of the attacking creature
         playerId    player defining the attacking creatures
         amount      not used for this event
         flag        not used for this event
         */
        ATTACKER_DECLARED,
        /* DECLARING_BLOCKERS
         targetId    attackerId
         sourceId    not used for this event
         playerId    attackerId
         amount      not used for this event
         flag        not used for this event
         */
        DEFENDER_ATTACKED,
        DECLARING_BLOCKERS,
        DECLARED_BLOCKERS,
        DECLARE_BLOCKER,
        /* BLOCKER_DECLARED
         targetId    attacker id
         sourceId    blocker id
         playerId    blocker controller id
         */
        BLOCKER_DECLARED,
        CREATURE_BLOCKED,
        BATCH_BLOCK_NONCOMBAT,
        UNBLOCKED_ATTACKER,
        SEARCH_LIBRARY, LIBRARY_SEARCHED,
        SHUFFLE_LIBRARY, LIBRARY_SHUFFLED,
        ENCHANT_PLAYER, ENCHANTED_PLAYER,
        CAN_TAKE_MULLIGAN,
        SCRY, SCRIED,
        SURVEIL, SURVEILED,
        FATESEALED,
        FLIP_COIN, COIN_FLIPPED,
        REPLACE_ROLLED_DIE, // for Clam-I-Am workaround only
        ROLL_DIE, DIE_ROLLED,
        ROLL_DICE, DICE_ROLLED,
        PLANESWALK, PLANESWALKED,
        PAID_CUMULATIVE_UPKEEP,
        DIDNT_PAY_CUMULATIVE_UPKEEP,
        LIFE_PAID,
        CASCADE_LAND,
        LEARN,
        //permanent events
        ENTERS_THE_BATTLEFIELD_SELF, /* 616.1a If any of the replacement and/or prevention effects are self-replacement effects (see rule 614.15),
                                        one of them must be chosen. If not, proceed to rule 616.1b. */
        ENTERS_THE_BATTLEFIELD_CONTROL, // 616.1b
        ENTERS_THE_BATTLEFIELD_COPY, // 616.1c
        ENTERS_THE_BATTLEFIELD, // 616.1d
        TAP,
        /* TAPPED,
         targetId    tapped permanent
         sourceId    id of the abilitity's source (can be null for standard tap actions like combat)
         playerId    controller of the tapped permanent
         amount      not used for this event
         flag        is it tapped for combat
         */
        TAPPED,
        TAPPED_FOR_MANA,
        /* TAPPED_FOR_MANA
         During calculation of the available mana for a player the "TappedForMana" event is fired to simulate triggered mana production.
         By checking the inCheckPlayableState these events are handled to give back only the available mana of instead really producing mana.
         IMPORTANT: Triggered non mana abilities have to ignore the event if game.inCheckPlayableState is true.
         */
        UNTAP, UNTAPPED,
        FLIP, FLIPPED,
        UNFLIP, UNFLIPPED,
        TRANSFORM, TRANSFORMING, TRANSFORMED,
        ADAPT,
        BECOMES_MONSTROUS,
        /* BECOMES_EXERTED
         targetId    id of the exerted creature
         sourceId    sourceId of the ability that triggered the event (do exert)
         playerId    player who makes the exert (can be different from permanent's controller)
         amount      not used for this event
         flag        not used for this event
         */
        BECOMES_EXERTED,
        BECOMES_RENOWNED,
        GAINS_CLASS_LEVEL,
        /* BECOMES_MONARCH
         targetId    playerId of the player that becomes the monarch
         sourceId    id of the source object that created that effect, if no effect exist it's null
         playerId    playerId of the player that becomes the monarch
         amount      not used for this event
         flag        not used for this event
         */
        BECOME_MONARCH,
        BECOMES_MONARCH,
        BECOMES_DAY_NIGHT,
        MEDITATED,
        PHASE_OUT, PHASED_OUT,
        PHASE_IN, PHASED_IN,
        TURNFACEUP, TURNEDFACEUP,
        TURNFACEDOWN, TURNEDFACEDOWN,
        /* OPTION_USED
         targetId    originalId of the ability that triggered the event
         sourceId    sourceId of the ability that triggered the event
         playerId    controller of the ability
         amount      not used for this event
         flag        not used for this event
         */
        OPTION_USED,
        DAMAGE_PERMANENT,
        DAMAGED_PERMANENT,
        DAMAGED_PERMANENT_BATCH,
        DESTROY_PERMANENT,
        /* DESTROY_PERMANENT_BY_LEGENDARY_RULE
         targetId    id of the permanent to destroy
         playerId    controller of the permanent to detroy
         */
        DESTROY_PERMANENT_BY_LEGENDARY_RULE,
        /* DESTROYED_PERMANENT
         targetId    id of the destroyed creature
         sourceId    sourceId of the ability with the destroy effect
         playerId    controller of the creature
         amount      not used for this event
         flag        true if no regeneration is allowed
         */
        DESTROYED_PERMANENT,
        SACRIFICE_PERMANENT, SACRIFICED_PERMANENT,
        FIGHTED_PERMANENT,
        BATCH_FIGHT,
        EXPLOITED_CREATURE,
        EVOLVED_CREATURE,
        EMBALMED_CREATURE,
        ETERNALIZED_CREATURE,
        TRAINED_CREATURE,
        ATTACH, ATTACHED,
        UNATTACH, UNATTACHED,
        /* ATTACH, ATTACHED,
           UNATTACH, UNATTACHED,
         targetId    id of the permanent who get/lose attachment
         sourceId    id of the attachment
         playerId    player who control the attachment
         amount      not used for this event
         flag        not used for this event
         */
        STAY_ATTACHED,
        ADD_COUNTER, COUNTER_ADDED,
        ADD_COUNTERS, COUNTERS_ADDED,
        COUNTER_REMOVED, COUNTERS_REMOVED,
        LOSE_CONTROL,
        /* LOST_CONTROL
         targetId    id of the creature that lost control
         sourceId    null
         playerId    player that controlles the creature before
         amount      not used for this event
         flag        not used for this event
         */
        LOST_CONTROL,
        /* GAIN_CONTROL
         targetId    id of the permanent that trying to get control
         sourceId    null
         playerId    new player that try to get control of permanent
         amount      not used for this event
         flag        not used for this event
         */
        GAIN_CONTROL,
        /* GAINED_CONTROL
         targetId    id of the permanent that got control
         sourceId    null
         playerId    new player that got control of permanent
         amount      not used for this event
         flag        not used for this event
         */
        GAINED_CONTROL,
        CREATE_TOKEN, CREATED_TOKEN,
        /* REGENERATE
         targetId    id of the creature to regenerate
         sourceId    sourceId of the effect doing the regeneration
         playerId    controller of the creature
         amount      not used for this event
         flag        not used for this event
         */
        REGENERATE,
        REGENERATED,
        CHANGE_COLOR, COLOR_CHANGED,
        NUMBER_OF_TRIGGERS,
        //combat events
        COMBAT_DAMAGE_APPLIED,
        SELECTED_ATTACKER, SELECTED_BLOCKER,
        /* voting
         targetId    player who voting
         sourceId    sourceId of the effect doing the voting
         playerId    player who deciding about voting, can be changed by replace events
         amount      not used for this event
         flag        not used for this event
         */
        VOTE, VOTED,
        /* dungeons
         targetId    id of the room
         sourceId    sourceId of the ability causing player to venture
         playerId    player in the dungeon
         */
        ROOM_ENTERED,
        VENTURE, VENTURED,
        DUNGEON_COMPLETED,
        REMOVED_FROM_COMBAT, // targetId    id of permanent removed from combat
        FORETOLD, // targetId   id of card foretold
        FORETELL, // targetId   id of card foretell  playerId   id of the controller
        //custom events
        CUSTOM_EVENT
    }

    public GameEvent(EventType type, UUID targetId, Ability source, UUID playerId) {
        this(type, null, targetId, source, playerId, 0, false);
    }

    public GameEvent(EventType type, UUID targetId, Ability source, UUID playerId, ApprovingObject approvingObject) {
        this(type, null, targetId, source, playerId, 0, false, approvingObject);
    }

    public GameEvent(EventType type, UUID targetId, Ability source, UUID playerId, int amount, boolean flag) {
        this(type, null, targetId, source, playerId, amount, flag);
    }

    public GameEvent(UUID customEventType, UUID targetId, Ability source, UUID playerId) {
        this(EventType.CUSTOM_EVENT, customEventType, targetId, source, playerId, 0, false);
    }

    public GameEvent(UUID customEventType, UUID targetId, Ability source, UUID playerId, int amount, boolean flag) {
        this(EventType.CUSTOM_EVENT, customEventType, targetId, source, playerId, amount, flag);
    }

    public static GameEvent getEvent(EventType type, UUID targetId, Ability source, UUID playerId, int amount) {
        return new GameEvent(type, targetId, source, playerId, amount, false);
    }

    public static GameEvent getEvent(EventType type, UUID targetId, Ability source, UUID playerId) {
        return new GameEvent(type, targetId, source, playerId);
    }

    public static GameEvent getEvent(EventType type, UUID targetId, Ability source, UUID playerId, ApprovingObject approvingObject) {
        return new GameEvent(type, targetId, source, playerId, approvingObject);
    }

    @Deprecated // usage must be replaced by getEvent with source ability
    public static GameEvent getEvent(EventType type, UUID targetId, UUID playerId) {
        return new GameEvent(type, targetId, null, playerId);
    }

    public static GameEvent getEvent(EventType type, UUID targetId, Ability source, UUID playerId, String data, int amount) {
        GameEvent event = getEvent(type, targetId, source, playerId);
        event.setAmount(amount);
        event.setData(data);
        return event;
    }

    public static GameEvent getEvent(UUID customEventType, UUID targetId, Ability source, UUID playerId, int amount) {
        return new GameEvent(customEventType, targetId, source, playerId, amount, false);
    }

    public static GameEvent getEvent(UUID customEventType, UUID targetId, Ability source, UUID playerId) {
        return new GameEvent(customEventType, targetId, source, playerId);
    }

    private GameEvent(EventType type, UUID customEventType, UUID targetId, Ability source, UUID playerId, int amount, boolean flag) {
        this(type, customEventType, targetId, source, playerId, amount, flag, null);
    }

    private GameEvent(EventType type, UUID customEventType,
                      UUID targetId, Ability source, UUID playerId, int amount, boolean flag, ApprovingObject approvingObject) {
        this.type = type;
        this.customEventType = customEventType;
        this.targetId = targetId;
        this.sourceId = source == null ? null : source.getSourceId();
        this.amount = amount;
        this.playerId = playerId;
        this.flag = flag;
        this.approvingObject = approvingObject;
        this.id = UUID.randomUUID();
    }

    public EventType getType() {
        return type;
    }

    public UUID getCustomEventType() {
        return customEventType;
    }

    public UUID getId() {
        return id;
    }

    public UUID getTargetId() {
        return targetId;
    }

    public void setTargetId(UUID targetId) {
        this.targetId = targetId;
    }

    public UUID getSourceId() {
        return sourceId;
    }

    public void setPlayerId(UUID playerId) {
        this.playerId = playerId;
    }

    public UUID getPlayerId() {
        return playerId;
    }

    public int getAmount() {
        return amount;
    }

    public void setAmount(int amount) {
        this.amount = amount;
    }

    public void setAmountForCounters(int amount, boolean isEffect) {
        this.amount = amount;

        // cost event must be "transformed" to effect event, as example:
        // planeswalker's +1 cost will be affected by Pir, Imaginative Rascal (1 + 1) and applied as effect by Doubling Season (2 * 2)
        // https://github.com/magefree/mage/issues/5802
        if (isEffect) {
            setFlag(true);
        }
    }

    public boolean getFlag() {
        return flag;
    }

    public void setFlag(boolean flag) {
        this.flag = flag;
    }

    public String getData() {
        return data;
    }

    public void setData(String data) {
        this.data = data;
    }

    public Zone getZone() {
        return zone;
    }

    public void setZone(Zone zone) {
        this.zone = zone;
    }

    /**
     * Returns possibly approving object that allowed the creation of the event.
     *
     * @return
     */
    public ApprovingObject getAdditionalReference() {
        return approvingObject;
    }

    public void setAdditionalReference(ApprovingObject approvingObject) {
        this.approvingObject = approvingObject;
    }

    /**
     * used to store which replacement effects were already applied to an event
     * or any modified events that may replace it
     * <p>
     * 614.5. A replacement effect doesn't invoke itself repeatedly; it gets
     * only one opportunity to affect an event or any modified events that may
     * replace it. Example: A player controls two permanents, each with an
     * ability that reads "If a creature you control would deal damage to a
     * creature or player, it deals double that damage to that creature or
     * player instead." A creature that normally deals 2 damage will deal 8
     * damage--not just 4, and not an infinite amount.
     *
     * @return
     */
    public List<UUID> getAppliedEffects() {
        return appliedEffects;
    }

    public boolean isCustomEvent(UUID customEventType) {
        return type == EventType.CUSTOM_EVENT && this.customEventType.equals(customEventType);
    }

    public void addAppliedEffects(List<UUID> appliedEffects) {
        if (appliedEffects != null) {
            this.appliedEffects.addAll(appliedEffects);
        }
    }

    public void setAppliedEffects(List<UUID> appliedEffects) {
        if (appliedEffects != null) {
            if (this.appliedEffects.isEmpty()) {
                this.appliedEffects = appliedEffects; // Use object refecence to handle that an replacement effect can only be once applied to an event
            } else {
                this.appliedEffects.addAll(appliedEffects);
            }
        }
    }

    public boolean hasApprovingIdentifier(MageIdentifier identifier) {
        if (approvingObject == null) {
            return false;
        }
        if (identifier == null) {
            return false;
        }
        return identifier.equals(approvingObject.getApprovingAbility().getIdentifier());
    }

    /**
     * Custom sourceId setup for some events (use it in constructor). TODO: replace all custom sourceId to normal event classes
     *
     * @param sourceId
     */
    protected void setSourceId(UUID sourceId) {
        this.sourceId = sourceId;
    }
}
