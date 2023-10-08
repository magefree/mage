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
        int startLife = options.isCustomStartLifeEnabled() ? options.getCustomStartLife() : 25;
        int startHandSize = options.isCustomStartHandSizeEnabled() ? options.getCustomStartHandSize() : 6;
        CustomPillarOfTheParunsDuel game = new CustomPillarOfTheParunsDuel(
                options.getAttackOption(), options.getRange(),
                mulligan, startLife, startHandSize
        );
        game.setStartMessage(this.createGameStartMessage());
        initGame(game);

        games.add(game);
    }
}
