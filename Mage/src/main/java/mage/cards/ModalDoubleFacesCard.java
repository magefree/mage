package mage.cards;

import mage.MageObject;
import mage.abilities.Abilities;
import mage.abilities.AbilitiesImpl;
import mage.abilities.Ability;
import mage.abilities.SpellAbility;
import mage.constants.CardType;
import mage.constants.SpellAbilityType;
import mage.constants.SubType;
import mage.constants.Zone;
import mage.game.Game;
import mage.game.events.ZoneChangeEvent;

import java.util.ArrayList;
import java.util.List;
import java.util.UUID;

/**
 * @author JayDi85
 */
public abstract class ModalDoubleFacesCard extends CardImpl {

    protected Card leftHalfCard;
    protected Card rightHalfCard;

    public ModalDoubleFacesCard(UUID ownerId, CardSetInfo setInfo,
                                CardType[] typesLeft, SubType[] subTypesLeft, String costsLeft,
                                String secondSideName, CardType[] typesRight, SubType[] subTypesRight, String costsRight) {
        super(ownerId, setInfo, typesLeft, costsLeft + costsRight, SpellAbilityType.MODAL);
        // main card name must be same as left side
        leftHalfCard = new ModalDoubleFacesCardHalfImpl(this.getOwnerId(), new CardSetInfo(setInfo.getName(), setInfo.getExpansionSetCode(), setInfo.getCardNumber(), setInfo.getRarity(), setInfo.getGraphicInfo()),
                typesLeft, subTypesLeft, costsLeft, this, SpellAbilityType.MODAL_LEFT);
        rightHalfCard = new ModalDoubleFacesCardHalfImpl(this.getOwnerId(), new CardSetInfo(secondSideName, setInfo.getExpansionSetCode(), setInfo.getCardNumber(), setInfo.getRarity(), setInfo.getGraphicInfo()),
                typesRight, subTypesRight, costsRight, this, SpellAbilityType.MODAL_RIGHT);
        this.modalDFC = true;
    }

    public ModalDoubleFacesCard(ModalDoubleFacesCard card) {
        super(card);
        this.leftHalfCard = card.getLeftHalfCard().copy();
        ((ModalDoubleFacesCardHalf) leftHalfCard).setParentCard(this);
        this.rightHalfCard = card.rightHalfCard.copy();
        ((ModalDoubleFacesCardHalf) rightHalfCard).setParentCard(this);
    }

    public ModalDoubleFacesCardHalf getLeftHalfCard() {
        return (ModalDoubleFacesCardHalf) leftHalfCard;
    }

    public ModalDoubleFacesCardHalf getRightHalfCard() {
        return (ModalDoubleFacesCardHalf) rightHalfCard;
    }

    @Override
    public void assignNewId() {
        super.assignNewId();
        leftHalfCard.assignNewId();
        rightHalfCard.assignNewId();
    }

    @Override
    public void setCopy(boolean isCopy, MageObject copiedFrom) {
        super.setCopy(isCopy, copiedFrom);
        leftHalfCard.setCopy(isCopy, copiedFrom);
        rightHalfCard.setCopy(isCopy, copiedFrom);
    }

    @Override
    public boolean moveToZone(Zone toZone, UUID sourceId, Game game, boolean flag, List<UUID> appliedEffects) {
        if (super.moveToZone(toZone, sourceId, game, flag, appliedEffects)) {
            game.getState().setZone(getLeftHalfCard().getId(), toZone);
            game.getState().setZone(getRightHalfCard().getId(), toZone);
            return true;
        }
        return false;
    }

    @Override
    public void setZone(Zone zone, Game game) {
        super.setZone(zone, game);
        game.setZone(getLeftHalfCard().getId(), zone);
        game.setZone(getRightHalfCard().getId(), zone);
    }

    @Override
    public boolean moveToExile(UUID exileId, String name, UUID sourceId, Game game, List<UUID> appliedEffects) {
        if (super.moveToExile(exileId, name, sourceId, game, appliedEffects)) {
            Zone currentZone = game.getState().getZone(getId());
            game.getState().setZone(getLeftHalfCard().getId(), currentZone);
            game.getState().setZone(getRightHalfCard().getId(), currentZone);
            return true;
        }
        return false;
    }

    @Override
    public boolean removeFromZone(Game game, Zone fromZone, UUID sourceId) {
        // zone contains only one main card
        return super.removeFromZone(game, fromZone, sourceId);
    }

    @Override
    public void updateZoneChangeCounter(Game game, ZoneChangeEvent event) {
        if (isCopy()) { // same as meld cards
            super.updateZoneChangeCounter(game, event);
            return;
        }
        super.updateZoneChangeCounter(game, event);
        getLeftHalfCard().updateZoneChangeCounter(game, event);
        getRightHalfCard().updateZoneChangeCounter(game, event);
    }

    @Override
    public boolean cast(Game game, Zone fromZone, SpellAbility ability, UUID controllerId) {
        switch (ability.getSpellAbilityType()) {
            case MODAL_LEFT:
                return this.getLeftHalfCard().cast(game, fromZone, ability, controllerId);
            case MODAL_RIGHT:
                return this.getRightHalfCard().cast(game, fromZone, ability, controllerId);
            default:
                this.getLeftHalfCard().getSpellAbility().setControllerId(controllerId);
                this.getRightHalfCard().getSpellAbility().setControllerId(controllerId);
                return super.cast(game, fromZone, ability, controllerId);
        }
    }

    @Override
    public Abilities<Ability> getAbilities() {
        Abilities<Ability> allAbilites = new AbilitiesImpl<>();
        allAbilites.addAll(super.getAbilities());
        allAbilites.addAll(leftHalfCard.getAbilities());
        allAbilites.addAll(rightHalfCard.getAbilities());
        return allAbilites;
    }

    public Abilities<Ability> getSharedAbilities(Game game) {
        return super.getAbilities(game);
    }

    @Override
    public Abilities<Ability> getAbilities(Game game) {
        Abilities<Ability> allAbilites = new AbilitiesImpl<>();

        // ignore default spell ability from main card (only halfes are actual)
        for (Ability ability : super.getAbilities(game)) {
            if (ability instanceof SpellAbility && ((SpellAbility) ability).getSpellAbilityType() == SpellAbilityType.MODAL) {
                continue;
            }
            allAbilites.add(ability);
        }

        allAbilites.addAll(leftHalfCard.getAbilities(game));
        allAbilites.addAll(rightHalfCard.getAbilities(game));
        return allAbilites;
    }

    @Override
    public List<String> getRules() {
        return new ArrayList<>();
    }

    @Override
    public void setOwnerId(UUID ownerId) {
        super.setOwnerId(ownerId);
        abilities.setControllerId(ownerId);
        leftHalfCard.getAbilities().setControllerId(ownerId);
        leftHalfCard.setOwnerId(ownerId);
        rightHalfCard.getAbilities().setControllerId(ownerId);
        rightHalfCard.setOwnerId(ownerId);
    }

    @Override
    public int getConvertedManaCost() {
        // Rules:
        // The converted mana cost of a modal double-faced card is based on the characteristics of the
        // face that’s being considered. On the stack and battlefield, consider whichever face is up.
        // In all other zones, consider only the front face. This is different than how the converted
        // mana cost of a transforming double-faced card is determined.

        // on stack or battlefield it must be half card with own cost
        return getLeftHalfCard().getConvertedManaCost();
    }
}
