

package mage.game;

/**
 *
 * @author BetaSteward_at_googlemail.com
 */
public class GameReplay {

    private GameStates savedGame;
    private int stateIndex;

    public GameReplay(GameStates savedGame) {
        this.savedGame = savedGame;
    }

    public void start() {
        this.stateIndex = 0;
    }

    public GameState next() {
        if (this.stateIndex < savedGame.getSize()) {
            return savedGame.get(stateIndex++);
        }
        return null;
    }

    public GameState previous() {
        if (this.stateIndex > 0) {
            return savedGame.get(--stateIndex);
        }
        return null;
    }

}
