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
package mage.cards;

import java.util.ArrayList;
import java.util.UUID;
import mage.constants.CardType;
import mage.constants.Rarity;
import mage.constants.Zone;
import static mage.constants.Zone.EXILED;
import static mage.constants.Zone.GRAVEYARD;
import static mage.constants.Zone.HAND;
import static mage.constants.Zone.LIBRARY;
import mage.counters.Counter;
import mage.game.Game;
import mage.game.events.ZoneChangeEvent;
import mage.game.permanent.PermanentMeld;
import mage.players.Player;

/**
 *
 * @author emerald000
 */
public abstract class MeldCard extends CardImpl {

    protected Card topHalfCard;
    protected Card bottomHalfCard;
    protected int topLastZoneChangeCounter;
    protected int bottomLastZoneChangeCounter;
    protected boolean isMelded;

    public MeldCard(UUID ownerId, int cardNumber, String name, Rarity rarity, CardType[] cardTypes, String costs) {
        super(ownerId, cardNumber, name, rarity, cardTypes, costs);
    }

    public MeldCard(MeldCard card) {
        super(card);
        this.topHalfCard = card.topHalfCard;
        this.bottomHalfCard = card.bottomHalfCard;
        this.topLastZoneChangeCounter = card.topLastZoneChangeCounter;
        this.bottomLastZoneChangeCounter = card.bottomLastZoneChangeCounter;
        this.isMelded = card.isMelded;
    }

    public void setMelded(boolean isMelded) {
        this.isMelded = isMelded;
    }

    public boolean isMelded() {
        return isMelded;
    }

    public Card getTopHalfCard() {
        return topHalfCard;
    }

    public void setTopHalfCard(Card topHalfCard, Game game) {
        this.topHalfCard = topHalfCard;
        this.topLastZoneChangeCounter = topHalfCard.getZoneChangeCounter(game);
    }

    public int getTopLastZoneChangeCounter() {
        return topLastZoneChangeCounter;
    }

    public void setTopLastZoneChangeCounter(int topLastZoneChangeCounter) {
        this.topLastZoneChangeCounter = topLastZoneChangeCounter;
    }

    public Card getBottomHalfCard() {
        return bottomHalfCard;
    }

    public void setbottomHalfCard(Card bottomHalfCard, Game game) {
        this.bottomHalfCard = bottomHalfCard;
        this.bottomLastZoneChangeCounter = bottomHalfCard.getZoneChangeCounter(game);
    }

    public int getBottomLastZoneChangeCounter() {
        return bottomLastZoneChangeCounter;
    }

    public void setBottomLastZoneChangeCounter(int bottomLastZoneChangeCounter) {
        this.bottomLastZoneChangeCounter = bottomLastZoneChangeCounter;
    }

    @Override
    public void assignNewId() {
        super.assignNewId();
        topHalfCard.assignNewId();
        bottomHalfCard.assignNewId();
    }

    @Override
    public void setCopy(boolean isCopy) {
        super.setCopy(isCopy);
        topHalfCard.setCopy(isCopy);
        bottomHalfCard.setCopy(isCopy);
    }

