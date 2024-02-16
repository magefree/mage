package mage.constants;

/**
 * Allows to change game rules and logic by special conditionals from ContinuousEffects (example: play card from non hand)
 * <p>
 * Can be applied to the game, object or specific ability (affectedAbility param in ContinuousEffects.asThough)
 * <p>
 * There are two usage styles possible:
 * - object specific rules and conditionals (use ContinuousEffects.asThough)
 * - global rules (use game.getState().getContinuousEffects().getApplicableAsThoughEffects)
 * Each AsThough type supports only one usage style
 *
 * @author North
 */
public enum AsThoughEffectType {
    ATTACK,
    ATTACK_AS_HASTE,
    ACTIVATE_HASTE(true, false),
    //
    BLOCK_TAPPED,
    BLOCK_SHADOW,
    BLOCK_DRAGON,
    BLOCK_LANDWALK,
    BLOCK_PLAINSWALK,
    BLOCK_ISLANDWALK,
    BLOCK_SWAMPWALK,
    BLOCK_MOUNTAINWALK,
    BLOCK_FORESTWALK,
    //
    DAMAGE_NOT_BLOCKED,
    //
    // PLAY_FROM_NOT_OWN_HAND_ZONE + CAST_AS_INSTANT:
    // 1. Do not use dialogs in "applies" method for that type of effect (it calls multiple times and will freeze the game)
    // 2. All effects in "applies" must checks affectedControllerId/playerId.equals(source.getControllerId()) (if not then all players will be able to play it)
    // 3. Target must points to mainCard, but checking goes for every card's parts and characteristics from objectId (split, adventure)
    // 4. You must implement/override an applies method with "Ability affectedAbility" (e.g. check multiple play/cast abilities from all card's parts)
    // TODO: search all PLAY_FROM_NOT_OWN_HAND_ZONE and CAST_AS_INSTANT effects and add support of mainCard and objectId
    PLAY_FROM_NOT_OWN_HAND_ZONE(true, true),
    CAST_ADVENTURE_FROM_NOT_OWN_HAND_ZONE(true, true),
    CAST_AS_INSTANT(true, true),
    //
    ACTIVATE_AS_INSTANT(true, false),
    //
    SHROUD,
    HEXPROOF,
    //
    PAY_0_ECHO(true, false),
    LOOK_AT_FACE_DOWN,
    //
    // SPEND_OTHER_MANA:
    // 1. It's uses for mana calcs at any zone, not stack only
    // 2. Compare zone change counter as "objectZCC <= targetZCC + 1"
    // 3. Compare zone with original (like exiled) and stack, not stack only
    // TODO: search all SPEND_ONLY_MANA effects and improve counters compare as SPEND_OTHER_MANA
    SPEND_OTHER_MANA(true, false),
    //
    SPEND_ONLY_MANA,
    //
    // ALLOW_FORETELL_ANYTIME:
    // For Cosmos Charger effect
    ALLOW_FORETELL_ANYTIME;

    private final boolean needAffectedAbility; // mark what AsThough check must be called for specific ability, not full object (example: spell check)
    private final boolean needPlayCardAbility; // mark what AsThough check must be called for play/cast abilities

    AsThoughEffectType() {
        this(false, false);
    }

    AsThoughEffectType(boolean needAffectedAbility, boolean needPlayCardAbility) {
        this.needAffectedAbility = needAffectedAbility;
        this.needPlayCardAbility = needPlayCardAbility;
    }

    public boolean needAffectedAbility() {
        return needAffectedAbility;
    }

    public boolean needPlayCardAbility() {
        return needPlayCardAbility;
    }
}
