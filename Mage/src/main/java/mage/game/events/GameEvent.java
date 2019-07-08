package mage.game.events;

import mage.MageObjectReference;
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
    protected UUID targetId;
    protected UUID sourceId;
    protected UUID playerId;
    protected int amount;
    // flags:
    // for counters: event is result of effect (+1 from planeswalkers is cost, not effect)
    // for combat damage: event is preventable damage
    // for discard: event is result of effect (1) or result of cost (0)
    protected boolean flag;
    protected String data;
    protected Zone zone;
    protected List<UUID> appliedEffects = new ArrayList<>();
    protected MageObjectReference reference; // e.g. the permitting object for casting a spell from non hand zone
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
         sourceId    sourceId of the ability with the object moving effect
         playerId    controller of the moved object
         amount      not used for this event
         flag        not used for this event
         */
        ZONE_CHANGE,
        ZONE_CHANGE_GROUP,
        EMPTY_DRAW,
        DRAW_CARDS, // applies to an instruction to draw more than one card before any replacement effects apply to individual cards drawn
        DRAW_CARD, DREW_CARD,
        EXPLORED,
        ECHO_PAID,
        MIRACLE_CARD_REVEALED,
        MADNESS_CARD_EXILED,
        INVESTIGATED,
        KICKED,
        /* CONVOKED
         targetId    id of the creature that was taped to convoke the sourceId
         sourceId    sourceId of the convoked spell
         playerId    controller of the convoked spell
         */
        CONVOKED,
        DISCARD_CARD,
        DISCARDED_CARD,
        CYCLE_CARD, CYCLED_CARD,
        CLASH, CLASHED,
        DAMAGE_PLAYER,
        /* DAMAGED_PLAYER
         targetId    the id of the damaged player
         sourceId    sourceId of the ability which caused the damage
         playerId    the id of the damged player
         amount      amount of damage
         flag        true = comabat damage - other damage = false
         */
        DAMAGED_PLAYER,
        DAMAGE_CAUSES_LIFE_LOSS,
        PLAYER_LIFE_CHANGE,
        GAIN_LIFE, GAINED_LIFE,
        LOSE_LIFE, LOST_LIFE,
        /* LOSE_LIFE + LOST_LIFE
         targetId    the id of the player loosing life
         sourceId    the id of the player loosing life
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
        X_MANA_ANNOUNCE,
        /* X_MANA_ANNOUNCE
         mana x-costs announced by players (X value can be changed by replace events like Unbound Flourishing)
         targetId    id of the spell that's cast
         playerId    player that casts the spell or ability
         amount      X multiplier to change X value, default 1
        */
        CAST_SPELL,
        /* SPELL_CAST
         x-Costs are already defined
         */
        CAST_SPELL_LATE,
        /* SPELL_CAST
         targetId    id of the spell that's cast
         sourceId    sourceId of the spell that's cast
         playerId    player that casts the spell
         amount      not used for this event
         flag        not used for this event
         zone        zone the spell is cast from
         */
        SPELL_CAST,
        ACTIVATE_ABILITY, ACTIVATED_ABILITY,
        TRIGGERED_ABILITY,
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
         flag        indicates a special condition
         data        originalId of the mana producing ability
         */
        MANA_PAID,
        LOSES, LOST, WINS, DRAW_PLAYER,
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
        DECLARING_BLOCKERS,
        DECLARED_BLOCKERS,
        /* DECLARING_BLOCKERS
         targetId    id of the blocking player
         sourceId    id of the blocking player
         */
        DECLARE_BLOCKER, BLOCKER_DECLARED,
        CREATURE_BLOCKED,
        UNBLOCKED_ATTACKER,
        SEARCH_LIBRARY, LIBRARY_SEARCHED,
        SHUFFLE_LIBRARY, LIBRARY_SHUFFLED,
        ENCHANT_PLAYER, ENCHANTED_PLAYER,
        CAN_TAKE_MULLIGAN,
        FLIP_COIN, COIN_FLIPPED, SCRY, SURVEIL, SURVEILED, FATESEAL,
        ROLL_DICE, DICE_ROLLED,
        ROLL_PLANAR_DIE, PLANAR_DIE_ROLLED,
        PLANESWALK, PLANESWALKED,
        PAID_CUMULATIVE_UPKEEP,
        DIDNT_PAY_CUMULATIVE_UPKEEP,
        LIFE_PAID,
        //permanent events
        ENTERS_THE_BATTLEFIELD_SELF, /* 616.1a If any of the replacement and/or prevention effects are self-replacement effects (see rule 614.15),
                                        one of them must be chosen. If not, proceed to rule 616.1b. */
        ENTERS_THE_BATTLEFIELD_CONTROL, // 616.1b
        ENTERS_THE_BATTLEFIELD_COPY, // 616.1c
        ENTERS_THE_BATTLEFIELD, // 616.1d
        TAP, TAPPED, TAPPED_FOR_MANA,
        UNTAP, UNTAPPED,
        FLIP, FLIPPED,
        UNFLIP, UNFLIPPED,
        TRANSFORM, TRANSFORMED,
        ADAPT,
        BECOMES_MONSTROUS,
        BECOMES_EXERTED,
        /* BECOMES_EXERTED
         targetId    id of the exerted creature
         sourceId    id of the exerted creature
         playerId    playerId of the player that controlls the creature
         amount      not used for this event
         flag        not used for this event
         */
        BECOMES_RENOWNED,
        /* BECOMES_MONARCH
         targetId    playerId of the player that becomes the monarch
         sourceId    id of the source object that created that effect, if no effect exist it's null
         playerId    playerId of the player that becomes the monarch
         amount      not used for this event
         flag        not used for this event
         */
        BECOMES_MONARCH,
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
        DAMAGE_CREATURE, DAMAGED_CREATURE,
        DAMAGE_PLANESWALKER, DAMAGED_PLANESWALKER,
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
        EXPLOITED_CREATURE,
        EVOLVED_CREATURE,
        EMBALMED_CREATURE,
        ETERNALIZED_CREATURE,
        ATTACH, ATTACHED,
        STAY_ATTACHED,
        UNATTACH, UNATTACHED,
        ADD_COUNTER, COUNTER_ADDED,
        ADD_COUNTERS, COUNTERS_ADDED,
        COUNTER_REMOVED, COUNTERS_REMOVED,
        LOSE_CONTROL,
        /* LOST_CONTROL
         targetId    id of the creature that lost control
         sourceId    id of the creature that lost control
         playerId    player that controlles the creature before
         amount      not used for this event
         flag        not used for this event
         */
        LOST_CONTROL,
        GAIN_CONTROL, GAINED_CONTROL,
        CREATE_TOKEN,
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
        //custom events
        CUSTOM_EVENT
    }

    public GameEvent(EventType type, UUID targetId, UUID sourceId, UUID playerId) {
        this(type, null, targetId, sourceId, playerId, 0, false);
    }

    public GameEvent(EventType type, UUID targetId, UUID sourceId, UUID playerId, MageObjectReference reference) {
        this(type, null, targetId, sourceId, playerId, 0, false, reference);
    }

    public GameEvent(EventType type, UUID targetId, UUID sourceId, UUID playerId, int amount, boolean flag) {
        this(type, null, targetId, sourceId, playerId, amount, flag);
    }

    public GameEvent(UUID customEventType, UUID targetId, UUID sourceId, UUID playerId) {
        this(EventType.CUSTOM_EVENT, customEventType, targetId, sourceId, playerId, 0, false);
    }

    public GameEvent(UUID customEventType, UUID targetId, UUID sourceId, UUID playerId, int amount, boolean flag) {
        this(EventType.CUSTOM_EVENT, customEventType, targetId, sourceId, playerId, amount, flag);
    }

    public static GameEvent getEvent(EventType type, UUID targetId, UUID sourceId, UUID playerId, int amount) {
        return new GameEvent(type, targetId, sourceId, playerId, amount, false);
    }

    public static GameEvent getEvent(EventType type, UUID targetId, UUID sourceId, UUID playerId) {
        return new GameEvent(type, targetId, sourceId, playerId);
    }

    public static GameEvent getEvent(EventType type, UUID targetId, UUID sourceId, UUID playerId, MageObjectReference reference) {
        return new GameEvent(type, targetId, sourceId, playerId, reference);
    }

    public static GameEvent getEvent(EventType type, UUID targetId, UUID playerId) {
        return new GameEvent(type, targetId, null, playerId);
    }

    public static GameEvent getEvent(EventType type, UUID targetId, UUID sourceId, UUID playerId, String data, int amount) {
        GameEvent event = getEvent(type, targetId, sourceId, playerId);
        event.setAmount(amount);
        event.setData(data);
        return event;
    }

    public static GameEvent getEvent(UUID customEventType, UUID targetId, UUID sourceId, UUID playerId, int amount) {
        return new GameEvent(customEventType, targetId, sourceId, playerId, amount, false);
    }

    public static GameEvent getEvent(UUID customEventType, UUID targetId, UUID sourceId, UUID playerId) {
        return new GameEvent(customEventType, targetId, sourceId, playerId);
    }

    public static GameEvent getEvent(UUID customEventType, UUID targetId, UUID playerId) {
        return new GameEvent(customEventType, targetId, null, playerId);
    }

    public static GameEvent getEvent(UUID customEventType, UUID targetId, UUID playerId, String data, int amount) {
        GameEvent event = getEvent(customEventType, targetId, playerId);
        event.setAmount(amount);
        event.setData(data);
        return event;
    }

    private GameEvent(EventType type, UUID customEventType,
                      UUID targetId, UUID sourceId, UUID playerId, int amount, boolean flag) {
        this(type, customEventType, targetId, sourceId, playerId, amount, flag, null);
    }

    private GameEvent(EventType type, UUID customEventType,
                      UUID targetId, UUID sourceId, UUID playerId, int amount, boolean flag, MageObjectReference reference) {
        this.type = type;
        this.customEventType = customEventType;
        this.targetId = targetId;
        this.sourceId = sourceId;
        this.amount = amount;
        this.playerId = playerId;
        this.flag = flag;
        this.reference = reference;
    }

    public EventType getType() {
        return type;
    }

    public UUID getCustomEventType() {
        return customEventType;
    }

    public UUID getTargetId() {
        return targetId;
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

    public MageObjectReference getAdditionalReference() {
        return reference;
    }

    public void setAdditionalReference(MageObjectReference additionalReference) {
        this.reference = additionalReference;
    }

    /**
     * used to store which replacement effects were already applied to an event
     * or or any modified events that may replace it
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
}
