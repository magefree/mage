

package mage.game;

import mage.game.match.MatchImpl;
import mage.game.match.MatchOptions;

/**
 *
 * @author spjspj
 */
public class PennyDreadfulCommanderFreeForAllMatch extends MatchImpl {

    public PennyDreadfulCommanderFreeForAllMatch(MatchOptions options) {
        super(options);
    }

    @Override
    public void startGame() throws GameException {
        int startLife = 40;
        boolean alsoHand = true;
        if (options.getDeckType().equals("Variant Magic - Duel Penny Dreadful Commander")) {
            startLife = 30;
            alsoHand = true;  // commander going to hand allowed to go to command zone effective July 17, 2015
        }
        PennyDreadfulCommanderFreeForAll game = new PennyDreadfulCommanderFreeForAll(options.getAttackOption(), options.getRange(), options.getFreeMulligans(), startLife);
        game.setStartMessage(this.createGameStartMessage());
        game.setAlsoHand(alsoHand);
        game.setAlsoLibrary(true);
        initGame(game);
        games.add(game);
    }

}
