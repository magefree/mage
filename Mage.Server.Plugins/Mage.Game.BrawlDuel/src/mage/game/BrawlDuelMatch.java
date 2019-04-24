
package mage.game;

import mage.game.match.MatchImpl;
import mage.game.match.MatchOptions;

/**
 *
 * @author spjspj
 */
public class BrawlDuelMatch extends MatchImpl {

    public BrawlDuelMatch(MatchOptions options) {
        super(options);
    }

    @Override
    public void startGame() throws GameException {
        int startLife = 25;
        boolean alsoHand = true;
        BrawlDuel game = new BrawlDuel(options.getAttackOption(), options.getRange(), options.getFreeMulligans(), startLife);
        game.setCheckCommanderDamage(false);
        game.setStartMessage(this.createGameStartMessage());
        game.setAlsoHand(true);
        game.setAlsoLibrary(true);
        initGame(game);
        games.add(game);
    }
}
