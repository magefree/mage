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
package mage.sets.apocalypse;

import java.util.UUID;
import mage.MageInt;
import mage.abilities.Ability;
import mage.abilities.common.EntersBattlefieldTriggeredAbility;
import mage.abilities.effects.OneShotEffect;
import mage.cards.Card;
import mage.cards.CardImpl;
import mage.cards.Cards;
import mage.constants.CardType;
import mage.constants.Outcome;
import mage.constants.Rarity;
import mage.constants.Zone;
import mage.game.Game;
import mage.players.Player;

/**
 *
 * @author LevelX2
 */
public class WhirlpoolRider extends CardImpl<WhirlpoolRider> {

    public WhirlpoolRider(UUID ownerId) {
        super(ownerId, 35, "Whirlpool Rider", Rarity.COMMON, new CardType[]{CardType.CREATURE}, "{1}{U}");
        this.expansionSetCode = "APC";
        this.subtype.add("Merfolk");

        this.color.setBlue(true);
        this.power = new MageInt(1);
        this.toughness = new MageInt(1);

        // When Whirlpool Rider enters the battlefield, shuffle the cards from your hand into your library, then draw that many cards.
        this.addAbility(new EntersBattlefieldTriggeredAbility(new WhirlpoolRiderTriggeredEffect()));

    }

    public WhirlpoolRider(final WhirlpoolRider card) {
        super(card);
    }

    @Override
    public WhirlpoolRider copy() {
        return new WhirlpoolRider(this);
    }
}

class WhirlpoolRiderTriggeredEffect extends OneShotEffect<WhirlpoolRiderTriggeredEffect> {

    public WhirlpoolRiderTriggeredEffect() {
        super(Outcome.DrawCard);
        this.staticText = "shuffle the cards from your hand into your library, then draw that many cards";
    }

    public WhirlpoolRiderTriggeredEffect(final WhirlpoolRiderTriggeredEffect effect) {
        super(effect);
    }

    @Override
    public WhirlpoolRiderTriggeredEffect copy() {
        return new WhirlpoolRiderTriggeredEffect(this);
    }

    @Override
    public boolean apply(Game game, Ability source) {
        Player controller = game.getPlayer(source.getControllerId());
        if (controller != null) {
            int cardsHand = controller.getHand().size();
            if (cardsHand > 0){
                Cards cards = controller.getHand();
                for (UUID cardId: cards) {
                    Card card = game.getCard(cardId);
                    if (card != null) {
                        controller.removeFromHand(card, game);
                        card.moveToZone(Zone.LIBRARY, source.getId(), game, true);
                    }
                }
                controller.shuffleLibrary(game);
                controller.drawCards(cardsHand, game);
                return true;
            }
        }

        return false;
    }
}
