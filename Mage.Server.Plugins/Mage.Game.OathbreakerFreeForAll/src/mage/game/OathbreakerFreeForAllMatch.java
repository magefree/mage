package mage.game;

import mage.game.match.MatchImpl;
import mage.game.match.MatchOptions;
import mage.game.mulligan.Mulligan;

/**
 * @author JayDi85
 */
public class OathbreakerFreeForAllMatch extends MatchImpl {

    public OathbreakerFreeForAllMatch(MatchOptions options) {
        super(options);
    }

    @Override
    public void startGame() throws GameException {
        int startLife = 20;
        boolean alsoHand = true;
        Mulligan mulligan = options.getMulliganType().getMulligan(options.getFreeMulligans());
        OathbreakerFreeForAll game = new OathbreakerFreeForAll(options.getAttackOption(), options.getRange(), mulligan, startLife);
        game.setStartMessage(this.createGameStartMessage());
        game.setAlsoHand(alsoHand);
        game.setAlsoLibrary(true);
        initGame(game);
        games.add(game);
    }

}
