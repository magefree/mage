

package mage.game;

import mage.game.match.MatchImpl;
import mage.game.match.MatchOptions;
import mage.game.mulligan.Mulligan;

/**
 * @author JRHerlehy
 */
public class TinyLeadersDuelMatch extends MatchImpl {

    public TinyLeadersDuelMatch(MatchOptions options) {
        super(options);
    }

    @Override
    public void startGame() throws GameException {
        //Tiny Leaders Play Rule 13: Players begin the game with 25 life.
        int startLife = 25;

        Mulligan mulligan = options.getMulliganType().getMulligan(options.getFreeMulligans());
        startLife = options.isCustomStartLifeEnabled() ? options.getCustomStartLife() : startLife;
        int startHandSize = options.isCustomStartHandSizeEnabled() ? options.getCustomStartHandSize() : 7;
        TinyLeadersDuel game = new TinyLeadersDuel(
                options.getAttackOption(), options.getRange(),
                mulligan, startLife, startHandSize
        );
        game.setStartMessage(this.createGameStartMessage());

        //Tucking a Tiny Leader is legal
        game.setAlsoHand(false);
        game.setAlsoLibrary(false);
        this.initGame(game);
        games.add(game);
    }

}
