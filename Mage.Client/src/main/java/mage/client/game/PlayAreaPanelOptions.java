
package mage.client.game;

/**
 * Defines some options for the PlayAreaPanel
 *
 * @author LevelX2
 */
public class PlayAreaPanelOptions {

    public PlayAreaPanelOptions(boolean isPlayer, boolean playerItself, boolean rollbackTurnsAllowed, boolean topRow) {
        this.isPlayer = isPlayer;
        this.playerItself = playerItself;
        this.rollbackTurnsAllowed = rollbackTurnsAllowed;
        this.topRow = topRow;
    }

    /**
     * true if the client is a player / false if the client is a watcher
     */
    public boolean isPlayer = false;

    /**
     * true if the player is the client player itself, false if the player is
     * another player playing with the clinet player
     */
    public boolean playerItself = false;

    /**
     * true if the player can rollback turns if all players agree
     */
    public boolean rollbackTurnsAllowed = false;

    /**
     * true if the battlefield is on the top row of player areas
     */
    public boolean topRow = false;

}
