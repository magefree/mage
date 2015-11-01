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
package mage.sets.eventide;

import java.util.UUID;
import mage.MageInt;
import mage.MageObject;
import mage.abilities.Ability;
import mage.abilities.common.AttacksTriggeredAbility;
import mage.abilities.effects.OneShotEffect;
import mage.abilities.keyword.IndestructibleAbility;
import mage.cards.Card;
import mage.cards.CardImpl;
import mage.cards.Cards;
import mage.cards.CardsImpl;
import mage.constants.CardType;
import mage.constants.Outcome;
import mage.constants.Rarity;
import mage.constants.Zone;
import mage.game.Game;
import mage.players.Player;

/**
 *
 * @author jeffwadsworth
 *
 */
public class SaplingOfColfenor extends CardImpl {

    public SaplingOfColfenor(UUID ownerId) {
        super(ownerId, 128, "Sapling of Colfenor", Rarity.RARE, new CardType[]{CardType.CREATURE}, "{3}{B/G}{B/G}");
        this.expansionSetCode = "EVE";
        this.supertype.add("Legendary");
        this.subtype.add("Treefolk");
        this.subtype.add("Shaman");

        this.power = new MageInt(2);
        this.toughness = new MageInt(5);

        // Sapling of Colfenor is indestructible.
        this.addAbility(IndestructibleAbility.getInstance());

        // Whenever Sapling of Colfenor attacks, reveal the top card of your library. If it's a creature card, you gain life equal to that card's toughness, lose life equal to its power, then put it into your hand.
        this.addAbility(new AttacksTriggeredAbility(new SaplingOfColfenorEffect(), false));

    }

    public SaplingOfColfenor(final SaplingOfColfenor card) {
        super(card);
    }

    @Override
    public SaplingOfColfenor copy() {
        return new SaplingOfColfenor(this);
    }
}

class SaplingOfColfenorEffect extends OneShotEffect {

    public SaplingOfColfenorEffect() {
        super(Outcome.Benefit);
        this.staticText = "reveal the top card of your library. If it's a creature card, you gain life equal to that card's toughness, lose life equal to its power, then put it into your hand";
    }

    public SaplingOfColfenorEffect(final SaplingOfColfenorEffect effect) {
        super(effect);
    }

    @Override
    public SaplingOfColfenorEffect copy() {
        return new SaplingOfColfenorEffect(this);
    }

    @Override
    public boolean apply(Game game, Ability source) {
        Player controller = game.getPlayer(source.getControllerId());
        MageObject sourceObject = source.getSourceObject(game);
        if (controller != null && sourceObject != null) {
            if (controller.getLibrary().size() > 0) {
                Card card = controller.getLibrary().getFromTop(game);
                Cards cards = new CardsImpl(card);
                controller.revealCards(sourceObject.getIdName(), cards, game);
                if (card.getCardType().contains(CardType.CREATURE)) {
                    controller.gainLife(card.getToughness().getValue(), game);
                    controller.loseLife(card.getPower().getValue(), game);
                    return controller.moveCards(cards.getCards(game), Zone.HAND, source, game);
                }
            }
            return true;
        }
        return false;
    }
}
