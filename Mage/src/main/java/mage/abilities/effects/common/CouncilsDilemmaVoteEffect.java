

package mage.abilities.effects.common;

import mage.abilities.Ability;
import mage.abilities.effects.OneShotEffect;
import mage.constants.Outcome;
import mage.game.Game;
import mage.players.Player;

import java.util.UUID;

/**
 * @author JRHerlehy
 */
public abstract class CouncilsDilemmaVoteEffect extends OneShotEffect {

    protected int voteOneCount = 0, voteTwoCount = 0;

    public CouncilsDilemmaVoteEffect(Outcome outcome) {
        super(outcome);
    }

    public CouncilsDilemmaVoteEffect(final CouncilsDilemmaVoteEffect effect) {
        super(effect);
    }

    protected void vote(String choiceOne, String choiceTwo, Player controller, Game game, Ability source) {
        for (UUID playerId : game.getState().getPlayersInRange(controller.getId(), game)) {
            Player player = game.getPlayer(playerId);
            if (player != null) {
                if (player.chooseUse(Outcome.Vote, "Choose " + choiceOne + '?', source, game)) {
                    voteOneCount++;
                    game.informPlayers(player.getName() + " has voted for " + choiceOne);
                } else {
                    voteTwoCount++;
                    game.informPlayers(player.getName() + " has voted for " + choiceTwo);
                }
            }
        }
    }

}
