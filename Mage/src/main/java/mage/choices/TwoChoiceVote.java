package mage.choices;

import mage.abilities.Ability;
import mage.constants.Outcome;
import mage.game.Game;
import mage.players.Player;

import java.util.*;

/**
 * @author TheElk801
 */
public class TwoChoiceVote extends VoteHandler<Boolean> {

    private final String choice1;
    private final String choice2;
    private final Map<UUID, List<Boolean>> playerMap = new HashMap<>();

    public TwoChoiceVote(String choice1, String choice2) {
        this.choice1 = choice1;
        this.choice2 = choice2;
    }

    @Override
    public Boolean playerChoose(Player player, Player decidingPlayer, Ability source, Game game) {
        boolean vote = decidingPlayer.chooseUse(Outcome.Neutral, "Vote", null, choice1, choice2, source, game);
        String message = player.getName() + " voted for " + (vote ? choice1 : choice2);
        if (!Objects.equals(player, decidingPlayer)) {
            message += " (chosen by " + decidingPlayer.getName() + ')';
        }
        game.informPlayers(message);
        return vote;
    }
}
