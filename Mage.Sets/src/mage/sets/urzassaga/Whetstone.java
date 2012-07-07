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

import java.util.UUID;
import mage.Constants;
import mage.Constants.CardType;
import mage.Constants.Rarity;
import mage.Constants.Zone;
import mage.abilities.Ability;
import mage.abilities.common.SimpleActivatedAbility;
import mage.abilities.costs.mana.ManaCostsImpl;
import mage.abilities.effects.OneShotEffect;
import mage.cards.Card;
import mage.cards.CardImpl;
import mage.game.Game;
import mage.players.Player;

/**
 *
 * @author Backfir3
 */
public class Whetstone extends CardImpl<Whetstone> {

    public Whetstone(UUID ownerId) {
        super(ownerId, 316, "Whetstone", Rarity.RARE, new CardType[]{CardType.ARTIFACT}, "{3}");
        this.expansionSetCode = "USG";

        //{3}: Each player puts the top two cards of his or her library into his or her graveyard.
        SimpleActivatedAbility ability = new SimpleActivatedAbility(Constants.Zone.BATTLEFIELD, new WhetstoneEffect(), new ManaCostsImpl("{3}"));
        this.addAbility(ability);
    }

    public Whetstone(final Whetstone card) {
        super(card);
    }

    @Override
    public Whetstone copy() {
        return new Whetstone(this);
    }
}

class WhetstoneEffect extends OneShotEffect<WhetstoneEffect> {

    WhetstoneEffect() {
        super(Constants.Outcome.Detriment);
        staticText = "Each player puts the top two cards of his or her library into his or her graveyard";
    }

    WhetstoneEffect(final WhetstoneEffect effect) {
        super(effect);
    }

    @Override
    public boolean apply(Game game, Ability source) {
        for (UUID playerId : game.getPlayerList()) {
            Player player = game.getPlayer(playerId);
            if (player != null) {
                // putting cards to grave shouldn't end the game, so getting minimun available
                int cardsCount = Math.min(2, player.getLibrary().size());
                for (int i = 0; i < cardsCount; i++) {
                    Card card = player.getLibrary().removeFromTop(game);
                    if (card != null) {
                        card.moveToZone(Zone.GRAVEYARD, source.getId(), game, false);
                    } else {
                        break;
                    }
                }
            }
        }
        return true;
    }

    @Override
    public WhetstoneEffect copy() {
        return new WhetstoneEffect(this);
    }
}
