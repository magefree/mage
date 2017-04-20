package mage.network.protocol.change;

/**
 * Defines parts that can be sent to client as partly update instead of full game update.
 *
 * @author magenoxx
 */
public enum ChangeType {

    PRIORITY_PLAYER_NAME,
    PRIORITY_TIME,

    ACTIVE_PLAYER_ID,
    ACTIVE_PLAYER_NAME,

    CAN_PLAY_IN_HAND,
    HAND,
    OPPONENT_HANDS,
    WATCHED_HANDS,
    LOOKED_AT,
    REVEALED,

    EXILE,
    STACK,
    PLAYERS,

    COMBAT,
    SPECIAL,
    PHASE,
    STEP,
    TURN,
    SPELLS_CAST_THIS_TURN,

    /**
     * This is full update that repeats previous approach.
     */
    FULL
    ;

}
