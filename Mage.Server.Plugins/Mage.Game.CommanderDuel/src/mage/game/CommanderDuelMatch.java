
package mage.game;

import mage.game.match.MatchImpl;
import mage.game.match.MatchOptions;

/**
 *
 * @author BetaSteward_at_googlemail.com
 */
public class CommanderDuelMatch extends MatchImpl {

    public CommanderDuelMatch(MatchOptions options) {
        super(options);
    }

    @Override
    public void startGame() throws GameException {
        int startLife = 40;
        boolean alsoHand = true;
        // Don't like it to compare but seems like it's complicated to do it in another way
        boolean checkCommanderDamage = true;
        if (options.getDeckType().equals("Variant Magic - Duel Commander")) {
            startLife = 20;   // Starting with the Commander 2016 update (on November 11th, 2016), Duel Commander will be played with 20 life points instead of 30.
            alsoHand = true;  // commander going to hand allowed to go to command zone effective July 17, 2015
            checkCommanderDamage = false; // since nov 16 duel commander uses no longer commander damage rule
        }
        if (options.getDeckType().equals("Variant Magic - MTGO 1v1 Commander")) {
            startLife = 30;
            alsoHand = true;  // commander going to hand allowed to go to command zone effective July 17, 2015
        }
        CommanderDuel game = new CommanderDuel(options.getAttackOption(), options.getRange(), options.getFreeMulligans(), startLife);
        game.setCheckCommanderDamage(checkCommanderDamage);
        game.setStartMessage(this.createGameStartMessage());
        game.setAlsoHand(alsoHand);
        game.setAlsoLibrary(true);
        initGame(game);
        games.add(game);
    }

}
