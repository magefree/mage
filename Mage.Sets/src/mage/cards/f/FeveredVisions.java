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
package mage.cards.f;

import java.util.Set;
import java.util.UUID;
import mage.abilities.Ability;
import mage.abilities.common.BeginningOfEndStepTriggeredAbility;
import mage.abilities.effects.OneShotEffect;
import mage.cards.CardImpl;
import mage.cards.CardSetInfo;
import mage.constants.CardType;
import mage.constants.Outcome;
import mage.constants.TargetController;
import mage.game.Game;
import mage.players.Player;

/**
 *
 * @author fireshoes
 */
public class FeveredVisions extends CardImpl {

    public FeveredVisions(UUID ownerId, CardSetInfo setInfo) {
        super(ownerId,setInfo,new CardType[]{CardType.ENCHANTMENT},"{1}{U}{R}");

        // At the beginning of each player's end step, that player draws a card. If the player is your opponent and has four or more cards in hand,
        // Fevered Visions deals 2 damage to him or her.
        this.addAbility(new BeginningOfEndStepTriggeredAbility(new FeveredVisionsEffect(), TargetController.ANY, false));
    }

    public FeveredVisions(final FeveredVisions card) {
        super(card);
    }

    @Override
    public FeveredVisions copy() {
        return new FeveredVisions(this);
    }
}

class FeveredVisionsEffect extends OneShotEffect {

    public FeveredVisionsEffect() {
        super(Outcome.DrawCard);
        staticText = "that player draws a card. If the player is your opponent and has four or more cards in hand, {this} deals 2 damage to him or her";
    }

    public FeveredVisionsEffect(final FeveredVisionsEffect effect) {
        super(effect);
    }

    @Override
    public boolean apply(Game game, Ability source) {
        Player controller = game.getPlayer(source.getControllerId());
        UUID activePlayerId = game.getActivePlayerId();
        Player player = game.getPlayer(activePlayerId);
        if (controller != null && player != null) {
            player.drawCards(1, game);
            Set<UUID> opponents = game.getOpponents(source.getControllerId());
            if (opponents.contains(player.getId()) && player.getHand().size() > 3) {
                player.damage(2, source.getSourceId(), game, false, true);
            }
            return true;
        }
        return false;
    }

    @Override
    public FeveredVisionsEffect copy() {
        return new FeveredVisionsEffect(this);
    }

}
