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
package mage.sets.onslaught;

import java.util.UUID;
import mage.abilities.Ability;
import mage.abilities.effects.OneShotEffect;
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
 * @author daagar
 */
public class Reminisce extends CardImpl {

    public Reminisce(UUID ownerId) {
        super(ownerId, 105, "Reminisce", Rarity.UNCOMMON, new CardType[]{CardType.SORCERY}, "{2}{U}");
        this.expansionSetCode = "ONS";

        // Target player shuffles his or her graveyard into his or her library.
        this.getSpellAbility().addEffect(new ReminisceEffect());
        this.getSpellAbility().addTarget(new TargetPlayer());
    }

    public Reminisce(final Reminisce card) {
        super(card);
    }

    @Override
    public Reminisce copy() {
        return new Reminisce(this);
    }
}

class ReminisceEffect extends OneShotEffect {
    
    ReminisceEffect() {
        super(Outcome.Neutral);
        this.staticText = "Target player shuffles his or her graveyard into his or her library";
    }
    
    ReminisceEffect(final ReminisceEffect effect) {
        super(effect);
    }
    
    @Override
    public ReminisceEffect copy() {
        return new ReminisceEffect(this);
    }
    
    @Override
    public boolean apply(Game game, Ability source) {
        Player player = game.getPlayer(this.getTargetPointer().getFirst(game, source));
        if (player != null) {
            for (Card card: player.getGraveyard().getCards(game)) {
                card.moveToZone(Zone.LIBRARY, source.getSourceId(), game, true);
            }               
            player.shuffleLibrary(game);
            return true;
        }
        return false;
    }
}
