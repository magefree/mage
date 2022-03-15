package mage.cards.b;

import mage.abilities.Ability;
import mage.abilities.effects.OneShotEffect;
import mage.cards.CardImpl;
import mage.cards.CardSetInfo;
import mage.constants.CardType;
import mage.constants.Outcome;
import mage.filter.FilterPlayer;
import mage.filter.common.FilterControlledLandPermanent;
import mage.filter.predicate.Predicates;
import mage.filter.predicate.other.PlayerIdPredicate;
import mage.game.Game;
import mage.game.permanent.Permanent;
import mage.players.Player;
import mage.players.PlayerList;
import mage.target.Target;
import mage.target.TargetPermanent;
import mage.target.TargetPlayer;
import mage.target.common.TargetControlledPermanent;

import java.util.*;
import java.util.stream.Collectors;

/**
 * @author LevelX2 & L_J
 */
public final class BendOrBreak extends CardImpl {

    public BendOrBreak(UUID ownerId, CardSetInfo setInfo) {
        super(ownerId, setInfo, new CardType[]{CardType.SORCERY}, "{3}{R}");

        // Each player separates all nontoken lands they control into two piles. For each player, one of their piles is chosen by one of their opponents of their choice. Destroy all lands in the chosen piles. Tap all lands in the other piles.
        this.getSpellAbility().addEffect(new BendOrBreakEffect());
    }

    private BendOrBreak(final BendOrBreak card) {
        super(card);
    }

    @Override
    public BendOrBreak copy() {
        return new BendOrBreak(this);
    }
}

class BendOrBreakEffect extends OneShotEffect {


    public BendOrBreakEffect() {
        super(Outcome.DestroyPermanent);
        this.staticText = "Each player separates all nontoken lands they control into two piles. For each player, one of their piles is chosen by one of their opponents of their choice. Destroy all lands in the chosen piles. Tap all lands in the other piles";
    }

    public BendOrBreakEffect(final BendOrBreakEffect effect) {
        super(effect);
    }

    @Override
    public BendOrBreakEffect copy() {
        return new BendOrBreakEffect(this);
    }

