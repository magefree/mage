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
package mage.cards.k;

import mage.MageInt;
import mage.abilities.Ability;
import mage.abilities.common.BeginningOfEndStepTriggeredAbility;
import mage.abilities.effects.OneShotEffect;
import mage.cards.Card;
import mage.cards.CardImpl;
import mage.cards.CardSetInfo;
import mage.constants.CardType;
import mage.constants.Outcome;
import mage.constants.TargetController;
import mage.constants.Zone;
import mage.filter.FilterCard;
import mage.filter.predicate.mageobject.CardTypePredicate;
import mage.game.Game;
import mage.game.permanent.Permanent;
import mage.players.Player;
import mage.players.PlayerList;
import mage.target.Target;
import mage.target.common.TargetCardInHand;

import java.util.ArrayList;
import java.util.HashMap;
import java.util.Objects;
import java.util.UUID;

/**
 * @author spjspj
 */
public class KynaiosAndTiroOfMeletis extends CardImpl {

    public KynaiosAndTiroOfMeletis(UUID ownerId, CardSetInfo setInfo) {
        super(ownerId, setInfo, new CardType[]{CardType.CREATURE}, "{R}{G}{W}{U}");

        this.supertype.add("Legendary");
        this.subtype.add("Human");
        this.subtype.add("Soldier");
        this.power = new MageInt(2);
        this.toughness = new MageInt(8);

        // At the beginning of your end step, draw a card. Each player may put a land card from his or her hand onto the battlefield, then each opponent who didn't draws a card.
        this.addAbility(new BeginningOfEndStepTriggeredAbility(new KynaiosAndTirosEffect(), TargetController.YOU, false));
    }

    public KynaiosAndTiroOfMeletis(final KynaiosAndTiroOfMeletis card) {
        super(card);
    }

    @Override
    public KynaiosAndTiroOfMeletis copy() {
        return new KynaiosAndTiroOfMeletis(this);
    }
}

class KynaiosAndTirosEffect extends OneShotEffect {

    private static final FilterCard filter = new FilterCard("a land card");

    static {
        filter.add(new CardTypePredicate(CardType.LAND));
    }

    public KynaiosAndTirosEffect() {
        super(Outcome.DrawCard);
        staticText = "draw a card. Each player may put a land card from his or her hand onto the battlefield, then each opponent who didn't draws a card";
    }

    public KynaiosAndTirosEffect(final KynaiosAndTirosEffect effect) {
        super(effect);
    }

    @Override
    public boolean apply(Game game, Ability source) {
        Player controller = game.getPlayer(source.getControllerId());
        if (controller != null) {
            controller.drawCards(1, game);
            PlayerList playerList = game.getState().getPlayerList().copy();

            while (!playerList.get().equals(source.getControllerId()) && controller.canRespond()) {
                playerList.getNext();
            }
            Player currentPlayer = game.getPlayer(playerList.get());
            UUID firstInactivePlayer = null;
            Target target = new TargetCardInHand(filter);
            ArrayList<UUID> noLandPlayers = new ArrayList<>();

            while (controller.canRespond()) {
                if (currentPlayer != null && currentPlayer.canRespond() && game.getState().getPlayersInRange(controller.getId(), game).contains(currentPlayer.getId())) {
                    if (firstInactivePlayer == null) {
                        firstInactivePlayer = currentPlayer.getId();
                    }
                    target.clearChosen();
                    boolean playedLand = false;
                    if (target.canChoose(source.getSourceId(), currentPlayer.getId(), game) && currentPlayer.chooseUse(outcome, "Put a land card from your hand onto the battlefield?", source, game)) {
                        if (target.chooseTarget(outcome, currentPlayer.getId(), source, game)) {
                            Card card = game.getCard(target.getFirstTarget());
                            if (card != null) {
                                currentPlayer.moveCards(card, Zone.BATTLEFIELD, source, game);
                                playedLand = true;
                            }
                        }
                    }
                    if (!playedLand && !Objects.equals(currentPlayer, controller)) {
                        noLandPlayers.add(currentPlayer.getId());
                    }
                }
                // get next player
                playerList.getNext();
                currentPlayer = game.getPlayer(playerList.get());
                // if all player since this player didn't put permanent in play finish the process
                if (currentPlayer.getId().equals(firstInactivePlayer)) {
                    break;
                }
            }

            for (UUID playerId : noLandPlayers) {
                Player player = game.getPlayer(playerId);
                if (player != null) {
                    if (player.chooseUse(outcome.DrawCard, "Draw a card?", source, game)) {
                        player.drawCards(1, game);
                    }
                }
            }
            return true;
        }
        return false;
    }

    @Override
    public KynaiosAndTirosEffect copy() {
        return new KynaiosAndTirosEffect(this);
    }
}
