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
import mage.counters.Counter;
import mage.game.Game;
import mage.game.events.ZoneChangeEvent;
import mage.game.permanent.Permanent;
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
    protected Cards halves;

    public MeldCard(UUID ownerId, int cardNumber, String name, Rarity rarity, CardType[] cardTypes, String costs) {
        super(ownerId, cardNumber, name, rarity, cardTypes, costs);
        halves = new CardsImpl();
    }

    public MeldCard(MeldCard card) {
        super(card);
        this.topHalfCard = card.topHalfCard;
        this.bottomHalfCard = card.bottomHalfCard;
        this.topLastZoneChangeCounter = card.topLastZoneChangeCounter;
        this.bottomLastZoneChangeCounter = card.bottomLastZoneChangeCounter;
        this.halves = new CardsImpl(halves);
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
        halves.add(topHalfCard.getId());
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

    public void setBottomHalfCard(Card bottomHalfCard, Game game) {
        this.bottomHalfCard = bottomHalfCard;
        this.bottomLastZoneChangeCounter = bottomHalfCard.getZoneChangeCounter(game);
        halves.add(bottomHalfCard.getId());
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
            if (hasTopHalf(game)) {
                returnState |= topHalfCard.addCounters(counter, game, appliedEffects);
            }
            if (hasBottomHalf(game)) {
                returnState |= bottomHalfCard.addCounters(counter, game, appliedEffects);
            }
            return returnState;
        }
    }

    public boolean hasTopHalf(Game game) {
        boolean value = topLastZoneChangeCounter == topHalfCard.getZoneChangeCounter(game)
                && halves.contains(topHalfCard.getId());
        if (!value) {
            halves.remove(topHalfCard);
        }
        return value;
    }

    public boolean hasBottomHalf(Game game) {
        boolean value = bottomLastZoneChangeCounter == bottomHalfCard.getZoneChangeCounter(game)
                && halves.contains(bottomHalfCard.getId());
        if (!value) {
            halves.remove(bottomHalfCard);
        }
        return value;
    }

    @Override
    public boolean removeFromZone(Game game, Zone fromZone, UUID sourceId) {
        if (isCopy()) {
            return super.removeFromZone(game, fromZone, sourceId);
        }
        if (isMelded() && fromZone == Zone.BATTLEFIELD) {
            Permanent permanent = game.getPermanent(objectId);
            return permanent != null && permanent.removeFromZone(game, fromZone, sourceId);
        }
        boolean  topRemoved = hasTopHalf(game) && topHalfCard.removeFromZone(game, fromZone, sourceId);
        if (!topRemoved) {
            // The top half isn't being moved with the pair anymore.
            halves.remove(topHalfCard);
        }
        boolean  bottomRemoved = hasBottomHalf(game) && bottomHalfCard.removeFromZone(game, fromZone, sourceId);
        if (!bottomRemoved) {
            // The bottom half isn't being moved with the pair anymore.
            halves.remove(bottomHalfCard);
        }
        return topRemoved || bottomRemoved;
    }

    @Override
    public void updateZoneChangeCounter(Game game) {
        if (isCopy() || !isMelded()) {
            super.updateZoneChangeCounter(game);
            return;
        }
        game.getState().updateZoneChangeCounter(objectId);
        if (topLastZoneChangeCounter == topHalfCard.getZoneChangeCounter(game)
                && halves.contains(topHalfCard.getId())) {
            topHalfCard.updateZoneChangeCounter(game);
            topLastZoneChangeCounter = topHalfCard.getZoneChangeCounter(game);
        }
        if (bottomLastZoneChangeCounter == bottomHalfCard.getZoneChangeCounter(game)
                && halves.contains(bottomHalfCard.getId())) {
            bottomHalfCard.updateZoneChangeCounter(game);
            bottomLastZoneChangeCounter = bottomHalfCard.getZoneChangeCounter(game);
        }
    }

    public Cards getHalves() {
        return halves;
    }
}
