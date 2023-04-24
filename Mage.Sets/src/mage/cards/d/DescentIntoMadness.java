package mage.cards.d;

import mage.abilities.Ability;
import mage.abilities.common.BeginningOfUpkeepTriggeredAbility;
import mage.abilities.effects.OneShotEffect;
import mage.cards.Card;
import mage.cards.CardImpl;
import mage.cards.CardSetInfo;
import mage.constants.CardType;
import mage.constants.Outcome;
import mage.constants.TargetController;
import mage.constants.Zone;
import mage.counters.CounterType;
import mage.filter.FilterCard;
import mage.filter.common.FilterControlledPermanent;
import mage.filter.predicate.Predicates;
import mage.filter.predicate.mageobject.CardIdPredicate;
import mage.filter.predicate.permanent.PermanentIdPredicate;
import mage.game.Game;
import mage.game.permanent.Permanent;
import mage.players.Player;
import mage.target.Target;
import mage.target.TargetCard;
import mage.target.common.TargetControlledPermanent;

import java.util.ArrayList;
import java.util.LinkedList;
import java.util.List;
import java.util.UUID;

/**
 * 5/1/2012 	For each despair counter on Descent into Madness, you'll exile a permanent
 * you control or exile a card from your hand, not both.
 * 5/1/2012 	First you choose the permanents and/or cards from your hand that will be
 * exiled. Then each other player in turn order does the same. Then all the chosen permanents
 * and cards are exiled simultaneously. Players who choose after you will know what permanents
 * you'll be exiling when they choose. They'll know how many cards you'll be exiling from
 * your hand, but they won't see those cards.
 * 5/1/2012 	If there are more counters on Descent into Madness than the total number of
 * permanents you control plus the number of cards in your hand, you'll exile all permanents
 * you control (including Descent into Madness) and all cards from your hand.
 * 5/1/2012 	If Descent into Madness isn't on the battlefield when its ability resolves,
 * use the number of counters on it when it left the battlefield to determine how many permanents
 * and/or cards from hands to exile.
 *
 * @author noxx
 */
public final class DescentIntoMadness extends CardImpl {

    public DescentIntoMadness(UUID ownerId, CardSetInfo setInfo) {
        super(ownerId, setInfo, new CardType[]{CardType.ENCHANTMENT}, "{3}{B}{B}");


        // At the beginning of your upkeep, put a despair counter on Descent into Madness, then each player exiles X permanents they control and/or cards from their hand, where X is the number of despair counters on Descent into Madness.
        this.addAbility(new BeginningOfUpkeepTriggeredAbility(new DescentIntoMadnessEffect(), TargetController.YOU, false));
    }

    private DescentIntoMadness(final DescentIntoMadness card) {
        super(card);
    }

    @Override
    public DescentIntoMadness copy() {
        return new DescentIntoMadness(this);
    }
}

class DescentIntoMadnessEffect extends OneShotEffect {

    public DescentIntoMadnessEffect() {
        super(Outcome.Sacrifice);
        this.staticText = "put a despair counter on {this}, then each player exiles X permanents they control and/or cards from their hand, where X is the number of despair counters on {this}";
    }

    public DescentIntoMadnessEffect(final DescentIntoMadnessEffect effect) {
        super(effect);
    }

    @Override
    public DescentIntoMadnessEffect copy() {
        return new DescentIntoMadnessEffect(this);
    }

