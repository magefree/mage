
package mage.game;

import mage.game.match.MatchImpl;
import mage.game.match.MatchOptions;

/**
 *
 * @author spjspj
 */
public class BrawlFreeForAllMatch extends MatchImpl {

    public BrawlFreeForAllMatch(MatchOptions options) {
        super(options);
    }

    @Override
    public void startGame() throws GameException {
        int startLife = 30;
        boolean alsoHand = true;
        BrawlFreeForAll game = new BrawlFreeForAll(options.getAttackOption(), options.getRange(), options.getFreeMulligans(), startLife);
        game.setStartMessage(this.createGameStartMessage());
        game.setAlsoHand(alsoHand);
        game.setCheckCommanderDamage(false);
        game.setAlsoLibrary(true);
        initGame(game);
        games.add(game);
    }

}
