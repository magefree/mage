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
package mage.sets.eldritchmoon;

import java.util.UUID;
import mage.abilities.Ability;
import mage.abilities.common.SimpleStaticAbility;
import mage.abilities.effects.ReplacementEffectImpl;
import mage.abilities.mana.ColorlessManaAbility;
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
import mage.game.events.ZoneChangeEvent;
import mage.players.Player;

/**
 *
 * @author escplan9 (Derek Monturo - dmontur1 at gmail dot com)
 */
public class NephaliaAcademy extends CardImpl {

    public NephaliaAcademy(UUID ownerId) {
        super(ownerId, 205, "Nephalia Academy", Rarity.UNCOMMON, new CardType[]{CardType.LAND}, "");
        this.expansionSetCode = "EMN";

        // If a spell or ability an opponent controls causes you to discard a card, you may reveal that card and put it on top of your library instead of putting it anywhere else.
        this.addAbility(new SimpleStaticAbility(Zone.BATTLEFIELD, new NephaliaAcademyEffect()));

        // {T}: Add {C} to your mana pool.
        this.addAbility(new ColorlessManaAbility());
    }

    public NephaliaAcademy(final NephaliaAcademy card) {
        super(card);
    }

    @Override
    public NephaliaAcademy copy() {
        return new NephaliaAcademy(this);
    }
}

class NephaliaAcademyEffect extends ReplacementEffectImpl {

    private UUID cardId;
    private int zoneChangeCounter;

    public NephaliaAcademyEffect() {
        super(Duration.WhileOnBattlefield, Outcome.Benefit);
        staticText = "If a spell or ability an opponent controls causes you to discard a card, you may reveal that card and put it on top of your library instead of putting it anywhere else.";
    }

    public NephaliaAcademyEffect(final NephaliaAcademyEffect effect) {
        super(effect);
        this.cardId = effect.cardId;
        this.zoneChangeCounter = effect.zoneChangeCounter;
    }

    @Override
    public NephaliaAcademyEffect copy() {
        return new NephaliaAcademyEffect(this);
    }

    @Override
    public boolean checksEventType(GameEvent event, Game game) {
        return event.getType().equals(GameEvent.EventType.DISCARD_CARD)
                || event.getType().equals(GameEvent.EventType.ZONE_CHANGE);
    }

    @Override
    public boolean applies(GameEvent event, Ability source, Game game) {
        if (event.getType().equals(GameEvent.EventType.DISCARD_CARD)) {
            return event.getPlayerId().equals(source.getControllerId());
        }
        if (event.getType().equals(GameEvent.EventType.ZONE_CHANGE)) {
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
        if (event.getType().equals(GameEvent.EventType.DISCARD_CARD)) {
            // only save card info
            Card card = game.getCard(event.getTargetId());
            if (card != null) {
                cardId = card.getId();
                zoneChangeCounter = game.getState().getZoneChangeCounter(cardId);
            }
            return false;
        }
        if (event.getType().equals(GameEvent.EventType.ZONE_CHANGE)) {
            Player controller = game.getPlayer(source.getControllerId());
            Card card = game.getCard(event.getTargetId());
            if (controller != null && card != null) {
                cardId = null;
                zoneChangeCounter = 0;
                if (controller.chooseUse(outcome, "Put " + card.getIdName() + " on top of your library instead?", source, game)) {
                    
                    Cards cardsToLibrary = new CardsImpl(card);                 
                    // reveal the card then put it on top of your library
                    controller.revealCards(card.getName(), cardsToLibrary, game);
                    controller.putCardsOnTopOfLibrary(cardsToLibrary, game, source, false);
                    return true;
                }
            }
        }
        return false;
    }

}