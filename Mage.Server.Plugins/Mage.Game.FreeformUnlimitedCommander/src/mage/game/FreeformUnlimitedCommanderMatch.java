package mage.game;

import mage.game.match.MatchImpl;
import mage.game.match.MatchOptions;
import mage.game.mulligan.Mulligan;

public class FreeformUnlimitedCommanderMatch extends MatchImpl {

    public FreeformUnlimitedCommanderMatch(MatchOptions options) {
        super(options);
    }

    @Override
    public void startGame() throws GameException {
        int startLife = 40;
        Mulligan mulligan = options.getMulliganType().getMulligan(options.getFreeMulligans());
        FreeformUnlimitedCommander game = new FreeformUnlimitedCommander(options.getAttackOption(), options.getRange(), mulligan, startLife);
        game.setStartMessage(this.createGameStartMessage());
        initGame(game);
        games.add(game);
    }

}