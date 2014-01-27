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
import java.util.Map;
import java.util.UUID;
import mage.abilities.Ability;
import mage.abilities.effects.ContinuousEffect;
import mage.abilities.effects.OneShotEffect;
import mage.abilities.effects.common.continious.GainControlTargetEffect;
import mage.cards.CardImpl;
import mage.constants.CardType;
import mage.constants.Duration;
import mage.constants.Outcome;
import mage.constants.Rarity;
import mage.filter.common.FilterCreaturePermanent;
import mage.filter.predicate.permanent.ControllerIdPredicate;
import mage.game.Game;
import mage.game.permanent.Permanent;
import mage.players.Player;
import mage.players.PlayerList;
import mage.target.Target;
import mage.target.common.TargetCreaturePermanent;
import mage.target.targetpointer.FixedTarget;

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
            Map<UUID, UUID> playerCreature = new HashMap<UUID,UUID>();
            boolean left = source.getChoices().get(0).getChoice().equals("Left");
            PlayerList playerList = game.getState().getPlayerList();
            while (!playerList.get().equals(source.getControllerId()) && controller.isInGame()) {
                playerList.getNext();
            }
            Player currentPlayer = game.getPlayer(playerList.get());
            Player nextPlayer;
            UUID firstNextPlayer = null;

            while (!getNextPlayerInDirection(left, playerList, game).equals(firstNextPlayer) && controller.isInGame()){
                nextPlayer = game.getPlayer(playerList.get());
                if (nextPlayer == null) {
                    return false;
                }
                if (firstNextPlayer == null) {
                    firstNextPlayer = nextPlayer.getId();
                }
                // if player is in range he chooses 3 piles
                if (currentPlayer != null && controller.getInRange().contains(currentPlayer.getId())) {



                    FilterCreaturePermanent filter = new FilterCreaturePermanent(new StringBuilder("creature controlled by ").append(nextPlayer.getName()).toString());
                    filter.add(new ControllerIdPredicate(nextPlayer.getId()));
                    Target target = new TargetCreaturePermanent(filter, true);
                    target.setNotTarget(false);
                    if (target.canChoose(source.getSourceId(), currentPlayer.getId(), game)) {
                        if (currentPlayer.chooseTarget(outcome, target, source, game)) {
                            playerCreature.put(currentPlayer.getId(), target.getFirstTarget());
                        }
                    }
                }
                currentPlayer = nextPlayer;
            }
            // change control of targets
            for (Map.Entry<UUID, UUID> entry : playerCreature.entrySet()) {
                Player player = game.getPlayer(entry.getKey());
                if (player != null) {
                    Permanent creature = game.getPermanent(entry.getValue());
                    if (creature != null) {
                        ContinuousEffect effect = new GainControlTargetEffect(Duration.EndOfGame, player.getId());
                        effect.setTargetPointer(new FixedTarget(creature.getId()));
                        game.addEffect(effect, source);
                        game.informPlayers(new StringBuilder(player.getName()).append(" gains control of ").append(creature.getName()).toString());
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
