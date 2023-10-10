
package mage.game;

import mage.game.match.MatchImpl;
import mage.game.match.MatchOptions;
import mage.game.mulligan.Mulligan;
import mage.game.mulligan.MulliganType;

/**
 * @author spjspj
 */
public class CanadianHighlanderDuelMatch extends MatchImpl {

    public CanadianHighlanderDuelMatch(MatchOptions options) {
        super(options);
    }

    @Override
    public void startGame() throws GameException {
        int startLife = 20;
        Mulligan mulligan = options.getMulliganType().orDefault(MulliganType.CANADIAN_HIGHLANDER).getMulligan(options.getFreeMulligans());
        startLife = options.isCustomStartLifeEnabled() ? options.getCustomStartLife() : startLife;
        int startHandSize = options.isCustomStartHandSizeEnabled() ? options.getCustomStartHandSize() : 7;
        CanadianHighlanderDuel game = new CanadianHighlanderDuel(
                options.getAttackOption(), options.getRange(),
                mulligan, startLife, startHandSize
        );
        game.setStartMessage(this.createGameStartMessage());
        initGame(game);
        games.add(game);
    }

}
