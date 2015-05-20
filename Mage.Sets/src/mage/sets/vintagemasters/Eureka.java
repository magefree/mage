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
package mage.sets.vintagemasters;

import java.util.UUID;
import mage.abilities.Ability;
import mage.abilities.effects.OneShotEffect;
import mage.cards.Card;
import mage.cards.CardImpl;
import mage.constants.CardType;
import mage.constants.Outcome;
import mage.constants.Rarity;
import mage.constants.Zone;
import mage.filter.common.FilterPermanentCard;
import mage.game.Game;
import mage.players.Player;
import mage.players.PlayerList;
import mage.target.Target;
import mage.target.common.TargetCardInHand;

/**
 *
 * @author LevelX2
 */
public class Eureka extends CardImpl {

    public Eureka(UUID ownerId) {
        super(ownerId, 208, "Eureka", Rarity.MYTHIC, new CardType[]{CardType.SORCERY}, "{2}{G}{G}");
        this.expansionSetCode = "VMA";


        // Starting with you, each player may put a permanent card from his or her hand onto the battlefield. Repeat this process until no one puts a card onto the battlefield.
        this.getSpellAbility().addEffect(new EurekaEffect());
    }

    public Eureka(final Eureka card) {
        super(card);
    }

    @Override
    public Eureka copy() {
        return new Eureka(this);
    }
}

class EurekaEffect extends OneShotEffect {

    public EurekaEffect() {
        super(Outcome.PutCardInPlay);
        this.staticText = "Starting with you, each player may put a permanent card from his or her hand onto the battlefield. Repeat this process until no one puts a card onto the battlefield";
    }

    public EurekaEffect(final EurekaEffect effect) {
        super(effect);
    }

    @Override
    public EurekaEffect copy() {
        return new EurekaEffect(this);
    }

    @Override
    public boolean apply(Game game, Ability source) {
        Player controller = game.getPlayer(source.getControllerId());
        if (controller != null) {
            PlayerList playerList = game.getState().getPlayerList().copy();
            while (!playerList.get().equals(source.getControllerId()) && controller.isInGame()) {
                playerList.getNext();
            }
            Player currentPlayer = game.getPlayer(playerList.get());
            UUID firstInactivePlayer = null;
            Target target = new TargetCardInHand(new FilterPermanentCard());

            while (controller.isInGame()) {
                if (firstInactivePlayer == null) {
                    firstInactivePlayer = currentPlayer.getId();
                }
                if (currentPlayer != null && currentPlayer.isInGame() && controller.getInRange().contains(currentPlayer.getId())) {

                    target.clearChosen();
                    if (target.canChoose(source.getSourceId(), currentPlayer.getId(), game)
                            && currentPlayer.chooseUse(outcome, "Put permanent from your hand to play?", game)) {
                        if (target.chooseTarget(outcome, currentPlayer.getId(), source, game)) {
                            Card card = game.getCard(target.getFirstTarget());
                            if (card != null) {
                                currentPlayer.putOntoBattlefieldWithInfo(card, game, Zone.HAND, source.getSourceId());
                                firstInactivePlayer = null;
                            }
                        }
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
            return true;
        }
        return false;
    }

}
