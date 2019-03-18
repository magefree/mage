

package mage.game;

import mage.game.match.MatchImpl;
import mage.game.match.MatchOptions;

/**
 *
 * @author LevelX2
 */
public class CommanderFreeForAllMatch extends MatchImpl {

    public CommanderFreeForAllMatch(MatchOptions options) {
        super(options);
    }

    @Override
    public void startGame() throws GameException {
        int startLife = 40;
        boolean alsoHand = true;
        if (options.getDeckType().equals("Variant Magic - Duel Commander")) {
            startLife = 30;
            alsoHand = true;  // commander going to hand allowed to go to command zone effective July 17, 2015
        }
        CommanderFreeForAll game = new CommanderFreeForAll(options.getAttackOption(), options.getRange(), options.getFreeMulligans(), startLife);
        game.setStartMessage(this.createGameStartMessage());
        game.setAlsoHand(alsoHand);
        game.setAlsoLibrary(true);
        initGame(game);
        games.add(game);
    }

}
