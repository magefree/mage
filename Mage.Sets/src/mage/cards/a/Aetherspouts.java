package mage.cards.a;

import mage.abilities.Ability;
import mage.abilities.effects.OneShotEffect;
import mage.cards.*;
import mage.constants.CardType;
import mage.constants.Outcome;
import mage.constants.Zone;
import mage.filter.FilterPermanent;
import mage.filter.common.FilterAttackingCreature;
import mage.filter.predicate.permanent.PermanentReferenceInCollectionPredicate;
import mage.game.Game;
import mage.game.permanent.Permanent;
import mage.game.permanent.PermanentToken;
import mage.players.Player;
import mage.players.PlayerList;
import mage.target.TargetPermanent;

import java.util.ArrayList;
import java.util.List;
import java.util.Map;
import java.util.UUID;
import java.util.stream.Collectors;

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

    AetherspoutsEffect() {
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
        if (controller == null) {
            return false;
        }

        PlayerList playerList = game.getState().getPlayersInRange(controller.getId(), game);
        playerList.setCurrent(game.getActivePlayerId());
        Player player = game.getPlayer(game.getActivePlayerId());
        Player activePlayer = player;
        do {
            List<Permanent> permanentsToTop = new ArrayList<>();
            List<Permanent> permanentsToBottom = new ArrayList<>();
            for (Permanent permanent : game.getBattlefield().getActivePermanents(new FilterAttackingCreature(), player.getId(), source, game)) {
                if (permanent.isOwnedBy(player.getId())) {
                    // it doesn't matter if tokens go to top or bottom of library, so don't bother asking the player
                    if (permanent instanceof PermanentToken) {
                        permanentsToTop.add(permanent);
                        continue;
                    }
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
            final Map<Boolean, List<Permanent>> orderedToTop = permanentsToTop.stream().collect(
                Collectors.partitioningBy(permanent -> permanent instanceof PermanentToken));
            while (orderedToTop.get(false).size() > 1) {
                if (!player.canRespond()) {
                    return false;
                }
                final FilterPermanent filter = new FilterPermanent("order to put on the top of library (last chosen will be the top most)");
                filter.add(new PermanentReferenceInCollectionPredicate(orderedToTop.get(false), game));
                final TargetPermanent target = new TargetPermanent(1, 1, filter, true);
                player.chooseTarget(Outcome.Neutral, target, source, game);
                final Permanent permanent = game.getPermanent(target.getFirstTarget());
                if (permanent != null) {
                    orderedToTop.get(false).remove(permanent);
                    orderedToTop.get(true).add(permanent);
                }
            }
            if (orderedToTop.get(false).size() == 1) {
                orderedToTop.get(true).add(orderedToTop.get(false).get(0));
            }
            // move all permanents to lib at the same time
            for (Permanent permanent : orderedToTop.get(true)) {
                player.moveCardToLibraryWithInfo(permanent, source, game, Zone.BATTLEFIELD, true, false);
            }
            // cards to bottom
            final Map<Boolean, List<Permanent>> orderedToBottom = permanentsToBottom.stream().collect(
                Collectors.partitioningBy(permanent -> permanent instanceof PermanentToken));
            while (player.canRespond() && orderedToBottom.get(false).size() > 1) {
                final FilterPermanent filter = new FilterPermanent("order to put on bottom of library (last chosen will be bottommost card)");
                filter.add(new PermanentReferenceInCollectionPredicate(orderedToBottom.get(false), game));
                final TargetPermanent target = new TargetPermanent(1, 1, filter, true);
                player.chooseTarget(Outcome.Neutral, target, source, game);
                final Permanent permanent = game.getPermanent(target.getFirstTarget());
                if (permanent != null) {
                    orderedToBottom.get(false).remove(permanent);
                    orderedToBottom.get(true).add(permanent);
                } else {
                    break;
                }
            }
            if (orderedToBottom.get(false).size() == 1) {
                orderedToBottom.get(true).add(orderedToBottom.get(false).get(0));
            }
            // move all permanents to lib at the same time
            for (Permanent permanent : orderedToBottom.get(true)) {
                player.moveCardToLibraryWithInfo(permanent, source, game, Zone.BATTLEFIELD, false, false);
            }
            player = playerList.getNext(game, false);
        } while (player != null && !player.getId().equals(game.getActivePlayerId()) && activePlayer.canRespond());

        return true;
    }
}
