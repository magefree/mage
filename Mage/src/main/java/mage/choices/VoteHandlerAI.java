package mage.choices;

import mage.abilities.Ability;
import mage.game.Game;
import mage.players.Player;

/**
 * @author JayDi85
 */
@FunctionalInterface
public interface VoteHandlerAI<T> {

    /**
     * AI choosing hints for votes
     *
     * @param voteHandler      voting handler for choosing
     * @param aiPlayer         player who must choose
     * @param aiDecidingPlayer real player who make a choice (cab be changed by another effect, example: Illusion of Choice)
     * @param aiSource
     * @param aiGame
     */
    T makeChoice(VoteHandler<T> voteHandler, Player aiPlayer, Player aiDecidingPlayer, Ability aiSource, Game aiGame);
}