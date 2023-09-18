
package mage.client.game;

/**
 * Defines some options for the PlayAreaPanel
 *
 * @author LevelX2
 */
public class PlayAreaPanelOptions {

    public PlayAreaPanelOptions(boolean isPlayer, boolean isHuman, boolean playerItself, boolean rollbackTurnsAllowed, boolean topRow) {
        this.isPlayer = isPlayer;
        this.isHuman = isHuman;
        this.playerItself = playerItself;
        this.rollbackTurnsAllowed = rollbackTurnsAllowed;
        this.topRow = topRow;
    }

    /**
     * true if the client is a player / false if the client is a watcher
     */
    public boolean isPlayer;

    /**
     * true if the player is the human, not computer
     */
    public boolean isHuman;

    /**
     * true if the player is the client player itself, false if the player is
     * another player playing with the client player
     */
    public boolean playerItself;

    /**
     * true if the player can rollback turns if all players agree
     */
    public boolean rollbackTurnsAllowed;

    /**
     * true if the battlefield is on the top row of player areas
     */
    public boolean topRow;

}
