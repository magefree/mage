package mage.cards.s;

import mage.abilities.Ability;
import mage.abilities.effects.OneShotEffect;
import mage.cards.*;
import mage.choices.TwoChoiceVote;
import mage.constants.CardType;
import mage.constants.Outcome;
import mage.constants.Zone;
import mage.filter.StaticFilters;
import mage.game.Game;
import mage.players.Player;
import mage.target.common.TargetCardInHand;

import java.util.UUID;

/**
 * @author JRHerlehy, TheElk801
 */
public final class SelvalasStampede extends CardImpl {

    public SelvalasStampede(UUID ownerId, CardSetInfo setInfo) {
        super(ownerId, setInfo, new CardType[]{CardType.SORCERY}, "{4}{G}{G}");

        // <i>Council's dilemma</i> &mdash Starting with you, each player votes for wild or free. Reveal cards from the top of your library until you reveal a creature card for each wild vote. Put those creature cards onto the battlefield, then shuffle the rest into your library. You may put a permanent card from your hand onto the battlefield for each free vote.
        this.getSpellAbility().addEffect(new SelvalasStampedeEffect());
    }

    private SelvalasStampede(final SelvalasStampede card) {
        super(card);
    }

    @Override
    public SelvalasStampede copy() {
        return new SelvalasStampede(this);
    }
}

class SelvalasStampedeEffect extends OneShotEffect {

    SelvalasStampedeEffect() {
        super(Outcome.Benefit);
        staticText = "<i>Council's dilemma</i> &mdash; Starting with you, each player votes for wild or free. " +
                "Reveal cards from the top of your library until you reveal a creature card for each wild vote. " +
                "Put those creature cards onto the battlefield, then shuffle the rest into your library. " +
                "You may put a permanent card from your hand onto the battlefield for each free vote";
    }

    private SelvalasStampedeEffect(final SelvalasStampedeEffect effect) {
        super(effect);
    }

    @Override
    public SelvalasStampedeEffect copy() {
        return new SelvalasStampedeEffect(this);
    }

    @Override
    public boolean apply(Game game, Ability source) {
        Player player = game.getPlayer(source.getControllerId());
        if (player == null) {
            return false;
        }

        // Outcome.Detriment - AI will use library will the time (Free choice)
        // TODO: add AI hint logic in the choice method, see Tyrant's Choice as example
        TwoChoiceVote vote = new TwoChoiceVote("Wild (from library to battlefield)", "Free (from hand to battlefield)", Outcome.Detriment);
        vote.doVotes(source, game);

        int wildCount = vote.getVoteCount(true);
        int freeCount = vote.getVoteCount(false);

        // Reveal cards from the top of your library until you reveal a creature card for each wild vote.
        // Put those creature cards onto the battlefield, then shuffle the rest into your library.
        Cards toReveal = new CardsImpl();
        Cards creatureCards = new CardsImpl();
        for (Card card : player.getLibrary().getCards(game)) {
            if (creatureCards.size() >= wildCount) {
                break;
            }
            if (card.isCreature(game)) {
                creatureCards.add(card);
            }
            toReveal.add(card);
        }
        if (toReveal.size() > 0) {
            player.revealCards(source, toReveal, game);
        }
        if (creatureCards.size() > 0) {
            player.moveCards(creatureCards, Zone.BATTLEFIELD, source, game);
        }
        player.shuffleLibrary(source, game);

        // You may put a permanent card from your hand onto the battlefield for each free vote
        if (freeCount > 0) {
            TargetCardInHand target = new TargetCardInHand(0, freeCount, StaticFilters.FILTER_CARD_PERMANENT);
            player.choose(Outcome.PutCreatureInPlay, player.getHand(), target, source, game);
            creatureCards.clear();
            creatureCards.addAll(target.getTargets());
            player.moveCards(creatureCards, Zone.BATTLEFIELD, source, game);
        }

        return wildCount + freeCount > 0;
    }
}
