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
import mage.game.events.ZoneChangeEvent;
import mage.util.CardUtil;

import java.util.ArrayList;
import java.util.List;
import java.util.UUID;

/**
 * @author LevelX2
 */
public abstract class SplitCard extends CardImpl implements CardWithHalves {

    static public String FUSE_RULE = "Fuse (You may cast both halves from your hand.)";

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
    }

    public SplitCard(SplitCard card) {
        super(card);
        this.leftHalfCard = card.getLeftHalfCard().copy();
        ((SplitCardHalf) leftHalfCard).setParentCard(this);
        this.rightHalfCard = card.rightHalfCard.copy();
        ((SplitCardHalf) rightHalfCard).setParentCard(this);
    }

    public void setParts(SplitCardHalf leftHalfCard, SplitCardHalf rightHalfCard) {
        // for card copy only - set new parts
        this.leftHalfCard = leftHalfCard;
        leftHalfCard.setParentCard(this);
        this.rightHalfCard = rightHalfCard;
        rightHalfCard.setParentCard(this);
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
    public boolean moveToZone(Zone toZone, Ability source, Game game, boolean flag, List<UUID> appliedEffects) {
        if (super.moveToZone(toZone, source, game, flag, appliedEffects)) {
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
    public boolean moveToExile(UUID exileId, String name, Ability source, Game game, List<UUID> appliedEffects) {
        if (super.moveToExile(exileId, name, source, game, appliedEffects)) {
            Zone currentZone = game.getState().getZone(getId());
            game.getState().setZone(getLeftHalfCard().getId(), currentZone);
            game.getState().setZone(getRightHalfCard().getId(), currentZone);
            return true;
        }
        return false;
    }

    @Override
    public boolean removeFromZone(Game game, Zone fromZone, Ability source) {
        // zone contains only one main card
        return super.removeFromZone(game, fromZone, source);
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
    public Abilities<Ability> getAbilities() {
        Abilities<Ability> allAbilites = new AbilitiesImpl<>();
        for (Ability ability : super.getAbilities()) {
            // ignore split abilities
            // TODO: why it here, for GUI's cleanup in card texts? Maybe it can be removed, see mdf cards
            if (ability instanceof SpellAbility
                    && (((SpellAbility) ability).getSpellAbilityType() == SpellAbilityType.SPLIT
                    || ((SpellAbility) ability).getSpellAbilityType() == SpellAbilityType.SPLIT_AFTERMATH)) {
                continue;
            }
            allAbilites.add(ability);
        }
        allAbilites.addAll(leftHalfCard.getAbilities());
        allAbilites.addAll(rightHalfCard.getAbilities());
        return allAbilites;
    }

    @Override
    public Abilities<Ability> getInitAbilities() {
        // must init only full split card aiblities like fuse, parts must be init separately
        return super.getAbilities();
    }

    /**
     * Currently only gets the fuse SpellAbility if there is one, but generally
     * gets any abilities on a split card as a whole, and not on either half
     * individually.
     *
     * @return
     */
    public Abilities<Ability> getSharedAbilities(Game game) {
        return super.getAbilities(game);
    }

    @Override
    public Abilities<Ability> getAbilities(Game game) {
        Abilities<Ability> allAbilites = new AbilitiesImpl<>();
        for (Ability ability : super.getAbilities(game)) {
            // ignore split abilities
            // TODO: why it here, for GUI's cleanup in card texts? Maybe it can be removed, see mdf cards
            if (ability instanceof SpellAbility
                    && (((SpellAbility) ability).getSpellAbilityType() == SpellAbilityType.SPLIT
                    || ((SpellAbility) ability).getSpellAbilityType() == SpellAbilityType.SPLIT_AFTERMATH)) {
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
        Abilities<Ability> sourceAbilities = this.getAbilities();
        List<String> res = CardUtil.getCardRulesWithAdditionalInfo(
                this.getId(),
                this.getName(),
                sourceAbilities,
                sourceAbilities
        );
        if (getSpellAbility().getSpellAbilityType() == SpellAbilityType.SPLIT_FUSED) {
            res.add("--------------------------------------------------------------------------\n" + FUSE_RULE);
        }
        return res;
    }

    @Override
    public List<String> getRules(Game game) {
        Abilities<Ability> sourceAbilities = this.getAbilities(game);
        List<String> res = CardUtil.getCardRulesWithAdditionalInfo(
                game,
                this.getId(),
                this.getName(),
                sourceAbilities,
                sourceAbilities
        );
        if (getSpellAbility().getSpellAbilityType() == SpellAbilityType.SPLIT_FUSED) {
            res.add("--------------------------------------------------------------------------\n" + FUSE_RULE);
        }
        return res;
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
    public int getManaValue() {
        // 202.3d The converted mana cost of a split card not on the stack or of a fused split spell on the
        // stack is determined from the combined mana costs of its halves. Otherwise, while a split card is
        // on the stack, the converted mana cost of the spell is determined by the mana cost of the half
        // that was chosen to be cast. See rule 708, “Split Cards.”

        // split card and it's halfes contains own mana costs, so no need to rewrite logic
        return super.getManaValue();
    }
}
