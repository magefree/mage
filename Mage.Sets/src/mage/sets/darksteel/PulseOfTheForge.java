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
package mage.sets.darksteel;

import java.util.UUID;
import mage.abilities.Ability;
import mage.abilities.effects.OneShotEffect;
import mage.abilities.effects.common.DamageTargetEffect;
import mage.cards.Card;
import mage.cards.CardImpl;
import mage.constants.CardType;
import mage.constants.Outcome;
import mage.constants.Rarity;
import mage.constants.Zone;
import mage.game.Game;
import mage.players.Player;
import mage.target.TargetPlayer;

/**
 *
 * @author fireshoes
 */
public class PulseOfTheForge extends CardImpl {

    public PulseOfTheForge(UUID ownerId) {
        super(ownerId, 66, "Pulse of the Forge", Rarity.RARE, new CardType[]{CardType.INSTANT}, "{1}{R}{R}");
        this.expansionSetCode = "DST";

        // Pulse of the Forge deals 4 damage to target player. Then if that player has more life than you, return Pulse of the Forge to its owner's hand.
        this.getSpellAbility().addEffect(new DamageTargetEffect(4));
        this.getSpellAbility().addTarget(new TargetPlayer());
        this.getSpellAbility().addEffect(new PulseOfTheForgeReturnToHandEffect());
    }

    public PulseOfTheForge(final PulseOfTheForge card) {
        super(card);
    }

    @Override
    public PulseOfTheForge copy() {
        return new PulseOfTheForge(this);
    }
}

class PulseOfTheForgeReturnToHandEffect extends OneShotEffect {

    PulseOfTheForgeReturnToHandEffect() {
        super(Outcome.Benefit);
        this.staticText = "Then if that player has more life than you, return {this} to its owner's hand";
    }

    PulseOfTheForgeReturnToHandEffect(final PulseOfTheForgeReturnToHandEffect effect) {
        super(effect);
    }

    @Override
    public PulseOfTheForgeReturnToHandEffect copy() {
        return new PulseOfTheForgeReturnToHandEffect(this);
    }

    @Override
    public boolean apply(Game game, Ability source) {
        Player controller = game.getPlayer(source.getControllerId());
        if (controller != null) {
            Player player = game.getPlayer(source.getFirstTarget());
            if (player != null && player.getLife() > controller.getLife()) {
                Card card = game.getCard(source.getSourceId());
                controller.moveCards(card, null, Zone.HAND, source, game);
                return true;
            }
        }
        return false;
    }
}
