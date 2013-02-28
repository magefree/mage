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
import mage.Constants.CardType;
import mage.Constants.Duration;
import mage.Constants.Outcome;
import mage.Constants.Rarity;
import mage.Constants.Zone;
import mage.abilities.Ability;
import mage.abilities.common.SimpleStaticAbility;
import mage.abilities.effects.ReplacementEffectImpl;
import mage.abilities.mana.ColorlessManaAbility;
import mage.cards.Card;
import mage.cards.CardImpl;
import mage.cards.Cards;
import mage.cards.CardsImpl;
import mage.game.Game;
import mage.game.events.GameEvent;
import mage.players.Player;

/**
 *
 * @author LevelX2
 */
public class SerumPowder extends CardImpl<SerumPowder> {

    public SerumPowder(UUID ownerId) {
        super(ownerId, 138, "Serum Powder", Rarity.RARE, new CardType[]{CardType.ARTIFACT}, "{3}");
        this.expansionSetCode = "DST";

        // {tap}: Add {1} to your mana pool.
        this.addAbility(new ColorlessManaAbility());
        
        // Any time you could mulligan and Serum Powder is in your hand, you may exile all the cards from your hand, then draw that many cards.
        this.addAbility(new SimpleStaticAbility(Zone.HAND, new SerumPowderReplaceEffect()));
    }

    public SerumPowder(final SerumPowder card) {
        super(card);
    }

    @Override
    public SerumPowder copy() {
        return new SerumPowder(this);
    }
}

class SerumPowderReplaceEffect extends ReplacementEffectImpl<SerumPowderReplaceEffect> {
    SerumPowderReplaceEffect() {
        super(Duration.EndOfGame, Outcome.Detriment);
        staticText = "Any time you could mulligan and {this} is in your hand, you may exile all the cards from your hand, then draw that many cards";
    }

    SerumPowderReplaceEffect(final SerumPowderReplaceEffect effect) {
        super(effect);
    }

    @Override
    public boolean replaceEvent(GameEvent event, Ability source, Game game) {
        Player controller = game.getPlayer(source.getControllerId());
        Card sourceCard = game.getCard(source.getSourceId());
        if (controller != null && sourceCard != null) {
            int cardsHand = controller.getHand().size();
            if (cardsHand > 0){
                Cards cards = new CardsImpl();
                for (UUID cardId: controller.getHand()) {
                    cards.add(game.getCard(cardId));
                }
                for (Card card: cards.getCards(game)) {
                    card.moveToExile(null, null, source.getId(), game);
                }
                controller.drawCards(cardsHand, game);
            }
            game.informPlayers(new StringBuilder(sourceCard.getName()).append(": ").append(controller.getName()).append(" exiles hand and draws ").append(cardsHand).append(" card(s)").toString());
            return true;
        }
        return false;
    }

    @Override
    public boolean applies(GameEvent event, Ability source, Game game) {
        if (event.getType() == GameEvent.EventType.CAN_TAKE_MULLIGAN && source.getControllerId().equals(event.getPlayerId())) {
            Player controller = game.getPlayer(source.getControllerId());
            if (controller != null) {
                return controller.chooseUse(outcome, "Exile all cards from hand and draw that many cards?", game);
            }
        }
        return false;
    }

    @Override
    public boolean apply(Game game, Ability source) {
        return false;
    }

    @Override
    public SerumPowderReplaceEffect copy() {
        return new SerumPowderReplaceEffect(this);
    }
}