    @Override
    public boolean apply(Game game, Ability source) {
        Player controller = game.getPlayer(source.getControllerId());
        if (controller == null) { return false; }

        // Map of players and their piles
        Map<UUID, List<List<Permanent>>> playerPermanents = new LinkedHashMap<>();

        PlayerList playerList = game.getState().getPlayerList().copy();
        while (!playerList.get().equals(source.getControllerId()) && controller.canRespond()) {
            playerList.getNext();
        }
        Player currentPlayer = game.getPlayer(playerList.get());
        Player nextPlayer;
        UUID firstNextPlayer = null;

        while (!getNextPlayerInDirection(true, playerList).equals(firstNextPlayer) && controller.canRespond()) {
            nextPlayer = game.getPlayer(playerList.get());
            if (nextPlayer == null) {
                return false;
            }
            if (firstNextPlayer == null) {
                firstNextPlayer = nextPlayer.getId();
            }
            if (!nextPlayer.canRespond()) {
                continue;
            }
            // Each player separates all nontoken lands they control into two piles
            if (currentPlayer == null || !game.getState().getPlayersInRange(controller.getId(), game).contains(currentPlayer.getId())) {
                continue;
            }
            List<Permanent> firstPile = new ArrayList<>();
            List<Permanent> secondPile = new ArrayList<>();
            FilterControlledLandPermanent filter = new FilterControlledLandPermanent("lands you control to assign to the first pile (lands not chosen will be assigned to the second pile)");
            TargetPermanent target = new TargetControlledPermanent(0, Integer.MAX_VALUE, filter, true);
            if (!target.canChoose(source.getSourceId(), currentPlayer.getId(), game)) { continue; }

            // TODO: add support for AI (50/50), need AI hints mechanic here
            currentPlayer.chooseTarget(Outcome.Neutral, target, source, game);
            for (Permanent permanent : game.getBattlefield().getAllActivePermanents(filter, currentPlayer.getId(), game)) {
                if (target.getTargets().contains(permanent.getId())) {
                    firstPile.add(permanent);
                } else {
                    secondPile.add(permanent);
                }
            }

            StringBuilder sb = new StringBuilder("First pile of ").append(currentPlayer.getLogName()).append(": ");
            sb.append(firstPile.stream().map(Permanent::getLogName).collect(Collectors.joining(", ")));

            game.informPlayers(sb.toString());

            sb = new StringBuilder("Second pile of ").append(currentPlayer.getLogName()).append(": ");
            sb.append(secondPile.stream().map(Permanent::getLogName).collect(Collectors.joining(", ")));

            game.informPlayers(sb.toString());

            List<List<Permanent>> playerPiles = new ArrayList<>();
            playerPiles.add(firstPile);
            playerPiles.add(secondPile);
            playerPermanents.put(currentPlayer.getId(), playerPiles);

            currentPlayer = nextPlayer;
        }

        // For each player, one of their piles is chosen by one of their opponents of their choice
        for (Map.Entry<UUID, List<List<Permanent>>> playerPiles : playerPermanents.entrySet()) {
            Player player = game.getPlayer(playerPiles.getKey());
            if (player == null) { continue; }

            FilterPlayer filter = new FilterPlayer("opponent");
            List<PlayerIdPredicate> opponentPredicates = new ArrayList<>();
            for (UUID opponentId : game.getOpponents(player.getId())) {
                opponentPredicates.add(new PlayerIdPredicate(opponentId));
            }
            filter.add(Predicates.or(opponentPredicates));
            Target target = new TargetPlayer(1, 1, true, filter);
            target.setTargetController(player.getId());
            target.setAbilityController(source.getControllerId());
            if (!player.chooseTarget(outcome, target, source, game)) { continue; }

            Player chosenOpponent = game.getPlayer(target.getFirstTarget());
            if (chosenOpponent == null) { continue; }

            List<Permanent> firstPile = playerPiles.getValue().get(0);
            List<Permanent> secondPile = playerPiles.getValue().get(1);
            game.informPlayers(player.getLogName() + " chose " + chosenOpponent.getLogName() + " to choose their pile");
            if (chosenOpponent.choosePile(outcome, "Piles of " + player.getName(), firstPile, secondPile, game)) {
                List<List<Permanent>> lists = playerPiles.getValue();
                lists.clear();
                lists.add(firstPile);
                lists.add(secondPile);
                game.informPlayers(player.getLogName() + " will have their first pile destroyed");
            } else {
                List<List<Permanent>> lists = playerPiles.getValue();
                lists.clear();
                lists.add(secondPile);
                lists.add(firstPile);
                game.informPlayers(player.getLogName() + " will have their second pile destroyed");
            }
        }
        // Destroy all lands in the chosen piles. Tap all lands in the other piles
        for (Map.Entry<UUID, List<List<Permanent>>> playerPiles : playerPermanents.entrySet()) {
            Player player = game.getPlayer(playerPiles.getKey());
            if (player == null) { continue; }

            List<Permanent> pileToSac = playerPiles.getValue().get(0);
            List<Permanent> pileToTap = playerPiles.getValue().get(1);
            for (Permanent permanent : pileToSac) {
                if (permanent != null) {
                    permanent.destroy(source, game, false);
                }
            }
            for (Permanent permanent : pileToTap) {
                if (permanent != null) {
                    permanent.tap(source, game);
                }
            }
        }
        return true;
    }

    private UUID getNextPlayerInDirection(boolean left, PlayerList playerList) {
        UUID nextPlayerId;
        if (left) {
            nextPlayerId = playerList.getNext();
        } else {
            nextPlayerId = playerList.getPrevious();
        }
        return nextPlayerId;
    }
}
