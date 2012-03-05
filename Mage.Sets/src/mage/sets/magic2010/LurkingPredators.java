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
package mage.sets.magic2010;

import java.util.UUID;
import mage.Constants.CardType;
import mage.Constants.Outcome;
import mage.Constants.Rarity;
import mage.Constants.Zone;
import mage.abilities.Ability;
import mage.abilities.TriggeredAbilityImpl;
import mage.abilities.effects.OneShotEffect;
import mage.cards.Card;
import mage.cards.CardImpl;
import mage.cards.Cards;
import mage.cards.CardsImpl;
import mage.game.Game;
import mage.game.events.GameEvent;
import mage.game.events.GameEvent.EventType;
import mage.players.Player;

/**
 *
 * @author North
 */
public class LurkingPredators extends CardImpl<LurkingPredators> {

    public LurkingPredators(UUID ownerId) {
        super(ownerId, 190, "Lurking Predators", Rarity.RARE, new CardType[]{CardType.ENCHANTMENT}, "{4}{G}{G}");
        this.expansionSetCode = "M10";

        this.color.setGreen(true);

        // Whenever an opponent casts a spell, reveal the top card of your library. If it's a creature card, put it onto the battlefield. Otherwise, you may put that card on the bottom of your library.
        this.addAbility(new LurkingPredatorsTriggeredAbility());
    }

    public LurkingPredators(final LurkingPredators card) {
        super(card);
    }

    @Override
    public LurkingPredators copy() {
        return new LurkingPredators(this);
    }
}

class LurkingPredatorsTriggeredAbility extends TriggeredAbilityImpl<LurkingPredatorsTriggeredAbility> {

    public LurkingPredatorsTriggeredAbility() {
        super(Zone.BATTLEFIELD, new LurkingPredatorsEffect());
    }

    public LurkingPredatorsTriggeredAbility(final LurkingPredatorsTriggeredAbility ability) {
        super(ability);
    }

    @Override
    public LurkingPredatorsTriggeredAbility copy() {
        return new LurkingPredatorsTriggeredAbility(this);
    }

    @Override
    public boolean checkTrigger(GameEvent event, Game game) {
        if (event.getType() == EventType.SPELL_CAST && game.getOpponents(controllerId).contains(event.getPlayerId())) {
            return true;
        }
        return false;
    }

    @Override
    public String getRule() {
        return "Whenever an opponent casts a spell, reveal the top card of your library. If it's a creature card, put it onto the battlefield. Otherwise, you may put that card on the bottom of your library";
    }
}

class LurkingPredatorsEffect extends OneShotEffect<LurkingPredatorsEffect> {

    public LurkingPredatorsEffect() {
        super(Outcome.PutCreatureInPlay);
        this.staticText = "reveal the top card of your library. If it's a creature card, put it onto the battlefield. Otherwise, you may put that card on the bottom of your library";
    }

    public LurkingPredatorsEffect(final LurkingPredatorsEffect effect) {
        super(effect);
    }

    @Override
    public LurkingPredatorsEffect copy() {
        return new LurkingPredatorsEffect(this);
    }

    @Override
    public boolean apply(Game game, Ability source) {
        Player player = game.getPlayer(source.getControllerId());
        if (player == null) {
            return false;
        }

        if (player.getLibrary().size() > 0) {
            Card card = player.getLibrary().getFromTop(game);
            Cards cards = new CardsImpl();
            cards.add(card);
            player.revealCards("Lurking Predators", cards, game);

            if (card != null) {
                if (card.getCardType().contains(CardType.CREATURE)) {
                    card.putOntoBattlefield(game, Zone.HAND, source.getId(), source.getControllerId());
                } else if (player.chooseUse(Outcome.Neutral, "Put " + card.getName() + " on the bottom of your library?", game)) {
                    card.moveToZone(Zone.LIBRARY, source.getId(), game, false);
                }
            }
        }
        return false;
    }
}
