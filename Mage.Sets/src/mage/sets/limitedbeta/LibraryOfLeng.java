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
package mage.sets.limitedbeta;

import java.util.UUID;
import mage.abilities.Ability;
import mage.abilities.common.SimpleStaticAbility;
import mage.abilities.effects.Effect;
import mage.abilities.effects.ReplacementEffectImpl;
import mage.abilities.effects.common.continuous.MaximumHandSizeControllerEffect;
import mage.cards.Card;
import mage.cards.CardImpl;
import mage.cards.Cards;
import mage.cards.CardsImpl;
import mage.constants.CardType;
import mage.constants.Duration;
import mage.constants.Outcome;
import mage.constants.Rarity;
import mage.constants.Zone;
import mage.game.Game;
import mage.game.events.GameEvent;
import mage.game.events.GameEvent.EventType;
import mage.game.events.ZoneChangeEvent;
import mage.players.Player;

/**
 *
 * @author LevelX2
 */
public class LibraryOfLeng extends CardImpl {

    public LibraryOfLeng(UUID ownerId) {
        super(ownerId, 259, "Library of Leng", Rarity.UNCOMMON, new CardType[]{CardType.ARTIFACT}, "{1}");
        this.expansionSetCode = "LEB";

        // You have no maximum hand size.
        Effect effect = new MaximumHandSizeControllerEffect(Integer.MAX_VALUE, Duration.WhileOnBattlefield, MaximumHandSizeControllerEffect.HandSizeModification.SET);
        addAbility(new SimpleStaticAbility(Zone.BATTLEFIELD, effect));

        // If an effect causes you to discard a card, discard it, but you may put it on top of your library instead of into your graveyard.
        addAbility(new SimpleStaticAbility(Zone.BATTLEFIELD, new LibraryOfLengEffect()));

    }

    public LibraryOfLeng(final LibraryOfLeng card) {
        super(card);
    }

    @Override
    public LibraryOfLeng copy() {
        return new LibraryOfLeng(this);
    }
}

class LibraryOfLengEffect extends ReplacementEffectImpl {

    private UUID cardId;
    private int zoneChangeCounter;

    public LibraryOfLengEffect() {
        super(Duration.WhileOnBattlefield, Outcome.Benefit);
        staticText = "If an effect causes you to discard a card, discard it, but you may put it on top of your library instead of into your graveyard";
    }

    public LibraryOfLengEffect(final LibraryOfLengEffect effect) {
        super(effect);
        this.cardId = effect.cardId;
        this.zoneChangeCounter = effect.zoneChangeCounter;
    }

    @Override
    public LibraryOfLengEffect copy() {
        return new LibraryOfLengEffect(this);
    }

    @Override
    public boolean checksEventType(GameEvent event, Game game) {
        return event.getType().equals(EventType.DISCARD_CARD)
                || event.getType().equals(EventType.ZONE_CHANGE);
    }

    @Override
    public boolean applies(GameEvent event, Ability source, Game game) {
        if (event.getType().equals(EventType.DISCARD_CARD)) {
            return event.getPlayerId().equals(source.getControllerId());
        }
        if (event.getType().equals(EventType.ZONE_CHANGE)) {
            if (event.getTargetId().equals(cardId) && game.getState().getZoneChangeCounter(event.getTargetId()) == zoneChangeCounter) {
                if (((ZoneChangeEvent) event).getFromZone().equals(Zone.HAND) && ((ZoneChangeEvent) event).getToZone().equals(Zone.GRAVEYARD)) {
                    return true;
                }
            }
        }
        return false;
    }

    @Override
    public boolean replaceEvent(GameEvent event, Ability source, Game game) {
        if (event.getType().equals(EventType.DISCARD_CARD)) {
            // only save card info
            Card card = game.getCard(event.getTargetId());
            if (card != null) {
                cardId = card.getId();
                zoneChangeCounter = game.getState().getZoneChangeCounter(cardId);
            }
            return false;
        }
        if (event.getType().equals(EventType.ZONE_CHANGE)) {
            Player controller = game.getPlayer(source.getControllerId());
            Card card = game.getCard(event.getTargetId());
            if (controller != null && card != null) {
                cardId = null;
                zoneChangeCounter = 0;
                if (controller.chooseUse(outcome, "Put " + card.getIdName() + " on top of your library instead?", source, game)) {
                    Cards cardsToLibrary = new CardsImpl(card);
                    controller.putCardsOnTopOfLibrary(cardsToLibrary, game, source, false);
                    return true;
                }
            }
        }
        return false;
    }

}
