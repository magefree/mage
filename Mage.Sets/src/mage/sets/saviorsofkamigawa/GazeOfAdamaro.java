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
package mage.sets.saviorsofkamigawa;

import java.util.UUID;
import mage.abilities.Ability;
import mage.abilities.effects.OneShotEffect;
import mage.cards.CardImpl;
import mage.constants.CardType;
import mage.constants.Outcome;
import mage.constants.Rarity;
import mage.game.Game;
import mage.players.Player;
import mage.target.TargetPlayer;

/**
 *
 * @author LevelX2
 */
public class GazeOfAdamaro extends CardImpl {

    public GazeOfAdamaro(UUID ownerId) {
        super(ownerId, 98, "Gaze of Adamaro", Rarity.UNCOMMON, new CardType[]{CardType.INSTANT}, "{2}{R}{R}");
        this.expansionSetCode = "SOK";
        this.subtype.add("Arcane");


        // Gaze of Adamaro deals damage to target player equal to the number of cards in that player's hand.
        this.getSpellAbility().addEffect(new GazeOfAdamaroEffect());
        this.getSpellAbility().addTarget(new TargetPlayer());
    }

    public GazeOfAdamaro(final GazeOfAdamaro card) {
        super(card);
    }

    @Override
    public GazeOfAdamaro copy() {
        return new GazeOfAdamaro(this);
    }
}

class GazeOfAdamaroEffect extends OneShotEffect {

    public GazeOfAdamaroEffect() {
        super(Outcome.Damage);
        this.staticText = "{this} deals damage to target player equal to the number of cards in that player's hand";
    }

    public GazeOfAdamaroEffect(final GazeOfAdamaroEffect effect) {
        super(effect);
    }

    @Override
    public GazeOfAdamaroEffect copy() {
        return new GazeOfAdamaroEffect(this);
    }

    @Override
    public boolean apply(Game game, Ability source) {
        Player targetPlayer = game.getPlayer(getTargetPointer().getFirst(game, source));
        if (targetPlayer != null) {
            targetPlayer.damage(targetPlayer.getHand().size(), source.getSourceId(), game, false, true);
            return true;
        }
        return false;
    }
}
