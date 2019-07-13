package mage.game;

import mage.game.match.MatchImpl;
import mage.game.match.MatchOptions;
import mage.game.mulligan.Mulligan;

/**
 * @author BetaSteward_at_googlemail.com
 */
public class CommanderDuelMatch extends MatchImpl {

    public CommanderDuelMatch(MatchOptions options) {
        super(options);
    }

    @Override
    public void startGame() throws GameException {
        int startLife = 40;
        // Don't like it to compare but seems like it's complicated to do it in another way
        boolean checkCommanderDamage = true;
        if (options.getDeckType().equals("Variant Magic - Duel Commander")) {
            startLife = 20;   // Starting with the Commander 2016 update (on November 11th, 2016), Duel Commander will be played with 20 life points instead of 30.
            checkCommanderDamage = false; // since nov 16 duel commander uses no longer commander damage rule
        }
        if (options.getDeckType().equals("Variant Magic - MTGO 1v1 Commander")) {
            startLife = 30;
        }
        Mulligan mulligan = options.getMulliganType().getMulligan(options.getFreeMulligans());
        CommanderDuel game = new CommanderDuel(options.getAttackOption(), options.getRange(), mulligan, startLife);
        game.setCheckCommanderDamage(checkCommanderDamage);
        game.setStartMessage(this.createGameStartMessage());
        initGame(game);
        games.add(game);
    }

}