    @Override
    public boolean apply(Game game, Ability source) {
        Player controller = game.getPlayer(source.getControllerId());
        Permanent sourcePermanent = game.getPermanent(source.getSourceId());
        if (sourcePermanent != null && controller != null) {
            sourcePermanent.addCounters(CounterType.DESPAIR.createInstance(), source.getControllerId(), source, game);
        }
        if (sourcePermanent == null) {
            sourcePermanent = (Permanent) game.getLastKnownInformation(source.getSourceId(), Zone.BATTLEFIELD);
        }
        if (sourcePermanent != null && controller != null) {
            int count = sourcePermanent.getCounters(game).getCount(CounterType.DESPAIR);
            if (count > 0) {
                // select the permanents and hand cards in turn order
                LinkedList<UUID> selectedObjects = new LinkedList<>();

                // ask all players
                for (UUID playerId : game.getState().getPlayersInRange(controller.getId(), game)) {
                    Player currentPlayer = game.getPlayer(playerId);
                    if (currentPlayer != null && currentPlayer.canRespond()) {
                        selectCards(currentPlayer, selectedObjects, count, source, game);
                    }
                }

                // move permanents and hand cards to exile
                for (UUID objectId : selectedObjects) {
                    if (game.getState().getZone(objectId) == Zone.BATTLEFIELD) {
                        Permanent permanent = game.getPermanent(objectId);
                        if (permanent != null) {
                            Player player = game.getPlayer(permanent.getControllerId());
                            if (player != null) {
                                player.moveCardToExileWithInfo(permanent, null, "", source, game, Zone.BATTLEFIELD, true);
                            }
                        }
                    } else if (game.getState().getZone(objectId) == Zone.HAND) {
                        Card card = game.getCard(objectId);
                        if (card != null) {
                            Player player = game.getPlayer(card.getOwnerId());
                            if (player != null) {
                                player.moveCardToExileWithInfo(card, null, "", source, game, Zone.HAND, true);
                            }
                        }
                    }
                }

            }
            return true;
        }
        return false;
    }

    private void selectCards(Player player, List<UUID> selectedObjects, int count, Ability source, Game game) {
        int amount = Math.min(count, player.getHand().size() + game.getBattlefield().getAllActivePermanents(player.getId()).size());
        int cardsFromHand = 0;

        while (player.canRespond() && amount > 0) {

            Target target;
            do {
                FilterControlledPermanent filter = new FilterControlledPermanent();
                filter.setMessage("permanent you control (" + amount + " left in total)");
                List<PermanentIdPredicate> uuidPredicates = new ArrayList<>();
                for (UUID uuid : selectedObjects) {
                    uuidPredicates.add(new PermanentIdPredicate(uuid));
                }
                filter.add(Predicates.not(Predicates.or(uuidPredicates)));

                target = new TargetControlledPermanent(0, 1, filter, true);
                if (target.canChoose(player.getId(), source, game)
                        && player.choose(Outcome.Exile, target, source, game)) {
                    for (UUID targetId : target.getTargets()) {
                        if (!selectedObjects.contains(targetId)) {
                            Permanent chosen = game.getPermanent(targetId);
                            if (chosen != null) {
                                amount--;
                                game.informPlayers(player.getLogName() + " selects " + chosen.getLogName() + " from battlefield");
                                selectedObjects.add(targetId);
                            }
                        }
                    }
                }
            } while (amount > 0 && !target.getTargets().isEmpty() && player.canRespond());
            if (amount > 0) {
                TargetCard targetInHand;
                do {
                    FilterCard filterInHand = new FilterCard();
                    filterInHand.setMessage("card from your hand (" + amount + " left in total)");
                    targetInHand = new TargetCard(0, 1, Zone.HAND, filterInHand);
                    List<CardIdPredicate> uuidPredicates = new ArrayList<>();
                    for (UUID uuid : selectedObjects) {
                        uuidPredicates.add(new CardIdPredicate(uuid));
                    }
                    filterInHand.add(Predicates.not(Predicates.or(uuidPredicates)));
                    if (targetInHand.canChoose(player.getId(), source, game) &&
                            player.choose(Outcome.Exile, player.getHand(), targetInHand, source, game)) {

                        Card card = player.getHand().get(targetInHand.getFirstTarget(), game);
                        if (card != null) {
                            selectedObjects.add(targetInHand.getFirstTarget());
                            amount--;
                            cardsFromHand++;
                        }
                    }
                } while (amount > 0 && !targetInHand.getTargets().isEmpty() && player.canRespond());

            }
        }
        if (cardsFromHand > 0) {
            game.informPlayers(player.getLogName() + " selects " + cardsFromHand + (cardsFromHand == 1 ? " card" : " cards") + " from their hand");
        }
    }
}
