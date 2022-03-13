
package mage.cards.w;

import java.util.*;
import mage.abilities.Ability;
import mage.abilities.effects.OneShotEffect;
import mage.cards.CardImpl;
import mage.cards.CardSetInfo;
import mage.constants.CardType;
import mage.constants.Outcome;
import mage.filter.FilterPermanent;
import mage.filter.predicate.permanent.ControllerIdPredicate;
import mage.game.Game;
import mage.game.permanent.Permanent;
import mage.players.Player;
import mage.players.PlayerList;
import mage.target.Target;
import mage.target.TargetPermanent;
import mage.util.RandomUtil;

/**
 *
 * @author LevelX2
 */
public final class WhimsOfTheFates extends CardImpl {

    public WhimsOfTheFates(UUID ownerId, CardSetInfo setInfo) {
        super(ownerId,setInfo,new CardType[]{CardType.SORCERY},"{5}{R}");


        // Starting with you, each player separates all permanents they control into three piles. Then each player chooses one of their piles at random and sacrifices those permanents.
        this.getSpellAbility().addEffect(new WhimsOfTheFateEffect());
    }

    private WhimsOfTheFates(final WhimsOfTheFates card) {
        super(card);
    }

    @Override
    public WhimsOfTheFates copy() {
        return new WhimsOfTheFates(this);
    }
}

class WhimsOfTheFateEffect extends OneShotEffect {


    public WhimsOfTheFateEffect() {
        super(Outcome.Detriment);
        this.staticText = "Starting with you, each player separates all permanents they control into three piles. Then each player chooses one of their piles at random and sacrifices those permanents.";
    }

    public WhimsOfTheFateEffect(final WhimsOfTheFateEffect effect) {
        super(effect);
    }

    @Override
    public WhimsOfTheFateEffect copy() {
        return new WhimsOfTheFateEffect(this);
    }

    @Override
    public boolean apply(Game game, Ability source) {
        Player controller = game.getPlayer(source.getControllerId());
        if (controller != null) {

            // Map of players and their piles (1,2,3) with values of UUID of the permanents
            Map<UUID, Map<Integer, Set<UUID>>> playerPermanents = new LinkedHashMap<>();

            PlayerList playerList = game.getState().getPlayerList().copy();
            while (!playerList.get().equals(source.getControllerId()) && controller.canRespond()) {
                playerList.getNext();
            }
            Player currentPlayer = game.getPlayer(playerList.get());
            Player nextPlayer;
            UUID firstNextPlayer = null;

            while (!getNextPlayerInDirection(true, playerList, game).equals(firstNextPlayer) && controller.canRespond()) {
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
                // if player is in range of controller they choose 3 piles with all their permanents
                if (currentPlayer != null && game.getState().getPlayersInRange(controller.getId(), game).contains(currentPlayer.getId())) {
                    Map<Integer, Set<UUID>> playerPiles = new HashMap<>();
                    for (int i = 1; i < 4; i++) {
                        playerPiles.put(i, new LinkedHashSet<>());
                    }
                    playerPermanents.put(currentPlayer.getId(), playerPiles);
                    for (int i = 1; i < 3; i++) {
                        FilterPermanent filter = new FilterPermanent(
                                "the permanents for the " + (i == 1 ? "first " : "second ") + "pile");
                        filter.add(new ControllerIdPredicate(currentPlayer.getId()));
                        Target target;
                        if (i == 1) {
                            target = new TargetPermanent(0, Integer.MAX_VALUE, filter, true);
                        } else {
                            target = new TargetSecondPilePermanent(playerPiles.get(1), filter);
                        }
                        target.setRequired(false);
                        currentPlayer.chooseTarget(outcome, target, source, game);
                        StringBuilder message = new StringBuilder(currentPlayer.getLogName()).append(" pile ").append(i).append(": ");
                        if (target.getTargets().isEmpty()) {
                            message.append(" (empty)");
                        } else {
                            for (UUID permanentId : target.getTargets()) {
                                Permanent permanent = game.getPermanent(permanentId);
                                if (permanent != null) {
                                    message.append(permanent.getName()).append(' ');
                                }
                            }
                        }
                        game.informPlayers(message.toString());
                        playerPiles.get(i).addAll(target.getTargets());
                    }

                    // add all permanents not targeted yet to the third pile
                    StringBuilder message = new StringBuilder(currentPlayer.getLogName()).append(" pile 3: ");
                    for (Permanent permanent : game.getState().getBattlefield().getAllActivePermanents(currentPlayer.getId())) {
                        if (!playerPiles.get(1).contains(permanent.getId()) && !playerPiles.get(2).contains(permanent.getId())) {
                            playerPiles.get(3).add(permanent.getId());
                            message.append(permanent.getName()).append(' ');
                        }
                    }
                    if (playerPiles.get(3).isEmpty()) {
                        message.append(" (empty)");
                    }
                    game.informPlayers(message.toString());
                }
                currentPlayer = nextPlayer;
            }
            // Sacrifice all permanents from a pile randomly selected 
            for (Map.Entry<UUID, Map<Integer, Set<UUID>>> playerPiles : playerPermanents.entrySet()) {
                Player player = game.getPlayer(playerPiles.getKey());
                if (player != null) {
                    // decide which pile to sacrifice
                    int sacrificePile = RandomUtil.nextInt(3) + 1; // random number from 1 - 3
                    game.informPlayers(player.getLogName() + " sacrifices pile number " + sacrificePile);
                    for (UUID permanentId : playerPiles.getValue().get(sacrificePile)) {
                        Permanent permanent = game.getPermanent(permanentId);
                        if (permanent != null) {
                            permanent.sacrifice(source, game);
                        }
                    }
                }

            }
            return true;
        }
        return false;
    }

    private UUID getNextPlayerInDirection(boolean left, PlayerList playerList, Game game) {
        UUID nextPlayerId;
        if (left) {
            nextPlayerId = playerList.getNext();
        } else {
            nextPlayerId = playerList.getPrevious();
        }
        return nextPlayerId;
    }
}

class TargetSecondPilePermanent extends TargetPermanent {

    protected Set<UUID> firstPile;

    public TargetSecondPilePermanent(Set<UUID> firstPile, FilterPermanent filter) {
        super(0, Integer.MAX_VALUE, filter, true);
        this.firstPile = firstPile;
    }

    @Override
    public boolean canTarget(UUID controllerId, UUID id, Ability source, Game game, boolean flag) {
        if (firstPile.contains(id)) {
            return false;
        }
        return super.canTarget(controllerId, id, source, game, flag);
    }

    @Override
    public boolean canTarget(UUID controllerId, UUID id, Ability source, Game game) {
        if (firstPile.contains(id)) {
            return false;
        }
        return super.canTarget(controllerId, id, source, game);
    }

    @Override
    public boolean canTarget(UUID id, Ability source, Game game) {
        if (firstPile.contains(id)) {
            return false;
        }
        return super.canTarget(id, source, game);
    }

}
