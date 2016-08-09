/*
 * Copyright 2010 BetaSteward_at_googlemail.com. All rights reserved.
 *
 * Redistribution and use in source and binary forms, with or without modification, are
 * permitted provided that the following conditions are met:
 *
 *    1. Redistributions of source code must retain the above copyright notice, this list of
 *       conditions and the following disclaimer.
 *
 *    2. Redistributions in binary form must reproduce the above copyright notice, this list
 *       of conditions and the following disclaimer in the documentation and/or other materials
 *       provided with the distribution.
 *
 * THIS SOFTWARE IS PROVIDED BY BetaSteward_at_googlemail.com ``AS IS'' AND ANY EXPRESS OR IMPLIED
 * WARRANTIES, INCLUDING, BUT NOT LIMITED TO, THE IMPLIED WARRANTIES OF MERCHANTABILITY AND
 * FITNESS FOR A PARTICULAR PURPOSE ARE DISCLAIMED. IN NO EVENT SHALL BetaSteward_at_googlemail.com OR
 * CONTRIBUTORS BE LIABLE FOR ANY DIRECT, INDIRECT, INCIDENTAL, SPECIAL, EXEMPLARY, OR
 * CONSEQUENTIAL DAMAGES (INCLUDING, BUT NOT LIMITED TO, PROCUREMENT OF SUBSTITUTE GOODS OR
 * SERVICES; LOSS OF USE, DATA, OR PROFITS; OR BUSINESS INTERRUPTION) HOWEVER CAUSED AND ON
 * ANY THEORY OF LIABILITY, WHETHER IN CONTRACT, STRICT LIABILITY, OR TORT (INCLUDING
 * NEGLIGENCE OR OTHERWISE) ARISING IN ANY WAY OUT OF THE USE OF THIS SOFTWARE, EVEN IF
 * ADVISED OF THE POSSIBILITY OF SUCH DAMAGE.
 *
 * The views and conclusions contained in the software and documentation are those of the
 * authors and should not be interpreted as representing official policies, either expressed
 * or implied, of BetaSteward_at_googlemail.com.
 */
package mage.game.permanent;

import java.util.ArrayList;
import java.util.UUID;
import mage.cards.Card;
import mage.cards.CardsImpl;
import mage.cards.MeldCard;
import mage.constants.Zone;
import mage.game.Game;
import mage.game.events.ZoneChangeEvent;
import mage.players.Player;

/**
 *
 * @author emerald000
 */
public class PermanentMeld extends PermanentCard {

    public PermanentMeld(Card card, UUID controllerId, Game game) {
        super(card, controllerId, game);
    }

    @Override
    public int getConvertedManaCost() {
        if (this.isCopy()) {
            return 0;
        } else {
            return this.getCard().getConvertedManaCost();
        }
    }

    @Override
    public boolean moveToZone(Zone toZone, UUID sourceId, Game game, boolean flag, ArrayList<UUID> appliedEffects) {
        ZoneChangeEvent event = new ZoneChangeEvent(this.getId(), sourceId, this.getOwnerId(), Zone.BATTLEFIELD, toZone, appliedEffects);
        if (!game.replaceEvent(event)) {
            Player controller = game.getPlayer(this.getControllerId());
            if (controller != null) {
                controller.removeFromBattlefield(this, game);
                updateZoneChangeCounter(game);
                MeldCard meldCard = (MeldCard) this.getCard();
                Card topHalfCard = meldCard.getTopHalfCard();
                Card bottomHalfCard = meldCard.getBottomHalfCard();
                switch (event.getToZone()) {
                    case GRAVEYARD:
                        game.getPlayer(this.getOwnerId()).putInGraveyard(topHalfCard, game, true);
                        game.getPlayer(this.getOwnerId()).putInGraveyard(bottomHalfCard, game, true);
                        break;
                    case HAND:
                        game.getPlayer(this.getOwnerId()).getHand().add(topHalfCard);
                        game.getPlayer(this.getOwnerId()).getHand().add(bottomHalfCard);
                        break;
                    case EXILED:
                        game.getExile().getPermanentExile().add(topHalfCard);
                        game.getExile().getPermanentExile().add(bottomHalfCard);
                        break;
                    case LIBRARY:
                        CardsImpl cardsToMove = new CardsImpl();
                        cardsToMove.add(topHalfCard);
                        cardsToMove.add(bottomHalfCard);
                        if (flag) {
                            controller.putCardsOnTopOfLibrary(cardsToMove, game, null, true);
                        } else {
                            controller.putCardsOnBottomOfLibrary(cardsToMove, game, null, true);
                        }
                        break;
                    default:
                        return false;
                }
                meldCard.setMelded(false);
                game.setZone(topHalfCard.getId(), event.getToZone());
                game.setZone(bottomHalfCard.getId(), event.getToZone());
                meldCard.setTopLastZoneChangeCounter(topHalfCard.getZoneChangeCounter(game));
                meldCard.setBottomLastZoneChangeCounter(bottomHalfCard.getZoneChangeCounter(game));
                game.addSimultaneousEvent(event);
                return true;
            }
        }
        return false;
    }

