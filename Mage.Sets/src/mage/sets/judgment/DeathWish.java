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
package mage.sets.judgment;

import java.util.UUID;
import mage.abilities.Ability;
import mage.abilities.effects.OneShotEffect;
import mage.abilities.effects.common.ExileSourceEffect;
import mage.cards.Card;
import mage.cards.CardImpl;
import mage.cards.Cards;
import mage.constants.CardType;
import mage.constants.Outcome;
import mage.constants.Rarity;
import mage.constants.Zone;
import mage.filter.FilterCard;
import mage.game.Game;
import mage.players.Player;
import mage.target.TargetCard;

/**
 *
 * @author Plopman
 */
public class DeathWish extends CardImpl {

    public DeathWish(UUID ownerId) {
        super(ownerId, 64, "Death Wish", Rarity.RARE, new CardType[]{CardType.SORCERY}, "{1}{B}{B}");
        this.expansionSetCode = "JUD";

        // You may choose a card you own from outside the game and put it into your hand. You lose half your life, rounded up. Exile Death Wish.
        this.getSpellAbility().addEffect(new DeathWishEffect());
        this.getSpellAbility().addEffect(new ExileSourceEffect());
    }

    public DeathWish(final DeathWish card) {
        super(card);
    }

    @Override
    public DeathWish copy() {
        return new DeathWish(this);
    }
}

class DeathWishEffect extends OneShotEffect {

    private static final String choiceText = "Choose a card you own from outside the game, and put it into your hand";

    public DeathWishEffect() {
        super(Outcome.Benefit);
        this.staticText = "You may choose a card you own from outside the game, reveal that card, and put it into your hand. You lose half your life, rounded up";
    }

    public DeathWishEffect(final DeathWishEffect effect) {
        super(effect);
    }

    @Override
    public DeathWishEffect copy() {
        return new DeathWishEffect(this);
    }

    @Override
    public boolean apply(Game game, Ability source) {
        Player controller = game.getPlayer(source.getControllerId());
        if (controller != null) {
            if (controller.chooseUse(Outcome.Benefit, choiceText, source, game)) {
                Cards cards = controller.getSideboard();
                if (cards.isEmpty()) {
                    game.informPlayer(controller, "You have no cards outside the game.");
                } else {
                    TargetCard target = new TargetCard(Zone.OUTSIDE, new FilterCard());
                    if (controller.choose(Outcome.Benefit, cards, target, game)) {
                        Card card = controller.getSideboard().get(target.getFirstTarget(), game);
                        if (card != null) {
                            controller.moveCards(card, null, Zone.HAND, source, game);
                        }
                    }
                }
            }

            int amount = (controller.getLife() + 1) / 2;
            if (amount > 0) {
                controller.loseLife(amount, game);
            }
            return true;
        }
        return false;
    }

}
