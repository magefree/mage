package mage.cards.s;

import mage.abilities.Ability;
import mage.abilities.effects.OneShotEffect;
import mage.cards.Card;
import mage.cards.CardImpl;
import mage.cards.CardSetInfo;
import mage.choices.TwoChoiceVote;
import mage.constants.AbilityWord;
import mage.constants.CardType;
import mage.constants.Outcome;
import mage.constants.Zone;
import mage.filter.FilterCard;
import mage.filter.predicate.card.OwnerIdPredicate;
import mage.game.Game;
import mage.players.Player;
import mage.target.Target;
import mage.target.common.TargetCardInGraveyard;

import java.util.Set;
import java.util.UUID;
import java.util.stream.Collectors;

/**
 *
 * @author Susucr
 */
public final class SailIntoTheWest extends CardImpl {

    public SailIntoTheWest(UUID ownerId, CardSetInfo setInfo) {
        super(ownerId, setInfo, new CardType[]{CardType.INSTANT}, "{2}{G}{U}");
        

        // Will of the council -- Starting with you, each player votes for return or embark.
        // If return gets more votes, each player returns up to two cards from their graveyard
        // to their hand, then you exile Sail into the West. If embark gets more votes or the
        // vote is tied, each player may discard their hand and draw seven cards.
        getSpellAbility().setAbilityWord(AbilityWord.WILL_OF_THE_COUNCIL);
        getSpellAbility().addEffect(new SailIntoTheWestEffect());
    }

    private SailIntoTheWest(final SailIntoTheWest card) {
        super(card);
    }

    @Override
    public SailIntoTheWest copy() {
        return new SailIntoTheWest(this);
    }
}


class SailIntoTheWestEffect extends OneShotEffect {

    SailIntoTheWestEffect() {
        super(Outcome.Benefit);
        staticText = "starting with you, each player votes for return or embark. " +
                "If return gets more votes, each player returns up to two cards from their graveyard " +
                    "to their hand, then you exile Sail into the West. " +
                "If embark gets more votes or the vote is tied, each player may discard their hand and draw seven cards.";
    }

    private SailIntoTheWestEffect(final SailIntoTheWestEffect effect) {
        super(effect);
    }

    @Override
    public SailIntoTheWestEffect copy() {
        return new SailIntoTheWestEffect(this);
    }

    @Override
    public boolean apply(Game game, Ability source) {
        Player controller = game.getPlayer(source.getControllerId());
        if (controller == null) {
            return false;
        }

        TwoChoiceVote vote = new TwoChoiceVote(
            "Return (return up to 2 cards from graveyard)",
            "Embark (may discard all and draw 7)",
            Outcome.Benefit);
        vote.doVotes(source, game);

        int returnCount = vote.getVoteCount(true);
        int embarkCount = vote.getVoteCount(false);

        if(returnCount > embarkCount) {
            return applyReturn(game, source);
        }
        else {
            return applyEmbark(game, source);
        }
    }

    private boolean applyReturn(Game game, Ability source) {
        game.informPlayers("'Return' won the vote. Each player may return up to two cards from their graveyard to their hand");
        for (UUID playerId : game.getState().getPlayersInRange(source.getControllerId(), game)) {
            Player player = game.getPlayer(playerId);

            if (player != null) {
                FilterCard filter = new FilterCard("card from your graveyard");
                filter.add(new OwnerIdPredicate(player.getId()));
                Target targetCard = new TargetCardInGraveyard(0,2,filter);
                targetCard.setNotTarget(true);

                if (targetCard.canChoose(player.getId(), source, game)) {
                    if (player.chooseTarget(Outcome.ReturnToHand, targetCard, source, game)) {
                        Set<Card> cards = targetCard
                            .getTargets()
                            .stream()
                            .map(game::getCard)
                            .collect(Collectors.toSet());
                        if (!cards.isEmpty()) {
                            player.moveCards(cards, Zone.HAND, source, game);
                        }
                        else {
                            game.informPlayers(player.getLogName() + " decided to not return anything.");
                        }
                    }
                }
            }
        }
        return true;
    }

    private boolean applyEmbark(Game game, Ability source) {
        game.informPlayers("'Embark' won the vote. Each player may discard their hand and draw seven cards.");

        for (UUID playerId : game.getState().getPlayersInRange(source.getControllerId(), game)) {
            Player player = game.getPlayer(playerId);

            if (player != null) {
                boolean choice = player.chooseUse(Outcome.DrawCard,
                    "discard your hand and draw seven cards?",
                    "", "yes", "no", source, game);

                if(choice){
                    player.discard(player.getHand().size(), false, false, source, game);
                    player.drawCards(7, source, game);
                }
                else {
                    game.informPlayers(player.getLogName() + " decided to not use the 'embark' action.");
                }
            }
        }
        return false;
    }
}
