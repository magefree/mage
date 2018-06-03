
package mage.game;

import mage.game.match.MatchImpl;
import mage.game.match.MatchOptions;

/**
 *
 * @author spjspj
 */
public class FreeformCommanderFreeForAllMatch extends MatchImpl {

    public FreeformCommanderFreeForAllMatch(MatchOptions options) {
        super(options);
    }

    @Override
    public void startGame() throws GameException {
        int startLife = 40;
        boolean alsoHand = true;
        FreeformCommanderFreeForAll game = new FreeformCommanderFreeForAll(options.getAttackOption(), options.getRange(), options.getFreeMulligans(), startLife);
        game.setStartMessage(this.createGameStartMessage());
        game.setAlsoHand(alsoHand);
        game.setAlsoLibrary(true);
        initGame(game);
        games.add(game);
    }

}
