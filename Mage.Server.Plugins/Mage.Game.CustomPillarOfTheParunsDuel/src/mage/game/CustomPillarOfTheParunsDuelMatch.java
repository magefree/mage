package mage.game;

import mage.game.match.MatchImpl;
import mage.game.match.MatchOptions;
import mage.game.mulligan.Mulligan;

/**
 * @author Susucr
 */
public class CustomPillarOfTheParunsDuelMatch extends MatchImpl {

    public CustomPillarOfTheParunsDuelMatch(MatchOptions options) {
        super(options);
    }

    @Override
    public void startGame() throws GameException {
        Mulligan mulligan = options.getMulliganType().getMulligan(options.getFreeMulligans());
        CustomPillarOfTheParunsDuel game = new CustomPillarOfTheParunsDuel(options.getAttackOption(), options.getRange(), mulligan);
        game.setStartMessage(this.createGameStartMessage());
        initGame(game);

        games.add(game);
    }
}
