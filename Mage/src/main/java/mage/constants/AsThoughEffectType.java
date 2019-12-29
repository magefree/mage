package mage.constants;

/**
 * @author North
 */
public enum AsThoughEffectType {
    ATTACK,
    ATTACK_AS_HASTE,
    ACTIVATE_HASTE,
    BLOCK_TAPPED,
    BLOCK_SHADOW,
    BLOCK_DRAGON,
    BLOCK_LANDWALK,
    BLOCK_PLAINSWALK,
    BLOCK_ISLANDWALK,
    BLOCK_SWAMPWALK,
    BLOCK_MOUNTAINWALK,
    BLOCK_FORESTWALK,
    DAMAGE_NOT_BLOCKED,
    BE_BLOCKED,

    // PLAY_FROM_NOT_OWN_HAND_ZONE + CAST_AS_INSTANT:
    // 1. Do not use dialogs in "applies" method for that type of effect (it calls multiple times and will freeze the game)
    // 2. All effects in "applies" must checks affectedControllerId.equals(source.getControllerId()) (if not then all players will be able to play it)
    // 3. Target points to mainCard, but card's characteristics from objectId (split, adventure)
    // TODO: search all PLAY_FROM_NOT_OWN_HAND_ZONE and CAST_AS_INSTANT effects and add support of mainCard and objectId
    PLAY_FROM_NOT_OWN_HAND_ZONE,
    CAST_AS_INSTANT,

    ACTIVATE_AS_INSTANT,
    DAMAGE,
    SHROUD,
    HEXPROOF,
    PAY_0_ECHO,
    LOOK_AT_FACE_DOWN,

    // SPEND_OTHER_MANA:
    // 1. It's uses for mana calcs at any zone, not stack only
    // 2. Compare zone change counter as "objectZCC <= targetZCC + 1"
    // 3. Compare zone with original (like exiled) and stack, not stack only
    // TODO: search all SPEND_ONLY_MANA effects and improve counters compare as SPEND_OTHER_MANA
    SPEND_OTHER_MANA,

    SPEND_ONLY_MANA,
    TARGET
}
