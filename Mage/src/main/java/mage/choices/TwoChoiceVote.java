package mage.choices;

import mage.abilities.Ability;
import mage.constants.Outcome;
import mage.game.Game;
import mage.players.Player;

import java.util.HashMap;
import java.util.List;
import java.util.Map;
import java.util.UUID;

/**
 * @author TheElk801
 */
public class TwoChoiceVote extends VoteHandler<Boolean> {

    private final String choice1;
    private final String choice2;
    private final Outcome outcome;
    private final Map<UUID, List<Boolean>> playerMap = new HashMap<>();

    public TwoChoiceVote(String choice1, String choice2, Outcome outcome) {
        this.choice1 = choice1;
        this.choice2 = choice2;
        this.outcome = outcome;
    }

    @Override
    public Boolean playerChoose(Player player, Player decidingPlayer, Ability source, Game game) {
        return decidingPlayer.chooseUse(outcome, "Vote", null, choice1, choice2, source, game);
    }

    @Override
    protected String voteName(Boolean vote) {
        return (vote ? choice1 : choice2);
    }
}
