/*
 *  Copyright 2010 BetaSteward_at_googlemail.com. All rights reserved.
 *
 *  Redistribution and use in source and binary forms, with or without modification, are
 *  permitted provided that the following conditions are met:
 *
 *     1. Redistributions of source code must retain the above copyright notice, this list of
 *        conditions and the following disclaimer.
 *
 *     2. Redistributions in binary form must reproduce the above copyright notice, this list
 *        of conditions and the following disclaimer in the documentation and/or other materials
 *        provided with the distribution.
 *
 *  THIS SOFTWARE IS PROVIDED BY BetaSteward_at_googlemail.com ``AS IS'' AND ANY EXPRESS OR IMPLIED
 *  WARRANTIES, INCLUDING, BUT NOT LIMITED TO, THE IMPLIED WARRANTIES OF MERCHANTABILITY AND
 *  FITNESS FOR A PARTICULAR PURPOSE ARE DISCLAIMED. IN NO EVENT SHALL BetaSteward_at_googlemail.com OR
 *  CONTRIBUTORS BE LIABLE FOR ANY DIRECT, INDIRECT, INCIDENTAL, SPECIAL, EXEMPLARY, OR
 *  CONSEQUENTIAL DAMAGES (INCLUDING, BUT NOT LIMITED TO, PROCUREMENT OF SUBSTITUTE GOODS OR
 *  SERVICES; LOSS OF USE, DATA, OR PROFITS; OR BUSINESS INTERRUPTION) HOWEVER CAUSED AND ON
 *  ANY THEORY OF LIABILITY, WHETHER IN CONTRACT, STRICT LIABILITY, OR TORT (INCLUDING
 *  NEGLIGENCE OR OTHERWISE) ARISING IN ANY WAY OUT OF THE USE OF THIS SOFTWARE, EVEN IF
 *  ADVISED OF THE POSSIBILITY OF SUCH DAMAGE.
 *
 *  The views and conclusions contained in the software and documentation are those of the
 *  authors and should not be interpreted as representing official policies, either expressed
 *  or implied, of BetaSteward_at_googlemail.com.
 */
package mage.sets.bornofthegods;

import java.util.HashMap;
import java.util.LinkedHashMap;
import java.util.LinkedHashSet;
import java.util.Map;
import java.util.Random;
import java.util.Set;
import java.util.UUID;
import mage.abilities.Ability;
import mage.abilities.effects.OneShotEffect;
import mage.cards.CardImpl;
import mage.constants.CardType;
import mage.constants.Outcome;
import mage.constants.Rarity;
import mage.filter.FilterPermanent;
import mage.filter.predicate.permanent.ControllerIdPredicate;
import mage.game.Game;
import mage.game.permanent.Permanent;
import mage.players.Player;
import mage.players.PlayerList;
import mage.target.Target;
import mage.target.TargetPermanent;

/**
 *
 * @author LevelX2
 */
public class WhimsOfTheFates extends CardImpl<WhimsOfTheFates> {

    public WhimsOfTheFates(UUID ownerId) {
        super(ownerId, 115, "Whims of the Fates", Rarity.RARE, new CardType[]{CardType.SORCERY}, "{5}{R}");
        this.expansionSetCode = "BNG";

        this.color.setRed(true);

        // Starting with you, each player separates all permanents he or she controls into three piles. Then each player chooses one of his or her piles at random and sacrifices those permanents.
        this.getSpellAbility().addEffect(new WhimsOfTheFateEffect());
    }

    public WhimsOfTheFates(final WhimsOfTheFates card) {
        super(card);
    }

    @Override
    public WhimsOfTheFates copy() {
        return new WhimsOfTheFates(this);
    }
}

class WhimsOfTheFateEffect extends OneShotEffect<WhimsOfTheFateEffect> {

    protected static Random rnd = new Random();

    public WhimsOfTheFateEffect() {
        super(Outcome.Detriment);
        this.staticText = "Starting with you, each player separates all permanents he or she controls into three piles. Then each player chooses one of his or her piles at random and sacrifices those permanents.";
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
            Map<UUID, Map<Integer, Set<UUID>>> playerPermanents = new LinkedHashMap<UUID, Map<Integer, Set<UUID>>>();

            PlayerList playerList = game.getState().getPlayerList();
            while (!playerList.get().equals(source.getControllerId()) && controller.isInGame()) {
                playerList.getNext();
            }
            Player currentPlayer = game.getPlayer(playerList.get());
            Player nextPlayer;
            UUID firstNextPlayer = null;

            while (!getNextPlayerInDirection(true, playerList, game).equals(firstNextPlayer) && controller.isInGame()) {
                nextPlayer = game.getPlayer(playerList.get());
                if (nextPlayer == null) {
                    return false;
                }
                if (firstNextPlayer == null) {
                    firstNextPlayer = nextPlayer.getId();
                }
                // if player is in range of controller he chooses 3 piles with all its permanents
                if (currentPlayer != null && controller.getInRange().contains(currentPlayer.getId())) {
                    Map<Integer, Set<UUID>> playerPiles = new HashMap<Integer, Set<UUID>>();
                    for (int i = 1; i < 4; i++) {
                        playerPiles.put(i, new LinkedHashSet<UUID>());
                    }
                    playerPermanents.put(currentPlayer.getId(), playerPiles);
                    for (int i = 1; i < 3; i++) {
                        FilterPermanent filter = new FilterPermanent(
                                new StringBuilder("the permanents for the ").append(i == 1 ? "first " : "second ").append("pile").toString());
                        filter.add(new ControllerIdPredicate(currentPlayer.getId()));
                        Target target;
                        if (i == 1) {
                            target = new TargetPermanent(0, Integer.MAX_VALUE, filter, true);
                        } else {
                            target = new TargetSecondPilePermanent(playerPiles.get(1), filter);
                        }
                        currentPlayer.chooseTarget(outcome, target, source, game);
                        StringBuilder message = new StringBuilder(currentPlayer.getName()).append(" pile ").append(i).append(": ");
                        if (target.getTargets().isEmpty()) {
                            message.append(" (empty)");
                        } else {
                            for (UUID permanentId : target.getTargets()) {
                                Permanent permanent = game.getPermanent(permanentId);
                                if (permanent != null) {
                                    message.append(permanent.getName()).append(" ");
                                }
                            }
                        }
                        game.informPlayers(message.toString());
                        playerPiles.get(i).addAll(target.getTargets());
                    }

                    // add all permanents not targeted yet to the third pile
                    StringBuilder message = new StringBuilder(currentPlayer.getName()).append(" pile 3: ");
                    for (Permanent permanent : game.getState().getBattlefield().getAllActivePermanents(currentPlayer.getId())) {
                        if (!playerPiles.get(1).contains(permanent.getId()) && !playerPiles.get(2).contains(permanent.getId())) {
                            playerPiles.get(3).add(permanent.getId());
                            message.append(permanent.getName()).append(" ");
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
                    int sacrificePile = rnd.nextInt(3) + 1; // random number from 1 - 3
                    game.informPlayers(new StringBuilder(player.getName()).append(" sacrifices pile number ").append(sacrificePile).toString());
                    for (UUID permanentId : playerPiles.getValue().get(sacrificePile)) {
                        Permanent permanent = game.getPermanent(permanentId);
                        if (permanent != null) {
                            permanent.sacrifice(source.getSourceId(), game);
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
    public boolean canTarget(UUID controllerId, UUID id, UUID sourceId, Game game, boolean flag) {
        if (firstPile.contains(id)) {
            return false;
        }
        return super.canTarget(controllerId, id, sourceId, game, flag);
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
