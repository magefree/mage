package mage.cards;

import mage.MageInt;
import mage.constants.CardType;
import mage.constants.SpellAbilityType;
import mage.constants.SubType;
import mage.constants.Zone;
import mage.game.Game;

import java.util.Arrays;
import java.util.List;
import java.util.UUID;

/**
 * @author JayDi85
 */
public class ModalDoubleFacesCardHalfImpl extends CardImpl implements ModalDoubleFacesCardHalf {

    ModalDoubleFacesCard parentCard;

    public ModalDoubleFacesCardHalfImpl(UUID ownerId, CardSetInfo setInfo, CardType[] cardTypes, SubType[] cardSubTypes,
                                        String costs, ModalDoubleFacesCard parentCard, SpellAbilityType spellAbilityType) {
        super(ownerId, setInfo, cardTypes, costs, spellAbilityType);
        this.subtype.addAll(Arrays.asList(cardSubTypes));
        this.parentCard = parentCard;
    }

    public ModalDoubleFacesCardHalfImpl(final ModalDoubleFacesCardHalfImpl card) {
        super(card);
        this.parentCard = card.parentCard;
    }

    @Override
    public UUID getOwnerId() {
        return parentCard.getOwnerId();
    }

    @Override
    public String getImageName() {
        // TODO: own name?
        return parentCard.getImageName();
    }

    @Override
    public String getExpansionSetCode() {
        // TODO: own set code?
        return parentCard.getExpansionSetCode();
    }

    @Override
    public String getCardNumber() {
        // TODO: own card number?
        return parentCard.getCardNumber();
    }

    @Override
    public boolean moveToZone(Zone toZone, UUID sourceId, Game game, boolean flag, List<UUID> appliedEffects) {
        return parentCard.moveToZone(toZone, sourceId, game, flag, appliedEffects);
    }

    @Override
    public boolean moveToExile(UUID exileId, String name, UUID sourceId, Game game, List<UUID> appliedEffects) {
        return parentCard.moveToExile(exileId, name, sourceId, game, appliedEffects);
    }

    @Override
    public boolean removeFromZone(Game game, Zone fromZone, UUID sourceId) {
        return parentCard.removeFromZone(game, fromZone, sourceId);
    }

    @Override
    public ModalDoubleFacesCard getMainCard() {
        return parentCard;
    }

    @Override
    public void setZone(Zone zone, Game game) {
        game.setZone(parentCard.getId(), zone);
        game.setZone(parentCard.getLeftHalfCard().getId(), zone);
        game.setZone(parentCard.getRightHalfCard().getId(), zone);
    }

    @Override
    public ModalDoubleFacesCardHalfImpl copy() {
        return new ModalDoubleFacesCardHalfImpl(this);
    }

    @Override
    public void setParentCard(ModalDoubleFacesCard card) {
        this.parentCard = card;
    }

    @Override
    public ModalDoubleFacesCard getParentCard() {
        return this.parentCard;
    }

    @Override
    public void setPT(MageInt power, MageInt toughness) {
        this.power = power;
        this.toughness = toughness;
    }
}