    @Override
    public boolean moveToZone(Zone toZone, UUID sourceId, Game game, boolean flag, ArrayList<UUID> appliedEffects) {
        if (this.isMelded()) {
            // Initial move to battlefield
            if (toZone == Zone.BATTLEFIELD) {
                return this.putOntoBattlefield(game, Zone.EXILED, sourceId, this.getOwnerId(), false, false, appliedEffects);
            } // Move when melded from the battlefield to elsewhere
            else {
                ZoneChangeEvent event = new ZoneChangeEvent(this.getId(), sourceId, this.getOwnerId(), Zone.BATTLEFIELD, toZone, appliedEffects);
                if (!game.replaceEvent(event)) {
                    updateZoneChangeCounter(game);
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
                            Player controller = game.getPlayer(this.getOwnerId());
                            if (controller != null) {
                                CardsImpl cardsToMove = new CardsImpl();
                                cardsToMove.add(topHalfCard);
                                cardsToMove.add(bottomHalfCard);
                                if (flag) {
                                    controller.putCardsOnTopOfLibrary(cardsToMove, game, null, true);
                                } else {
                                    controller.putCardsOnBottomOfLibrary(cardsToMove, game, null, true);
                                }
                            }
                            break;
                        default:
                            return false;
                    }
                    this.setMelded(false);
                    game.setZone(topHalfCard.getId(), event.getToZone());
                    game.setZone(bottomHalfCard.getId(), event.getToZone());
                    this.topLastZoneChangeCounter = topHalfCard.getZoneChangeCounter(game);
                    this.bottomLastZoneChangeCounter = bottomHalfCard.getZoneChangeCounter(game);
                    game.addSimultaneousEvent(event);
                    return true;
                } else {
                    return false;
                }
            }
        } else {
            // Try to move the former meld cards after it has already left the battlefield.
            // If the meld parts didn't move from that zone, move them instead of the meld card.
            // Reset the local zcc so the meld card lose track of them.
            boolean returnValue = false;
            if (topLastZoneChangeCounter == topHalfCard.getZoneChangeCounter(game)) {
                topHalfCard.moveToZone(toZone, sourceId, game, flag, appliedEffects);
                topLastZoneChangeCounter = topHalfCard.getZoneChangeCounter(game);
                returnValue = true;
            }
            if (bottomLastZoneChangeCounter == bottomHalfCard.getZoneChangeCounter(game)) {
                bottomHalfCard.moveToZone(toZone, sourceId, game, flag, appliedEffects);
                bottomLastZoneChangeCounter = bottomHalfCard.getZoneChangeCounter(game);
                returnValue = true;
            }
            return returnValue;
        }
    }

    @Override
    public boolean moveToExile(UUID exileId, String name, UUID sourceId, Game game, ArrayList<UUID> appliedEffects) {
        if (this.isMelded()) {
            // Move when melded from the battlefield to exile
            ZoneChangeEvent event = new ZoneChangeEvent(this.getId(), sourceId, this.getOwnerId(), Zone.BATTLEFIELD, Zone.EXILED, appliedEffects);
            if (!game.replaceEvent(event)) {
                updateZoneChangeCounter(game);
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
                        Player controller = game.getPlayer(this.getOwnerId());
                        if (controller != null) {
                            CardsImpl cardsToMove = new CardsImpl();
                            cardsToMove.add(topHalfCard);
                            cardsToMove.add(bottomHalfCard);
                            if (event.getFlag()) {
                                controller.putCardsOnTopOfLibrary(cardsToMove, game, null, true);
                            } else {
                                controller.putCardsOnBottomOfLibrary(cardsToMove, game, null, true);
                            }
                        }
                        break;
                    default:
                        return false;
                }
                this.setMelded(false);
                game.setZone(topHalfCard.getId(), event.getToZone());
                game.setZone(bottomHalfCard.getId(), event.getToZone());
                this.topLastZoneChangeCounter = topHalfCard.getZoneChangeCounter(game);
                this.bottomLastZoneChangeCounter = bottomHalfCard.getZoneChangeCounter(game);
                game.addSimultaneousEvent(event);
                return true;
            } else {
                return false;
            }
        } else {
            // Try to move the former meld cards after it has already left the battlefield.
            // If the meld parts didn't move from that zone, move them instead of the meld card.
            // Reset the local zcc so the meld card lose track of them.
            boolean returnValue = false;
            if (topLastZoneChangeCounter == topHalfCard.getZoneChangeCounter(game)) {
                topHalfCard.moveToExile(exileId, name, sourceId, game, appliedEffects);
                topLastZoneChangeCounter = topHalfCard.getZoneChangeCounter(game);
                returnValue = true;
            }
            if (bottomLastZoneChangeCounter == bottomHalfCard.getZoneChangeCounter(game)) {
                bottomHalfCard.moveToExile(exileId, name, sourceId, game, appliedEffects);
                bottomLastZoneChangeCounter = bottomHalfCard.getZoneChangeCounter(game);
                returnValue = true;
            }
            return returnValue;
        }
    }

    @Override
    public boolean putOntoBattlefield(Game game, Zone fromZone, UUID sourceId, UUID controllerId, boolean tapped, boolean facedown, ArrayList<UUID> appliedEffects) {
        // Initial move to battlefield
        if (this.isMelded()) {
            ZoneChangeEvent event = new ZoneChangeEvent(this.objectId, sourceId, controllerId, Zone.EXILED, Zone.BATTLEFIELD, appliedEffects);
            if (!game.replaceEvent(event) && event.getToZone() == Zone.BATTLEFIELD) {
                updateZoneChangeCounter(game);
                PermanentMeld permanent = new PermanentMeld(this, event.getPlayerId(), game); // controller can be replaced (e.g. Gather Specimens)
                game.addPermanent(permanent);
                game.setZone(objectId, Zone.BATTLEFIELD);
                game.setScopeRelevant(true);
                game.applyEffects();
                boolean entered = permanent.entersBattlefield(sourceId, game, event.getFromZone(), true);
                game.setScopeRelevant(false);
                game.applyEffects();
                if (entered) {
                    if (tapped) {
                        permanent.setTapped(true);
                    }
                    event.setTarget(permanent);
                } else {
                    return false;
                }
                game.setZone(objectId, event.getToZone());
                game.addSimultaneousEvent(event);
                game.getExile().removeCard(this.topHalfCard, game);
                game.getExile().removeCard(this.bottomHalfCard, game);
                return true;
            } else {
                this.setMelded(false);
                return false;
            }
        } else {
            // Try to move the former meld cards after it has already left the battlefield.
            // If the meld parts didn't move from that zone, move them instead of the meld card.
            // Reset the local zcc so the meld card lose track of them.
            boolean returnValue = false;
            if (topLastZoneChangeCounter == topHalfCard.getZoneChangeCounter(game)) {
                topHalfCard.moveToZone(Zone.BATTLEFIELD, sourceId, game, tapped, appliedEffects);
                topLastZoneChangeCounter = topHalfCard.getZoneChangeCounter(game);
                returnValue = true;
            }
            if (bottomLastZoneChangeCounter == bottomHalfCard.getZoneChangeCounter(game)) {
                bottomHalfCard.moveToZone(Zone.BATTLEFIELD, sourceId, game, tapped, appliedEffects);
                bottomLastZoneChangeCounter = bottomHalfCard.getZoneChangeCounter(game);
                returnValue = true;
            }
            return returnValue;
        }
    }

    @Override
    public void setOwnerId(UUID ownerId) {
        super.setOwnerId(ownerId);
        abilities.setControllerId(ownerId);
    }

    @Override
    public int getConvertedManaCost() {
        if (this.isCopy()) {
            return 0;
        } else {
            return (this.topHalfCard != null ? this.topHalfCard.getConvertedManaCost() : 0)
                    + (this.bottomHalfCard != null ? this.bottomHalfCard.getConvertedManaCost() : 0);
        }
    }

    @Override
    public boolean addCounters(Counter counter, Game game, ArrayList<UUID> appliedEffects) {
        if (this.isMelded()) {
            return super.addCounters(counter, game, appliedEffects);
        } else {
            // can this really happen?
            boolean returnState = true;
            if (topLastZoneChangeCounter == topHalfCard.getZoneChangeCounter(game)) {
                returnState |= topHalfCard.addCounters(counter, game, appliedEffects);
            }
            if (bottomLastZoneChangeCounter == bottomHalfCard.getZoneChangeCounter(game)) {
                returnState |= bottomHalfCard.addCounters(counter, game, appliedEffects);
            }
            return returnState;
        }
    }
}
