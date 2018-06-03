
package mage.cards;

import mage.abilities.Ability;
import mage.constants.CardType;
import mage.constants.Zone;
import mage.counters.Counter;
import mage.game.Game;
import mage.game.events.ZoneChangeEvent;
import mage.game.permanent.Permanent;

import java.util.List;
import java.util.UUID;

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

    public MeldCard(UUID ownerId, CardSetInfo setInfo, CardType[] cardTypes, String costs) {
        super(ownerId, setInfo, cardTypes, costs);
        halves = new CardsImpl();
    }

    public MeldCard(final MeldCard card) {
        super(card);
        this.topHalfCard = card.topHalfCard;
        this.bottomHalfCard = card.bottomHalfCard;
        this.topLastZoneChangeCounter = card.topLastZoneChangeCounter;
        this.bottomLastZoneChangeCounter = card.bottomLastZoneChangeCounter;
        this.halves = new CardsImpl(card.halves);
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
    public boolean addCounters(Counter counter, Ability source, Game game, List<UUID> appliedEffects) {
        if (this.isMelded()) {
            return super.addCounters(counter, source, game, appliedEffects);
        } else {
            // can this really happen?
            boolean returnState = true;
            if (hasTopHalf(game)) {
                returnState |= topHalfCard.addCounters(counter, source, game, appliedEffects);
            }
            if (hasBottomHalf(game)) {
                returnState |= bottomHalfCard.addCounters(counter, source, game, appliedEffects);
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
        boolean topRemoved = hasTopHalf(game) && topHalfCard.removeFromZone(game, fromZone, sourceId);
        if (!topRemoved) {
            // The top half isn't being moved with the pair anymore.
            halves.remove(topHalfCard);
        }
        boolean bottomRemoved = hasBottomHalf(game) && bottomHalfCard.removeFromZone(game, fromZone, sourceId);
        if (!bottomRemoved) {
            // The bottom half isn't being moved with the pair anymore.
            halves.remove(bottomHalfCard);
        }
        return topRemoved || bottomRemoved;
    }

    @Override
    public void updateZoneChangeCounter(Game game, ZoneChangeEvent event) {
        if (isCopy() || !isMelded()) {
            super.updateZoneChangeCounter(game, event);
            return;
        }
        game.getState().updateZoneChangeCounter(objectId);
        if (topLastZoneChangeCounter == topHalfCard.getZoneChangeCounter(game)
                && halves.contains(topHalfCard.getId())) {
            topHalfCard.updateZoneChangeCounter(game, event);
            topLastZoneChangeCounter = topHalfCard.getZoneChangeCounter(game);
        }
        if (bottomLastZoneChangeCounter == bottomHalfCard.getZoneChangeCounter(game)
                && halves.contains(bottomHalfCard.getId())) {
            bottomHalfCard.updateZoneChangeCounter(game, event);
            bottomLastZoneChangeCounter = bottomHalfCard.getZoneChangeCounter(game);
        }
    }

    public Cards getHalves() {
        return halves;
    }

}
