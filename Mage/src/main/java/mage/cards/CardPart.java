package mage.cards;

import mage.abilities.Ability;
import mage.constants.CardType;
import mage.constants.SpellAbilityType;
import mage.constants.Zone;
import mage.game.Game;
import mage.game.events.ZoneChangeEvent;

import java.util.List;
import java.util.UUID;

/**
 * Base class for card parts (halves of split cards, modal double-faced cards, etc.).
 * Each part references its parent card.
 *
 * @param <C> the type of the parent card
 * @author JayDi85, Jmlundeen
 */
public abstract class CardPart<C extends CardWithPartsImpl<?, C>> extends CardImpl implements SubCard<C> {

    protected C parentCard;

    protected CardPart(UUID ownerId, CardSetInfo setInfo, CardType[] cardTypes, String costs,
                       C parentCard, SpellAbilityType spellAbilityType) {
        super(ownerId, setInfo, cardTypes, costs, spellAbilityType);
        this.parentCard = parentCard;
    }

    protected CardPart(final CardPart<C> card) {
        super(card);
        this.parentCard = card.parentCard;
    }

    @Override
    public UUID getOwnerId() {
        return getParentCard().getOwnerId();
    }

    @Override
    public String getExpansionSetCode() {
        return getParentCard().getExpansionSetCode();
    }

    @Override
    public String getCardNumber() {
        return getParentCard().getCardNumber();
    }

    @Override
    public void updateZoneChangeCounter(Game game, ZoneChangeEvent event) {
        getParentCard().updateZoneChangeCounter(game, event);
    }

    @Override
    public boolean moveToZone(Zone toZone, Ability source, Game game, boolean flag, List<UUID> appliedEffects) {
        return getParentCard().moveToZone(toZone, source, game, flag, appliedEffects);
    }

    @Override
    public boolean moveToExile(UUID exileId, String name, Ability source, Game game, List<UUID> appliedEffects) {
        return getParentCard().moveToExile(exileId, name, source, game, appliedEffects);
    }

    @Override
    public boolean removeFromZone(Game game, Zone fromZone, Ability source) {
        return getParentCard().removeFromZone(game, fromZone, source);
    }

    @Override
    public C getMainCard() {
        return parentCard;
    }

    public Card getOtherSide() {
        Card otherSide;
        if (!getParentCard().getLeftHalfCard().getId().equals(this.getId())) {
            otherSide = getParentCard().getLeftHalfCard();
        } else if (!getParentCard().getRightHalfCard().getId().equals(this.getId())) {
            otherSide = getParentCard().getRightHalfCard();
        } else {
            throw new IllegalStateException("Wrong code usage: Card halves must use different ids");
        }
        return otherSide;
    }

    @Override
    public void setZone(Zone zone, Game game) {
        game.setZone(getParentCard().getId(), zone);
        game.setZone(getParentCard().getLeftHalfCard().getId(), zone);
        game.setZone(getParentCard().getRightHalfCard().getId(), zone);
        getParentCard().checkGoodZones(game);
    }

    @Override
    public void setParentCard(C card) {
        this.parentCard = card;
    }

    @Override
    public C getParentCard() {
        return parentCard;
    }

    @Override
    public String getIdName() {
        // id must send to main card (popup card hint in game logs)
        return getName() + " [" + getParentCard().getId().toString().substring(0, 3) + ']';
    }
}

