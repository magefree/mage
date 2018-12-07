package mage.cards;

import mage.MageObject;
import mage.abilities.Abilities;
import mage.abilities.AbilitiesImpl;
import mage.abilities.Ability;
import mage.abilities.SpellAbility;
import mage.constants.CardType;
import mage.constants.SpellAbilityType;
import mage.constants.Zone;
import mage.game.Game;

import java.util.ArrayList;
import java.util.List;
import java.util.UUID;

/**
 * @author LevelX2
 */
public abstract class SplitCard extends CardImpl {

    protected Card leftHalfCard;
    protected Card rightHalfCard;

    public SplitCard(UUID ownerId, CardSetInfo setInfo, CardType[] cardTypes, String costsLeft, String costsRight, SpellAbilityType spellAbilityType) {
        this(ownerId, setInfo, cardTypes, cardTypes, costsLeft, costsRight, spellAbilityType);
    }

    public SplitCard(UUID ownerId, CardSetInfo setInfo, CardType[] typesLeft, CardType[] typesRight, String costsLeft, String costsRight, SpellAbilityType spellAbilityType) {
        super(ownerId, setInfo, CardType.mergeTypes(typesLeft, typesRight), costsLeft + costsRight, spellAbilityType);
        String[] names = setInfo.getName().split(" // ");
        leftHalfCard = new SplitCardHalfImpl(this.getOwnerId(), new CardSetInfo(names[0], setInfo.getExpansionSetCode(), setInfo.getCardNumber(), setInfo.getRarity(), setInfo.getGraphicInfo()), typesLeft, costsLeft, this, SpellAbilityType.SPLIT_LEFT);
        rightHalfCard = new SplitCardHalfImpl(this.getOwnerId(), new CardSetInfo(names[1], setInfo.getExpansionSetCode(), setInfo.getCardNumber(), setInfo.getRarity(), setInfo.getGraphicInfo()), typesRight, costsRight, this, SpellAbilityType.SPLIT_RIGHT);
        this.splitCard = true;
    }

    public SplitCard(SplitCard card) {
        super(card);
        this.leftHalfCard = card.getLeftHalfCard().copy();
        ((SplitCardHalf) leftHalfCard).setParentCard(this);
        this.rightHalfCard = card.rightHalfCard.copy();
        ((SplitCardHalf) rightHalfCard).setParentCard(this);
    }

    public SplitCardHalf getLeftHalfCard() {
        return (SplitCardHalf) leftHalfCard;
    }

    public SplitCardHalf getRightHalfCard() {
        return (SplitCardHalf) rightHalfCard;
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
    public boolean cast(Game game, Zone fromZone, SpellAbility ability, UUID controllerId) {
        switch (ability.getSpellAbilityType()) {
            case SPLIT_LEFT:
                return this.getLeftHalfCard().cast(game, fromZone, ability, controllerId);
            case SPLIT_RIGHT:
                return this.getRightHalfCard().cast(game, fromZone, ability, controllerId);
            default:
                this.getLeftHalfCard().getSpellAbility().setControllerId(controllerId);
                this.getRightHalfCard().getSpellAbility().setControllerId(controllerId);
                return super.cast(game, fromZone, ability, controllerId);
        }
    }

    @Override
    public void setZone(Zone zone, Game game) {
        super.setZone(zone, game);
        game.setZone(getLeftHalfCard().getId(), zone);
        game.setZone(getRightHalfCard().getId(), zone);
    }

    @Override
    public Abilities<Ability> getAbilities() {
        Abilities<Ability> allAbilites = new AbilitiesImpl<>();
        for (Ability ability : super.getAbilities()) {
            if (ability instanceof SpellAbility
                    && ((SpellAbility) ability).getSpellAbilityType() != SpellAbilityType.SPLIT
                    && ((SpellAbility) ability).getSpellAbilityType() != SpellAbilityType.SPLIT_AFTERMATH) {
                allAbilites.add(ability);
            }
        }
        allAbilites.addAll(leftHalfCard.getAbilities());
        allAbilites.addAll(rightHalfCard.getAbilities());
        return allAbilites;
    }

    /**
     * Currently only gets the fuse SpellAbility if there is one, but generally
     * gets any abilities on a split card as a whole, and not on either half
     * individually.
     *
     * @return
     */
    public Abilities<Ability> getSharedAbilities() {
        return super.getAbilities();
    }

    @Override
    public Abilities<Ability> getAbilities(Game game) {
        Abilities<Ability> allAbilites = new AbilitiesImpl<>();
        for (Ability ability : super.getAbilities(game)) {
            if (ability instanceof SpellAbility
                    && ((SpellAbility) ability).getSpellAbilityType() != SpellAbilityType.SPLIT
                    && ((SpellAbility) ability).getSpellAbilityType() != SpellAbilityType.SPLIT_AFTERMATH) {
                allAbilites.add(ability);
            }
        }
        allAbilites.addAll(leftHalfCard.getAbilities(game));
        allAbilites.addAll(rightHalfCard.getAbilities(game));
        return allAbilites;
    }

    @Override
    public List<String> getRules() {
        List<String> rules = new ArrayList<>();
        if (getSpellAbility().getSpellAbilityType() == SpellAbilityType.SPLIT_FUSED) {
            rules.add("--------------------------------------------------------------------------\nFuse (You may cast one or both halves of this card from your hand.)");
        }
        return rules;
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

}