    @Override
    public boolean moveToExile(UUID exileId, String name, UUID sourceId, Game game, ArrayList<UUID> appliedEffects) {
        ZoneChangeEvent event = new ZoneChangeEvent(this.getId(), sourceId, this.getOwnerId(), Zone.BATTLEFIELD, Zone.EXILED, appliedEffects);
        if (!game.replaceEvent(event)) {
            Player controller = game.getPlayer(this.getControllerId());
            if (controller != null) {
                controller.removeFromBattlefield(this, game);
                updateZoneChangeCounter(game);
                MeldCard meldCard = (MeldCard) this.getCard();
                Card topHalfCard = meldCard.getTopHalfCard();
                Card bottomHalfCard = meldCard.getBottomHalfCard();
                switch (event.getToZone()) {
                    case GRAVEYARD:
                        game.getPlayer(this.getOwnerId()).putInGraveyard(topHalfCard, game, true);
                        game.getPlayer(this.getOwnerId()).putInGraveyard(bottomHalfCard, game, true);
                        break;
                    case HAND:
                        game.getPlayer(this.getOwnerId()).getHand().add(topHalfCard);
                        game.getPlayer(this.getOwnerId()).getHand().add(bottomHalfCard);
                        break;
                    case EXILED:
                        if (exileId == null) {
                            game.getExile().getPermanentExile().add(topHalfCard);
                            game.getExile().getPermanentExile().add(bottomHalfCard);
                        } else {
                            game.getExile().createZone(exileId, name).add(topHalfCard);
                            game.getExile().getExileZone(exileId).add(bottomHalfCard);
                        }
                        break;
                    case LIBRARY:
                        CardsImpl cardsToMove = new CardsImpl();
                        cardsToMove.add(topHalfCard);
                        cardsToMove.add(bottomHalfCard);
                        if (event.getFlag()) {
                            controller.putCardsOnTopOfLibrary(cardsToMove, game, null, true);
                        } else {
                            controller.putCardsOnBottomOfLibrary(cardsToMove, game, null, true);
                        }
                        break;
                    default:
                        return false;
                }
                meldCard.setMelded(false);
                game.setZone(meldCard.getId(), Zone.OUTSIDE);
                game.setZone(topHalfCard.getId(), event.getToZone());
                game.setZone(bottomHalfCard.getId(), event.getToZone());
                meldCard.setTopLastZoneChangeCounter(topHalfCard.getZoneChangeCounter(game));
                meldCard.setBottomLastZoneChangeCounter(bottomHalfCard.getZoneChangeCounter(game));
                game.addSimultaneousEvent(event);
                return true;
            }
        }
        return false;
    }

    @Override
    public void addCounters(String name, int amount, Game game, ArrayList<UUID> appliedEffects) {
        MeldCard meldCard = (MeldCard) this.getCard();
        if (meldCard.isMelded()) {
            super.addCounters(name, amount, game, appliedEffects);
        } else {
            meldCard.getTopHalfCard().addCounters(name, amount, game, appliedEffects);
            meldCard.getBottomHalfCard().addCounters(name, amount, game, appliedEffects);
        }
    }
}
