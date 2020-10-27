package mage.cards;

import mage.MageInt;
import mage.MageObject;
import mage.ObjectColor;
import mage.abilities.Abilities;
import mage.abilities.AbilitiesImpl;
import mage.abilities.Ability;
import mage.abilities.SpellAbility;
import mage.abilities.costs.mana.ManaCost;
import mage.abilities.costs.mana.ManaCosts;
import mage.constants.*;
import mage.game.Game;
import mage.game.events.ZoneChangeEvent;
import mage.util.SubTypeList;

import java.util.ArrayList;
import java.util.EnumSet;
import java.util.List;
import java.util.UUID;

/**
 * @author JayDi85
 */
public abstract class ModalDoubleFacesCard extends CardImpl {

    protected Card leftHalfCard; // main card in all zone
    protected Card rightHalfCard; // second side card, can be only in stack and battlefield zones

    public ModalDoubleFacesCard(UUID ownerId, CardSetInfo setInfo,
                                CardType[] typesLeft, SubType[] subTypesLeft, String costsLeft,
                                String secondSideName, CardType[] typesRight, SubType[] subTypesRight, String costsRight) {
        super(ownerId, setInfo, typesLeft, costsLeft + costsRight, SpellAbilityType.MODAL);
        // main card name must be same as left side
        leftHalfCard = new ModalDoubleFacesCardHalfImpl(this.getOwnerId(), new CardSetInfo(setInfo.getName(), setInfo.getExpansionSetCode(), setInfo.getCardNumber(), setInfo.getRarity(), setInfo.getGraphicInfo()),
                typesLeft, subTypesLeft, costsLeft, this, SpellAbilityType.MODAL_LEFT);
        rightHalfCard = new ModalDoubleFacesCardHalfImpl(this.getOwnerId(), new CardSetInfo(secondSideName, setInfo.getExpansionSetCode(), setInfo.getCardNumber(), setInfo.getRarity(), setInfo.getGraphicInfo()),
                typesRight, subTypesRight, costsRight, this, SpellAbilityType.MODAL_RIGHT);
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
            game.getState().setZone(leftHalfCard.getId(), toZone);
            game.getState().setZone(rightHalfCard.getId(), toZone);
            return true;
        }
        return false;
    }

    @Override
    public void setZone(Zone zone, Game game) {
        super.setZone(zone, game);
        game.setZone(leftHalfCard.getId(), zone);
        game.setZone(rightHalfCard.getId(), zone);
    }

    @Override
    public boolean moveToExile(UUID exileId, String name, UUID sourceId, Game game, List<UUID> appliedEffects) {
        if (super.moveToExile(exileId, name, sourceId, game, appliedEffects)) {
            Zone currentZone = game.getState().getZone(getId());
            game.getState().setZone(leftHalfCard.getId(), currentZone);
            game.getState().setZone(rightHalfCard.getId(), currentZone);
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
        leftHalfCard.updateZoneChangeCounter(game, event);
        rightHalfCard.updateZoneChangeCounter(game, event);
    }

    @Override
    public boolean cast(Game game, Zone fromZone, SpellAbility ability, UUID controllerId) {
        switch (ability.getSpellAbilityType()) {
            case MODAL_LEFT:
                return this.leftHalfCard.cast(game, fromZone, ability, controllerId);
            case MODAL_RIGHT:
                return this.rightHalfCard.cast(game, fromZone, ability, controllerId);
            default:
                if (this.leftHalfCard.getSpellAbility() != null)
                    this.leftHalfCard.getSpellAbility().setControllerId(controllerId);
                if (this.rightHalfCard.getSpellAbility() != null)
                    this.rightHalfCard.getSpellAbility().setControllerId(controllerId);
                return super.cast(game, fromZone, ability, controllerId);
        }
    }


    @Override
    public ArrayList<CardType> getCardType() {
        // CardImpl's constructor can call some code on init, so you must check left/right before
        // it's a bad workaround
        return leftHalfCard != null ? leftHalfCard.getCardType() : cardType;
    }

    @Override
    public SubTypeList getSubtype(Game game) {
        // rules: While a double-faced card isn’t on the stack or battlefield, consider only the characteristics of its front face.

        // CardImpl's constructor can call some code on init, so you must check left/right before
        return leftHalfCard != null ? leftHalfCard.getSubtype(game) : subtype;
    }

    @Override
    public boolean hasSubtype(SubType subtype, Game game) {
        return leftHalfCard.hasSubtype(subtype, game);
    }

    @Override
    public EnumSet<SuperType> getSuperType() {
        return EnumSet.noneOf(SuperType.class);
    }

    @Override
    public Abilities<Ability> getAbilities() {
        Abilities<Ability> allAbilites = new AbilitiesImpl<>();

        // ignore default spell ability from main card (only halfes are actual)
        for (Ability ability : super.getAbilities()) {
            if (ability instanceof SpellAbility && ((SpellAbility) ability).getSpellAbilityType() == SpellAbilityType.MODAL) {
                continue;
            }
            allAbilites.add(ability);
        }

        allAbilites.addAll(super.getAbilities());
        allAbilites.addAll(leftHalfCard.getAbilities());
        allAbilites.addAll(rightHalfCard.getAbilities());
        return allAbilites;
    }

    public Abilities<Ability> getSharedAbilities(Game game) {
        // no shared abilities for mdf cards (e.g. must be left or right only)
        return new AbilitiesImpl<>();
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
    public boolean hasAbility(Ability ability, Game game) {
        return super.hasAbility(ability, game);
    }

    @Override
    public ObjectColor getColor(Game game) {
        return leftHalfCard.getColor(game);
    }

    @Override
    public ObjectColor getFrameColor(Game game) {
        return leftHalfCard.getFrameColor(game);
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
    public ManaCosts<ManaCost> getManaCost() {
        return leftHalfCard.getManaCost();
    }

    @Override
    public int getConvertedManaCost() {
        // Rules:
        // The converted mana cost of a modal double-faced card is based on the characteristics of the
        // face that’s being considered. On the stack and battlefield, consider whichever face is up.
        // In all other zones, consider only the front face. This is different than how the converted
        // mana cost of a transforming double-faced card is determined.

        // on stack or battlefield it must be half card with own cost
        return leftHalfCard.getConvertedManaCost();
    }

    @Override
    public MageInt getPower() {
        return leftHalfCard.getPower();
    }

    @Override
    public MageInt getToughness() {
        return leftHalfCard.getToughness();
    }
}
