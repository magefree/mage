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
package mage.sets.commander;

import java.util.UUID;
import mage.MageInt;
import mage.abilities.Ability;
import mage.abilities.common.EntersBattlefieldTriggeredAbility;
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
 * @author jeffwadsworth
 *
 */
public class DesecratorHag extends CardImpl<DesecratorHag> {

    public DesecratorHag(UUID ownerId) {
        super(ownerId, 193, "Desecrator Hag", Rarity.COMMON, new CardType[]{CardType.CREATURE}, "{2}{B/G}{B/G}");
        this.expansionSetCode = "CMD";
        this.subtype.add("Hag");

        this.color.setGreen(true);
        this.color.setBlack(true);
        this.power = new MageInt(2);
        this.toughness = new MageInt(2);

        // When Desecrator Hag enters the battlefield, return to your hand the creature card in your graveyard with the greatest power. If two or more cards are tied for greatest power, you choose one of them.
        this.addAbility(new EntersBattlefieldTriggeredAbility(new DesecratorHagEffect(), false));

    }

    public DesecratorHag(final DesecratorHag card) {
        super(card);
    }

    @Override
    public DesecratorHag copy() {
        return new DesecratorHag(this);
    }
}

class DesecratorHagEffect extends OneShotEffect<DesecratorHagEffect> {

    int creatureGreatestPower = 0;
    Cards cards = new CardsImpl();
    TargetCard target = new TargetCard(Zone.GRAVEYARD, new FilterCard());

    public DesecratorHagEffect() {
        super(Outcome.DrawCard);
        this.staticText = "return to your hand the creature card in your graveyard with the greatest power. If two or more cards are tied for greatest power, you choose one of them";
    }

    public DesecratorHagEffect(final DesecratorHagEffect effect) {
        super(effect);
    }

    @Override
    public DesecratorHagEffect copy() {
        return new DesecratorHagEffect(this);
    }

    @Override
    public boolean apply(Game game, Ability source) {
        Player you = game.getPlayer(source.getControllerId());
        if (you != null) {
            for (Card card : you.getGraveyard().getCards(game)) {
                if (card.getCardType().contains(CardType.CREATURE)) {
                    if (card.getPower().getValue() > creatureGreatestPower) {
                        creatureGreatestPower = card.getPower().getValue();
                        cards.clear();
                        cards.add(card);
                    } else {
                        if (card.getPower().getValue() == creatureGreatestPower) {
                            cards.add(card);
                        }
                    }
                }
            }
            if (cards.size() == 0) {
                return false;
            }
            if (cards.size() > 1
                    && you.choose(Outcome.DrawCard, cards, target, game)) {
                if (target != null) {
                    Card card = game.getCard(target.getFirstTarget());
                    if (card != null) {
                        return you.moveCardToHandWithInfo(card, source.getSourceId(), game, Zone.GRAVEYARD);
                    }
                }
            } else {
                for (Card card : cards.getCards(game)) {
                    return you.moveCardToHandWithInfo(card, source.getSourceId(), game, Zone.GRAVEYARD);
                }
            }
        }
        return false;
    }
}