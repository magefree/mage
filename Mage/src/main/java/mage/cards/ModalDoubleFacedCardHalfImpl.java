package mage.cards;

import mage.MageInt;
import mage.abilities.Ability;
import mage.constants.*;
import mage.game.Game;

import java.util.Arrays;
import java.util.List;
import java.util.UUID;

/**
 * @author JayDi85
 */
public class ModalDoubleFacedCardHalfImpl extends CardImpl implements ModalDoubleFacedCardHalf {

    ModalDoubleFacedCard parentCard;

    public ModalDoubleFacedCardHalfImpl(
            UUID ownerId, CardSetInfo setInfo,
            SuperType[] cardSuperTypes, CardType[] cardTypes, SubType[] cardSubTypes,
            String costs, ModalDoubleFacedCard parentCard, SpellAbilityType spellAbilityType
    ) {
        super(ownerId, setInfo, cardTypes, costs, spellAbilityType);
        this.supertype.addAll(Arrays.asList(cardSuperTypes));
        this.subtype.addAll(Arrays.asList(cardSubTypes));
        this.parentCard = parentCard;
    }

    protected ModalDoubleFacedCardHalfImpl(final ModalDoubleFacedCardHalfImpl card) {
        super(card);
        this.parentCard = card.parentCard;
    }

    @Override
    public UUID getOwnerId() {
        return parentCard.getOwnerId();
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
    public boolean moveToZone(Zone toZone, Ability source, Game game, boolean flag, List<UUID> appliedEffects) {
        return parentCard.moveToZone(toZone, source, game, flag, appliedEffects);
    }

    @Override
    public boolean moveToExile(UUID exileId, String name, Ability source, Game game, List<UUID> appliedEffects) {
        return parentCard.moveToExile(exileId, name, source, game, appliedEffects);
    }

    @Override
    public boolean removeFromZone(Game game, Zone fromZone, Ability source) {
        return parentCard.removeFromZone(game, fromZone, source);
    }

    @Override
    public ModalDoubleFacedCard getMainCard() {
        return parentCard;
    }

    @Override
    public void setZone(Zone zone, Game game) {
        // see ModalDoubleFacedCard.checkGoodZones for details
        game.setZone(parentCard.getId(), zone);
        game.setZone(this.getId(), zone);

        // find another side to sync
        ModalDoubleFacedCardHalf otherSide;
        if (!parentCard.getLeftHalfCard().getId().equals(this.getId())) {
            otherSide = parentCard.getLeftHalfCard();
        } else if (!parentCard.getRightHalfCard().getId().equals(this.getId())) {
            otherSide = parentCard.getRightHalfCard();
        } else {
            throw new IllegalStateException("Wrong code usage: MDF halves must use different ids");
        }

        switch (zone) {
            case STACK:
            case BATTLEFIELD:
                // stack and battlefield must have only one side
                game.setZone(otherSide.getId(), Zone.OUTSIDE);
                break;
            default:
                game.setZone(otherSide.getId(), zone);
                break;
        }

        ModalDoubleFacedCard.checkGoodZones(game, parentCard);
    }

    @Override
    public ModalDoubleFacedCardHalfImpl copy() {
        return new ModalDoubleFacedCardHalfImpl(this);
    }

    @Override
    public void setParentCard(ModalDoubleFacedCard card) {
        this.parentCard = card;
    }

    @Override
    public ModalDoubleFacedCard getParentCard() {
        return this.parentCard;
    }

    @Override
    public void setPT(int power, int toughness) {
        this.setPT(new MageInt(power), new MageInt(toughness));
    }

    @Override
    public void setPT(MageInt power, MageInt toughness) {
        this.power = power;
        this.toughness = toughness;
    }

    @Override
    public String getIdName() {
        // id must send to main card (popup card hint in game logs)
        return getName() + " [" + parentCard.getId().toString().substring(0, 3) + ']';
    }
}
