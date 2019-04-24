

package mage.game;

import mage.game.match.MatchImpl;
import mage.game.match.MatchOptions;

/**
 *
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
        
        TinyLeadersDuel game = new TinyLeadersDuel(options.getAttackOption(), options.getRange(), options.getFreeMulligans(), startLife);
        game.setStartMessage(this.createGameStartMessage());
        
        //Tucking a Tiny Leader is legal
        game.setAlsoHand(false);
        game.setAlsoLibrary(false);
        this.initGame(game);
        games.add(game);
    }

}
