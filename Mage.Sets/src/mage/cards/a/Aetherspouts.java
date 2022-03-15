package mage.cards.a;

import mage.abilities.Ability;
import mage.abilities.effects.OneShotEffect;
import mage.cards.*;
import mage.constants.CardType;
import mage.constants.Outcome;
import mage.constants.Zone;
import mage.filter.FilterCard;
import mage.filter.common.FilterAttackingCreature;
import mage.game.Game;
import mage.game.permanent.Permanent;
import mage.game.permanent.PermanentToken;
import mage.players.Player;
import mage.players.PlayerList;
import mage.target.TargetCard;

import java.util.ArrayList;
import java.util.List;
import java.util.UUID;

/**
 * @author LevelX2
 */
public final class Aetherspouts extends CardImpl {

    public Aetherspouts(UUID ownerId, CardSetInfo setInfo) {
        super(ownerId, setInfo, new CardType[]{CardType.INSTANT}, "{3}{U}{U}");


        // For each attacking creature, its owner puts it on the top or bottom of their library.
        this.getSpellAbility().addEffect(new AetherspoutsEffect());
    }

    private Aetherspouts(final Aetherspouts card) {
        super(card);
    }

    @Override
    public Aetherspouts copy() {
        return new Aetherspouts(this);
    }
}

/*
7/18/2014 	The owner of each attacking creature chooses whether to put it on the top or bottom
            of their library.
            The active player (the player whose turn it is) makes all of their choices first,
            followed by each other player in turn order.

7/18/2014 	If an effect puts two or more cards on the top or bottom of a library at the same time,
            the owner of those cards may arrange them in any order.
            That library's owner doesn't reveal the order in which the cards go into their library.
*/
class AetherspoutsEffect extends OneShotEffect {

    public AetherspoutsEffect() {
        super(Outcome.Benefit);
        this.staticText = "For each attacking creature, its owner puts it on the top or bottom of their library";
    }

    private AetherspoutsEffect(final AetherspoutsEffect effect) {
        super(effect);
    }

    @Override
    public AetherspoutsEffect copy() {
        return new AetherspoutsEffect(this);
    }

    @Override
    public boolean apply(Game game, Ability source) {
        game.getPlayerList();
        Player controller = game.getPlayer(source.getControllerId());
        if (controller == null) { return false; }

        PlayerList playerList = game.getState().getPlayersInRange(controller.getId(), game);
        playerList.setCurrent(game.getActivePlayerId());
        Player player = game.getPlayer(game.getActivePlayerId());
        Player activePlayer = player;
        do {
            List<Permanent> permanentsToTop = new ArrayList<>();
            List<Permanent> permanentsToBottom = new ArrayList<>();
            for (Permanent permanent : game.getState().getBattlefield().getActivePermanents(new FilterAttackingCreature(), player.getId(), source.getSourceId(), game)) {
                if (permanent.isOwnedBy(player.getId())) {
                    if (player.chooseUse(outcome, "Put " + permanent.getLogName() + " to the top? (else it goes to bottom)", source, game)) {
                        permanentsToTop.add(permanent);
                        game.informPlayers(permanent.getLogName() + " goes to the top of " + player.getLogName() + "'s library");
                    } else {
                        permanentsToBottom.add(permanent);
                        game.informPlayers(permanent.getLogName() + " goes to the bottom of " + player.getLogName() + "'s library");
                    }
                }
            }
            // cards to top
            Cards cards = new CardsImpl();
            List<Permanent> toLibrary = new ArrayList<>();
            for (Permanent permanent : permanentsToTop) {
                if (permanent instanceof PermanentToken) {
                    toLibrary.add(permanent);
                } else {
                    Card card = game.getCard(permanent.getId());
                    if (card != null) {
                        cards.add(card);
                    }
                }
            }
            TargetCard target = new TargetCard(Zone.BATTLEFIELD, new FilterCard("order to put on the top of library (last choosen will be the top most)"));
            while (cards.size() > 1) {
                if (!player.canRespond()) {
                    return false;
                }
                player.choose(Outcome.Neutral, cards, target, game);
                Card card = cards.get(target.getFirstTarget(), game);
                if (card != null) {
                    cards.remove(card);
                    Permanent permanent = game.getPermanent(card.getId());
                    if (permanent != null) {
                        toLibrary.add(permanent);
                    }
                }
                target.clearChosen();
            }
            if (cards.size() == 1) {
                Card card = cards.get(cards.iterator().next(), game);
                Permanent permanent = game.getPermanent(card.getId());
                if (permanent != null) {
                    toLibrary.add(permanent);
                }
            }
            // move all permanents to lib at the same time
            for (Permanent permanent : toLibrary) {
                player.moveCardToLibraryWithInfo(permanent, source, game, Zone.BATTLEFIELD, true, false);
            }
            // cards to bottom
            cards.clear();
            toLibrary.clear();
            for (Permanent permanent : permanentsToBottom) {
                if (permanent instanceof PermanentToken) {
                    toLibrary.add(permanent);
                } else {
                    Card card = game.getCard(permanent.getId());
                    if (card != null) {
                        cards.add(card);
                    }
                }
            }
            target = new TargetCard(Zone.BATTLEFIELD, new FilterCard("order to put on bottom of library (last choosen will be bottommost card)"));
            while (player.canRespond() && cards.size() > 1) {
                player.choose(Outcome.Neutral, cards, target, game);

                Card card = cards.get(target.getFirstTarget(), game);
                if (card != null) {
                    cards.remove(card);
                    Permanent permanent = game.getPermanent(card.getId());
                    if (permanent != null) {
                        toLibrary.add(permanent);
                    }
                }
                target.clearChosen();
            }
            if (cards.size() == 1) {
                Card card = cards.get(cards.iterator().next(), game);
                Permanent permanent = game.getPermanent(card.getId());
                if (permanent != null) {
                    toLibrary.add(permanent);
                }
            }
            // move all permanents to lib at the same time
            for (Permanent permanent : toLibrary) {
                player.moveCardToLibraryWithInfo(permanent, source, game, Zone.BATTLEFIELD, false, false);
            }
            player = playerList.getNext(game, false);
        } while (player != null && !player.getId().equals(game.getActivePlayerId()) && activePlayer.canRespond());

        return true;
    }
}
