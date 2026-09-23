package mage.game;

import mage.game.match.MatchImpl;
import mage.game.match.MatchOptions;
import mage.game.mulligan.Mulligan;

/**
 * @author ArcadesSaboth
 */
public class PauperCommanderFreeForAllMatch extends MatchImpl {

    public PauperCommanderFreeForAllMatch(MatchOptions options) {
        super(options);
    }

    @Override
    public void startGame() throws GameException {
        int startLife = 40;
        if (options.getDeckType().equals("Variant Magic - Duel Pauper Commander")) {
            startLife = 30;
        }
        Mulligan mulligan = options.getMulliganType().getMulligan(options.getFreeMulligans());
        startLife = options.isCustomStartLifeEnabled() ? options.getCustomStartLife() : startLife;
        int startHandSize = options.isCustomStartHandSizeEnabled() ? options.getCustomStartHandSize() : 7;
        PauperCommanderFreeForAll game = new PauperCommanderFreeForAll(
                options.getAttackOption(), options.getRange(),
                mulligan, startLife, startHandSize
        );
        game.setStartMessage(this.createGameStartMessage());
        initGame(game);
        games.add(game);
    }

}
