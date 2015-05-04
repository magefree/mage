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
package mage.sets.tenthedition;

import java.util.UUID;
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
import mage.filter.predicate.Predicates;
import mage.filter.predicate.mageobject.CardTypePredicate;
import mage.game.Game;
import mage.game.events.GameEvent;
import mage.players.Player;

/**
 *
 * @author emerald000
 */
public class Abundance extends CardImpl {

    public Abundance(UUID ownerId) {
        super(ownerId, 249, "Abundance", Rarity.RARE, new CardType[]{CardType.ENCHANTMENT}, "{2}{G}{G}");
        this.expansionSetCode = "10E";

        // If you would draw a card, you may instead choose land or nonland and reveal cards from the top of your library until you reveal a card of the chosen kind. Put that card into your hand and put all other cards revealed this way on the bottom of your library in any order.
        this.addAbility(new SimpleStaticAbility(Zone.BATTLEFIELD, new AbundanceReplacementEffect()));
    }

    public Abundance(final Abundance card) {
        super(card);
    }

    @Override
    public Abundance copy() {
        return new Abundance(this);
    }
}

class AbundanceReplacementEffect extends ReplacementEffectImpl {

    AbundanceReplacementEffect() {
        super(Duration.WhileOnBattlefield, Outcome.Benefit);
        staticText = "If you would draw a card, you may instead choose land or nonland and reveal cards from the top of your library until you reveal a card of the chosen kind. Put that card into your hand and put all other cards revealed this way on the bottom of your library in any order";
    }

    AbundanceReplacementEffect(final AbundanceReplacementEffect effect) {
        super(effect);
    }

    @Override
    public AbundanceReplacementEffect copy() {
        return new AbundanceReplacementEffect(this);
    }

    @Override
    public boolean apply(Game game, Ability source) {
        return true;
    }

    @Override
    public boolean replaceEvent(GameEvent event, Ability source, Game game) {
        Player player = game.getPlayer(event.getPlayerId());
        if (player != null) {
            FilterCard filter = new FilterCard();
            if (player.chooseUse(Outcome.Benefit, "Choose land? (No = nonland)", game)) {
                filter.add(new CardTypePredicate(CardType.LAND));
            }
            else {
                filter.add(Predicates.not(new CardTypePredicate(CardType.LAND)));
            }
            Cards cards = new CardsImpl();
            while (player.getLibrary().size() > 0) {
                Card card = player.getLibrary().removeFromTop(game);
                if (filter.match(card, source.getSourceId(), source.getControllerId(), game)) {
                    player.moveCardToHandWithInfo(card, source.getSourceId(), game, Zone.LIBRARY);
                    break;
                }
                cards.add(card);
            }
            player.revealCards("Abundance", cards, game);
            player.putCardsOnBottomOfLibrary(cards, game, source, true);
        }
        return true;
    }
    
    @Override
    public boolean checksEventType(GameEvent event, Game game) {
        return event.getType() == GameEvent.EventType.DRAW_CARD;
    }
    
    @Override
    public boolean applies(GameEvent event, Ability source, Game game) {
        if (event.getPlayerId().equals(source.getControllerId())) {
            Player player = game.getPlayer(source.getControllerId());
            if (player != null) {
                return player.chooseUse(Outcome.Benefit, "Choose land or nonland?", game);
            }
        }
        return false;
    }
}