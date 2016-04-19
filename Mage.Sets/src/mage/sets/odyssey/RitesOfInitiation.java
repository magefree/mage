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
package mage.sets.odyssey;

import java.util.UUID;
import mage.abilities.Ability;
import mage.abilities.effects.OneShotEffect;
import mage.abilities.effects.common.continuous.BoostControlledEffect;
import mage.cards.CardImpl;
import mage.constants.CardType;
import mage.constants.Duration;
import mage.constants.Outcome;
import mage.constants.Rarity;
import mage.game.Game;
import mage.players.Player;

/**
 *
 * @author emerald000
 */
public class RitesOfInitiation extends CardImpl {

    public RitesOfInitiation(UUID ownerId) {
        super(ownerId, 217, "Rites of Initiation", Rarity.COMMON, new CardType[]{CardType.INSTANT}, "{R}");
        this.expansionSetCode = "ODY";

        // Discard any number of cards at random. Creatures you control get +1/+0 until end of turn for each card discarded this way.
        this.getSpellAbility().addEffect(new RitesOfInitiationEffect());
    }

    public RitesOfInitiation(final RitesOfInitiation card) {
        super(card);
    }

    @Override
    public RitesOfInitiation copy() {
        return new RitesOfInitiation(this);
    }
}

class RitesOfInitiationEffect extends OneShotEffect {
    
    RitesOfInitiationEffect() {
        super(Outcome.BoostCreature);
        this.staticText = "Discard any number of cards at random. Creatures you control get +1/+0 until end of turn for each card discarded this way";
    }
    
    RitesOfInitiationEffect(final RitesOfInitiationEffect effect) {
        super(effect);
    }
    
    @Override
    public RitesOfInitiationEffect copy() {
        return new RitesOfInitiationEffect(this);
    }
    
    @Override
    public boolean apply(Game game, Ability source) {
        Player player = game.getPlayer(source.getControllerId());
        if (player != null) {            
            int numToDiscard = player.getAmount(0, player.getHand().size(), "Discard how many cards at random?", game);
            player.discard(numToDiscard, true, source, game);
            game.addEffect(new BoostControlledEffect(numToDiscard, 0, Duration.EndOfTurn), source);
            return true;
        }
        return false;
    }
}
