package mage.abilities.effects.common;

import mage.abilities.Ability;
import mage.abilities.effects.OneShotEffect;
import mage.constants.AbilityWord;
import mage.constants.Outcome;
import mage.game.Game;

/**
 * @author TheElk801
 */
public class WillOfThePlaneswalkersEffect extends OneShotEffect {

    public WillOfThePlaneswalkersEffect() {
        super(Outcome.Benefit);
        staticText = AbilityWord.WILL_OF_THE_COUNCIL.formatWord() + "Starting with you, each player votes " +
                "for planeswalk or chaos. If planeswalk gets more votes, planeswalk. " +
                "If chaos gets more votes or the vote is tied, chaos ensues";
        concatBy("<br>");
    }

    private WillOfThePlaneswalkersEffect(final WillOfThePlaneswalkersEffect effect) {
        super(effect);
    }

    @Override
    public WillOfThePlaneswalkersEffect copy() {
        return new WillOfThePlaneswalkersEffect(this);
    }

    @Override
    public boolean apply(Game game, Ability source) {
        // TODO: Implement when planes have been refactored
        return true;
    }
}
