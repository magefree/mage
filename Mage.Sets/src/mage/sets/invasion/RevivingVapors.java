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
package mage.sets.invasion;

import java.util.UUID;
import mage.MageObject;
import mage.abilities.Ability;
import mage.abilities.effects.OneShotEffect;
import mage.cards.Card;
import mage.cards.CardImpl;
import mage.cards.Cards;
import mage.cards.CardsImpl;
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
 * @author LevelX2
 */
public class RevivingVapors extends CardImpl {

    public RevivingVapors(UUID ownerId) {
        super(ownerId, 265, "Reviving Vapors", Rarity.UNCOMMON, new CardType[]{CardType.INSTANT}, "{2}{W}{U}");
        this.expansionSetCode = "INV";

        // Reveal the top three cards of your library and put one of them into your hand. You gain life equal to that card's converted mana cost. Put all other cards revealed this way into your graveyard.
        this.getSpellAbility().addEffect(new RevivingVaporsEffect());
    }

    public RevivingVapors(final RevivingVapors card) {
        super(card);
    }

    @Override
    public RevivingVapors copy() {
        return new RevivingVapors(this);
    }
}

class RevivingVaporsEffect extends OneShotEffect {

    public RevivingVaporsEffect() {
        super(Outcome.Benefit);
        staticText = "Reveal the top three cards of your library and put one of them into your hand. You gain life equal to that card's converted mana cost. Put all other cards revealed this way into your graveyard";
    }

    public RevivingVaporsEffect(final RevivingVaporsEffect effect) {
        super(effect);
    }

    @Override
    public boolean apply(Game game, Ability source) {
        Player controller = game.getPlayer(source.getControllerId());
        MageObject sourceObject = game.getObject(source.getSourceId());
        if (controller == null || sourceObject ==  null) {
            return false;
        }

        Cards cards = new CardsImpl();
        int count = Math.min(controller.getLibrary().size(), 3);
        for (int i = 0; i < count; i++) {
            Card card = controller.getLibrary().removeFromTop(game);
            if (card != null) {
                cards.add(card);
            } else {
                return false;
            }
        }

        if (!cards.isEmpty()) {
            controller.revealCards(sourceObject.getLogName(), cards, game);
            Card card = null;
            if (cards.size() == 1) {
                card = cards.getRandom(game);
            } else {
                TargetCard target = new TargetCard(Zone.PICK, new FilterCard("card to put into your hand"));
                target.setRequired(true);
                if (controller.choose(Outcome.DrawCard, cards, target, game)) {
                    card = cards.get(target.getFirstTarget(), game);
                }
            }
            if (card != null) {
                cards.remove(card);
                controller.moveCardToHandWithInfo(card, source.getSourceId(), game, Zone.LIBRARY);
            }

            for (Card moveCard: cards.getCards(game)) {
                controller.moveCardToGraveyardWithInfo(moveCard, source.getSourceId(), game, Zone.LIBRARY);
            }
        }
        return true;
    }

    @Override
    public RevivingVaporsEffect copy() {
        return new RevivingVaporsEffect(this);
    }
}
