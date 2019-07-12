package mage.constants;

/**
 *
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
    PLAY_FROM_NOT_OWN_HAND_ZONE, // do not use dialogs in "applies" method for that type of effect (it calls multiple times)
    CAST_AS_INSTANT,
    ACTIVATE_AS_INSTANT,
    DAMAGE,
    SHROUD,
    HEXPROOF,
    PAY_0_ECHO,
    LOOK_AT_FACE_DOWN,
    SPEND_OTHER_MANA,
    SPEND_ONLY_MANA,
    TARGET
}
