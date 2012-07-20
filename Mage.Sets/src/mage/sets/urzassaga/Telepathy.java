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
package mage.sets.urzassaga;

import mage.Constants;
import mage.Constants.CardType;
import mage.Constants.Rarity;
import mage.abilities.Ability;
import mage.abilities.common.SimpleStaticAbility;
import mage.abilities.effects.ContinuousEffectImpl;
import mage.cards.CardImpl;
import mage.game.Game;
import mage.players.Player;

import java.util.UUID;

/**
 *
 * @author magenoxx_at_gmail.com
 */
public class Telepathy extends CardImpl<Telepathy> {

    public Telepathy(UUID ownerId) {
        super(ownerId, 102, "Telepathy", Rarity.UNCOMMON, new CardType[]{CardType.ENCHANTMENT}, "{U}");
        this.expansionSetCode = "USG";

        this.color.setBlue(true);

        // Your opponents play with their hands revealed.
        this.addAbility(new SimpleStaticAbility(Constants.Zone.BATTLEFIELD, new OpponentsPlayWithTheTopCardRevealedEffect()));
    }

    public Telepathy(final Telepathy card) {
        super(card);
    }

    @Override
    public Telepathy copy() {
        return new Telepathy(this);
    }
}

class OpponentsPlayWithTheTopCardRevealedEffect extends ContinuousEffectImpl<OpponentsPlayWithTheTopCardRevealedEffect> {

    public OpponentsPlayWithTheTopCardRevealedEffect() {
        super(Constants.Duration.WhileOnBattlefield, Constants.Layer.PlayerEffects, Constants.SubLayer.NA, Constants.Outcome.Detriment);
        staticText = "Your opponents play with their hands revealed";
    }

    public OpponentsPlayWithTheTopCardRevealedEffect(final OpponentsPlayWithTheTopCardRevealedEffect effect) {
        super(effect);
    }

    @Override
    public boolean apply(Game game, Ability source) {
        Player player = game.getPlayer(source.getControllerId());
        if (player != null) {
            for (UUID opponentId : game.getOpponents(player.getId())) {
                Player opponent = game.getPlayer(opponentId);
                if (opponent != null) {
                    opponent.setTopCardRevealed(true);
                }
            }
            return true;
        }
        return false;
    }

    @Override
    public OpponentsPlayWithTheTopCardRevealedEffect copy() {
        return new OpponentsPlayWithTheTopCardRevealedEffect(this);
    }

}
