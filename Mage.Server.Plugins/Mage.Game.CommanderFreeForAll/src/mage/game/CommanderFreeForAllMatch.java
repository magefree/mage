package mage.game;

import mage.game.match.MatchImpl;
import mage.game.match.MatchOptions;
import mage.game.mulligan.Mulligan;

/**
 * @author LevelX2
 */
public class CommanderFreeForAllMatch extends MatchImpl {

    public CommanderFreeForAllMatch(MatchOptions options) {
        super(options);
    }

    @Override
    public void startGame() throws GameException {
        int startLife = 40;
        if (options.getDeckType().equals("Variant Magic - Duel Commander")) {
            startLife = 30;
        }
        Mulligan mulligan = options.getMulliganType().getMulligan(options.getFreeMulligans());
        CommanderFreeForAll game = new CommanderFreeForAll(options.getAttackOption(), options.getRange(), mulligan, startLife);
        game.setStartMessage(this.createGameStartMessage());
        initGame(game);
        games.add(game);
    }

}
