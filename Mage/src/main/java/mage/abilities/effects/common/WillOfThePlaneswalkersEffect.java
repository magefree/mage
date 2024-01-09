package mage.abilities.effects.common;

import mage.abilities.Ability;
import mage.abilities.effects.OneShotEffect;
import mage.choices.TwoChoiceVote;
import mage.constants.AbilityWord;
import mage.constants.Outcome;
import mage.game.Game;
import mage.game.events.GameEvent;

/**
 * @author TheElk801
 */
public class WillOfThePlaneswalkersEffect extends OneShotEffect {

    public WillOfThePlaneswalkersEffect() {
        super(Outcome.Benefit);
        staticText = AbilityWord.WILL_OF_THE_PLANESWALKERS.formatWord() + "Starting with you, each player votes " +
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
        TwoChoiceVote vote = new TwoChoiceVote("Planeswalk", "Chaos", Outcome.Benefit);
        vote.doVotes(source, game);
        int planeswalkCount = vote.getVoteCount(true);
        int chaosCount = vote.getVoteCount(false);
        // TODO: Implement when planes have been refactored
        if (planeswalkCount > chaosCount) {
            game.fireEvent(GameEvent.getEvent(
                    GameEvent.EventType.ROLLED_PLANESWALK,
                    source.getControllerId(), source, source.getControllerId()
            ));
        } else {
            game.fireEvent(GameEvent.getEvent(
                    GameEvent.EventType.CHAOS_ENSUES,
                    source.getControllerId(), source, source.getControllerId()
            ));
        }
        return true;
    }
}
