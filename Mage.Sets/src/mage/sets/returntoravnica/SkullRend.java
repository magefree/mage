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
package mage.sets.returntoravnica;

import java.util.UUID;
import mage.Constants;
import mage.Constants.CardType;
import mage.Constants.Rarity;
import mage.abilities.Ability;
import mage.abilities.effects.OneShotEffect;
import mage.cards.Card;
import mage.cards.CardImpl;
import mage.cards.Cards;
import mage.game.Game;
import mage.players.Player;

/**
 *
 * @author LevelX2
 */
public class SkullRend extends CardImpl<SkullRend> {

    public SkullRend(UUID ownerId) {
        super(ownerId, 195, "Skull Rend", Rarity.COMMON, new CardType[]{CardType.SORCERY}, "{3}{B}{R}");
        this.expansionSetCode = "RTR";

        this.color.setBlack(true);
        this.color.setRed(true);

        // Skull Rend deals 2 damage to each opponent. Those players each discard two cards at random.
        this.getSpellAbility().addEffect(new SkullRendEffect());

    }

    public SkullRend(final SkullRend card) {
        super(card);
    }

    @Override
    public SkullRend copy() {
        return new SkullRend(this);
    }

    private class SkullRendEffect extends OneShotEffect<SkullRendEffect> {

        public SkullRendEffect() {
            super(Constants.Outcome.Damage);
            staticText = "{this} deals 2 damage to each opponent. Those players each discard two cards at random";
        }

        public SkullRendEffect(final SkullRendEffect effect) {
            super(effect);
        }

        @Override
        public boolean apply(Game game, Ability source) {
            Player controller = game.getPlayer(source.getControllerId());
            if (controller != null) {
                for (UUID playerId: controller.getInRange()) {
                    if (playerId != source.getControllerId()) {
                        Player opponent = game.getPlayer(playerId);
                        if (opponent != null) {
                            // damage
                            opponent.damage(2, source.getId(), game, false, true);
                            // discard 2 cards at random
                            Cards cards = opponent.getHand();
                            for (int i = 0; i < 2 && !cards.isEmpty(); i++) {
                                Card card = cards.getRandom(game);
                                if (card != null) {
                                    card.moveToZone(Constants.Zone.HAND, source.getId(), game, true);
                                    cards.remove(card);
                                }
                            }
                        }
                    }
                }
                return true;
            }
            return false;
        }

        @Override
        public SkullRendEffect copy() {
            return new SkullRendEffect(this);
        }
    }
}