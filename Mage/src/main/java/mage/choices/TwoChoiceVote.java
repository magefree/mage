package mage.choices;

import mage.abilities.Ability;
import mage.constants.Outcome;
import mage.game.Game;
import mage.players.Player;

import java.util.Arrays;
import java.util.LinkedHashSet;
import java.util.Set;

/**
 * @author TheElk801
 */
public class TwoChoiceVote extends VoteHandler<Boolean> {

    private final String choice1;
    private final String choice2;
    private final Outcome outcome;
    private final boolean secret;

    public TwoChoiceVote(String choice1, String choice2, Outcome outcome) {
        this(choice1, choice2, outcome, false);
    }

    public TwoChoiceVote(String choice1, String choice2, Outcome outcome, boolean secret) {
        this.choice1 = choice1;
        this.choice2 = choice2;
        this.outcome = outcome;
        this.secret = secret;
    }

    @Override
    protected Set<Boolean> getPossibleVotes(Ability source, Game game) {
        return new LinkedHashSet<>(Arrays.asList(Boolean.TRUE, Boolean.FALSE));
    }

    @Override
    public Boolean playerChoose(String voteInfo, Player player, Player decidingPlayer, Ability source, Game game) {
        return decidingPlayer.chooseUse(outcome, voteInfo, null, choice1, choice2, source, game);
    }

    @Override
    protected String voteName(Boolean vote) {
        return (vote ? choice1 : choice2);
    }
}
