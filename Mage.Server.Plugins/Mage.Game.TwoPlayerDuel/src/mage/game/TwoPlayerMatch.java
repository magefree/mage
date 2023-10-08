package mage.game;

import mage.game.match.MatchImpl;
import mage.game.match.MatchOptions;
import mage.game.mulligan.Mulligan;

/**
 * @author BetaSteward_at_googlemail.com
 */
public class TwoPlayerMatch extends MatchImpl {

    public TwoPlayerMatch(MatchOptions options) {
        super(options);
    }

    @Override
    public void startGame() throws GameException {
        Mulligan mulligan = options.getMulliganType().getMulligan(options.getFreeMulligans());

        // workaround to enable limited deck size in limited set as deck type (Yorion, Sky Nomad)
        // see comments from https://github.com/magefree/mage/commit/4874ad31c199ea573187ea2790268be3a4d4c95a
        boolean isLimitedDeck = options.isLimited() || "Limited".equals(options.getDeckType());

        int startLife = options.isCustomStartLifeEnabled() ? options.getCustomStartLife() : 20;
        int startHandSize = options.isCustomStartHandSizeEnabled() ? options.getCustomStartHandSize() : 7;
        TwoPlayerDuel game = new TwoPlayerDuel(
                options.getAttackOption(), options.getRange(), mulligan,
                isLimitedDeck ? 40 : 60, startLife, startHandSize
        );
        // Sets a start message about the match score
        game.setStartMessage(this.createGameStartMessage());
        initGame(game);
        games.add(game);
    }

}
