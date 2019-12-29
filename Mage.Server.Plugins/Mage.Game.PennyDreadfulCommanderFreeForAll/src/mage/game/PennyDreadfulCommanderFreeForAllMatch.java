package mage.game;

import mage.game.match.MatchImpl;
import mage.game.match.MatchOptions;
import mage.game.mulligan.Mulligan;

/**
 * @author spjspj
 */
public class PennyDreadfulCommanderFreeForAllMatch extends MatchImpl {

    public PennyDreadfulCommanderFreeForAllMatch(MatchOptions options) {
        super(options);
    }

    @Override
    public void startGame() throws GameException {
        int startLife = 40;
        if (options.getDeckType().equals("Variant Magic - Duel Penny Dreadful Commander")) {
            startLife = 30;
        }
        Mulligan mulligan = options.getMulliganType().getMulligan(options.getFreeMulligans());
        PennyDreadfulCommanderFreeForAll game = new PennyDreadfulCommanderFreeForAll(options.getAttackOption(), options.getRange(), mulligan, startLife);
        game.setStartMessage(this.createGameStartMessage());
        initGame(game);
        games.add(game);
    }

}
