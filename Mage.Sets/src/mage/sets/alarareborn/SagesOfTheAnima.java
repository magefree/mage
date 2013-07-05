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
package mage.sets.alarareborn;

import java.util.UUID;
import mage.MageInt;
import mage.abilities.Ability;
import mage.abilities.common.SimpleStaticAbility;
import mage.abilities.effects.ReplacementEffectImpl;
import mage.cards.Card;
import mage.cards.CardImpl;
import mage.cards.Cards;
import mage.cards.CardsImpl;
import mage.constants.CardType;
import mage.constants.Duration;
import mage.constants.Outcome;
import mage.constants.Rarity;
import mage.constants.Zone;
import mage.filter.FilterCard;
import mage.filter.predicate.mageobject.CardTypePredicate;
import mage.game.Game;
import mage.game.events.GameEvent;
import mage.players.Player;
import mage.target.TargetCard;

/**
 *
 * @author jeffwadsworth
 */
public class SagesOfTheAnima extends CardImpl<SagesOfTheAnima> {

    public SagesOfTheAnima(UUID ownerId) {
        super(ownerId, 103, "Sages of the Anima", Rarity.RARE, new CardType[]{CardType.CREATURE}, "{3}{G}{U}");
        this.expansionSetCode = "ARB";
        this.subtype.add("Elf");
        this.subtype.add("Wizard");

        this.color.setBlue(true);
        this.color.setGreen(true);
        this.power = new MageInt(3);
        this.toughness = new MageInt(4);

        // If you would draw a card, instead reveal the top three cards of your library. Put all creature cards revealed this way into your hand and the rest on the bottom of your library in any order.
        this.addAbility(new SimpleStaticAbility(Zone.BATTLEFIELD, new SagesOfTheAnimaReplacementEffect()));

    }

    public SagesOfTheAnima(final SagesOfTheAnima card) {
        super(card);
    }

    @Override
    public SagesOfTheAnima copy() {
        return new SagesOfTheAnima(this);
    }
}

class SagesOfTheAnimaReplacementEffect extends ReplacementEffectImpl<SagesOfTheAnimaReplacementEffect> {

    public SagesOfTheAnimaReplacementEffect() {
        super(Duration.WhileOnBattlefield, Outcome.Benefit);
        staticText = "If you would draw a card, instead reveal the top three cards of your library. Put all creature cards revealed this way into your hand and the rest on the bottom of your library in any order";
    }

    public SagesOfTheAnimaReplacementEffect(final SagesOfTheAnimaReplacementEffect effect) {
        super(effect);
    }

    @Override
    public SagesOfTheAnimaReplacementEffect copy() {
        return new SagesOfTheAnimaReplacementEffect(this);
    }

    @Override
    public boolean apply(Game game, Ability source) {
        return true;
    }

    @Override
    public boolean replaceEvent(GameEvent event, Ability source, Game game) {
        FilterCard filter = new FilterCard();
        filter.add(new CardTypePredicate(CardType.CREATURE));
        Player player = game.getPlayer(event.getPlayerId());
        Cards cards = new CardsImpl(Zone.PICK);
        if (player != null) {
            for (int i = 0; i < 3; i++) {
                Card card = player.getLibrary().removeFromTop(game);
                if (card != null) {
                    cards.add(card);
                }
            }
            player.revealCards("Top three cards of library revealed", cards, game);
            for (Card revealedCard : cards.getCards(game)) {
                if (revealedCard.getCardType().contains(CardType.CREATURE)) {
                    revealedCard.moveToZone(Zone.HAND, source.getId(), game, false);
                    cards.remove(revealedCard);
                }
            }
            TargetCard target = new TargetCard(Zone.PICK, new FilterCard());
            target.setRequired(true);
            while (cards.size() > 1) {
                player.choose(Outcome.Neutral, cards, target, game);
                Card card = cards.get(target.getFirstTarget(), game);
                if (card != null) {
                    cards.remove(card);
                    card.moveToZone(Zone.LIBRARY, source.getId(), game, false);
                }
                target.clearChosen();
            }
            if (cards.size() == 1) {
                Card card = cards.get(cards.iterator().next(), game);
                card.moveToZone(Zone.LIBRARY, source.getId(), game, false);
            }
        }
        return true;
    }

    @Override
    public boolean applies(GameEvent event, Ability source, Game game) {
        if (event.getType() == GameEvent.EventType.DRAW_CARD && event.getPlayerId().equals(source.getControllerId())) {
            return true;
        }
        return false;
    }
}
